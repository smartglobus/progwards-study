package ru.progwards.java2.lessons.http.app.service.impl;

import ru.progwards.java2.lessons.http.app.model.Account;
import ru.progwards.java2.lessons.http.app.service.AccountService;
import ru.progwards.java2.lessons.http.app.service.StoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountServiceImpl implements AccountService {
    private StoreService service;

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
    public void withdraw(Account account, double amount) throws RuntimeException {
        serviceLock.lock();
        double sum = account.getAmount() - amount;
        if (sum < 0) {
            serviceLock.unlock();
            throw new RuntimeException("Error: account operation canceled. Not enough money.");
        }
        account.setAmount(sum);
        service.update(account);
        serviceLock.unlock();
    }

    @Override
    public void transfer(Account from, Account to, double amount) throws RuntimeException {
        serviceLock.lock();
        double fromSum = from.getAmount() - amount;
        double toSum = to.getAmount() + amount;
        if (fromSum < 0) {
            serviceLock.unlock();
            throw new RuntimeException("Error: account operation canceled. Not enough money.");
        }
        from.setAmount(fromSum);
        service.update(from);
        to.setAmount(toSum);
        service.update(to);
        serviceLock.unlock();
    }
}
