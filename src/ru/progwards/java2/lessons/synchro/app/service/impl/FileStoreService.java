package ru.progwards.java2.lessons.synchro.app.service.impl;

import ru.progwards.java2.lessons.synchro.app.Store;
import ru.progwards.java2.lessons.synchro.app.model.Account;
import ru.progwards.java2.lessons.synchro.app.service.StoreService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileStoreService implements StoreService {
    private File storeFile;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public FileStoreService() {
        storeFile = setStoreFile();
    }

    @Override
    public Account get(String id) {
        readWriteLock.readLock().lock();
        Account account = null;
        try (FileReader reader = new FileReader(storeFile);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(id)) {
                    account = accountFromString(line);
                    break;
                }
            }
            if (account == null) throw new RuntimeException("Account not found by id:" + id);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return account;
    }

    @Override
    public Collection<Account> get() {
        readWriteLock.readLock().lock();
        Collection<Account> accounts = new ArrayList<>();
        try (FileReader reader = new FileReader(storeFile);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) accounts.add(accountFromString(scanner.nextLine()));
            if (accounts.isEmpty()) throw new RuntimeException("Store is empty");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }

        return accounts;
    }

    @Override
    public void delete(String id) {
        readWriteLock.writeLock().lock();
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
            writer.close();
            reader.close();
            if (!found) throw new RuntimeException("Account not found by id:" + id);
            Files.copy(tmpStoreFilePath, storeFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.delete(tmpStoreFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void insert(Account account) {
        readWriteLock.readLock().lock();
        String id = account.getId();
        try (FileReader reader = new FileReader(storeFile);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(id)) {
                    reader.close();
                    readWriteLock.readLock().unlock();
                    delete(account.getId());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        readWriteLock.writeLock().lock();
        try (FileWriter writer = new FileWriter(storeFile, true)) {
            writer.write(accountToString(account));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void update(Account account) {
        readWriteLock.readLock().lock();
        boolean found = false;
        try (FileReader reader = new FileReader(storeFile);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(account.getId())) {
                    found = true;
                    reader.close();
                    readWriteLock.readLock().unlock();
                    insert(account);
                    break;
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
        Account acc = new Account();
        try {
            String[] accArr = line.split("[\\n,]");
            if (accArr.length != 5) throw new RuntimeException("Wrong account line format.");
            acc.setId(accArr[0]);
            acc.setHolder(accArr[1]);
            acc.setDate(new Date(Long.parseLong(accArr[2])));
            acc.setPin(Integer.parseInt(accArr[3]));
            acc.setAmount(Double.parseDouble(accArr[4]));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return acc;
    }

    @Override
    public String accountToString(Account account) {
        return Store.accToString(account);
    }


    public static void main(String[] args) {
        FileStoreService fss = new FileStoreService();
        List<Account> accounts = new ArrayList<>(fss.get());
        List<String> idList = new ArrayList<>();
        for (Account acc : accounts) {
            idList.add(acc.getId());
        }
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Account account1 = fss.get(idList.get((int) (Math.random() * 10)));
                    fss.update(account1);
                    System.out.println(account1);
                }
            });
            thread.start();
        }

    }
}
