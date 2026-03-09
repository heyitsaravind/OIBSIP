import java.text.SimpleDateFormat;
import java.util.*;

public class IssuedBook {
    private String bookId;
    private String username;
    private String userName;
    private Date issueDate;
    private Date dueDate;
    
    public IssuedBook(String bookId, String username, String userName) {
        this.bookId = bookId;
        this.username = username;
        this.userName = userName;
        this.issueDate = new Date();
        
        // Set due date to 14 days from issue date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        this.dueDate = calendar.getTime();
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getIssueDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(issueDate);
    }
    
    public String getDueDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(dueDate);
    }
}
