package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*
 * FileManager
 * ----------
 *  - users.csv         -> username,password,role
 *  - memberships.csv   -> username,plan,(optional addOns),(optional status)
 *  - transactions.csv  -> yyyy-MM-dd|HH:mm:ss|TYPE|DESCRIPTION|VENDOR|AMOUNT
 * Also includes a polished console display for transactions:
 *  - Black header
 *  - Green deposit amounts (positive)
 *  - Red payment amounts (negative)
 *  - Sorted by newest first
 */
public class FileManager {

    // ---- Filenames (kept simple, same folder as the app) ----
    private static final String USER_FILE = "users.csv";
    private static final String MEMBERSHIP_FILE = "memberships.csv";
    private static final String TRANSACTION_FILE = "transactions.csv";

    // ---- Date/time formats used everywhere for consistency ----
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    // ---- ANSI colors for console output (no dependency on Colors.java) ----
    private static final String RESET = "\u001B[0m";
    private static final String BLACK = "\u001B[30m";
    private static final String WHITE_BRIGHT = "\u001B[97m";
    private static final String BLACK_BG = "\u001B[40m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    // =========================================================
    // Ensure CSV files exist (create empty files if missing)
    // =========================================================
    public static void ensureFiles() {
        createIfMissing(USER_FILE);
        createIfMissing(MEMBERSHIP_FILE);
        createIfMissing(TRANSACTION_FILE);
    }

    private static void createIfMissing(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists()) {
                if (f.createNewFile()) {
                    System.out.println("[INFO] Created file: " + filename);
                }
            }
        } catch (IOException e) {
            System.out.println(RED + "[ERROR] Could not create " + filename + ": " + e.getMessage() + RESET);
        }
    }

    // =========================================================
    // USERS  (username,password,role)
    // =========================================================
    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String role = parts[2].trim();
                    users.add(new User(username, password, role));
                }
            }
        } catch (IOException e) {
            System.out.println(RED + "[ERROR] Reading users: " + e.getMessage() + RESET);
        }
        return users;
    }

    public static void writeUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USER_FILE))) {
            for (User u : users) {
                pw.println(u.getUsername() + "," + u.getPassword() + "," + u.getRole());
            }
        } catch (IOException e) {
            System.out.println(RED + "[ERROR] Writing users: " + e.getMessage() + RESET);
        }
    }

    // =========================================================
    // MEMBERSHIPS  (username,plan[,addOns][,status])
    // =========================================================
    public static List<Membership> readMembership() {
        // Keep existing method name to match your codebase
        return readMemberships();
    }

    public static List<Membership> readMemberships() {
        List<Membership> memberships = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBERSHIP_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                // Minimum: username, plan
                if (parts.length >= 2) {
                    String username = parts[0].trim();
                    String planText = parts[1].trim();
                    Membership.Plan plan;
                    try {
                        plan = Membership.Plan.valueOf(planText.toUpperCase());
                    } catch (Exception ex) {
                        // Fallback: if file contains just a string, your Membership(String,String) should handle it
                        // If not, default to BASIC
                        plan = Membership.Plan.BASIC;
                    }

                    List<String> addOns = new ArrayList<>();
                    if (parts.length >= 3 && !parts[2].trim().isEmpty()) {
                        addOns = Membership.parseAddOns(parts[2].trim());
                    }

                    Membership m = new Membership(username, plan, addOns);
                    if (parts.length >= 4) {
                        if ("CANCELED".equalsIgnoreCase(parts[3].trim())) {
                            m.cancel();
                        }
                    }
                    memberships.add(m);
                }
            }
        } catch (IOException e) {
            System.out.println(RED + "[ERROR] Reading memberships: " + e.getMessage() + RESET);
        }
        return memberships;
    }

    public static void writeMembership(List<Membership> memberships) {
        // Keep existing method name to match your codebase
        writeMemberships(memberships);
    }

    public static void writeMemberships(List<Membership> memberships) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(MEMBERSHIP_FILE))) {
            for (Membership m : memberships) {
                // username,PLAN,addOn1;addOn2;...,STATUS
                pw.println(
                        m.getUsername() + "," +
                                m.getPlan().name() + "," +
                                Membership.addOnsToString(m.getAddOns()) + "," +
                                m.getStatus()
                );
            }
        } catch (IOException e) {
            System.out.println(RED + "[ERROR] Writing memberships: " + e.getMessage() + RESET);
        }
    }

    // =========================================================
    // TRANSACTIONS  (date|time|type|description|vendor|amount)
    // =========================================================
    public static List<Transaction> readTransactions() {
        List<Transaction> txns = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split("\\|");
                if (parts.length < 6) {
                    // skip malformed line
                    continue;
                }

                try {
                    LocalDate date = LocalDate.parse(parts[0].trim(), DATE_FMT);

                    // Strip any fractional seconds if an old row had them
                    String timeText = parts[1].trim();
                    int dot = timeText.indexOf('.');
                    if (dot > -1) timeText = timeText.substring(0, dot);
                    LocalTime time = LocalTime.parse(timeText, TIME_FMT);

                    String type = parts[2].trim().toUpperCase();
                    String description = parts[3].trim();
                    String vendor = parts[4].trim();

                    // Sanitize amount: remove anything not digit, dot, minus
                    String cleanAmount = parts[5].trim().replaceAll("[^0-9.-]", "");
                    double amount = Double.parseDouble(cleanAmount);

                    // Construct a Transaction using your full constructor
                    Transaction t = new Transaction(date, time, type, amount, description, vendor);
                    txns.add(t);
                } catch (Exception ex) {
                    System.out.println(RED + "[WARN] Skipping bad line in transactions.csv: " + line + RESET);
                }
            }
        } catch (IOException e) {
            System.out.println(RED + "[ERROR] Reading transactions: " + e.getMessage() + RESET);
        }
        return txns;
    }

    public static void writeTransactions(List<Transaction> transactions) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TRANSACTION_FILE))) {
            for (Transaction t : transactions) {
                // Write a clean CSV line (no ANSI, no spaces)
                pw.printf(
                        "%s|%s|%s|%s|%s|%.2f%n",
                        t.getDate().format(DATE_FMT),
                        t.getTime().format(TIME_FMT),
                        t.getType().toUpperCase(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount()
                );
            }
        } catch (IOException e) {
            System.out.println(RED + "[ERROR] Writing transactions: " + e.getMessage() + RESET);
        }
    }

    // =========================================================
    // Display transactions (black header, green deposits, red payments)
    // Sorted newest first by date/time
    // Call this from your Admin menu when the user chooses "View Transactions"
    // =========================================================
    public static void displayTransactions(List<Transaction> transactions) {
        // Sort newest first (date desc, then time desc)
        transactions.sort(
                Comparator
                        .comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed()
        );

        // Header (black background + white bright text)
        String header = String.format("%-12s | %-8s | %-8s | %-30s | %-18s | %10s",
                "Date", "Time", "Type", "Description", "Vendor", "Amount");
        System.out.println(BLACK_BG + WHITE_BRIGHT + header + RESET);

        // Ruler line (plain)
        System.out.println("----------------------------------------------------------------------------------------------");

        // Rows
        for (Transaction t : transactions) {
            String date = t.getDate().format(DATE_FMT);
            String time = t.getTime().format(TIME_FMT);
            String type = t.getType().toUpperCase();
            String description = t.getDescription();
            String vendor = t.getVendor();

            // Color/sign rules:
            //  - DEPOSIT = positive (green)
            //  - PAYMENT = negative (red)
            String amountText;
            if ("PAYMENT".equalsIgnoreCase(type)) {
                double neg = -Math.abs(t.getAmount());        // force negative for display
                amountText = RED + String.format("%10.2f", neg) + RESET;
            } else if ("DEPOSIT".equalsIgnoreCase(type)) {
                double pos = Math.abs(t.getAmount());         // force positive for display
                amountText = GREEN + String.format("%10.2f", pos) + RESET;
            } else {
                // Unknown type -> no color, just print as-is
                amountText = String.format("%10.2f", t.getAmount());
            }

            System.out.printf("%-12s | %-8s | %-8s | %-30s | %-18s | %s%n",
                    date, time, type, truncate(description, 30), truncate(vendor, 18), amountText);
        }
        System.out.println();
    }

    // Small helper to keep long text from breaking the table
    private static String truncate(String s, int max) {
        if (s == null) return "";
        if (s.length() <= max) return s;
        return s.substring(0, Math.max(0, max - 1)) + "…";
    }
}


