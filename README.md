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
- 
<img width="1919" height="1127" alt="Screenshot 2025-10-16 230506" src="https://github.com/user-attachments/assets/2998e03c-356a-432b-9332-a51b0a41a705" />
<img width="1903" height="1124" alt="Screenshot 2025-10-16 230432" src="https://github.com/user-attachments/assets/402871f1-e6a0-4275-b8de-ea6ee095ba7e" />
<img width="1910" height="1123" alt="Screenshot 2025-10-16 230227" src="https://github.com/user-attachments/assets/cdeb4ea9-61f3-4c53-a90c-705bb110a728" />
<img width="1911" height="1122" alt="Screenshot 2025-10-16 230144" src="https://github.com/user-attachments/assets/952ad706-d7cd-4350-aa8a-1d50008c0c1c" />
<img width="1918" height="1126" alt="Screenshot 2025-10-16 225938" src="https://github.com/user-attachments/assets/71e32bb5-083d-4fea-b83e-803d2756a91e" />
<img width="1919" height="1127" alt="Screenshot 2025-10-16 230852" src="https://github.com/user-attachments/assets/d3c6b617-2f49-448e-883f-6c938088d906" />
