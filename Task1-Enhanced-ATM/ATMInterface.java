/**
 * ATM Interface System
 * A simple and efficient ATM banking application
 * Author: Aravind M S
 */

import java.util.*;
import java.text.SimpleDateFormat;

public class ATMInterface {
    private static Scanner input = new Scanner(System.in);
    private static Map<String, BankAccount> accountDatabase = new HashMap<>();
    private static BankAccount loggedInAccount = null;
    
    public static void main(String[] args) {
        setupSampleAccounts();
        displayWelcomeBanner();
        
        while (true) {
            if (loggedInAccount == null) {
                handleLoginProcess();
            } else {
                displayMainMenu();
            }
        }
    }
    
    private static void setupSampleAccounts() {
        accountDatabase.put("12345", new BankAccount("12345", "1234", "Aravind Sreekumar", 15000.0));
        accountDatabase.put("67890", new BankAccount("67890", "5678", "Shreyas R Pillai", 25000.0));
        accountDatabase.put("11111", new BankAccount("11111", "0000", "Vighnesh C", 5000.0));
    }
    
    private static void displayWelcomeBanner() {
        System.out.println("\n" + repeatChar('=', 55));
        System.out.println("          WELCOME TO ATM BANKING SYSTEM");
        System.out.println("              Your Money, Our Priority");
        System.out.println(repeatChar('=', 55));
    }
    
    private static void handleLoginProcess() {
        System.out.println("\nPlease authenticate to continue:");
        System.out.println("1. Login to your account");
        System.out.println("2. View sample credentials");
        System.out.println("3. Exit application");
        System.out.print("\nEnter your choice: ");
        
        try {
            int option = Integer.parseInt(input.nextLine().trim());
            
            switch (option) {
                case 1:
                    performLogin();
                    break;
                case 2:
                    displaySampleCredentials();
                    break;
                case 3:
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    private static void performLogin() {
        System.out.println("\n" + repeatChar('-', 40));
        System.out.println("           ACCOUNT LOGIN");
        System.out.println(repeatChar('-', 40));
        
        System.out.print("Enter Account Number: ");
        String accountNum = input.nextLine().trim();
        
        System.out.print("Enter PIN: ");
        String pin = input.nextLine().trim();
        
        if (validateCredentials(accountNum, pin)) {
            loggedInAccount = accountDatabase.get(accountNum);
            System.out.println("\nLogin successful!");
            System.out.println("Welcome, " + loggedInAccount.getAccountHolderName());
            loggedInAccount.recordActivity("Login", 0.0, "Account accessed successfully");
        } else {
            System.out.println("\nLogin failed!");
            System.out.println("Invalid account number or PIN. Please try again.");
        }
    }
    
    private static boolean validateCredentials(String accountNum, String pin) {
        BankAccount account = accountDatabase.get(accountNum);
        return account != null && account.getPinCode().equals(pin);
    }
    
    private static void displaySampleCredentials() {
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("         SAMPLE ACCOUNT CREDENTIALS");
        System.out.println(repeatChar('=', 50));
        System.out.println("Account Number: 12345  |  PIN: 1234");
        System.out.println("Account Number: 67890  |  PIN: 5678");
        System.out.println("Account Number: 11111  |  PIN: 0000");
        System.out.println(repeatChar('=', 50));
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("ATM MAIN MENU");
        System.out.println("Account: " + loggedInAccount.getAccountNumber() + 
                         " | " + loggedInAccount.getAccountHolderName());
        System.out.println(repeatChar('=', 50));
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("\nSelect an option: ");
        
        try {
            int choice = Integer.parseInt(input.nextLine().trim());
            
            switch (choice) {
                case 1:
                    viewTransactionHistory();
                    break;
                case 2:
                    performWithdrawal();
                    break;
                case 3:
                    performDeposit();
                    break;
                case 4:
                    performTransfer();
                    break;
                case 5:
                    performLogout();
                    break;
                default:
                    System.out.println("Invalid option. Please select 1-5.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    private static void viewTransactionHistory() {
        System.out.println("\n" + repeatChar('=', 70));
        System.out.println("TRANSACTION HISTORY");
        System.out.println("Account: " + loggedInAccount.getAccountNumber());
        System.out.println("Current Balance: Rs. " + formatAmount(loggedInAccount.getAccountBalance()));
        System.out.println(repeatChar('=', 70));
        
        List<TransactionRecord> history = loggedInAccount.getTransactionHistory();
        
        if (history.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }
        
        System.out.printf("%-20s %-15s %-15s %-20s%n", 
                         "Date & Time", "Type", "Amount (Rs.)", "Details");
        System.out.println(repeatChar('-', 70));
        
        for (TransactionRecord record : history) {
            System.out.printf("%-20s %-15s %-15s %-20s%n",
                record.getFormattedDateTime(),
                record.getTransactionType(),
                formatAmount(Math.abs(record.getTransactionAmount())),
                record.getTransactionDetails()
            );
        }
        System.out.println(repeatChar('=', 70));
    }
    
    private static void performWithdrawal() {
        System.out.println("\n" + repeatChar('-', 40));
        System.out.println("         CASH WITHDRAWAL");
        System.out.println(repeatChar('-', 40));
        System.out.println("Available Balance: Rs. " + formatAmount(loggedInAccount.getAccountBalance()));
        System.out.print("Enter amount to withdraw: Rs. ");
        
        try {
            double amount = Double.parseDouble(input.nextLine().trim());
            
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }
            
            if (amount > loggedInAccount.getAccountBalance()) {
                System.out.println("Insufficient balance!");
                System.out.println("Your current balance is Rs. " + 
                                 formatAmount(loggedInAccount.getAccountBalance()));
                return;
            }
            
            if (amount > 50000) {
                System.out.println("Withdrawal limit exceeded!");
                System.out.println("Maximum withdrawal per transaction: Rs. 50,000");
                return;
            }
            
            loggedInAccount.deductAmount(amount);
            System.out.println("\nWithdrawal successful!");
            System.out.println("Amount withdrawn: Rs. " + formatAmount(amount));
            System.out.println("Remaining balance: Rs. " + formatAmount(loggedInAccount.getAccountBalance()));
            System.out.println("Transaction completed at: " + getCurrentDateTime());
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
        }
    }
    
    private static void performDeposit() {
        System.out.println("\n" + repeatChar('-', 40));
        System.out.println("         CASH DEPOSIT");
        System.out.println(repeatChar('-', 40));
        System.out.println("Current Balance: Rs. " + formatAmount(loggedInAccount.getAccountBalance()));
        System.out.print("Enter amount to deposit: Rs. ");
        
        try {
            double amount = Double.parseDouble(input.nextLine().trim());
            
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }
            
            if (amount > 100000) {
                System.out.println("Deposit limit exceeded!");
                System.out.println("Maximum deposit per transaction: Rs. 1,00,000");
                return;
            }
            
            loggedInAccount.addAmount(amount);
            System.out.println("\nDeposit successful!");
            System.out.println("Amount deposited: Rs. " + formatAmount(amount));
            System.out.println("Updated balance: Rs. " + formatAmount(loggedInAccount.getAccountBalance()));
            System.out.println("Transaction completed at: " + getCurrentDateTime());
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
        }
    }
    
    private static void performTransfer() {
        System.out.println("\n" + repeatChar('-', 40));
        System.out.println("         FUND TRANSFER");
        System.out.println(repeatChar('-', 40));
        System.out.println("Available Balance: Rs. " + formatAmount(loggedInAccount.getAccountBalance()));
        
        System.out.print("Enter recipient account number: ");
        String recipientAccountNum = input.nextLine().trim();
        
        if (recipientAccountNum.equals(loggedInAccount.getAccountNumber())) {
            System.out.println("Cannot transfer to the same account!");
            return;
        }
        
        BankAccount recipientAccount = accountDatabase.get(recipientAccountNum);
        if (recipientAccount == null) {
            System.out.println("Recipient account not found!");
            return;
        }
        
        System.out.println("Recipient: " + recipientAccount.getAccountHolderName());
        System.out.print("Enter amount to transfer: Rs. ");
        
        try {
            double amount = Double.parseDouble(input.nextLine().trim());
            
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }
            
            if (amount > loggedInAccount.getAccountBalance()) {
                System.out.println("Insufficient balance!");
                return;
            }
            
            if (amount > 25000) {
                System.out.println("Transfer limit exceeded!");
                System.out.println("Maximum transfer per transaction: Rs. 25,000");
                return;
            }
            
            // Execute transfer
            loggedInAccount.deductAmount(amount);
            recipientAccount.addAmount(amount);
            
            // Record transactions
            loggedInAccount.recordActivity("Transfer Out", -amount, 
                "To " + recipientAccount.getAccountHolderName() + " (" + recipientAccountNum + ")");
            recipientAccount.recordActivity("Transfer In", amount, 
                "From " + loggedInAccount.getAccountHolderName() + " (" + loggedInAccount.getAccountNumber() + ")");
            
            System.out.println("\nTransfer successful!");
            System.out.println("Amount transferred: Rs. " + formatAmount(amount));
            System.out.println("Recipient: " + recipientAccount.getAccountHolderName());
            System.out.println("Remaining balance: Rs. " + formatAmount(loggedInAccount.getAccountBalance()));
            System.out.println("Transaction completed at: " + getCurrentDateTime());
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
        }
    }
    
    private static void performLogout() {
        System.out.println("\nLogging out...");
        System.out.println("Thank you for using our ATM service, " + 
                         loggedInAccount.getAccountHolderName() + "!");
        
        loggedInAccount.recordActivity("Logout", 0.0, "Session ended");
        loggedInAccount = null;
        
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("Session terminated successfully.");
        System.out.println("Have a great day!");
        System.out.println(repeatChar('=', 50));
    }
    
    private static void exitApplication() {
        System.out.println("\nThank you for using our ATM Banking System!");
        System.out.println("Goodbye!");
        System.exit(0);
    }
    
    private static String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }
    
    private static String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(new Date());
    }
    
    private static String repeatChar(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
