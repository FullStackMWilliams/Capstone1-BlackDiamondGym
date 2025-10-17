# 🏋️‍♂️ BlackDiamondGym CLI Application

**BlackDiamondGym** is a full-featured Java command-line application that simulates a fitness club management system — complete with membership handling, payment tracking, financial reporting, and admin controls. Designed as a capstone-style project, it demonstrates key Java concepts while mimicking real-world business logic.

---

## 📋 Table of Contents
- [✨ Overview](#-overview)
- [📦 Features](#-features)
- [🧰 Technologies Used](#-technologies-used)
- [📂 Application Structure](#-application-structure)
- [📊 Admin Dashboard (Interesting Code)](#-admin-dashboard-interesting-code)
- [💡 Bonus: Member Features Explained](#-bonus-member-features-explained)


---

## ✨ Overview

BlackDiamondGym is designed to be both **beginner-friendly** and **production-ready**, demonstrating core programming skills such as:
- File I/O (CSV persistence)
- Class modeling and inheritance
- Menu-driven CLI navigation
- Date and time formatting
- Report generation and filtering
- Role-based access control (Admin vs. Member)

It’s a practical simulation of how gyms or subscription-based businesses manage users, payments, and operations — all from the command line.

---

## 📦 Features

### 👤 Member Functionality
- 🧑‍💻 **Sign Up / Login** — Create an account and manage your profile.  
- 📦 **Membership Plans** — Choose from `BASIC`, `PREMIUM`, or `VIP`.  
- ➕ **Add Amenities** — Add services like towel service, personal trainer, pool, or sauna.  
- ❌ **Cancel Membership** — Cancel your plan anytime.  
- 💰 **Automatic Transactions** — New memberships and add-ons generate deposit records.

### 👑 Admin Functionality
- 💵 **Sales & Purchases** — Record deposits and payments.  
- 📊 **Ledger System** — View transactions, filter by type, or generate time-based reports.  
- 📈 **Reports** — Run Month-to-Date, Previous Month, Year-to-Date, Previous Year, Vendor Search, and Date Range reports.  
- 🧾 **CSV Persistence** — All transactions are saved in a `transactions.csv` file.

---

## 🧰 Technologies Used

- ☕ **Java 17+**  
- 📂 CSV File Storage (No external database required)  
- 🛠️ IntelliJ IDEA or VS Code  
- 🧪 JUnit (Optional for testing)  

---

## 👷‍♂️ App Structure
📁 src/com/pluralsight/
│
├─ BlackDiamondGym.java     # Main application & CLI menus
├─ Admin.java               # Admin class (inherits from User)
├─ Member.java              # Member class (inherits from User)
├─ User.java                # Base user class
├─ Membership.java          # Membership plan & pricing logic
├─ Transaction.java         # Transaction model
├─ Ledger.java              # Reporting and filtering engine
├─ FileManager.java         # CSV read/write operations
└─ Colors.java              # ANSI color palette for CLI UI

---

## 💡 Interesting Code
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
            case "1" -> ledgerMenu();   // Record income & expenses
            case "2" -> reportsMenu();  // Generate time-based and vendor reports
            case "3" -> appInfo();      // View membership & revenue data
            case "4" -> { return; }     // Exit dashboard
            default -> printlnWarn("⚠️ Invalid option.");
        }
    }
}

---

## 🏁 Final Notes

BlackDiamondGym is more than a Java CLI project — it’s a foundational template for building real-world applications with modular structure, clean code, and meaningful business logic. Whether you’re a student, instructor, or developer, this project demonstrates how Java can power fully functional management systems.

💪 "Strength is not just in the body — it's in the code." - BlackDiamond Gym



<img width="1919" height="1125" alt="Screenshot 2025-10-17 010934" src="https://github.com/user-attachments/assets/30a0391f-dc78-43b2-b373-4d2402a87e97" />

<img width="1919" height="1126" alt="Screenshot 2025-10-17 011021" src="https://github.com/user-attachments/assets/2a637d06-324d-4316-ac50-d63807bd9fbd" />

<img width="1919" height="1128" alt="Screenshot 2025-10-17 011051" src="https://github.com/user-attachments/assets/aed60f20-6de3-46a7-8fb9-cb24d336652c" />

<img width="1907" height="1122" alt="Screenshot 2025-10-17 011138" src="https://github.com/user-attachments/assets/4d9da81a-5fcc-42c2-9df8-7fc23b780829" />

<img width="1919" height="1130" alt="Screenshot 2025-10-17 011230" src="https://github.com/user-attachments/assets/9007d511-3b1c-4db4-bc11-0f2f1b8484b8" />

<img width="1919" height="1128" alt="Screenshot 2025-10-17 013029" src="https://github.com/user-attachments/assets/b41825f4-e9c9-4022-b12f-4f00fc9c1d8c" />

<img width="1918" height="1129" alt="Screenshot 2025-10-17 013541" src="https://github.com/user-attachments/assets/402a17df-2dcc-48f9-b99a-956cf9e35c5c" />
