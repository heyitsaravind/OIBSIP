import java.util.*;

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
    }
    
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
        return transactionHistory;
    }
    
    public void deductAmount(double amount) {
        this.accountBalance -= amount;
        recordActivity("Withdrawal", -amount, "Cash withdrawn");
    }
    
    public void addAmount(double amount) {
        this.accountBalance += amount;
        recordActivity("Deposit", amount, "Cash deposited");
    }
    
    public void recordActivity(String type, double amount, String details) {
        transactionHistory.add(new TransactionRecord(type, amount, details));
    }
}
