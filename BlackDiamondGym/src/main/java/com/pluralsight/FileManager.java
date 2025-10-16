package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.pluralsight.Colors.*;

public class FileManager {

    private static final String USER_FILE = "users.csv";
    private static final String MEMBERSHIP_FILE = "memberships.csv";
    private static final String TRANSACTION_FILE = "transactions.csv";

    // ✅ Make sure CSV files exist before use
    public static void ensureFiles() {
        try {
            new File(USER_FILE).createNewFile();
            new File(MEMBERSHIP_FILE).createNewFile();
            new File(TRANSACTION_FILE).createNewFile();
        } catch (IOException e) {
            System.out.println(RED + "Error creating files: " + e.getMessage() + RESET);
        }
    }

    // ✅ Read transactions from CSV
    public static List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 6) continue;

                LocalDate date = LocalDate.parse(parts[0].trim(), dateFormatter);
                LocalTime time = LocalTime.parse(parts[1].trim(), timeFormatter);
                String type = parts[2].trim();
                String description = parts[3].trim();
                String vendor = parts[4].trim();
                double amount = Double.parseDouble(parts[5].trim());

                transactions.add(new Transaction(date, time, type, amount, description, vendor));
            }
        } catch (IOException e) {
            System.out.println(RED + "Error reading transactions: " + e.getMessage() + RESET);
        }
        return transactions;
    }

    // ✅ Write transactions to CSV
    public static void writeTransactions(List<Transaction> transactions) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TRANSACTION_FILE))) {
            for (Transaction t : transactions) {
                pw.println(t.toString());
            }
        } catch (IOException e) {
            System.out.println(RED + "Error writing transactions: " + e.getMessage() + RESET);
        }
    }

    // ✅ Display transactions (Formatted output with color logic)
    public static void displayTransactions(List<Transaction> transactions) {
        clear();
        System.out.printf("%s%-15s%-12s%-12s%-20s%-25s%-15s%s%n",
                BLACK_BACKGROUND + WHITE_BRIGHT,
                "Date", "Time", "Type", "Vendor", "Description", "Amount", RESET);
        System.out.println("--------------------------------------------------------------------------");

        for (Transaction t : transactions) {
            String amountColor = t.getType().equalsIgnoreCase("PAYMENT") ? RED : GREEN;
            String formattedAmount = amountColor + String.format("%.2f", t.getAmount()) + RESET;

            System.out.printf("%-15s%-12s%-12s%-20s%-25s%-15s%n",
                    t.getDate(),
                    t.getTime(),
                    t.getType(),
                    t.getVendor(),
                    t.getDescription(),
                    formattedAmount
            );
        }
        System.out.println();
    }

    // ✅ Utility: clear console screen
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

