package ru.progwards.java2.lessons.threads.app.service.impl;

import ru.progwards.java2.lessons.threads.app.model.Account;
import ru.progwards.java2.lessons.threads.app.service.AccountService;
import ru.progwards.java2.lessons.threads.app.service.StoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountServiceImpl implements AccountService {


    private StoreService service;

    public AccountServiceImpl() {

    }

    public AccountServiceImpl(StoreService service) {
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
        serviceLock.lock();
        double sum = account.getAmount() - amount;
        if (sum < 0) {
            serviceLock.unlock();
            throw new RuntimeException("Not enough money");
        }
        account.setAmount(sum);
        service.update(account);
        serviceLock.unlock();
    }

    @Override
    public void transfer(Account from, Account to, double amount) {
        serviceLock.lock();
        double fromSum = from.getAmount() - amount;
        double toSum = to.getAmount() + amount;
        if (fromSum < 0) {
            serviceLock.unlock();
            throw new RuntimeException("Not enough money");
        }
        from.setAmount(fromSum);
        service.update(from);
        to.setAmount(toSum);
        service.update(to);
        serviceLock.unlock();
    }

    public static void main(String[] args) {
        StoreService storeService = new StoreServiceImpl();
        AccountService accountService = new AccountServiceImpl(storeService);
        List<Account> accounts = new ArrayList<>(storeService.get());
        Lock lock = new ReentrantLock();

        for (int i = 0; i < 5; i++) {
            long startTime = System.currentTimeMillis();
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + " started");
                    for (int k = 0; k < 100000; k++) {

                        Account account1 = accounts.get((int) (Math.random() * 10));
                        Account account2 = accounts.get((int) (Math.random() * 10));
                        lock.lock();
                        double diff0 = accountService.balance(account1) - accountService.balance(account2);
                        accountService.deposit(account1, k % 7);
                        accountService.withdraw(account1, k % 7);
                        accountService.deposit(account2, k % 11);
                        accountService.withdraw(account2, k % 11);
                        double diff1 = accountService.balance(account1) - accountService.balance(account2);
                        lock.unlock();

                        if (Math.abs(diff0 - diff1) > 0.01d)
                            System.out.println("OMG! diff = " + (diff0 - diff1) + ", " + threadName);
                    }
                    System.out.println(threadName + " finish time " + (System.currentTimeMillis() - startTime));
                }
            });
            thread.start();
        }
    }
}
