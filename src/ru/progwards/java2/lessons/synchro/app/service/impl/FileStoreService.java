package ru.progwards.java2.lessons.synchro.app.service.impl;

import ru.progwards.java2.lessons.synchro.app.Store;
import ru.progwards.java2.lessons.synchro.app.model.Account;
import ru.progwards.java2.lessons.synchro.app.service.StoreService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class FileStoreService implements StoreService {
    private File storeFile;

    public FileStoreService() {
        storeFile = setStoreFile();
    }

    @Override
    public Account get(String id) {
        Account account = null;
        try (FileReader reader = new FileReader(storeFile);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(id)) account = accountFromString(line);
            }
            if (account == null) throw new RuntimeException("Account not found by id:" + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Collection<Account> get() {
        Collection<Account> accounts = new ArrayList<>();
        try (FileReader reader = new FileReader(storeFile);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) accounts.add(accountFromString(scanner.nextLine()));
            if (accounts.isEmpty()) throw new RuntimeException("Store is empty");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void delete(String id) {
        Path tmpStoreFilePath = Paths.get("tmpTestFile.csv");
        boolean found = false;
        try (FileWriter writer = new FileWriter(tmpStoreFilePath.toString());
             FileReader reader = new FileReader(storeFile);
             Scanner scanner = new Scanner(reader)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(id)) {
                    found = true;
                    continue;
                }
                writer.write(line + "\n");
            }
            if (!found) throw new RuntimeException("Account not found by id:" + id);
            Files.copy(tmpStoreFilePath, storeFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.delete(tmpStoreFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Account account) {
        try (FileReader reader = new FileReader(storeFile);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(account.getId())) delete(account.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(storeFile, true)) {
            writer.write(accountToString(account));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account account) {
        boolean found = false;
        try (FileReader reader = new FileReader(storeFile);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(account.getId())) {
                    found = true;
                    insert(account);
                }
            }
            if (!found) throw new RuntimeException("Account not found by id:" + account.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File setStoreFile() {
        return Store.getStoreFile();
    }

    @Override
    public Account accountFromString(String line) {
        // Исключения: строка null, ошибка формата строки, ошибки парсинга
        Account acc = new Account();
        String[] accArr = line.split("[\\n,]");
        acc.setId(accArr[0]);
        acc.setHolder(accArr[1]);
        acc.setDate(new Date(Long.parseLong(accArr[2])));
        acc.setPin(Integer.parseInt(accArr[3]));
        acc.setAmount(Double.parseDouble(accArr[4]));
        return acc;
    }

    @Override
    public String accountToString(Account account) {
        return Store.accToString(account);
    }


    public static void main(String[] args) {
        FileStoreService fss = new FileStoreService();
        Account account = fss.accountFromString("e939ab7c-1ac5-4801-b160-bb858e3ea426,Account_2,1740081925963,1002,162519.45510791955\n");
        System.out.println();
    }
}
