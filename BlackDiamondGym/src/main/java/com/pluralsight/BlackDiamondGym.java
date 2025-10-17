package com.pluralsight;

import java.time.LocalDate;
import java.util.*;
import static com.pluralsight.Colors.*;

/*
 * 🏋️‍♂️ BlackDiamondGym
 * -------------------------------------------------------
 * This is the main entry point for the BlackDiamond Gym CLI application.
 * It handles:
 *  - 🏠 Home screen navigation
 *  - 🔐 Login and authentication
 *  - 🧑‍🤝‍🧑 Member sign-up and membership creation
 *  - 🧑‍💼 Admin login and dashboard access
 *
 * All data is read from and written to CSV files using FileManager.
 * Transactions are handled by the Ledger class.
 */
public class BlackDiamondGym {


    private static final Scanner in = new Scanner(System.in);

    // Store users and memberships in memory
    private static List<User> users = new ArrayList<>();
    private static List<Membership> memberships = new ArrayList<>();
    private static final Ledger ledger = new Ledger();

    public static void main(String[] args) {

        // Ensure CSV files exist before we do anything
        FileManager.ensureFiles();

        // Load existing data from CSV files
        users = FileManager.readUsers();
        memberships = FileManager.readMembership();
        ledger.setTransactions(FileManager.readTransactions());

        // Main app loop
        while (true) {
            displayHomeScreen();
            String choice = prompt(Seafoam + "👉 Enter your choice: " + RESET);
            switch (choice) {
                case "1" -> loginFlow();
                case "2" -> signUpFlow();
                case "3" -> {
                    exitScreen();
                    return;
                }
                default -> printlnWarn("⚠️ Invalid option. Try again.");
            }
        }
    }

    /*
     * Displays the main home screen with a welcoming message and options.
     */
    public static void displayHomeScreen() {
        clear();
        System.out.println(BeigeBackground + "========================================" + RESET);
        System.out.println(DeepBlue + " 🏋️‍♂️ Welcome to BlackDiamond Gym " + RESET);
        System.out.println(BeigeBackground + "========================================" + RESET);
        System.out.println(Seafoam + "\n🌿 Your journey to strength and balance begins here." + RESET);
        System.out.println(Gray + "\n-----------------------------------------------------" + RESET);
        System.out.println(Gray + "[1]" + RESET + " 🔐 Login");
        System.out.println(Gray + "[2]" + RESET + " 🧑‍💻 Sign Up");
        System.out.println(Gray + "[3]" + RESET + " 🚪 Exit");
        System.out.println(Gray + "-------------------------------------------------------" + RESET);
        System.out.println(Amber + "💡 \"Life is easier when you're stronger.\" — Markus" + RESET);
    }

    /*
     * Displays a farewell message before exiting.
     */
    private static void exitScreen() {
        clear();
        println(DeepBlue, "==================================================================================");
        println(OceanBlue, "🙏 THANK YOU FOR VISITING BLACKDIAMOND GYM 🙏");
        println(BeigeBackground, "\n🌟 Your dedication shapes your strength.");
        println(BeigeBackground, "Come back soon to continue your journey!");
        println(Amber, "\n— The BlackDiamondGym Team 🏋️‍♂️");
    }

    /*
     * 🔐 Handles user login for both members and admins.
     */
    private static void loginFlow() {
        clear();
        println(Olive, "===================== 🔐 LOGIN ============================");
        String username = prompt("👤 Username: ");
        String password = prompt("🔑 Password: ");

        // Find a matching user
        User found = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password))
                .findFirst().orElse(null);

        if (found == null) {
            printlnError("❌ Login failed. Check username/password.");
            pause();
            return;
        }

        //  Admin access
        if (found.isAdmin()) {
            adminDashboard((Admin) toAdmin(found));
        }
        //  Member access
        else {
            Member m = toMember(found);
            Membership mm = memberships.stream()
                    .filter(ms -> ms.getUsername().equalsIgnoreCase(m.getUsername()))
                    .findFirst().orElse(null);
            m.setMembership(mm);
            memberDashboard(m);
        }
    }


     //  Handles new user sign-ups and membership creation.

    private static void signUpFlow() {
        clear();
        println(Amber, "=================== 🧑‍💻 SIGN UP ====================");

        //  Username selection
        String username = prompt("📛 Choose a username: ").trim();
        if (username.isEmpty()) {
            printlnWarn("⚠️ Username cannot be empty.");
            pause();
            return;
        }
        boolean exists = users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
        if (exists) {
            printlnWarn("⚠️ Username already exists. Please try another.");
            pause();
            return;
        }

        // 🔑 Password selection
        String password = prompt("🔑 Choose a password: ");
        if (password.trim().isEmpty()) {
            printlnWarn("⚠️ Password cannot be empty.");
            pause();
            return;
        }

        // 📦 Choose membership plan
        println(null, "\n💳 Choose a base membership:");
        println(null, "1️⃣ BASIC ($29.99)");
        println(null, "2️⃣ PREMIUM ($49.99)");
        println(null, "3️⃣ VIP ($79.99)");
        String planChoice = prompt("👉 Enter (1-3): ");
        Membership.Plan plan = switch (planChoice) {
            case "1" -> Membership.Plan.BASIC;
            case "2" -> Membership.Plan.PREMIUM;
            case "3" -> Membership.Plan.VIP;
            default -> Membership.Plan.BASIC;
        };

        // 🏷️ Choose add-ons
        println(null, "\n✨ Add-ons (type the numbers separated by commas, or press Enter for none):");
        println(null, "1️⃣ Towel Service ($5)");
        println(null, "2️⃣ Gym Class ($25)");
        println(null, "3️⃣ Personal Trainer ($100)");
        println(null, "4️⃣ Pool ($10)");
        println(null, "5️⃣ Sauna ($20)");

        String addonInput = prompt("🛠️ Choose add-ons (1-5): ");
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

        // 🧑‍🤝‍🧑 Create new member + membership
        Member newMember = new Member(username, password);
        Membership newMembership = new Membership(username, plan, addOns);

        users.add(newMember);
        memberships.add(newMembership);

        // 💰 Record a deposit transaction for the membership purchase
        ledger.addDeposit(newMembership.getTotalPrice(), "New membership (" + plan + ")", "Membership");

        // 📁 Save to CSV
        FileManager.writeUsers(users);
        FileManager.writeMembership(memberships);
        FileManager.writeTransactions(ledger.getTransactions());

        // 🎉 Confirmation screen
        clear();
        println(Aqua, "======================== 🎉 WELCOME =====================");
        println(null, "🙌 Welcome, " + username + "!");
        println(null, "📦 Plan: " + plan);
        println(null, "🧰 Add-ons: " + (addOns.isEmpty() ? "None" : addOns));
        println(null, "💵 Monthly Total: $" + String.format("%.2f", newMembership.getTotalPrice()));
        println(BeigeBackground, "\n✅ Your account has been created. Please login from the home screen.");
        pause();
    }
    /**
     * 👤 Member Dashboard
     * ---------------------------------------------------------
     * Displays the main dashboard for a logged-in member.
     * Allows them to:
     *  - ✅ View membership details
     *  - ➕ Add amenities
     *  - ❌ Cancel membership
     *  - 🚪 Logout
     */
    private static void memberDashboard(Member member) {
        while (true) {
            clear();
            println(Amber, "==================== 👤 MEMBER DASHBOARD ===================");
            println(Aqua, "💪 Welcome, " + member.getUsername() + "!");
            println(DeepBlue, "--------------------------------------------------------");

            // 🔍 Find this member's membership info
            Membership ms = memberships.stream()
                    .filter(m -> m.getUsername().equalsIgnoreCase(member.getUsername()))
                    .findFirst()
                    .orElse(null);

            // 📋 Display membership info or show a message if none
            if (ms == null) {
                println(Red, "⚠️ No active membership found.");
            } else {
                println(null, "📦 Membership Plan: " + ms.getPlan());
                println(null, "🧰 Add-ons: " + (ms.getAddOns().isEmpty() ? "None" : ms.getAddOns()));
                println(null, "📌 Status: " + ms.getStatus());
                println(null, "💵 Monthly Total: $" + String.format("%.2f", ms.getTotalPrice()));
            }

            println(BeigeBackground, "-------------------------------------");
            println(null, "[1] ➕ Add Amenities");
            println(null, "[2] ❌ Cancel Membership");
            println(null, "[3] 🚪 Logout");

            String choice = prompt(Seafoam + "👉 Choose an option: " + RESET);
            switch (choice) {
                case "1" -> addAmenities(member);
                case "2" -> cancelMembership(member);
                case "3" -> { return; } // 🔙 Return to home screen
                default  -> printlnWarn("⚠️ Invalid option.");
            }
        }
    }
    /**
     * ➕ Add Amenities to a Membership
     * ---------------------------------------------------------
     * Prompts the member to choose additional amenities and updates their plan.
     * Automatically updates their total monthly price and records the transaction.
     */
    private static void addAmenities(Member member) {
        Membership ms = memberships.stream()
                .filter(m -> m.getUsername().equalsIgnoreCase(member.getUsername()))
                .findFirst()
                .orElse(null);

        if (ms == null) {
            printlnWarn("⚠️ No membership to modify.");
            pause();
            return;
        }
        if ("CANCELED".equalsIgnoreCase(ms.getStatus())) {
            printlnWarn("⚠️ Cannot modify a canceled membership.");
            pause();
            return;
        }

        println(null, "\n🛠️ Select amenities to add (comma separated):");
        println(null, "1️⃣ Towel Service ($5)");
        println(null, "2️⃣ Gym Class ($25)");
        println(null, "3️⃣ Personal Trainer ($100)");
        println(null, "4️⃣ Pool Access ($10)");
        println(null, "5️⃣ Sauna Access ($20)");

        String input = prompt("👉 Choose your amenities (1-5): ");
        if (input.trim().isEmpty()) {
            printlnWarn("⚠️ No changes made.");
            pause();
            return;
        }

        // 🧩 Process selected amenities
        String[] picks = input.split(",");
        for (String p : picks) {
            switch (p.trim()) {
                case "1" -> ms.addAddOn("Towel Service");
                case "2" -> ms.addAddOn("Gym Class");
                case "3" -> ms.addAddOn("Personal Trainer");
                case "4" -> ms.addAddOn("Pool");
                case "5" -> ms.addAddOn("Sauna");
            }
        }

        // 💰 Record this as a deposit transaction (income for the gym)
        ledger.addDeposit(ms.getTotalPrice(), "Membership add-ons update", "Membership");

        // 💾 Save changes to file
        FileManager.writeMembership(memberships);
        FileManager.writeTransactions(ledger.getTransactions());

        printlnSuccess("✅ Amenities updated. New monthly: $" + String.format("%.2f", ms.getTotalPrice()));
        pause();
    }

    /**
     * ❌ Cancel Membership
     * ---------------------------------------------------------
     * Allows a member to cancel their membership.
     * The status is updated to "CANCELED" and persisted to the CSV.
     */
    private static void cancelMembership(Member member) {
        Membership ms = memberships.stream()
                .filter(m -> m.getUsername().equalsIgnoreCase(member.getUsername()))
                .findFirst()
                .orElse(null);

        if (ms == null) {
            printlnWarn("⚠️ No membership to cancel.");
            pause();
            return;
        }
        if ("CANCELED".equalsIgnoreCase(ms.getStatus())) {
            printlnWarn("⚠️ Membership already canceled.");
            pause();
            return;
        }

        String sure = prompt("⚠️ Are you sure you want to cancel? (yes/no): ");
        if (sure.equalsIgnoreCase("yes")) {
            ms.cancel();
            FileManager.writeMembership(memberships);
            printlnSuccess("✅ Membership canceled successfully.");
        } else {
            printlnWarn("❎ Cancellation aborted.");
        }
        pause();
    }
    /**
     * 👑 Admin Dashboard
     * ---------------------------------------------------------
     * The main admin hub that provides access to:
     *  - 💵 Sales & Purchases (Ledger management)
     *  - 📊 Reports & Transactions
     *  - 📈 App Information (Membership and revenue stats)
     */
    private static void adminDashboard(Admin admin) {
        while (true) {
            clear();
            println(DeepBlue, "==================== 👑 ADMIN DASHBOARD =======================");
            println(BeigeBackground, "👤 User: " + admin.getUsername());
            println(BeigeBackground, "------------------------------------------------------");
            println(null, "[1] 💵 Sales & Purchases");
            println(null, "[2] 📊 View Reports (Ledger)");
            println(null, "[3] 📈 App Info (Members/Revenue)");
            println(null, "[4] 🚪 Logout");

            String choice = prompt(Purple + "👉 Choose: " + RESET);
            switch (choice) {
                case "1" -> ledgerMenu();
                case "2" -> reportsMenu();
                case "3" -> appInfo();
                case "4" -> { return; }
                default -> printlnWarn("⚠️ Invalid option.");
            }
        }
    }

    /**
     * 📁 Ledger Menu
     * ---------------------------------------------------------
     * Admin can record new transactions or view existing ones:
     *  - 💰 Record deposits (income)
     *  - 🧾 Record payments (expenses)
     *  - 📜 View transactions
     */
    private static void ledgerMenu() {
        while (true) {
            clear();
            println(DeepBlue, "========================== 📁 LEDGER / ACCOUNTING ===================");
            println(null, "[1] 💰 Record Deposit (Income)");
            println(null, "[2] 🧾 Record Payment (Expenses)");
            println(null, "[3] 📜 View All Transactions");
            println(null, "[4] 📈 View Deposits Only");
            println(null, "[5] 💸 View Payments Only");
            println(null, "[6] 🔙 Back");

            String c = prompt(BeigeBackground + "👉 Choose: " + RESET);
            switch (c) {
                case "1" -> recordDeposit();
                case "2" -> recordPayment();
                case "3" -> showTransactions(ledger.all());
                case "4" -> showTransactions(ledger.deposits());
                case "5" -> showTransactions(ledger.payments());
                case "6" -> { return; }
                default -> printlnWarn("⚠️ Invalid option.");
            }
        }
    }

    /*
     *  Record a new deposit transaction (income)
     */
    private static void recordDeposit() {
        double amt = parseDouble(prompt("💵 Enter Amount: $"));
        String desc = prompt("📝 Description: ");
        String vendor = prompt("🏢 Vendor: ");

        ledger.addDeposit(amt, desc, vendor);
        FileManager.writeTransactions(ledger.getTransactions());
        printlnSuccess("Deposit recorded successfully.");
        pause();
    }

    /*
     *  Record a new payment transaction (expense)
     */
    private static void recordPayment() {
        double amt = parseDouble(prompt("💸 Enter Amount: $"));
        String desc = prompt("📝 Description: ");
        String vendor = prompt("🏢 Vendor: ");

        ledger.addPayment(amt, desc, vendor);
        FileManager.writeTransactions(ledger.getTransactions());
        printlnSuccess(" Payment recorded successfully.");
        pause();
    }
    private static void showTransactions(List<Transaction> list) {
        clear();

        // 📊 Print header
        System.out.println(Gray + "=========================================================================================================================");
        System.out.printf("%-15s %-10s %-12s %-15s %-30s %-20s%n",
                "📅 Date", "⏰ Time", "📂 Type", "💲 Amount", "🏢 Vendor", "📝 Description");
        System.out.println(Gray + "=========================================================================================================================");

        for (Transaction t : list) {
            // Format each field with fixed width
            String date = String.format("%-15.15s", t.getDate().toString());
            String time = String.format("%-10.10s", t.getTime().toString());
            String type = String.format("%-12.12s", t.getType());
            String amountColor = t.getAmount() < 0 ? Red : Green;
            String amount = String.format("%-15.2f", t.getAmount());

            String vendor = String.format("%-30.30s", t.getVendor());         // 30 chars max
            String description = String.format("%-20.20s", t.getDescription()); // 20 chars max

            // Print row
            System.out.printf("%-15s %-10s %-12s %s%-15s%s %-30s %-20s%n",
                    date, time, type, amountColor, amount, RESET, vendor, description);
        }

        System.out.println(Gray + "=========================================================================================================================");
        pause();
    }



    /*
     * Reports Menu
     * ---------------------------------------------------------
     * Allows the admin to view transaction summaries:
     *   Month-to-date / Previous month
     *   Year-to-date / Previous year
     *   Search by vendor or date range
     */
    private static void reportsMenu() {
        while (true) {
            clear();
            println(DeepBlue, "================================= 📊 REPORTS =========================");
            println(null, "[1] 📆 Month-to-Date");
            println(null, "[2] 🗓️ Previous Month");
            println(null, "[3] 📅 Year-to-Date");
            println(null, "[4] 📉 Previous Year");
            println(null, "[5] 🔎 Search by Vendor");
            println(null, "[6] ⏱️ Search by Date Range");
            println(null, "[7] 📜 View All Transactions");
            println(null, "[8] 🔙 Back");

            String c = prompt(Seafoam + "👉 Choose: " + RESET);
            switch (c) {
                case "1" -> showTransactions(ledger.monthToDate());
                case "2" -> showTransactions(ledger.previousMonth());
                case "3" -> showTransactions(ledger.yearToDate());
                case "4" -> showTransactions(ledger.previousYear());
                case "5" -> {
                    String vendor = prompt("🏢 Vendor contains: ");
                    showTransactions(ledger.byVendor(vendor));
                }
                case "6" -> {
                    LocalDate start = LocalDate.parse(prompt("📅 Start date (YYYY-MM-DD): "));
                    LocalDate end = LocalDate.parse(prompt("📅 End date (YYYY-MM-DD): "));
                    showTransactions(ledger.byDateRange(start, end));
                }
                case "7" -> showTransactions(ledger.all());
                case "8" -> { return; }
                default -> printlnWarn(" Invalid option.");
            }
        }
    }

    /*
     *  Application Info
     * ---------------------------------------------------------
     * Displays basic metrics about the gym's performance:
     *  Active members
     *  Projected monthly recurring revenue (MRR)
     */
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

    // Helper Methods for converting users
    private static Admin toAdmin(User u) {
        return new Admin(u.getUsername(), u.getPassword());
    }

    private static Member toMember(User u) {
        return new Member(u.getUsername(), u.getPassword());
    }

    // Prompt for input
    private static String prompt(String msg) {
        System.out.print(msg);
        return in.nextLine();
    }

    //  Print with color (optional)
    private static void println(String color, String s) {
        if (color == null) System.out.println(s);
        else System.out.println(color + s + RESET);
    }

    // classes for  Warnings, Errors, Success messages
    private static void printlnWarn(String s) { println(Yellow, "⚠️ " + s); }
    private static void printlnError(String s) { println(Red, "❌ " + s); }
    private static void printlnSuccess(String s) { println(Green, "✅ " + s); }

    // Clears the screen for a fresh page view/almost like reset for colors
    private static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // System pauses for user input before continuing/ helps app flow
    private static void pause() {
        System.out.println(OceanBlue + "\n🔄 Press ENTER to continue..." + RESET);
        in.nextLine();
    }

    //  Safe double parsing with fallback exception for user input
    private static double parseDouble(String input) {
        try {
            return Double.parseDouble(input.trim());
        } catch (Exception e) {
            printlnWarn("⚠️ Invalid number. Using 0.0");
            return 0.0;
        }
    }
}
