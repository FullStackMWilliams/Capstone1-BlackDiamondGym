package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;   // ✅ Fix: Import LocalTime
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ledger {
    private List<Transaction> transactions = new ArrayList<>();

    // ✅ Basic getters & setters
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    // ✅ Show all transactions
    public List<Transaction> all() {
        return transactions;
    }

    // ✅ Filter deposits (amount > 0)
    public List<Transaction> deposits() {
        return transactions.stream()
                .filter(t -> t.getAmount() > 0)
                .collect(Collectors.toList());
    }

    // ✅ Filter payments (amount < 0)
    public List<Transaction> payments() {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.toList());
    }

    // ✅ Transactions for the current month
    public List<Transaction> monthToDate() {
        LocalDate now = LocalDate.now();
        return transactions.stream()
                .filter(t -> t.getDate().getYear() == now.getYear() &&
                        t.getDate().getMonth() == now.getMonth())
                .collect(Collectors.toList());
    }

    // ✅ Transactions from previous month
    public List<Transaction> previousMonth() {
        LocalDate prev = LocalDate.now().minusMonths(1);
        return transactions.stream()
                .filter(t -> t.getDate().getYear() == prev.getYear() &&
                        t.getDate().getMonth() == prev.getMonth())
                .collect(Collectors.toList());
    }

    // ✅ Transactions for current year
    public List<Transaction> yearToDate() {
        int year = LocalDate.now().getYear();
        return transactions.stream()
                .filter(t -> t.getDate().getYear() == year)
                .collect(Collectors.toList());
    }

    // ✅ Transactions for previous year
    public List<Transaction> previousYear() {
        int prevYear = LocalDate.now().getYear() - 1;
        return transactions.stream()
                .filter(t -> t.getDate().getYear() == prevYear)
                .collect(Collectors.toList());
    }

    // ✅ Filter transactions by vendor
    public List<Transaction> byVendor(String vendor) {
        return transactions.stream()
                .filter(t -> t.getVendor().equalsIgnoreCase(vendor))
                .collect(Collectors.toList());
    }

    // ✅ Filter by date range
    public List<Transaction> byDateRange(LocalDate start, LocalDate end) {
        return transactions.stream()
                .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
                .collect(Collectors.toList());
    }

    // ✅ Overloaded version that takes a Transaction directly
    public void addDeposit(Transaction transaction) {
        transaction.setType("DEPOSIT");
        transactions.add(transaction);
    }

    public void addPayment(Transaction transaction) {
        transaction.setType("PAYMENT");
        transactions.add(transaction);
    }

    // ✅ Overloaded version that takes amount, description, vendor
    public void addDeposit(double amount, String description, String vendor) {
        Transaction t = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                "DEPOSIT",
                amount,
                description,
                vendor
        );
        addDeposit(t);
    }

    public void addPayment(double amount, String description, String vendor) {
        Transaction t = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                "PAYMENT",
                amount,
                description,
                vendor
        );
        addPayment(t);
    }
}


