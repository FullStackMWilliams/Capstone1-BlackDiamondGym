# Capstone1-BlackDiamondGym

# 🏋️‍♂️ BlackDiamondGym CLI Application

**BlackDiamondGym** is a beginner-friendly Java CLI (Command-Line Interface) application designed as a comprehensive capstone project that simulates a real-world gym management system. The project covers fundamental and intermediate Java concepts including OOP, file I/O, CSV handling, date/time formatting, collections, conditionals, loops, and user authentication.

---

## 📚 Project Overview

This project is structured to simulate how a real gym would manage **memberships, sales, ledger accounting, and admin operations**. Users interact with the system through a text-based menu system with color-themed screens and logical navigation.

The system includes two types of users:
- 👤 **Members** — can sign up, log in, manage memberships, add amenities, and view their plans.
- 🛠️ **Admins** — can log in to access sales data, transaction history, manage the accounting ledger, and view business performance.

---

## ✨ Key Features

### 🔐 Authentication
- Member and Admin login system with credential storage in `users.csv`.
- Ability to sign up as a new member with custom username and password.

### 🏃‍♂️ Member Features
- Sign up for new memberships with **3 base plans** and **5 optional add-ons**.
- View or cancel current membership.
- Add new amenities (e.g., personal trainer, sauna, pool, towel service, classes).
- View membership details in a friendly, color-coded dashboard.

### 🧰 Admin Features
- Access the accounting ledger and sales dashboard.
- View, add, or remove transactions stored in `transactions.csv`.
- Generate financial summaries and reports.
- View all active memberships and revenue streams.

### 💾 CSV Storage
All key data is stored in CSV format for transparency and simplicity:
- `users.csv` – user credentials and roles
- `transactions.csv` – all financial transactions in the format:
