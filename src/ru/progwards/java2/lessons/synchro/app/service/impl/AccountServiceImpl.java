package ru.progwards.java2.lessons.synchro.app.service.impl;

import ru.progwards.java2.lessons.synchro.app.model.Account;
import ru.progwards.java2.lessons.synchro.app.service.AccountService;
import ru.progwards.java2.lessons.synchro.app.service.StoreService;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountServiceImpl implements AccountService {


    private StoreService service;

    public AccountServiceImpl(){

    }

    public AccountServiceImpl(StoreService service){
        this.service = service;
    }

    @Override
    public double balance(Account account) {
        return account.getAmount();
    }

    @Override
    public void deposit(Account account, double amount) {
        Lock lock = new ReentrantLock();
        lock.lock();
        double sum = account.getAmount() + amount;
        account.setAmount(sum);
        service.update(account);
        lock.unlock();
    }

    @Override
    public void withdraw(Account account, double amount) {
        Lock lock = new ReentrantLock();
        lock.lock();
        double sum = account.getAmount() - amount;
        if(sum < 0){
            lock.unlock();
            throw new RuntimeException("Not enough money");
        }
        account.setAmount(sum);
        service.update(account);
        lock.unlock();

    }

    @Override
    public void transfer(Account from, Account to, double amount) {
        Lock lock = new ReentrantLock();
        lock.lock();
        double fromSum = from.getAmount() - amount;
        double toSum = to.getAmount() + amount;
        if(fromSum < 0 ){
            lock.unlock();
            throw new RuntimeException("Not enough money");
        }
        from.setAmount(fromSum);
        service.update(from);
        to.setAmount(toSum);
        service.update(to);
        lock.unlock();
    }

}
