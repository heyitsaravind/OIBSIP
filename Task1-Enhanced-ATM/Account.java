/**
 * Account Class - Represents a bank account with enhanced features
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String pin;
    private String holderName;
    private double balance;
    private List<Transaction> transactions;
    private LocalDateTime createdAt;
    private LocalDateTime lastActivity;
    
    public Account(String accountNumber, String pin, String holderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.holderName = holderName;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.lastActivity = LocalDateTime.now();
        
        // Add initial balance as first transaction
        addTransaction("Account Opening", initialBalance, "Initial deposit - Account created");
    }
    
    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getPin() {
        return pin;
    }
    
    public String getHolderName() {
        return holderName;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // Return copy to prevent external modification
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getLastActivity() {
        return lastActivity;
    }
    
    // Setters
    public void setPin(String newPin) {
        this.pin = newPin;
        updateLastActivity();
    }
    
    public void setHolderName(String newName) {
        this.holderName = newName;
        updateLastActivity();
    }
    
    // Transaction methods
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        
        if (amount > balance) {
            return false;
        }
        
        balance -= amount;
        addTransaction("Withdrawal", -amount, "Cash withdrawal from ATM");
        updateLastActivity();
        return true;
    }
    
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        
        balance += amount;
        addTransaction("Deposit", amount, "Cash deposit to account");
        updateLastActivity();
        return true;
    }
    
    public void addTransaction(String type, double amount, String description) {
        Transaction transaction = new Transaction(type, amount, description);
        transactions.add(transaction);
        
        // Keep only last 50 transactions to manage memory
        if (transactions.size() > 50) {
            transactions.remove(0);
        }
        
        updateLastActivity();
    }
    
    private void updateLastActivity() {
        this.lastActivity = LocalDateTime.now();
    }
    
    // Utility methods
    public boolean hasTransactions() {
        return !transactions.isEmpty();
    }
    
    public int getTransactionCount() {
        return transactions.size();
    }
    
    public Transaction getLastTransaction() {
        if (transactions.isEmpty()) {
            return null;
        }
        return transactions.get(transactions.size() - 1);
    }
    
    public double getTotalDeposits() {
        return transactions.stream()
                .filter(t -> t.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    public double getTotalWithdrawals() {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .mapToDouble(t -> Math.abs(t.getAmount()))
                .sum();
    }
    
    @Override
    public String toString() {
        return String.format("Account{number='%s', holder='%s', balance=%.2f, transactions=%d}", 
                accountNumber, holderName, balance, transactions.size());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Account account = (Account) obj;
        return accountNumber.equals(account.accountNumber);
    }
    
    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
}