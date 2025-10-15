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