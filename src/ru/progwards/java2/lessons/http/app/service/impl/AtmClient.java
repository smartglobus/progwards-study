package ru.progwards.java2.lessons.http.app.service.impl;

import ru.progwards.java2.lessons.http.app.model.Account;
import ru.progwards.java2.lessons.http.app.service.AccountService;
import ru.progwards.java2.lessons.http.app.service.StoreService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AtmClient implements AccountService {
    StoreService storeService;
    private int port;

    public AtmClient(StoreService storeService, int port) {
        this.storeService = storeService;
        this.port = port;
    }

    @Override
    public double balance(Account account) {
        String paramsAndValues = "accountID=" + account.getId();
        sendQuery(getQuery("balance?", paramsAndValues));
        return account.getAmount();
    }

    @Override
    public void deposit(Account account, double amount) {
        String paramsAndValues = "accountID=" + account.getId() + "&amount=" + amount;
        sendQuery(getQuery("deposit?", paramsAndValues));
    }

    @Override
    public void withdraw(Account account, double amount) {
        String paramsAndValues = "accountID=" + account.getId() + "&amount=" + amount;
        sendQuery(getQuery("withdraw?", paramsAndValues));
    }

    @Override
    public void transfer(Account from, Account to, double amount) {
        String paramsAndValues = "accountFromID=" + from.getId() + "&accountToID=" + to.getId() + "&amount=" + amount;
        sendQuery(getQuery("transfer?", paramsAndValues));
    }

    String getQuery(String method, String paramsAndValues) {
        final String GET = "GET /";
        final String HTTP = " HTTP/1.1";
        return GET + method + paramsAndValues + HTTP;
    }

    void sendQuery(String query) {
        try (Socket socket = new Socket("localhost", port);
             InputStream is = socket.getInputStream();
             OutputStream os = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(os, true);
             Scanner scanner = new Scanner(is)
        ) {
            writer.println(query);
            writer.println("hostname: localhost");
            writer.println();

            while (scanner.hasNextLine()) System.out.println(scanner.nextLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
