/**
 * ATM class that handles all ATM operations and manages user accounts
 */
public class ATM {
    private Account currentAccount;
    private UserDatabase userDatabase;
    
    public ATM() {
        this.userDatabase = new UserDatabase();
    }
    
    /**
     * Authenticates user with provided credentials
     */
    public boolean authenticate(String userId, String pin) {
        currentAccount = userDatabase.getAccount(userId, pin);
        if (currentAccount != null) {
            System.out.println("Authentication successful!");
            System.out.println("Welcome, " + currentAccount.getAccountHolderName());
            return true;
        } else {
            System.out.println("Invalid User ID or PIN!");
            return false;
        }
    }
    
    /**
     * Displays transaction history for current account
     */
    public void showTransactionHistory() {
        if (currentAccount == null) {
            System.out.println("No account logged in.");
            return;
        }
        
        System.out.println("\n=== TRANSACTION HISTORY ===");
        System.out.println("Account: " + currentAccount.getAccountNumber());
        System.out.println("Current Balance: $" + String.format("%.2f", currentAccount.getBalance()));
        System.out.println("\nRecent Transactions:");
        
        for (Transaction transaction : currentAccount.getTransactionHistory()) {
            System.out.println(transaction);
        }
        
        if (currentAccount.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions found.");
        }
    }
    
    /**
     * Withdraws specified amount from current account
     */
    public void withdraw(double amount) {
        if (currentAccount == null) {
            System.out.println("No account logged in.");
            return;
        }
        
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
            return;
        }
        
        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient funds. Current balance: $" + 
                             String.format("%.2f", currentAccount.getBalance()));
            return;
        }
        
        currentAccount.debit(amount);
        Transaction transaction = new Transaction("WITHDRAW", amount, currentAccount.getBalance());
        currentAccount.addTransaction(transaction);
        
        System.out.println("Withdrawal successful!");
        System.out.println("Amount withdrawn: $" + String.format("%.2f", amount));
        System.out.println("Current balance: $" + String.format("%.2f", currentAccount.getBalance()));
    }
    
    /**
     * Deposits specified amount to current account
     */
    public void deposit(double amount) {
        if (currentAccount == null) {
            System.out.println("No account logged in.");
            return;
        }
        
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
            return;
        }
        
        currentAccount.credit(amount);
        Transaction transaction = new Transaction("DEPOSIT", amount, currentAccount.getBalance());
        currentAccount.addTransaction(transaction);
        
        System.out.println("Deposit successful!");
        System.out.println("Amount deposited: $" + String.format("%.2f", amount));
        System.out.println("Current balance: $" + String.format("%.2f", currentAccount.getBalance()));
    }
    
    /**
     * Transfers specified amount to another account
     */
    public void transfer(String recipientAccount, double amount) {
        if (currentAccount == null) {
            System.out.println("No account logged in.");
            return;
        }
        
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
            return;
        }
        
        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient funds. Current balance: $" + 
                             String.format("%.2f", currentAccount.getBalance()));
            return;
        }
        
        if (recipientAccount.equals(currentAccount.getAccountNumber())) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }
        
        // In a real system, you'd verify the recipient account exists
        currentAccount.debit(amount);
        Transaction transaction = new Transaction("TRANSFER TO " + recipientAccount, amount, currentAccount.getBalance());
        currentAccount.addTransaction(transaction);
        
        System.out.println("Transfer successful!");
        System.out.println("Amount transferred: $" + String.format("%.2f", amount));
        System.out.println("To account: " + recipientAccount);
        System.out.println("Current balance: $" + String.format("%.2f", currentAccount.getBalance()));
    }
}