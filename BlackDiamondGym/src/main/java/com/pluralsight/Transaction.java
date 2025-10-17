package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private LocalDate date;
    private LocalTime time;
    private String type;
    private String description;
    private String vendor;
    private double amount;

    // ANSI escape codes for colors (for console output only)
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    // Constructor with all fields
    public Transaction(LocalDate date, LocalTime time, String type, double amount, String description, String vendor) {
        this.date = date;
        this.time = time;
        this.type = type.trim().toUpperCase();
        this.amount = amount;
        this.description = description;
        this.vendor = vendor;
    }

    // Default constructor
    public Transaction() {}

    // ---------- Getters ----------
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public String getVendor() { return vendor; }
    public double getAmount() { return amount; }

    // ---------- Setters ----------
    public void setDate(LocalDate date) { this.date = date; }
    public void setTime(LocalTime time) { this.time = time; }
    public void setType(String type) { this.type = type.trim().toUpperCase(); }
    public void setDescription(String description) { this.description = description; }
    public void setVendor(String vendor) { this.vendor = vendor; }
    public void setAmount(double amount) { this.amount = amount; }

    @Override
    public String toString() {
        // Format date and time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String formattedDate = date.format(dateFormatter);
        String formattedTime = time.format(timeFormatter);

        // Format amount with two decimals and color it:
        // - Red if negative (payment)
        // - Green if positive (deposit)
        String formattedAmount = String.format("%.2f", amount);
        if (amount < 0) {
            formattedAmount = RED + formattedAmount + RESET;
        } else {
            formattedAmount = GREEN + formattedAmount + RESET;
        }

        // Build a clean, human-readable transaction line
        return String.format("%s | %s | %-8s | %-25s | %-15s | %s",
                formattedDate,
                formattedTime,
                type,
                description,
                vendor,
                formattedAmount
        );
    }

    /*
     This method returns a plain CSV-formatted string (no colors)
      for writing to a .csv file.
     */
    public String toCsvString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return String.format("%s|%s|%s|%s|%s|%.2f",
                date.format(dateFormatter),
                time.format(timeFormatter),
                type,
                description,
                vendor,
                amount
        );
    }
}
