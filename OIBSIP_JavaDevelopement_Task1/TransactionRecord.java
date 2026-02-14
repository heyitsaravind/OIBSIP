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
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public double getTransactionAmount() {
        return transactionAmount;
    }
    
    public String getTransactionDetails() {
        return transactionDetails;
    }
    
    public String getFormattedDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
        return formatter.format(transactionDateTime);
    }
}
