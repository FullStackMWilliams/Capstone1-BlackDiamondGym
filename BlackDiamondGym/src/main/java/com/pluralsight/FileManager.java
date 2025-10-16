package com.pluralsight;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static final String USERS_FILE = "users.csv";
    public static final String MEMBERS_FILE = "members.csv";
    public static final String TRANSACTIONS_FILE = "transactions.csv";

    public static void ensureFiles() {
        try {
            createIfMissing(USERS_FILE);
            createIfMissing(MEMBERS_FILE);
            createIfMissing(TRANSACTIONS_FILE);

            List<User> users = readUsers();
            boolean hasAdmin = users.stream().anyMatch(User::isAdmin);
            if (!hasAdmin) {
                users.add(new User("Markus","admin123","ADMIN"));
                writeUsers(users);
            }

        } catch (Exception e) {
            System.out.println(Colors.Red + "File set up error: " + e.getMessage() + Colors.RESET);

        }
    }

    public static void createIfMissing(String file) throws IOException {
        Path path = Path.of(file);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    public static List<User> readUsers() {
        List<User> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",", -1);
                if (t.length >= 3) list.add(new User(t[0], t[1], t[2]));
            }
        } catch (IOException ignored) {}
        return list;
    }
    public static void writeUsers(List<User> users) {
        try(PrintWriter pw = new PrintWriter( (new FileWriter(USERS_FILE)))) {
            for (User u : users) {
                pw.println(u.getUsername() + "," + u.getPassword() + "," +u.getRole());
            }
        } catch (IOException e) {
            System.out.println(Colors.Red + "Error writing users: " + e.getMessage() + Colors.RESET);
        }
    }
    public static List<Membership> readMembership(){
        List<Membership> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",",-1);
                if (t.length >= 4) {
                    String username = t[0];
                    Membership.Plan plan = Membership.Plan.valueOf(t[1]);
                    List<String> addons = Membership.parseAddOns(t[2]);
                    String status = t[3];
                    Membership m = new Membership(username,plan,addons);
                    if ("CANCELED".equalsIgnoreCase(status)) m.cancel();
                    list.add(m);
                }
            }
        } catch (IOException ignored) {}
        return list;
    }
    public static void writeMembership(List<Membership> memberships) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(MEMBERS_FILE))) {
            for (Membership m : memberships) {
                pw.println(
                        m.getUsername() + "," +
                        m.getPlan().name() + "," +
                        Membership.addOnsToString(m.getAddOns()) + "," +
                        m.getStatus()
                );
            }
        } catch (IOException e) {
            System.out.println(Colors.Red + "Error writing membership: " + e.getMessage() + Colors.RESET);
        }
    }
    public static
    List<Transaction> readTransactions() {
        List<Transaction> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",",-1);
                if (t.length >= 6) {
                    LocalDate date = LocalDate.parse(t[0]);
                    LocalTime time = LocalTime.parse(t[1]);
                    String type = t[2];
                    double amount = Double.parseDouble(t[3]);
                    String description = t[4];
                    String vendor = t[5];
                    list.add(new Transaction(date, time, type, amount, description, vendor));
                }
            }
        } catch (IOException ignored) {}
        return list;
    }
    public static void writeTransaction(List<Transaction> txs) {
        try(PrintWriter pw = new PrintWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Transaction t : txs) {
                pw.println(
                        t.getDate() + "|" +
                        t.getTime() + "|" +
                        t.getType() + "|" +
                        t.getAmount() + "|" +
                        sanitize(t.getDescription()) + "|" +
                        sanitize(t.getVendor())
                );
            }
        } catch (IOException e) {
            System.out.println(Colors.Red + "Error writing transactions: " + e.getMessage() + Colors.RESET);
        }
    }
    private static String sanitize(String s) {
        if (s == null) return "";
        return s.replace(","," ");
    }
}

