package ru.progwards.java2.lessons.synchro.app.service;

import ru.progwards.java2.lessons.synchro.app.model.Account;

import java.io.File;
import java.util.Collection;

public interface StoreService {
    Account get(String id);
    Collection<Account> get();
    void delete(String id);
    void insert(Account account);
    void update(Account account);

    File setStoreFile();
    Account accountFromString(String line);
    String accountToString(Account account);
}