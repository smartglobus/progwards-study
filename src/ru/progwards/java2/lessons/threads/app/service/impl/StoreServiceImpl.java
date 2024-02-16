package ru.progwards.java2.lessons.threads.app.service.impl;

import ru.progwards.java2.lessons.threads.app.Store;
import ru.progwards.java2.lessons.threads.app.model.Account;
import ru.progwards.java2.lessons.threads.app.service.StoreService;

import java.util.Collection;

public class StoreServiceImpl implements StoreService {
    @Override
    public synchronized Account get(String id) {
        Account account = Store.getStore().get(id);
        if (account == null) {
            throw new RuntimeException("Account not found by id:" + id);
        }
        return account;
    }

    @Override
    public synchronized Collection<Account> get() {
        if (Store.getStore().size() == 0) {
            throw new RuntimeException("Store is empty");
        }
        return Store.getStore().values();
    }

    @Override
    public synchronized void delete(String id) {
        if (Store.getStore().get(id) == null) {
            throw new RuntimeException("Account not found by id:" + id);
        }
        Store.getStore().remove(id);
    }

    @Override
    public synchronized void insert(Account account) {
        Store.getStore().put(account.getId(), account);
    }

    @Override
    public synchronized void update(Account account) {
        if (Store.getStore().get(account.getId()) == null) {
            throw new RuntimeException("Account not found by id:" + account.getId());
        }
            this.insert(account);
    }
}