package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDate date;
    private String type;
    private double amount;
    private String description;
    private String vendor;
    private LocalTime time;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");


    public Transaction() {

    }
    public Transaction(LocalDate date, LocalTime time, String type, double amount, String description, String vendor) {
        this.date = date;
        this.time = time;
        this.type = type != null ? type.trim().toUpperCase() : "";
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

    public LocalTime getTime() { return time; }

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

    public void setTime(LocalTime time) { this.time = time; }

    @Override
    public String toString() {
        String formattedDate = date != null ? date.format(DATE_TIME_FORMATTER) : "";
        String formattedTime = time != null ? time.format(TIME_FORMATTER) : "";
        String formattedType = type != null ? type.toUpperCase() : "";
        String formattedDescription = description != null ? description : "";
        String formattedVendor = vendor != null ? vendor : "";
        String formattedAmount = String.format("%.2f", amount);

        return formattedDate + "|" +
                formattedTime + "|" +
                formattedType + "|" +
                formattedDescription + "|" +
                formattedVendor + "|" +
                formattedAmount;
    }
}
