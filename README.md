🏋️‍♂️ BlackDiamondGym — CLI Fitness Management App
📦 Tech Stack & Requirements

Language: Java 17+
IDE: IntelliJ IDEA or any Java-compatible IDE
Build Tool: No external build tools required (standard javac and java commands work)
Data Storage: CSV files (users.csv, membership.csv, transactions.csv)
Libraries:

☕ Standard Java SE Libraries

📅 java.time for date/time handling

📂 java.io for CSV I/O operations

🧰 java.util for collections and user input

📘 Overview

BlackDiamondGym is a command-line fitness management system designed to manage memberships, transactions, and administrative operations for a gym. It provides two distinct user experiences:

👤 Members — Can view and manage their memberships, add amenities, or cancel services.

👑 Admins — Can manage transactions, generate reports, and track revenue.

All data is stored in CSV files and updated in real time. The app is designed for simplicity, scalability, and reliability — making it an excellent foundation for future expansion (like database integration or GUI).

🏠 Home Screen Menu

Upon launch, users are greeted with a simple interactive menu:

Option	Action
D	➕ Add Deposit — Record income transactions
P	💸 Make Payment — Record expense transactions
L	📊 Ledger — View all financial records
B	👤 Bonus Member Menu — Exclusive member tools
X	🚪 Exit — Close the application
📊 Ledger Features

The Ledger is the heart of BlackDiamondGym’s accounting system. All transactions are displayed newest-first and can be filtered with a single key press:

A) All Transactions — Shows every record

D) Deposits — Displays only income transactions

P) Payments — Displays only expenses

📑 Reports & Insights

The reporting module allows admins to generate detailed views of financial activity:

Report	Description
1) Month-to-Date	Transactions from the current month
2) Previous Month	Transactions from the previous month
3) Year-to-Date	All transactions this year
4) Previous Year	All transactions from last year
5) Search by Vendor	Search transactions by vendor name
0) Back	Return to the Ledger screen
👤 Bonus Member Menu — A Dynamic Feature

💡 Why it’s interesting:
This menu showcases dynamic state management, financial integration, and real-time data updates — all from a single user action.

🔧 Member Options:

➕ Add Amenities: Members can customize their plans (e.g., pool, personal trainer, sauna). The system recalculates their total cost and records a new transaction in the ledger.

❌ Cancel Membership: Instantly deactivate an account. The status updates in both the membership and ledger systems, ensuring accurate reporting.

📈 Auto-Billing Integration: Every change (new amenity, cancellation, etc.) automatically writes a new line to the CSV file, preserving a full financial audit trail.

<img width="1919" height="1125" alt="Screenshot 2025-10-17 010934" src="https://github.com/user-attachments/assets/30a0391f-dc78-43b2-b373-4d2402a87e97" />

<img width="1919" height="1126" alt="Screenshot 2025-10-17 011021" src="https://github.com/user-attachments/assets/2a637d06-324d-4316-ac50-d63807bd9fbd" />

<img width="1919" height="1128" alt="Screenshot 2025-10-17 011051" src="https://github.com/user-attachments/assets/aed60f20-6de3-46a7-8fb9-cb24d336652c" />

<img width="1907" height="1122" alt="Screenshot 2025-10-17 011138" src="https://github.com/user-attachments/assets/4d9da81a-5fcc-42c2-9df8-7fc23b780829" />

<img width="1919" height="1130" alt="Screenshot 2025-10-17 011230" src="https://github.com/user-attachments/assets/9007d511-3b1c-4db4-bc11-0f2f1b8484b8" />

<img width="1919" height="1128" alt="Screenshot 2025-10-17 013029" src="https://github.com/user-attachments/assets/b41825f4-e9c9-4022-b12f-4f00fc9c1d8c" />

<img width="1918" height="1129" alt="Screenshot 2025-10-17 013541" src="https://github.com/user-attachments/assets/402a17df-2dcc-48f9-b99a-956cf9e35c5c" />
