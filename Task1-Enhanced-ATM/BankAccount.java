/**
 * BankAccount Class - Manages individual bank account operations
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountNumber;
    private String pinCode;
    private String accountHolderName;
    private double accountBalance;
    private List<TransactionRecord> transactionHistory;
    
    public BankAccount(String accountNumber, String pinCode, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pinCode = pinCode;
        this.accountHolderName = accountHolderName;
        this.accountBalance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        
        // Record initial balance
        recordActivity("Account Opening", initialBalance, "Initial deposit");
    }
    
    // Getter methods
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getPinCode() {
        return pinCode;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public double getAccountBalance() {
        return accountBalance;
    }
    
    public List<TransactionRecord> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
    
    // Transaction methods
    public boolean deductAmount(double amount) {
        if (amount <= 0 || amount > accountBalance) {
            return false;
        }
        
        accountBalance -= amount;
        recordActivity("Withdrawal", -amount, "Cash withdrawal from ATM");
        return true;
    }
    
    public boolean addAmount(double amount) {
        if (amount <= 0) {
            return false;
        }
        
        accountBalance += amount;
        recordActivity("Deposit", amount, "Cash deposit to account");
        return true;
    }
    
    public void recordActivity(String type, double amount, String details) {
        TransactionRecord record = new TransactionRecord(type, amount, details);
        transactionHistory.add(record);
        
        // Keep only last 50 transactions
        if (transactionHistory.size() > 50) {
            transactionHistory.remove(0);
        }
    }
    
    // Utility methods
    public int getTotalTransactions() {
        return transactionHistory.size();
    }
    
    public boolean hasTransactions() {
        return !transactionHistory.isEmpty();
    }
    
    @Override
    public String toString() {
        return String.format("Account[%s, %s, Balance: %.2f]", 
                accountNumber, accountHolderName, accountBalance);
    }
}