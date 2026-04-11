package com.knight.simulator.domain;

public class Account {
    private double balance;

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized para evitar bugs com muitas Threads
    public synchronized void deduct(double amount) {
        this.balance -= amount;
    }

    public synchronized void addFunds(double amount) {
        this.balance += amount;
    }

    public synchronized double getBalance() {
        return this.balance;
    }
}
