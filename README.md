# Capstone1-BlackDiamondGym

# 🏋️‍♂️ BlackDiamondGym CLI Application

**BlackDiamondGym** is a Java CLI (Command-Line Interface) application designed as a comprehensive capstone project that simulates a real-world gym management system. The project covers fundamental and intermediate Java concepts including OOP, file I/O, CSV handling, date/time formatting, collections, conditionals, loops, and user authentication.

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

<img width="1919" height="1125" alt="Screenshot 2025-10-17 010934" src="https://github.com/user-attachments/assets/30a0391f-dc78-43b2-b373-4d2402a87e97" />

<img width="1919" height="1126" alt="Screenshot 2025-10-17 011021" src="https://github.com/user-attachments/assets/2a637d06-324d-4316-ac50-d63807bd9fbd" />

<img width="1919" height="1128" alt="Screenshot 2025-10-17 011051" src="https://github.com/user-attachments/assets/aed60f20-6de3-46a7-8fb9-cb24d336652c" />

<img width="1907" height="1122" alt="Screenshot 2025-10-17 011138" src="https://github.com/user-attachments/assets/4d9da81a-5fcc-42c2-9df8-7fc23b780829" />

<img width="1919" height="1130" alt="Screenshot 2025-10-17 011230" src="https://github.com/user-attachments/assets/9007d511-3b1c-4db4-bc11-0f2f1b8484b8" />

<img width="1919" height="1128" alt="Screenshot 2025-10-17 013029" src="https://github.com/user-attachments/assets/b41825f4-e9c9-4022-b12f-4f00fc9c1d8c" />

<img width="1918" height="1129" alt="Screenshot 2025-10-17 013541" src="https://github.com/user-attachments/assets/402a17df-2dcc-48f9-b99a-956cf9e35c5c" />
