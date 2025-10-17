package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import static com.pluralsight.Colors.*;

//            BlackDiamondGym – Ledger-first CLI with Login/Signup Submenu

public class BlackDiamondGym {

    //
    private static final Scanner in = new Scanner(System.in);
    private static final Ledger ledger = new Ledger();
    private static List<User> users = new ArrayList<>();
    private static List<Membership> memberships = new ArrayList<>();

    // Column widths (tweak if needed)
    private static final int W_DATE = 12;
    private static final int W_TIME = 10;
    private static final int W_TYPE = 10;
    private static final int W_AMT  = 15;
    private static final int W_VENDOR = 28;
    private static final int W_DESC   = 28;

    public static void main(String[] args) {
        // Ensure files exist and load data
        FileManager.ensureFiles();
        users = FileManager.readUsers();
        memberships = FileManager.readMembership();
        ledger.setTransactions(FileManager.readTransactions());

        // Main loop (HOME MENU per requirements)
        boolean running = true;
        while (running) {
            showHomeMenu();
            String choice = prompt(Seafoam + "👉 Enter your choice: " + RESET).trim().toUpperCase();

            switch (choice) {
                case "D" -> recordDeposit();
                case "P" -> recordPayment();
                case "L" -> ledgerMenu();
                case "M" -> authMenu();            // NEW: login/signup submenu
                case "X" -> { exitScreen(); running = false; }
                default  -> printlnWarn("Invalid option. Try again.");
            }
        }
    }


    //       HOME SCREEN

    private static void showHomeMenu() {
        clear();
        System.out.println(BeigeBackground + "=================================================" + RESET);
        System.out.println(DeepBlue + "   🏋️ BlackDiamond Gym — Main Menu" + RESET);
        System.out.println(BeigeBackground + "=================================================" + RESET);
        System.out.println();
        System.out.println("[D] 💵 Add Deposit");
        System.out.println("[P] 💸 Make Payment (Debit)");
        System.out.println("[L] 📜 Ledger");
        System.out.println("[M] 👤 Member / Admin Login");
        System.out.println("[X] 🚪 Exit");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("\"Life is easier when you're stronger\"- Markus");
    }

    private static void exitScreen() {
        clear();
        println(DeepBlue, "===============================================");
        println(OceanBlue, "🙏 Thanks for using BlackDiamond Gym Ledger!");
        println(BeigeBackground, "Come back soon to continue your journey. 💪");
        println(Amber, "— The BlackDiamond Team");
        println(DeepBlue, "===============================================");
    }


    //   LOGIN / SIGNUP SUBMENU

    private static void authMenu() {
        boolean sub = true;
        while (sub) {
            clear();
            println(DeepBlue, "===================== 👥 Login / Signup =====================");
            System.out.println("[1] 🔐 Login");
            System.out.println("[2] 🧑‍💻 Sign Up");
            System.out.println("[0] 🔙 Back");
            System.out.println();
            String c = prompt(Seafoam + "👉 Choose: " + RESET).trim();

            switch (c) {
                case "1" -> loginFlow();
                case "2" -> signUpFlow();
                case "0" -> sub = false;
                default  -> printlnWarn("Invalid option.");
            }
        }
    }


    //        AUTH FLOWS

    private static void loginFlow() {
        clear();
        println(Olive, "===================== 🔐 LOGIN ============================");
        String username = prompt("👤 Username: ");
        String password = prompt("🔑 Password: ");

        User found = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password))
                .findFirst().orElse(null);

        if (found == null) {
            printlnError("❌ Login failed. Check username/password.");
            pause();
            return;
        }

        if (found.isAdmin()) {
            adminDashboard(new Admin(found.getUsername(), found.getPassword()));
        } else {
            Member m = new Member(found.getUsername(), found.getPassword());
            Membership mm = memberships.stream()
                    .filter(ms -> ms.getUsername().equalsIgnoreCase(m.getUsername()))
                    .findFirst().orElse(null);
            m.setMembership(mm);
            memberDashboard(m);
        }
    }

    private static void signUpFlow() {
        clear();
        println(Amber, "=================== 🧑‍💻 SIGN UP ====================");

        String username = prompt("📛 Choose a username: ").trim();
        if (username.isEmpty()) { printlnWarn("Username cannot be empty."); pause(); return; }
        boolean exists = users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
        if (exists) { printlnWarn("Username already exists. Try another."); pause(); return; }

        String password = prompt("🔑 Choose a password: ").trim();
        if (password.isEmpty()) { printlnWarn("Password cannot be empty."); pause(); return; }

        // Plan
        println(null, "\n💳 Choose a base membership:");
        println(null, "1) BASIC ($29.99)");
        println(null, "2) PREMIUM ($49.99)");
        println(null, "3) VIP ($79.99)");
        String planChoice = prompt("👉 Enter (1-3): ");
        Membership.Plan plan = switch (planChoice) {
            case "1" -> Membership.Plan.BASIC;
            case "2" -> Membership.Plan.PREMIUM;
            case "3" -> Membership.Plan.VIP;
            default  -> Membership.Plan.BASIC;
        };

        // Add-ons
        println(null, "\n✨ Add-ons (comma separated numbers, or Enter for none):");
        println(null, "1) Towel Service ($5)");
        println(null, "2) Gym Class ($25)");
        println(null, "3) Personal Trainer ($100)");
        println(null, "4) Pool ($10)");
        println(null, "5) Sauna ($20)");
        String addonInput = prompt("🛠️ Choose add-ons (1-5): ");
        List<String> addOns = new ArrayList<>();
        if (!addonInput.trim().isEmpty()) {
            for (String p : addonInput.split(",")) {
                switch (p.trim()) {
                    case "1" -> addOns.add("Towel Service");
                    case "2" -> addOns.add("Gym Class");
                    case "3" -> addOns.add("Personal Trainer");
                    case "4" -> addOns.add("Pool");
                    case "5" -> addOns.add("Sauna");
                }
            }
        }

        // Create and persist
        Member newMember = new Member(username, password);
        Membership newMembership = new Membership(username, plan, addOns);
        users.add(newMember);
        memberships.add(newMembership);

        ledger.addDeposit(newMembership.getTotalPrice(), "New membership (" + plan + ")", "Membership");
        FileManager.writeUsers(users);
        FileManager.writeMembership(memberships);
        FileManager.writeTransactions(ledger.getTransactions());

        clear();
        println(Aqua, "======================== 🎉 WELCOME =====================");
        println(null, "🙌 Welcome, " + username + "!");
        println(null, "📦 Plan: " + plan);
        println(null, "🧰 Add-ons: " + (addOns.isEmpty() ? "None" : addOns));
        println(null, "💵 Monthly Total: $" + String.format("%.2f", newMembership.getTotalPrice()));
        println(BeigeBackground, "\n✅ Your account has been created. Please login from the Home menu (M).");
        pause();
    }

    //   MEMBER / ADMIN MENUS

    private static void memberDashboard(Member member) {
        while (true) {
            clear();
            println(Amber, "==================== 👤 MEMBER DASHBOARD ===================");
            println(Aqua, "💪 Welcome, " + member.getUsername() + "!");
            println(DeepBlue, "--------------------------------------------------------");

            Membership ms = memberships.stream()
                    .filter(m -> m.getUsername().equalsIgnoreCase(member.getUsername()))
                    .findFirst().orElse(null);

            if (ms == null) {
                println(Red, "⚠️ No active membership found.");
            } else {
                println(null, "📦 Membership Plan: " + ms.getPlan());
                println(null, "🧰 Add-ons: " + (ms.getAddOns().isEmpty() ? "None" : ms.getAddOns()));
                println(null, "📌 Status: " + ms.getStatus());
                println(null, "💵 Monthly Total: $" + String.format("%.2f", ms.getTotalPrice()));
            }

            println(BeigeBackground, "-------------------------------------");
            System.out.println("[1] ➕ Add Amenities");
            System.out.println("[2] ❌ Cancel Membership");
            System.out.println("[3] 🔙 Logout");
            String choice = prompt(Seafoam + "👉 Choose: " + RESET);

            switch (choice) {
                case "1" -> addAmenities(member);
                case "2" -> cancelMembership(member);
                case "3" -> { return; }
                default  -> printlnWarn("Invalid option.");
            }
        }
    }

    private static void adminDashboard(Admin admin) {
        while (true) {
            clear();
            println(DeepBlue, "==================== 👑 ADMIN DASHBOARD =======================");
            println(BeigeBackground, "👤 User: " + admin.getUsername());
            println(BeigeBackground, "------------------------------------------------------");
            System.out.println("[1] 💵 Sales & Purchases (Record deposit/payment)");
            System.out.println("[2] 📊 View Reports (Ledger)");
            System.out.println("[3] 📈 App Info (Members/Revenue)");
            System.out.println("[4] 🔙 Logout");

            String choice = prompt(Purple + "👉 Choose: " + RESET);
            switch (choice) {
                case "1" -> ledgerMenu();
                case "2" -> reportsMenu();
                case "3" -> appInfo();
                case "4" -> { return; }
                default -> printlnWarn("Invalid option.");
            }
        }
    }


    //  MEMBER MODIFICATION

    private static void addAmenities(Member member) {
        Membership ms = memberships.stream()
                .filter(m -> m.getUsername().equalsIgnoreCase(member.getUsername()))
                .findFirst().orElse(null);

        if (ms == null) { printlnWarn("No membership to modify."); pause(); return; }
        if ("CANCELED".equalsIgnoreCase(ms.getStatus())) { printlnWarn("Cannot modify a canceled membership."); pause(); return; }

        println(null, "\n🛠️ Select amenities to add (comma separated):");
        println(null, "1) Towel Service $5");
        println(null, "2) Gym Class $25");
        println(null, "3) Personal Trainer $100");
        println(null, "4) Pool $10");
        println(null, "5) Sauna $20");
        String input = prompt("👉 Choose your amenities (1-5): ");
        if (input.trim().isEmpty()) { printlnWarn("No changes made."); pause(); return; }

        for (String p : input.split(",")) {
            switch (p.trim()) {
                case "1" -> ms.addAddOn("Towel Service");
                case "2" -> ms.addAddOn("Gym Class");
                case "3" -> ms.addAddOn("Personal Trainer");
                case "4" -> ms.addAddOn("Pool");
                case "5" -> ms.addAddOn("Sauna");
            }
        }

        ledger.addDeposit(ms.getTotalPrice(), "Membership add-ons update", "Membership");
        FileManager.writeMembership(memberships);
        FileManager.writeTransactions(ledger.getTransactions());

        printlnSuccess("Amenities updated. New monthly: $" + String.format("%.2f", ms.getTotalPrice()));
        pause();
    }

    private static void cancelMembership(Member member) {
        Membership ms = memberships.stream()
                .filter(m -> m.getUsername().equalsIgnoreCase(member.getUsername()))
                .findFirst().orElse(null);

        if (ms == null) { printlnWarn("No membership to cancel."); pause(); return; }
        if ("CANCELED".equalsIgnoreCase(ms.getStatus())) { printlnWarn("Membership already canceled."); pause(); return; }

        String sure = prompt("⚠️ Are you sure you want to cancel? (yes/no): ");
        if (sure.equalsIgnoreCase("yes")) {
            ms.cancel();
            FileManager.writeMembership(memberships);
            printlnSuccess("Membership canceled.");
        } else {
            printlnWarn("Cancellation aborted.");
        }
        pause();
    }

    //    LEDGER & REPORTS

    private static void ledgerMenu() {
        boolean viewing = true;
        while (viewing) {
            clear();
            println(DeepBlue, "====================== 📊 LEDGER ======================");
            System.out.println("[A] 📁 All");
            System.out.println("[D] 💵 Deposits");
            System.out.println("[P] 💳 Payments");
            System.out.println("[R] 📈 Reports");
            System.out.println("[0] 🔙 Back");
            System.out.println();

            String c = prompt(Seafoam + "👉 Choose: " + RESET).trim().toUpperCase();
            switch (c) {
                case "A" -> showTransactions(ledger.all());
                case "D" -> showTransactions(ledger.deposits());
                case "P" -> showTransactions(ledger.payments());
                case "R" -> reportsMenu();
                case "0" -> viewing = false;
                default  -> printlnWarn("Invalid option.");
            }
        }
    }

    private static void reportsMenu() {
        boolean reporting = true;
        while (reporting) {
            clear();
            println(DeepBlue, "======================== 📈 REPORTS ========================");
            System.out.println("[1] 📆 Month To Date");
            System.out.println("[2] 🗓 Previous Month");
            System.out.println("[3] 📅 Year To Date");
            System.out.println("[4] 📉 Previous Year");
            System.out.println("[5] 🔍 Search by Vendor");
            System.out.println("[0] 🔙 Back");
            System.out.println();

            String c = prompt(Seafoam + "👉 Choose: " + RESET).trim();
            switch (c) {
                case "1" -> showTransactions(ledger.monthToDate());
                case "2" -> showTransactions(ledger.previousMonth());
                case "3" -> showTransactions(ledger.yearToDate());
                case "4" -> showTransactions(ledger.previousYear());
                case "5" -> {
                    String vendor = prompt("Vendor (case-insensitive contains): ");
                    showTransactions(ledger.byVendor(vendor));
                }
                case "0" -> reporting = false;
                default  -> printlnWarn("Invalid option.");
            }
        }
    }


    //      ADD / PAYMENT

    private static void recordDeposit() {
        clear();
        println(Green, "💰 Add Deposit");
        double amt = parseDouble(prompt("Amount ($): "));
        String desc = prompt("Description: ");
        String vendor = prompt("Vendor: ");

        ledger.addDeposit(Math.abs(amt), desc, vendor);
        FileManager.writeTransactions(ledger.getTransactions());
        printlnSuccess("Deposit recorded.");
        pause();
    }

    private static void recordPayment() {
        clear();
        println(Red, "💸 Make Payment (Debit)");
        double amt = parseDouble(prompt("Amount ($): "));
        String desc = prompt("Description: ");
        String vendor = prompt("Vendor: ");

        ledger.addPayment(Math.abs(amt), desc, vendor);
        FileManager.writeTransactions(ledger.getTransactions());
        printlnSuccess("Payment recorded.");
        pause();
    }
    private static void appInfo() {
        clear();
        long totalMembers = memberships.stream()
                .filter(m -> "ACTIVE".equalsIgnoreCase(m.getStatus()))
                .count();

        double mrr = memberships.stream()
                .filter(m -> "ACTIVE".equalsIgnoreCase(m.getStatus()))
                .mapToDouble(Membership::getTotalPrice)
                .sum();

        println(BeigeBackground, "📊 Active Members: " + totalMembers);
        println(BeigeBackground, "💰 Projected Monthly Recurring Revenue: $" + String.format("%.2f", mrr));
        pause();
    }


    //      TABLE DISPLAY

    private static void showTransactions(List<Transaction> list) {
        clear();

        // Newest first by date then time
        list.sort(Comparator
                .comparing(Transaction::getDate)
                .thenComparing(Transaction::getTime)
                .reversed());

        // Header
        System.out.println(Gray + "========================================================================================================" + RESET);
        System.out.printf(
                BLACK_BOLD + "%-" + W_DATE + "s %-" + W_TIME + "s %-" + W_TYPE + "s %-" + W_AMT + "s %-" + W_VENDOR + "s %-" + W_DESC + "s%n" + RESET,
                "📅 Date", "⏰ Time", "📂 Type", "💵 Amount", "🏢 Vendor", "📝 Description"
        );
        System.out.println(Gray + "--------------------------------------------------------------------------------------------------------" + RESET);

        // Rows (truncate, then pad -> color only the amount)
        for (Transaction t : list) {
            String date = pad(trunc(safe(t.getDate())), W_DATE);
            String time = pad(trunc(safe(t.getTime())), W_TIME);
            String type = pad(trunc(safe(t.getType())), W_TYPE);

            String rawAmount = String.format("$%,.2f", t.getAmount());            // build first
            String amtPadded = pad(trunc(rawAmount), W_AMT);                      // pad without color
            String amtColored = (t.getAmount() < 0 ? Red : Green) + amtPadded + RESET;

            String vendor = pad(trunc(safe(t.getVendor()), W_VENDOR), W_VENDOR);  // max W_VENDOR
            String desc   = pad(trunc(safe(t.getDescription()), W_DESC), W_DESC); // max W_DESC

            System.out.printf("%s %s %s %s %s %s%n",
                    date, time, type, amtColored, vendor, desc);
        }

        System.out.println(Gray + "========================================================================================================" + RESET);
        pause();
    }


    //         UTILITIES/HELPER METHODS
    private static String prompt(String msg) {
        System.out.print(msg);
        return in.nextLine();
    }
    private static void println(String color, String s) {
        if (color == null) System.out.println(s);
        else System.out.println(color + s + RESET);
    }
    private static void printlnWarn(String s) { println(Yellow, "⚠️ " + s); }
    private static void printlnError(String s) { println(Red, "❌ " + s); }
    private static void printlnSuccess(String s) { println(Green, "✅ " + s); }
    private static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    private static void pause() {
        System.out.println(OceanBlue + "\n🔄 Press ENTER to continue..." + RESET);
        in.nextLine();
    }
    private static double parseDouble(String input) {
        try { return Double.parseDouble(input.trim()); }
        catch (Exception e) { printlnWarn("Invalid number. Using 0.0"); return 0.0; }
    }

    // ---------- Formatting helpers (truncate THEN pad) ----------
    private static String safe(Object o) { return (o == null) ? "" : o.toString(); }
    private static String trunc(String s) { return s == null ? "" : s; } // generic
    private static String trunc(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, Math.max(0, max - 3)) + "...";
    }
    private static String pad(String s, int width) {
        return String.format("%-" + width + "s", s == null ? "" : s);
    }
}
