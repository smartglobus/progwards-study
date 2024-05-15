package ru.progwards.java2.lessons.http.app.service;

import ru.progwards.java2.lessons.http.app.model.Account;

import java.util.Collection;

public interface StoreService {
    public Account get(String id) throws RuntimeException;
    public Collection<Account> get() throws RuntimeException;
    public void delete(String id) throws RuntimeException;
    public void insert(Account account);
    public void update(Account account) throws RuntimeException;
}