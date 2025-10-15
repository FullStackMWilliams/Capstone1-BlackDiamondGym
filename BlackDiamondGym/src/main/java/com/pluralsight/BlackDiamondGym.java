package com.pluralsight;

                                            /* BLACK DIAMOND GYM */


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.pluralsight.Colors.*;

public class BlackDiamondGym {

    private static final Scanner in = new Scanner(System.in);

    private static List<User> users = new ArrayList<>();
    private static List<Membership> memberships = new ArrayList<>();
    private static final Ledger ledger = new Ledger();


    public static void main(String[] args) {

        FileManager.ensureFiles();

        users = FileManager.readUsers();
        memberships = FileManager.readMembership();
        ledger.setTransactions(FileManager.readTransactions());

        while (true) {
            displayHomeScreen();
        }


    }

    public static void displayHomeScreen() {
        // clearScreen();

        System.out.println(Colors.BeigeBackground + "================================="+ RESET);
        System.out.println(DeepBlue + " Welcome to BlackDiamond Gym" + RESET);
        System.out.println(BeigeBackground + "=================================" + RESET);
        System.out.println(Seafoam + "\nYour journey to strength and balance begins here." + RESET);
        System.out.println(Gray + "\n-----------------------------------------------------" + RESET);
        System.out.println(Olive + "[1]" + RESET + " Login");
        System.out.println(Olive + "[2]" + RESET + " Sign Up");
        System.out.println(Olive + "[3]" + RESET + " Exit");
        System.out.println(Gray + "-------------------------------------------------------");
        System.out.println(Amber + "\"Life is easier when you're stronger\".- Markus" + RESET);
    }
    private static void exitScreen() {
        clear();
        println(DeepBlue, "===================================");
        println(OceanBlue, "                       THANK YOU FOR VISITING");
        println(BeigeBackground,"\nYour dedication shapes your strength.");
        println(BeigeBackground,"Come back soon to continue your journey!");
        println(Amber,"\n--- BlackDiamondGym Team");
    }
    private static void loginFlow() {
        clear();
        println(Olive,"===================== LOGIN ============================");
        String username = prompt("Username: ");
        String password = prompt("Password: ");

        User found = users.stream()
                .filter(u ->u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password))
                .findFirst().orElse(null);
        if (found == null) {
            printlnError("Login failed. Check username/password.");
            pause();
            return;
        }
        if (found.isAdmin()) {

        }else {

        }
    }
    private static void signUpFlow() {
        clear();
        println(Amber,"=================== SIGN UP ====================");
        String username = prompt("Choose a username: ").trim();
        if (username.isEmpty()) {
            printlnWarn("Username cannot be empty."); pause(); return;
        }
        boolean exists = users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
        if (exists) {
            printlnWarn("Username already exists. Please try another."); pause(); return;
        }
        String password = prompt("Choose a password: ");
        if (password.trim().isEmpty()) {
            printlnWarn("Password cannot be empty."); pause(); return;
        }
        println(null,"\nChoose a base membership:");
        println(null,"1) BASIC ($29.99)");
        println(null,"2) PREMIUM ($49.99)");
        println(null,"3) VIP ($79.99)");
        String planChoice = prompt("Enter (1-3): ");
        Membership.Plan plan = switch (planChoice) {
            case "1" -> Membership.Plan.BASIC;
            case "2" -> Membership.Plan.PREMIUM;
            case "3" -> Membership.Plan.VIP;
            default -> Membership.Plan.BASIC;
        };
        println(null,"\nAdd-ons (type the numbers separated by commas, or press Enter for none):");
        println(null,"1) Towel Service ($5)");
        println(null, "2) Gym Class ($25)");
        println(null,"3) Personal trainer ($100)");
        println(null,"4) Pool ($10");
        println(null,"5) Sauna ($20)");
        String addonInput = prompt("Coose add-ons (1-5)");
        List<String> addOns = new ArrayList<>();
        if (!addonInput.trim().isEmpty()) {
            String[] picks = addonInput.split(",");
            for (String p : picks) {
                switch (p.trim()) {
                    case "1" -> addOns.add("Towel Service");
                    case "2" -> addOns.add("Gym Class");
                    case "3" -> addOns.add("Personal Trainer");
                    case "4" -> addOns.add("Pool");
                    case "5" -> addOns.add("Sauna");
                }
            }
        }
        Member newMember = new Member(username,password);
        Membership newMembership = new Membership(username, plan, addOns);

        users.add(newMember);
        memberships.add(newMembership);

        ledger.addDeposit(newMembership.getTotalPrice(), "New membership (" + plan + ")", "Membership");

        FileManager.writeUsers(users);
        FileManager.writeMembership(memberships);
        FileManager.writeTransaction(ledger.getTransactions());

        clear();
        println(null,"Welcome, " + username + "!");
        println(null,"Plan: " + plan);
        println(null,"Add-ons: " + (addOns.isEmpty() ? "None" : addOns));
        println(null,"Monthly Total: $" + String.format("%.2f", newMembership.getTotalPrice()));
        println(BeigeBackground,"\nYour account has been created. please login from the home screen.");
        pause();
    }
    private static void memberDashboard(Member member) {
        while (true) {
            clear();
            println(Amber,"==================== MEMBER DASHBOARD ===================");
            println(Aqua,"Welcome, " + member.getUsername() + "!");
            println(DeepBlue,"--------------------------------------------------------");

            Membership ms = memberships.stream()
                    .filter(m -> m.getUsername().equalsIgnoreCase(member.getUsername()))
                    .findFirst().orElse(null);

            if (ms == null) {
                println(null, "No active membership found.");
            } else {
                println(null,"Membership: " + ms.getPlan());
                println(null,"Add-ons:  " + (ms.getAddOns().isEmpty() ? "None" : ms.getAddOns()));
                println(null,"Status:  " + ms.getStatus());
                println(null,"Monthly:  $" + String.format("%.2f", ms.getTotalPrice()));
            }
            println(BeigeBackground,"-------------------------------------");
            println(null,"[1] Add amenities");
            println(null,"[2] Cancel membership");
            println(null,"[3] Logout");

            String choice = prompt(Seafoam + "Choose: " + RESET);
            switch (choice) {

            }
        }
    }
    private static void addAmenities(Member member) {
        Membership ms = memberships.stream()
                .filter(m -> m.getUsername().equalsIgnoreCase(member.getUsername()))
                .findFirst().orElse(null);
            if (ms == null) {
                printlnWarn("No membership to modify."); pause(); return;
            }
            if ("CANCELED".equalsIgnoreCase(ms.getStatus())) {
                printlnWarn("Cannot modify a canceled membership."); pause(); return;
            }
            println(null,"\nSelect amenities to add (comma separated):");
            println(null,"1) Towel Service $5");
            println(null,"2) Gym Class $20");
            println(null,"3) Personal trainer $100");
            println(null,"4) Pool Access $10");
            println(null,"5) Sauna Access $20");
            String input = prompt("Choose your amenities (1-5): ");
            if (input.trim().isEmpty()) {
                printlnWarn("No changes made."); pause(); return;
            }
    }

    private static Admin toAdmin(User u) {return new Admin(u.getUsername(), u.getPassword()); }
    private static Member toMember(User u) {return new Member(u.getUsername(), u.getPassword()); }

    private static String prompt(String msg) {
        System.out.println(msg);
        return in.nextLine();
    }
    private static void println(String color, String s) {
        if ( color == null) System.out.println(s);
        else System.out.println(color + s + RESET);
    }
    private static void printlnWarn(String s) { println(Yellow, "  " + s); }
    private static void printlnError(String s) { println(Red, " " + s); }
    private static void printlnSuccess(String s) {println(Green, " " + s); }

    private static void clear() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }
    private static void pause() {
        System.out.println(OceanBlue + "\nPress ENTER to continue...." + RESET );
        in.nextLine();
    }
    private static double parseDouble(String input) {
        try {
            return Double.parseDouble(input.trim());
        } catch (Exception e) {
            printlnWarn("Invalid number. Using 0.0");
            return 0.0;
        }
    }
}