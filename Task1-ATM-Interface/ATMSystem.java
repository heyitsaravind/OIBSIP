import java.util.Scanner;

/**
 * Main ATM System class that handles the application flow
 */
public class ATMSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static ATM atm = new ATM();
    
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("    Welcome to ATM System");
        System.out.println("=================================");
        
        // Authentication
        if (authenticateUser()) {
            showMainMenu();
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
        
        scanner.close();
    }
    
    private static boolean authenticateUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        
        return atm.authenticate(userId, pin);
    }
    
    private static void showMainMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n=================================");
            System.out.println("           ATM MENU");
            System.out.println("=================================");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("=================================");
            System.out.print("Select an option (1-5): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    atm.showTransactionHistory();
                    break;
                case 2:
                    handleWithdraw();
                    break;
                case 3:
                    handleDeposit();
                    break;
                case 4:
                    handleTransfer();
                    break;
                case 5:
                    System.out.println("Thank you for using ATM System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void handleWithdraw() {
        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();
        atm.withdraw(amount);
    }
    
    private static void handleDeposit() {
        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        atm.deposit(amount);
    }
    
    private static void handleTransfer() {
        System.out.print("Enter recipient account number: ");
        String recipientAccount = scanner.next();
        System.out.print("Enter amount to transfer: $");
        double amount = scanner.nextDouble();
        atm.transfer(recipientAccount, amount);
    }
}