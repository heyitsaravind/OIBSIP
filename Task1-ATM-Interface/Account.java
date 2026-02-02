import java.util.ArrayList;
import java.util.List;

/**
 * Account class representing a bank account with basic operations
 */
public class Account {
    private String accountNumber;
    private String accountHolderName;
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;
    
    public Account(String accountNumber, String accountHolderName, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        
        // Add initial balance as first transaction
        if (initialBalance > 0) {
            Transaction initialTransaction = new Transaction("INITIAL DEPOSIT", initialBalance, initialBalance);
            transactionHistory.add(initialTransaction);
        }
    }
    
    /**
     * Debits (subtracts) amount from account balance
     */
    public void debit(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }
    
    /**
     * Credits (adds) amount to account balance
     */
    public void credit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    /**
     * Adds transaction to history
     */
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        
        // Keep only last 10 transactions for simplicity
        if (transactionHistory.size() > 10) {
            transactionHistory.remove(0);
        }
    }
    
    /**
     * Validates PIN
     */
    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }
    
    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory); // Return copy for security
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + String.format("%.2f", balance) +
                '}';
    }
}