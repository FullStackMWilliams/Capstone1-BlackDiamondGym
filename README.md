# 🏋️‍♂️ BlackDiamondGym CLI Application

## 📘 Overview

**BlackDiamondGym** is a fully interactive **Java CLI application** that simulates a gym membership and accounting system.  
It is designed to help users explore Java fundamentals like **OOP, file I/O, collections, date/time APIs, exception handling, and formatting** while building a realistic, real-world project.

The system supports two primary user types:
- 👤 **Members:** Can create accounts, manage memberships, and view their services.
- 👑 **Admins:** Can record financial transactions, view reports, and analyze gym data.

All application data — including users, memberships, and transactions — are stored in CSV files, making it easy to persist data without databases.

---

## 🧭 Features

### 🏠 Home Screen
- 🔑 **Login** – Securely log into your account.
- 📝 **Sign Up** – Create a new membership and select add-ons.
- 📊 **Ledger Menu** – Add deposits, record payments, and view transactions.
- 📅 **Reports Menu** – Generate financial summaries and search transactions.
- ❌ **Exit** – Close the application.

---

### 👤 Member Dashboard
- 🧾 View current membership plan and add-ons.
- ➕ Add additional amenities.
- ❌ Cancel membership.
- 🚪 Log out safely.

---

### 👑 Admin Dashboard
- 💵 Record deposits (income) and payments (expenses).
- 📈 View ledger of all transactions (newest first).
- 🔍 Filter transactions by deposits, payments, or vendor.
- 📅 Run financial reports (Month-to-Date, Previous Month, YTD, etc.).
- 📊 View gym metrics such as active members and revenue.

---

## 🗃️ Data Storage Format

All transactions are stored in `transactions.csv` with the following structure:

date|time|type|description|vendor|amount

2025-10-16|17:10:08|DEPOSIT|Membership Payment|Stripe|79.99

2025-10-17|09:25:31|PAYMENT|Equipment Repair|Rogue Fitness|-450.00

---


- **Deposits** are shown in ✅ green.
- **Payments** are shown in ❌ red.

---

## 🧪 Bonus: Dynamic Member Menu

One of the most engaging features is the **Bonus Member Menu**, which introduces a more personalized user experience.  
Members can manage their account without needing admin access — from adding amenities to viewing their monthly billing breakdown.

This demonstrates how **conditional logic and class composition** can be combined to provide tailored user experiences based on roles.

---

## 👑 Interesting Code – Admin Dashboard

Below is one of the most crucial parts of the system: the **Admin Dashboard**.  
It demonstrates advanced CLI control flow, modular design, and dynamic navigation.

```java
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
````
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
