# Enhanced ATM Banking System

A comprehensive console-based ATM interface that simulates real banking operations with modern features and security measures.

## 🌟 Features

### Core Banking Operations
- **Account Authentication** - Secure login with username/PIN validation
- **Balance Inquiry** - Real-time account balance checking
- **Cash Withdrawal** - Secure money withdrawal with limit validation
- **Cash Deposit** - Easy money deposit functionality
- **Fund Transfer** - Transfer money between accounts
- **PIN Management** - Change PIN with security validation

### Advanced Features
- **Transaction History** - Complete record of all transactions
- **Account Information** - Detailed account profile view
- **Security Limits** - Daily withdrawal and deposit limits
- **Real-time Updates** - Instant balance and availability updates
- **User-friendly Interface** - Intuitive menu system with emojis
- **Error Handling** - Comprehensive input validation and error messages

### Security Features
- **PIN Validation** - 4-digit PIN security
- **Transaction Limits** - Configurable daily limits
- **Session Management** - Secure login/logout functionality
- **Input Validation** - Protection against invalid inputs

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Command line interface

### Installation & Running
```bash
# Compile the Java files
javac *.java

# Run the main application
java BankingSystem
```

### Demo Accounts
```
Account: 12345 | PIN: 1234 | Balance: ₹15,000
Account: 67890 | PIN: 5678 | Balance: ₹25,000
Account: 11111 | PIN: 0000 | Balance: ₹5,000
```

## 🏗️ Architecture

### Class Structure
- **BankingSystem.java** - Main application with user interface
- **Account.java** - Account management and operations
- **Transaction.java** - Transaction record management

### Key Components
1. **Authentication Module** - Handles user login/logout
2. **Transaction Engine** - Processes all banking operations
3. **Security Layer** - Validates inputs and enforces limits
4. **User Interface** - Console-based interactive menus

## 💡 Usage Examples

### Login Process
```
🔐 AUTHENTICATION REQUIRED
1. 🆔 Login to Account
2. 📋 View Demo Credentials
3. ❌ Exit System

👉 Select option (1-3): 1

🔑 LOGIN TO YOUR ACCOUNT
🆔 Enter Account Number: 12345
🔐 Enter PIN: 1234

✅ Login Successful!
🎉 Welcome back, Aravind Kumar!
```

### Making a Transaction
```
💳 BANKING SERVICES MENU
1. 💰 Check Account Balance
2. 💸 Withdraw Money
3. 💵 Deposit Money
4. 🔄 Transfer Funds

👉 Select service (1-8): 2

💸 CASH WITHDRAWAL
Available Balance: ₹15,000.00
💵 Enter withdrawal amount: ₹5000

✅ Withdrawal Successful!
💸 Amount Withdrawn: ₹5,000.00
💰 Remaining Balance: ₹10,000.00
```

## 🔧 Technical Details

### Transaction Limits
- **Withdrawal**: ₹50,000 per transaction
- **Deposit**: ₹1,00,000 per transaction
- **Transfer**: ₹25,000 per transaction

### Data Management
- In-memory storage for demo purposes
- Transaction history (last 50 transactions)
- Real-time balance updates
- Automatic timestamp generation

### Error Handling
- Invalid PIN attempts
- Insufficient funds validation
- Amount limit checking
- Input format validation

## 🎯 Learning Objectives

This project demonstrates:
- **Object-Oriented Programming** - Classes, encapsulation, inheritance
- **Java Collections** - HashMap, ArrayList usage
- **Date/Time Handling** - LocalDateTime operations
- **Input Validation** - Scanner and exception handling
- **Console UI Design** - User-friendly interface creation
- **Banking Logic** - Real-world transaction processing

## 🔮 Future Enhancements

- Database integration for persistent storage
- Multi-currency support
- Account statement generation
- Mobile number/email verification
- Biometric authentication simulation
- Transaction categorization
- Spending analytics

## 👨‍💻 Author

**Aravind M S**
- Email: aravindms046@gmail.com
- GitHub: heyitsaravind
- Project: OIBSIP Java Development Internship

## 📝 License

This project is created for educational purposes as part of the Oasis Infobyte internship program.

---

*Built with ❤️ for learning and demonstration purposes*