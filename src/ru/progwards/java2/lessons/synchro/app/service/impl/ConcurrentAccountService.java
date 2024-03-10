package ru.progwards.java2.lessons.synchro.app.service.impl;

import ru.progwards.java2.lessons.synchro.app.model.Account;
import ru.progwards.java2.lessons.synchro.app.service.AccountService;
import ru.progwards.java2.lessons.synchro.app.service.StoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentAccountService implements AccountService {


    private StoreService service;

    public ConcurrentAccountService() {

    }

    public ConcurrentAccountService(StoreService service) {
        this.service = service;
    }

    @Override
    public double balance(Account account) {
        return account.getAmount();
    }

    Lock serviceLock = new ReentrantLock();

    @Override
    public void deposit(Account account, double amount) {

        serviceLock.lock();
        double sum = account.getAmount() + amount;
        account.setAmount(sum);
        service.update(account);
        serviceLock.unlock();
    }

    @Override
    public void withdraw(Account account, double amount) {
        try {
            serviceLock.lock();
            double sum = account.getAmount() - amount;
            if (sum < 0) throw new RuntimeException("Not enough money");
            account.setAmount(sum);
            service.update(account);
        } finally {
            serviceLock.unlock();
        }
    }

    @Override
    public void transfer(Account from, Account to, double amount) {
        try {
            serviceLock.lock();
            double fromSum = from.getAmount() - amount;
            double toSum = to.getAmount() + amount;
            if (fromSum < 0) throw new RuntimeException("Not enough money");
            from.setAmount(fromSum);
            service.update(from);
            to.setAmount(toSum);
            service.update(to);
        } finally {
            serviceLock.unlock();
        }
    }

    public static void main(String[] args) {
        FileStoreService fileStoreService = new FileStoreService();
        AccountService accountService = new ConcurrentAccountService(fileStoreService);
        List<Account> accounts = new ArrayList<>(fileStoreService.get());
        List<String> idList = new ArrayList<>();
        for (Account acc : accounts) {
            idList.add(acc.getId());
        }

        Lock lock = new ReentrantLock();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    for (int k = 0; k < 23; k++) {

                        Account account1 = fileStoreService.get(idList.get((int) (Math.random() * 10)));
                        Account account2 = fileStoreService.get(idList.get((int) (Math.random() * 10)));
                        lock.lock();
                        double diff0 = accountService.balance(account1) - accountService.balance(account2);
                        accountService.deposit(account1, k % 7);
                        accountService.withdraw(account1, k % 7);
                        accountService.deposit(account2, k % 5);
                        accountService.withdraw(account2, k % 5);
                        double diff1 = accountService.balance(account1) - accountService.balance(account2);
                        lock.unlock();

                        if (Math.abs(diff0 - diff1) > 0.01d)
                            System.out.println("OMG! diff = " + (diff0 - diff1) + ", " + threadName);
                    }
                }
            });
            thread.start();
        }
    }
}
