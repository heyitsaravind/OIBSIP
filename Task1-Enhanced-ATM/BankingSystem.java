/**
 * Enhanced ATM Banking System
 * A comprehensive console-based banking application with modern features
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Account> accounts = new HashMap<>();
    private static Account currentUser = null;
    
    public static void main(String[] args) {
        initializeSampleAccounts();
        displayWelcomeMessage();
        
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }
    
    private static void initializeSampleAccounts() {
        accounts.put("12345", new Account("12345", "1234", "Aravind Kumar", 15000.0));
        accounts.put("67890", new Account("67890", "5678", "Priya Sharma", 25000.0));
        accounts.put("11111", new Account("11111", "0000", "Demo User", 5000.0));
    }
    
    private static void displayWelcomeMessage() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🏦  WELCOME TO SECURE BANKING SYSTEM  🏦");
        System.out.println("    Your Trusted Financial Partner");
        System.out.println("=".repeat(60));
        System.out.println("📅 Current Time: " + getCurrentTimestamp());
        System.out.println("🔒 Secure • Reliable • Fast");
        System.out.println("=".repeat(60));
    }
    
    private static void showLoginMenu() {
        System.out.println("\n🔐 AUTHENTICATION REQUIRED");
        System.out.println("1. 🆔 Login to Account");
        System.out.println("2. 📋 View Demo Credentials");
        System.out.println("3. ❌ Exit System");
        System.out.print("\n👉 Select option (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    performLogin();
                    break;
                case 2:
                    showDemoCredentials();
                    break;
                case 3:
                    exitSystem();
                    break;
                default:
                    System.out.println("❌ Invalid option! Please select 1-3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void performLogin() {
        System.out.println("\n🔑 LOGIN TO YOUR ACCOUNT");
        System.out.println("-".repeat(30));
        
        System.out.print("🆔 Enter Account Number: ");
        String accountNumber = scanner.nextLine().trim();
        
        System.out.print("🔐 Enter PIN: ");
        String pin = scanner.nextLine().trim();
        
        if (authenticateUser(accountNumber, pin)) {
            currentUser = accounts.get(accountNumber);
            System.out.println("\n✅ Login Successful!");
            System.out.println("🎉 Welcome back, " + currentUser.getHolderName() + "!");
            currentUser.addTransaction("Login", 0.0, "Successful login at " + getCurrentTimestamp());
        } else {
            System.out.println("\n❌ Authentication Failed!");
            System.out.println("🚫 Invalid account number or PIN. Please try again.");
        }
    }
    
    private static boolean authenticateUser(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);
        return account != null && account.getPin().equals(pin);
    }
    
    private static void showDemoCredentials() {
        System.out.println("\n📋 DEMO ACCOUNT CREDENTIALS");
        System.out.println("=".repeat(40));
        System.out.println("Account: 12345 | PIN: 1234 | Balance: ₹15,000");
        System.out.println("Account: 67890 | PIN: 5678 | Balance: ₹25,000");
        System.out.println("Account: 11111 | PIN: 0000 | Balance: ₹5,000");
        System.out.println("=".repeat(40));
    }
    
    private static void showMainMenu() {
        System.out.println("\n💳 BANKING SERVICES MENU");
        System.out.println("Account: " + currentUser.getAccountNumber() + " | " + currentUser.getHolderName());
        System.out.println("=".repeat(50));
        System.out.println("1. 💰 Check Account Balance");
        System.out.println("2. 💸 Withdraw Money");
        System.out.println("3. 💵 Deposit Money");
        System.out.println("4. 🔄 Transfer Funds");
        System.out.println("5. 📊 Transaction History");
        System.out.println("6. 👤 Account Information");
        System.out.println("7. 🔐 Change PIN");
        System.out.println("8. 🚪 Logout");
        System.out.print("\n👉 Select service (1-8): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdrawMoney();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    transferFunds();
                    break;
                case 5:
                    showTransactionHistory();
                    break;
                case 6:
                    showAccountInfo();
                    break;
                case 7:
                    changePIN();
                    break;
                case 8:
                    logout();
                    break;
                default:
                    System.out.println("❌ Invalid option! Please select 1-8.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void checkBalance() {
        System.out.println("\n💰 ACCOUNT BALANCE INQUIRY");
        System.out.println("=".repeat(35));
        System.out.println("Account Holder: " + currentUser.getHolderName());
        System.out.println("Account Number: " + currentUser.getAccountNumber());
        System.out.println("Current Balance: ₹" + String.format("%.2f", currentUser.getBalance()));
        System.out.println("Last Updated: " + getCurrentTimestamp());
        System.out.println("=".repeat(35));
        
        currentUser.addTransaction("Balance Inquiry", 0.0, "Balance checked");
    }
    
    private static void withdrawMoney() {
        System.out.println("\n💸 CASH WITHDRAWAL");
        System.out.println("-".repeat(25));
        System.out.println("Available Balance: ₹" + String.format("%.2f", currentUser.getBalance()));
        System.out.print("💵 Enter withdrawal amount: ₹");
        
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            
            if (amount <= 0) {
                System.out.println("❌ Invalid amount! Please enter a positive value.");
                return;
            }
            
            if (amount > currentUser.getBalance()) {
                System.out.println("❌ Insufficient funds!");
                System.out.println("💡 Available balance: ₹" + String.format("%.2f", currentUser.getBalance()));
                return;
            }
            
            if (amount > 50000) {
                System.out.println("❌ Daily withdrawal limit exceeded!");
                System.out.println("💡 Maximum withdrawal: ₹50,000 per transaction");
                return;
            }
            
            currentUser.withdraw(amount);
            System.out.println("\n✅ Withdrawal Successful!");
            System.out.println("💸 Amount Withdrawn: ₹" + String.format("%.2f", amount));
            System.out.println("💰 Remaining Balance: ₹" + String.format("%.2f", currentUser.getBalance()));
            System.out.println("📅 Transaction Time: " + getCurrentTimestamp());
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid amount!");
        }
    }
    
    private static void depositMoney() {
        System.out.println("\n💵 CASH DEPOSIT");
        System.out.println("-".repeat(20));
        System.out.println("Current Balance: ₹" + String.format("%.2f", currentUser.getBalance()));
        System.out.print("💰 Enter deposit amount: ₹");
        
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            
            if (amount <= 0) {
                System.out.println("❌ Invalid amount! Please enter a positive value.");
                return;
            }
            
            if (amount > 100000) {
                System.out.println("❌ Daily deposit limit exceeded!");
                System.out.println("💡 Maximum deposit: ₹1,00,000 per transaction");
                return;
            }
            
            currentUser.deposit(amount);
            System.out.println("\n✅ Deposit Successful!");
            System.out.println("💵 Amount Deposited: ₹" + String.format("%.2f", amount));
            System.out.println("💰 Updated Balance: ₹" + String.format("%.2f", currentUser.getBalance()));
            System.out.println("📅 Transaction Time: " + getCurrentTimestamp());
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid amount!");
        }
    }
    
    private static void transferFunds() {
        System.out.println("\n🔄 FUND TRANSFER");
        System.out.println("-".repeat(25));
        System.out.println("Available Balance: ₹" + String.format("%.2f", currentUser.getBalance()));
        
        System.out.print("🎯 Enter recipient account number: ");
        String recipientAccount = scanner.nextLine().trim();
        
        if (recipientAccount.equals(currentUser.getAccountNumber())) {
            System.out.println("❌ Cannot transfer to the same account!");
            return;
        }
        
        Account recipient = accounts.get(recipientAccount);
        if (recipient == null) {
            System.out.println("❌ Recipient account not found!");
            return;
        }
        
        System.out.println("✅ Recipient: " + recipient.getHolderName());
        System.out.print("💰 Enter transfer amount: ₹");
        
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            
            if (amount <= 0) {
                System.out.println("❌ Invalid amount! Please enter a positive value.");
                return;
            }
            
            if (amount > currentUser.getBalance()) {
                System.out.println("❌ Insufficient funds!");
                return;
            }
            
            if (amount > 25000) {
                System.out.println("❌ Transfer limit exceeded!");
                System.out.println("💡 Maximum transfer: ₹25,000 per transaction");
                return;
            }
            
            // Perform transfer
            currentUser.withdraw(amount);
            recipient.deposit(amount);
            
            // Add transaction records
            currentUser.addTransaction("Fund Transfer", -amount, 
                "Transferred to " + recipient.getHolderName() + " (" + recipientAccount + ")");
            recipient.addTransaction("Fund Transfer", amount, 
                "Received from " + currentUser.getHolderName() + " (" + currentUser.getAccountNumber() + ")");
            
            System.out.println("\n✅ Transfer Successful!");
            System.out.println("💸 Amount Transferred: ₹" + String.format("%.2f", amount));
            System.out.println("👤 To: " + recipient.getHolderName());
            System.out.println("💰 Remaining Balance: ₹" + String.format("%.2f", currentUser.getBalance()));
            System.out.println("📅 Transaction Time: " + getCurrentTimestamp());
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid amount!");
        }
    }
    
    private static void showTransactionHistory() {
        System.out.println("\n📊 TRANSACTION HISTORY");
        System.out.println("Account: " + currentUser.getAccountNumber() + " | " + currentUser.getHolderName());
        System.out.println("=".repeat(70));
        
        List<Transaction> transactions = currentUser.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("📝 No transactions found.");
            return;
        }
        
        System.out.printf("%-20s %-15s %-12s %-20s%n", "Date & Time", "Type", "Amount", "Description");
        System.out.println("-".repeat(70));
        
        for (Transaction transaction : transactions) {
            System.out.printf("%-20s %-15s ₹%-10.2f %-20s%n",
                transaction.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")),
                transaction.getType(),
                Math.abs(transaction.getAmount()),
                transaction.getDescription()
            );
        }
        System.out.println("=".repeat(70));
    }
    
    private static void showAccountInfo() {
        System.out.println("\n👤 ACCOUNT INFORMATION");
        System.out.println("=".repeat(40));
        System.out.println("Account Holder: " + currentUser.getHolderName());
        System.out.println("Account Number: " + currentUser.getAccountNumber());
        System.out.println("Account Type: Savings Account");
        System.out.println("Current Balance: ₹" + String.format("%.2f", currentUser.getBalance()));
        System.out.println("Account Status: Active");
        System.out.println("Last Login: " + getCurrentTimestamp());
        System.out.println("Total Transactions: " + currentUser.getTransactions().size());
        System.out.println("=".repeat(40));
    }
    
    private static void changePIN() {
        System.out.println("\n🔐 CHANGE PIN");
        System.out.println("-".repeat(20));
        
        System.out.print("🔒 Enter current PIN: ");
        String currentPIN = scanner.nextLine().trim();
        
        if (!currentPIN.equals(currentUser.getPin())) {
            System.out.println("❌ Incorrect current PIN!");
            return;
        }
        
        System.out.print("🆕 Enter new PIN (4 digits): ");
        String newPIN = scanner.nextLine().trim();
        
        if (newPIN.length() != 4 || !newPIN.matches("\\d{4}")) {
            System.out.println("❌ PIN must be exactly 4 digits!");
            return;
        }
        
        System.out.print("🔄 Confirm new PIN: ");
        String confirmPIN = scanner.nextLine().trim();
        
        if (!newPIN.equals(confirmPIN)) {
            System.out.println("❌ PINs do not match!");
            return;
        }
        
        currentUser.setPin(newPIN);
        System.out.println("\n✅ PIN changed successfully!");
        System.out.println("🔒 Your account is now secured with the new PIN.");
        
        currentUser.addTransaction("PIN Change", 0.0, "PIN updated successfully");
    }
    
    private static void logout() {
        System.out.println("\n🚪 LOGGING OUT...");
        System.out.println("👋 Thank you for using our banking services, " + currentUser.getHolderName() + "!");
        System.out.println("🔒 Your session has been securely terminated.");
        
        currentUser.addTransaction("Logout", 0.0, "Session ended at " + getCurrentTimestamp());
        currentUser = null;
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🏦 Have a great day! Visit us again soon! 🏦");
        System.out.println("=".repeat(50));
    }
    
    private static void exitSystem() {
        System.out.println("\n👋 THANK YOU FOR CHOOSING OUR BANKING SYSTEM!");
        System.out.println("🔒 Exiting securely...");
        System.out.println("💙 Your trust is our priority. Stay safe!");
        System.exit(0);
    }
    
    private static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}