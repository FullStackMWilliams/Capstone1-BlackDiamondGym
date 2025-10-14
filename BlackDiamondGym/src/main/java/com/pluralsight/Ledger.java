package com.pluralsight;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.stream.Collectors;


public class Ledger {
    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getTransactions() {
        return transactions;
        }

    public void setTransactions(List<Transaction> list) {
       transactions.clear();
       if (list != null) transactions.addAll(list);
    }
    public void addDeposit (double amount, String description, String vendor) {
        Transaction t = new Transaction(LocalDate.now(), "DEPOSIT", amount, description, vendor);
        transactions.add(t);
    }
    public void addPayment (double amount, String description, String vendor) {
        Transaction t = new Transaction(LocalDate.now(),"PAYMENT", amount, description, vendor);
        transactions.add(t);
    }
    public List<Transaction> all() {
        return new ArrayList<>(transactions);
    }
    public List<Transaction> deposits() {
        return transactions.stream()
                .filter(t -> "DEPOSIT".equalsIgnoreCase(t.getType()))
                .collect(Collectors.toList());
    }
    public List<Transaction> payments() {
        return transactions.stream()
                .filter(t -> "PAYMENT".equalsIgnoreCase(t.getType()))
                .collect(Collectors.toList());
    }
    public List<Transaction> byVendor(String vendor) {
        String v = vendor.toLowerCase();
        return transactions.stream()
                .filter(t -> t.getVendor() != null && t.getVendor().toLowerCase().contains(v))
                .collect(Collectors.toList());
    }
    public List<Transaction> byDateRange (LocalDate start, LocalDate end) {
        return transactions.stream()
                .filter(t -> (t.getDate().isEqual(start) || t.getDate().isAfter(start)) && (t.getDate().isEqual(end) || t.getDate().isBefore(end)))
                .collect(Collectors.toList());
    }
    // Capstone Accounting ledger code
    public List<Transaction> monthToDate() {
        YearMonth ym = YearMonth.now();
        LocalDate start = ym.atDay(1);
        LocalDate end = LocalDate.now();
        return byDateRange(start,end);
    }
    public List<Transaction> previousMonth() {
        YearMonth ym = YearMonth.now().minusMonths(1);
        return byDateRange(ym.atDay(1), ym.atEndOfMonth());
    }

    public List<Transaction> yearToDate() {
        Year y = Year.now();
        LocalDate start = y.atDay(1);
        LocalDate end = LocalDate.now();
        return byDateRange(start,end);
    }
    public List<Transaction> previousYear() {
        Year y = Year.now().minusYears(1);
        return byDateRange(y.atDay(1), y.atMonth(12).atEndOfMonth());
    }
}