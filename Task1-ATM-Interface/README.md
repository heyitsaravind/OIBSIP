# ATM System - Java Console Application

A comprehensive ATM (Automated Teller Machine) system built in Java for internship project demonstration.

## Project Overview

This console-based ATM system simulates real ATM functionality with user authentication and core banking operations. The system is built using Object-Oriented Programming principles with five main classes.

## Features

- **User Authentication**: Secure login with User ID and PIN
- **Transaction History**: View recent account transactions
- **Withdraw**: Withdraw money from account with balance validation
- **Deposit**: Deposit money to account
- **Transfer**: Transfer money to other accounts
- **Balance Management**: Real-time balance updates

## Classes Structure

1. **ATMSystem.java** - Main class handling application flow and user interface
2. **ATM.java** - Core ATM operations and business logic
3. **Account.java** - Account management and basic banking operations
4. **Transaction.java** - Transaction record keeping with timestamps
5. **UserDatabase.java** - User data management and authentication

## How to Run

1. Compile all Java files:
   ```bash
   javac *.java
   ```

2. Run the main application:
   ```bash
   java ATMSystem
   ```

## Sample Test Accounts

The system comes with pre-loaded test accounts:

| User ID | PIN  | Account Holder | Initial Balance |
|---------|------|----------------|-----------------|
| user1   | 1234 | John Doe       | $1,500.00       |
| user2   | 5678 | Jane Smith     | $2,500.50       |
| user3   | 9999 | Bob Johnson    | $750.25         |

## Usage Example

1. Start the application
2. Enter User ID (e.g., "user1")
3. Enter PIN (e.g., "1234")
4. Select from menu options:
   - 1: View transaction history
   - 2: Withdraw money
   - 3: Deposit money
   - 4: Transfer to another account
   - 5: Exit system

## Key Features Demonstrated

- **Object-Oriented Design**: Proper class separation and encapsulation
- **Data Validation**: Input validation for amounts and account verification
- **Error Handling**: Comprehensive error messages and edge case handling
- **Security**: PIN-based authentication and account protection
- **Transaction Tracking**: Complete audit trail of all operations
- **User Experience**: Clean console interface with formatted output

## Technical Highlights

- Uses Java Collections (ArrayList, HashMap) for data management
- Implements proper encapsulation with private fields and public methods
- Demonstrates inheritance and polymorphism concepts
- Includes comprehensive input validation and error handling
- Features formatted output for better user experience
- Maintains transaction history with timestamps

Perfect for demonstrating Java fundamentals, OOP principles, and practical application development skills in an internship setting.