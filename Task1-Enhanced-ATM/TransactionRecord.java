/**
 * TransactionRecord Class - Stores individual transaction details
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionRecord {
    private String transactionType;
    private double transactionAmount;
    private String transactionDetails;
    private Date transactionDateTime;
    
    public TransactionRecord(String type, double amount, String details) {
        this.transactionType = type;
        this.transactionAmount = amount;
        this.transactionDetails = details;
        this.transactionDateTime = new Date();
    }
    
    // Getter methods
    public String getTransactionType() {
        return transactionType;
    }
    
    public double getTransactionAmount() {
        return transactionAmount;
    }
    
    public String getTransactionDetails() {
        return transactionDetails;
    }
    
    public Date getTransactionDateTime() {
        return transactionDateTime;
    }
    
    public String getFormattedDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
        return formatter.format(transactionDateTime);
    }
    
    public String getFormattedAmount() {
        return String.format("%.2f", Math.abs(transactionAmount));
    }
    
    public boolean isDebit() {
        return transactionAmount < 0;
    }
    
    public boolean isCredit() {
        return transactionAmount > 0;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s: Rs.%.2f - %s", 
                getFormattedDateTime(), transactionType, 
                Math.abs(transactionAmount), transactionDetails);
    }
}