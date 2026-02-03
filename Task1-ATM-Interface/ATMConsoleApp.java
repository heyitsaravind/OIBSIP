import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Console-driven ATM application
 * Handles user login and banking operations
 */
public class ATMConsoleApp {
    private final Scanner input;
    private final ATM atmService;

    public ATMConsoleApp() {
        input = new Scanner(System.in);
        atmService = new ATM();
    }

    public static void main(String[] args) {
        ATMConsoleApp app = new ATMConsoleApp();
        app.startApplication();
    }

    private void startApplication() {
        printHeader();
        if (!login()) {
            System.out.println("Access denied. Program terminated.");
            closeResources();
            return;
        }
        executeMenuLoop();
        closeResources();
    }

    private void printHeader() {
        System.out.println("---------------------------------");
        System.out.println("        SECURE ATM PORTAL         ");
        System.out.println("---------------------------------");
    }

    private boolean login() {
        System.out.print("User ID   : ");
        String userId = input.nextLine();
        System.out.print("User PIN  : ");
        String pin = input.nextLine();
        return atmService.authenticate(userId, pin);
    }

    private void executeMenuLoop() {
        boolean sessionActive = true;
        while (sessionActive) {
            displayOptions();
            int selection = readInteger();
            switch (selection) {
                case 1 -> atmService.showTransactionHistory();
                case 2 -> withdrawFunds();
                case 3 -> depositFunds();
                case 4 -> transferFunds();
                case 5 -> {
                    System.out.println("Session ended. Have a great day!");
                    sessionActive = false;
                }
                default -> System.out.println("Invalid selection. Choose 1–5.");
            }
        }
    }

    private void displayOptions() {
        System.out.println("\n===== AVAILABLE OPERATIONS =====");
        System.out.println("1 → View Transactions");
        System.out.println("2 → Cash Withdrawal");
        System.out.println("3 → Cash Deposit");
        System.out.println("4 → Account Transfer");
        System.out.println("5 → Exit");
        System.out.print("Enter choice: ");
    }

    private void withdrawFunds() {
        System.out.print("Withdrawal amount: ₹");
        double amount = readDouble();
        atmService.withdraw(amount);
    }

    private void depositFunds() {
        System.out.print("Deposit amount: ₹");
        double amount = readDouble();
        atmService.deposit(amount);
    }

    private void transferFunds() {
        System.out.print("Beneficiary account: ");
        String account = input.next();
        System.out.print("Transfer amount: ₹");
        double amount = readDouble();
        atmService.transfer(account, amount);
    }

    private int readInteger() {
        try {
            int value = input.nextInt();
            input.nextLine(); // buffer cleanup
            return value;
        } catch (InputMismatchException e) {
            input.nextLine();
            return -1;
        }
    }

    private double readDouble() {
        try {
            double value = input.nextDouble();
            input.nextLine();
            return value;
        } catch (InputMismatchException e) {
            input.nextLine();
            return 0.0;
        }
    }

    private void closeResources() {
        input.close();
    }
}