# Task 1: ATM Interface System

## Objective
Develop a console-based ATM banking application that simulates real-world ATM operations including secure user authentication, transaction management, and account operations.

## Steps Performed

1. **System Design**
   - Designed three-class architecture (ATMInterface, BankAccount, TransactionRecord)
   - Implemented secure PIN-based authentication system
   - Created menu-driven user interface

2. **Core Features Implementation**
   - Developed transaction history tracking with timestamps
   - Implemented cash withdrawal with balance validation
   - Created cash deposit functionality
   - Built fund transfer system between accounts
   - Added secure logout mechanism

3. **Data Management**
   - Used HashMap for efficient account storage and retrieval
   - Implemented ArrayList for transaction history
   - Created TransactionRecord class for detailed logging

4. **Validation & Security**
   - Added input validation for all operations
   - Implemented transaction limits (withdrawal: Rs. 50,000, transfer: Rs. 25,000)
   - Created balance checking before withdrawals
   - Added account verification for transfers

## Tools Used

- **Programming Language:** Java (JDK 8+)
- **Development Environment:** Terminal/Command Line
- **Data Structures:** HashMap, ArrayList
- **Design Pattern:** Object-Oriented Programming (OOP)
- **Version Control:** Git & GitHub

## Outcome

Successfully created a fully functional ATM Interface System that demonstrates:
- Secure authentication and session management
- Complete banking operations (withdraw, deposit, transfer)
- Real-time transaction tracking with detailed history
- Robust error handling and input validation
- Clean, maintainable code following OOP principles

The system provides a realistic ATM experience with professional console interface and comprehensive transaction logging.

## How to Run

```bash
cd OIBSIP_JavaDevelopement_Task1
javac *.java
java ATMInterface
```

## Sample Credentials
- Account: 12345 | PIN: 1234
- Account: 67890 | PIN: 5678
- Account: 11111 | PIN: 0000
