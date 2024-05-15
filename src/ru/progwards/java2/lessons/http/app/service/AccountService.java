package ru.progwards.java2.lessons.http.app.service;

import ru.progwards.java2.lessons.http.app.model.Account;

public interface AccountService {

    public double balance(Account account);
    public void deposit(Account account, double amount);
    public void withdraw(Account account, double amount) throws RuntimeException;
    public void transfer(Account from, Account to, double amount) throws RuntimeException;

}