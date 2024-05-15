package ru.progwards.java2.lessons.http;

import ru.progwards.java2.lessons.http.app.model.Account;
import ru.progwards.java2.lessons.http.app.service.AccountService;
import ru.progwards.java2.lessons.http.app.service.StoreService;
import ru.progwards.java2.lessons.http.app.service.impl.AccountServiceImpl;
import ru.progwards.java2.lessons.http.app.service.impl.AtmClient;
import ru.progwards.java2.lessons.http.app.service.impl.StoreServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ATM {
    private StoreService storeService;
    private int port;

    public ATM(StoreService storeService, int port) {
        this.storeService = storeService;
        storeService.get();// инициализация списка аккаунтов
        this.port = port;
    }

    public static void main(String[] args) {
        StoreService storeService = new StoreServiceImpl();
        ATM atm = new ATM(storeService, 1973);
        AtmClient client = new AtmClient(storeService,atm.port);
        List<Account> accounts = new ArrayList<>(storeService.get());

        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket atmServer = new ServerSocket(atm.port)) {
                    while (true) {
                        Socket client = atmServer.accept();
                        AtmClientThread clientThread = new AtmClientThread(client, atm.storeService);
                        clientThread.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client.balance(accounts.get(3));
        client.deposit(accounts.get(3), 20000);
        client.withdraw(accounts.get(3),7000000);
        client.balance(accounts.get(5));
        client.transfer(accounts.get(3),accounts.get(5),50000);
    }
}

class AtmClientThread extends Thread {
    private Socket incoming;
    private AccountService accountService;
    private StoreService storeService;

    final static String BAD_REQUEST = "HTTP/1.1 400 Bad Request";
    final static String SUCCESS = "HTTP/1.1 200 OK";
    final static String CONTENT_TYPE = "Content-Type: text/html; charset=utf-8";
    final static String NOT_FOUND = "HTTP/1.1 404 Not Found";

    public AtmClientThread(Socket incoming, StoreService storeService) {
        this.storeService = storeService;
        this.incoming = incoming;
        this.accountService = new AccountServiceImpl(storeService);
    }

    void reply(int code, PrintWriter writer, String response, double balance) {
        switch (code) {
            case 200:
                writer.println(SUCCESS);
                break;
            case 400:
                writer.println(BAD_REQUEST);
                break;
            case 404:
                writer.println(NOT_FOUND);
        }
        writer.println(CONTENT_TYPE);
        String contentLength = "Content-Length: " + getContentLength(response, balance) + "\n";
        writer.println(contentLength);
        if (!"".equals(response))writer.println(response);
        if (balance >= 0) {
            String bal = "Balance: " + balance;
            writer.println(bal);
        }
        writer.println();
    }

    int getContentLength(String response, double balance) {
        String bal = balance < 0 ? "" : "Balance: " + balance + "\n";
        return (response + "\n" + bal).getBytes().length;
    }

    String response = "";
    double resultingBalance;

    @Override
    public void run() {
        try (InputStream is = incoming.getInputStream();
             OutputStream os = incoming.getOutputStream();
             Scanner scanner = new Scanner(is);
             PrintWriter writer = new PrintWriter(os, true)
        ) {
            Account account = null, accountFrom = null, accountTo = null;
            double amount = 0;
            Map<String, String> params = new HashMap<>();

            //формат запроса:
            //GET /resource?param1=value1&param2=value2 HTTP/1.1
            //hostname: localhost
            //пустая строка
            String line1 = scanner.nextLine();
            boolean wrongHost = !("hostname: localhost".equals(scanner.nextLine()) && "".equals(scanner.nextLine()));//??? 2 lines actually???
            String[] startLine = line1.split("\\s");

            // здесь проверка правильности формата запроса
            if (startLine.length != 3 || !"GET".equals(startLine[0]) || !startLine[1].contains("?") || wrongHost) {
                reply(400, writer, "400 Bad Request", -1);
                return;
            }

            // здесь проверка правильности формата запроса методов и параметров
            String methodAndParams = startLine[1];
            int idx = startLine[1].indexOf("?");
            String[] paramsAndValues = startLine[1].substring(idx + 1).split("&");
            for (String s : paramsAndValues) {
                String[] pv = s.split("=");
                if (pv.length != 2) {
                    reply(400, writer, "400 Bad Request", -1);
                    return;
                } else params.put(pv[0], pv[1]);
            }

            // здесь проверка корректности ID аккаунтов и парсинга баланса
            String errMsg;
            for (String s : params.keySet()) {
                try {
                    switch (s) {
                        case "accountID":
                            account = storeService.get(params.get(s));
                            break;
                        case "accountFromID":
                            accountFrom = storeService.get(params.get(s));
                            break;
                        case "accountToID":
                            accountTo = storeService.get(params.get(s));
                            break;
                        case "amount":
                            amount = Double.parseDouble(params.get(s));
                            break;
                    }
                } catch (RuntimeException e) {
                    errMsg = e.getMessage();
                    reply(404, writer, errMsg, -1);
                    return;
                }
            }

            // здесь проверка достаточности средств на балансе
            if (methodAndParams.contains("balance") && account != null) {
                resultingBalance = accountService.balance(account);
            } else if (methodAndParams.contains("deposit") && account != null && amount != 0) {
                accountService.deposit(account, amount);
                resultingBalance = accountService.balance(account);
                response = "Deposit: successful";
            } else if (methodAndParams.contains("withdraw") && account != null && amount != 0) {
                try {
                    accountService.withdraw(account, amount);
                } catch (RuntimeException e) {
                    errMsg = e.getMessage();
                    reply(400, writer, errMsg, accountService.balance(account));
                    return;
                }
                resultingBalance = accountService.balance(account);
                response = "Withdraw: successful";
            } else if (methodAndParams.contains("transfer") && accountFrom != null && accountTo != null && amount != 0) {
                try {
                    accountService.transfer(accountFrom, accountTo, amount);
                } catch (RuntimeException e) {
                    errMsg = e.getMessage();
                    reply(400, writer, errMsg, accountService.balance(accountFrom));
                    return;
                }
                resultingBalance = accountService.balance(accountFrom);
                response = "Transfer: successful";
            } else {
                reply(404, writer, "Wrong parameters", -1);
                return;
            }

            reply(200, writer, response, resultingBalance);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
