/**
 * Transaction Class - Represents a banking transaction record
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String type;
    private double amount;
    private String description;
    private LocalDateTime timestamp;
    private String transactionId;
    
    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.transactionId = generateTransactionId();
    }
    
    // Getters
    public String getType() {
        return type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    // Utility methods
    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
    
    public String getFormattedAmount() {
        return String.format("₹%.2f", Math.abs(amount));
    }
    
    public boolean isDebit() {
        return amount < 0;
    }
    
    public boolean isCredit() {
        return amount > 0;
    }
    
    private String generateTransactionId() {
        // Generate a simple transaction ID based on timestamp
        long timestamp = System.currentTimeMillis();
        return "TXN" + String.valueOf(timestamp).substring(7);
    }
    
    @Override
    public String toString() {
        String amountStr = isDebit() ? "-₹" + String.format("%.2f", Math.abs(amount)) 
                                    : "+₹" + String.format("%.2f", amount);
        
        return String.format("[%s] %s: %s - %s (ID: %s)", 
                getFormattedTimestamp(), type, amountStr, description, transactionId);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Transaction that = (Transaction) obj;
        return transactionId.equals(that.transactionId);
    }
    
    @Override
    public int hashCode() {
        return transactionId.hashCode();
    }
}