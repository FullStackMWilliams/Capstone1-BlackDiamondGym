package com.pluralsight;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private String type;
    private double amount;
    private String description;
    private String vendor;

    public Transaction() {

    }
    public Transaction(LocalDate date, String type, double amount, String description, String vendor) {
        this.date = date;
        this.type = type.trim().toUpperCase();
        this.amount = amount;
        this.description = description;
        this.vendor = vendor;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    @Override
    public String toString() {
        return date + " | " + type + " | $" + String.format("%.2f", amount) + " | " + vendor + " | " + description;
    }
}
