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

<img width="1918" height="1126" alt="Screenshot 2025-10-16 225938" src="https://github.com/user-attachments/assets/01ad06dd-534b-4c7e-9544-fd86933835ab" />

<img width="1911" height="1122" alt="Screenshot 2025-10-16 230144" src="https://github.com/user-attachments/assets/96edc6a5-831b-43cf-904d-9a1735b3d3eb" />

<img width="1910" height="1123" alt="Screenshot 2025-10-16 230227" src="https://github.com/user-attachments/assets/62f3df3c-86fd-4610-9599-85864e76ecbe" />

<img width="1903" height="1124" alt="Screenshot 2025-10-16 230432" src="https://github.com/user-attachments/assets/e42cd33a-d668-4712-b32c-d90fe25ea62d" />

<img width="1919" height="1127" alt="Screenshot 2025-10-16 230506" src="https://github.com/user-attachments/assets/98452464-92cd-4e26-babc-544bdeaf3b30" />

<img width="1919" height="1127" alt="Screenshot 2025-10-16 230852" src="https://github.com/user-attachments/assets/d8078388-7134-4e4f-a4f6-48701ef8ac52" />
