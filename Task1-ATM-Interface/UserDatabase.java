import java.util.HashMap;
import java.util.Map;

/**
 * UserDatabase class to simulate a database of user accounts
 */
public class UserDatabase {
    private Map<String, Account> accounts;
    
    public UserDatabase() {
        accounts = new HashMap<>();
        initializeAccounts();
    }
    
    /**
     * Initialize some sample accounts for testing
     */
    private void initializeAccounts() {
        // Create sample accounts
        Account account1 = new Account("123456789", "John Doe", "1234", 1500.00);
        Account account2 = new Account("987654321", "Jane Smith", "5678", 2500.50);
        Account account3 = new Account("555666777", "Bob Johnson", "9999", 750.25);
        
        // Store accounts with userId as key
        accounts.put("user1", account1);
        accounts.put("user2", account2);
        accounts.put("user3", account3);
        
        System.out.println("Sample accounts initialized:");
        System.out.println("User ID: user1, PIN: 1234 (John Doe - $1500.00)");
        System.out.println("User ID: user2, PIN: 5678 (Jane Smith - $2500.50)");
        System.out.println("User ID: user3, PIN: 9999 (Bob Johnson - $750.25)");
        System.out.println();
    }
    
    /**
     * Retrieves account based on userId and PIN
     */
    public Account getAccount(String userId, String pin) {
        Account account = accounts.get(userId);
        
        if (account != null && account.validatePin(pin)) {
            return account;
        }
        
        return null; // Invalid credentials
    }
    
    /**
     * Adds a new account to the database
     */
    public void addAccount(String userId, Account account) {
        accounts.put(userId, account);
    }
    
    /**
     * Checks if userId exists
     */
    public boolean userExists(String userId) {
        return accounts.containsKey(userId);
    }
    
    /**
     * Gets all account information (for admin purposes)
     */
    public void displayAllAccounts() {
        System.out.println("=== ALL ACCOUNTS ===");
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            System.out.println("User ID: " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}