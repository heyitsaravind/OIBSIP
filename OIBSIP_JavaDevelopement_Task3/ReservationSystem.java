/**
 * Online Reservation System
 * A train ticket booking and cancellation system
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.*;
import java.text.SimpleDateFormat;

public class ReservationSystem {
    private static Scanner input = new Scanner(System.in);
    private static Map<String, User> userDatabase = new HashMap<>();
    private static Map<String, Reservation> reservationDatabase = new HashMap<>();
    private static Map<String, Train> trainDatabase = new HashMap<>();
    private static User currentUser = null;
    private static int pnrCounter = 1000;
    
    public static void main(String[] args) {
        initializeData();
        displayWelcomeBanner();
        
        while (true) {
            if (currentUser == null) {
                handleLoginProcess();
            } else {
                displayMainMenu();
            }
        }
    }
    
    private static void initializeData() {
        // Initialize users
        userDatabase.put("user1", new User("user1", "pass123", "Aravind Kumar"));
        userDatabase.put("user2", new User("user2", "pass456", "Priya Sharma"));
        
        // Initialize trains
        trainDatabase.put("12345", new Train("12345", "Rajdhani Express", "Delhi", "Mumbai"));
        trainDatabase.put("12346", new Train("12346", "Shatabdi Express", "Chennai", "Bangalore"));
        trainDatabase.put("12347", new Train("12347", "Duronto Express", "Kolkata", "Delhi"));
        trainDatabase.put("12348", new Train("12348", "Garib Rath", "Mumbai", "Ahmedabad"));
        trainDatabase.put("12349", new Train("12349", "Jan Shatabdi", "Bangalore", "Mysore"));
    }
    
    private static void displayWelcomeBanner() {
        System.out.println("\n" + repeatChar('=', 55));
        System.out.println("       WELCOME TO ONLINE RESERVATION SYSTEM");
        System.out.println("           Book Your Journey With Ease");
        System.out.println(repeatChar('=', 55));
    }
    
    private static void handleLoginProcess() {
        System.out.println("\nPlease authenticate to continue:");
        System.out.println("1. Login to your account");
        System.out.println("2. View sample credentials");
        System.out.println("3. Exit application");
        System.out.print("\nEnter your choice: ");
        
        try {
            int option = Integer.parseInt(input.nextLine().trim());
            
            switch (option) {
                case 1:
                    performLogin();
                    break;
                case 2:
                    displaySampleCredentials();
                    break;
                case 3:
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    private static void performLogin() {
        System.out.println("\n" + repeatChar('-', 40));
        System.out.println("           USER LOGIN");
        System.out.println(repeatChar('-', 40));
        
        System.out.print("Enter Login ID: ");
        String loginId = input.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String password = input.nextLine().trim();
        
        if (validateCredentials(loginId, password)) {
            currentUser = userDatabase.get(loginId);
            System.out.println("\nLogin successful!");
            System.out.println("Welcome, " + currentUser.getName());
        } else {
            System.out.println("\nLogin failed!");
            System.out.println("Invalid login ID or password. Please try again.");
        }
    }
    
    private static boolean validateCredentials(String loginId, String password) {
        User user = userDatabase.get(loginId);
        return user != null && user.getPassword().equals(password);
    }
    
    private static void displaySampleCredentials() {
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("         SAMPLE USER CREDENTIALS");
        System.out.println(repeatChar('=', 50));
        System.out.println("Login ID: user1  |  Password: pass123");
        System.out.println("Login ID: user2  |  Password: pass456");
        System.out.println(repeatChar('=', 50));
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("RESERVATION SYSTEM MENU");
        System.out.println("User: " + currentUser.getLoginId() + 
                         " | " + currentUser.getName());
        System.out.println(repeatChar('=', 50));
        System.out.println("1. Make Reservation");
        System.out.println("2. Cancel Reservation");
        System.out.println("3. Logout");
        System.out.print("\nSelect an option: ");
        
        try {
            int choice = Integer.parseInt(input.nextLine().trim());
            
            switch (choice) {
                case 1:
                    makeReservation();
                    break;
                case 2:
                    cancelReservation();
                    break;
                case 3:
                    performLogout();
                    break;
                default:
                    System.out.println("Invalid option. Please select 1-3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    private static void makeReservation() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("TRAIN RESERVATION FORM");
        System.out.println(repeatChar('=', 60));
        
        // Basic Details
        System.out.print("Enter Passenger Name: ");
        String passengerName = input.nextLine().trim();
        
        if (passengerName.isEmpty()) {
            System.out.println("Passenger name cannot be empty!");
            return;
        }
        
        System.out.print("Enter Age: ");
        String age = input.nextLine().trim();
        
        System.out.print("Enter Gender (M/F): ");
        String gender = input.nextLine().trim().toUpperCase();
        
        // Display available trains
        System.out.println("\n" + repeatChar('-', 60));
        System.out.println("AVAILABLE TRAINS");
        System.out.println(repeatChar('-', 60));
        System.out.printf("%-10s %-25s %-15s %-15s%n", 
                         "Train No", "Train Name", "From", "To");
        System.out.println(repeatChar('-', 60));
        
        for (Train train : trainDatabase.values()) {
            System.out.printf("%-10s %-25s %-15s %-15s%n",
                train.getTrainNumber(),
                train.getTrainName(),
                train.getFromStation(),
                train.getToStation()
            );
        }
        System.out.println(repeatChar('-', 60));
        
        // Train Number
        System.out.print("\nEnter Train Number: ");
        String trainNumber = input.nextLine().trim();
        
        Train selectedTrain = trainDatabase.get(trainNumber);
        if (selectedTrain == null) {
            System.out.println("Invalid train number!");
            return;
        }
        
        System.out.println("Train Name: " + selectedTrain.getTrainName());
        
        // Class Type
        System.out.println("\nAvailable Classes:");
        System.out.println("1. Sleeper Class (SL)");
        System.out.println("2. AC 3 Tier (3A)");
        System.out.println("3. AC 2 Tier (2A)");
        System.out.println("4. AC 1 Tier (1A)");
        System.out.print("Select Class Type (1-4): ");
        
        String classType = "";
        try {
            int classChoice = Integer.parseInt(input.nextLine().trim());
            switch (classChoice) {
                case 1: classType = "Sleeper Class"; break;
                case 2: classType = "AC 3 Tier"; break;
                case 3: classType = "AC 2 Tier"; break;
                case 4: classType = "AC 1 Tier"; break;
                default:
                    System.out.println("Invalid class selection!");
                    return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }
        
        // Date of Journey
        System.out.print("Enter Date of Journey (DD/MM/YYYY): ");
        String dateOfJourney = input.nextLine().trim();
        
        // From and To
        System.out.println("From: " + selectedTrain.getFromStation());
        System.out.println("To: " + selectedTrain.getToStation());
        
        // Generate PNR
        String pnr = "PNR" + (pnrCounter++);
        
        // Create reservation
        Reservation reservation = new Reservation(
            pnr,
            currentUser.getLoginId(),
            passengerName,
            age,
            gender,
            trainNumber,
            selectedTrain.getTrainName(),
            classType,
            dateOfJourney,
            selectedTrain.getFromStation(),
            selectedTrain.getToStation()
        );
        
        reservationDatabase.put(pnr, reservation);
        
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("RESERVATION SUCCESSFUL!");
        System.out.println(repeatChar('=', 60));
        System.out.println("PNR Number: " + pnr);
        System.out.println("Passenger Name: " + passengerName);
        System.out.println("Train: " + selectedTrain.getTrainName() + " (" + trainNumber + ")");
        System.out.println("Class: " + classType);
        System.out.println("Date: " + dateOfJourney);
        System.out.println("From: " + selectedTrain.getFromStation());
        System.out.println("To: " + selectedTrain.getToStation());
        System.out.println(repeatChar('=', 60));
        System.out.println("Please save your PNR number for future reference.");
    }
    
    private static void cancelReservation() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("CANCELLATION FORM");
        System.out.println(repeatChar('=', 60));
        
        System.out.print("Enter PNR Number: ");
        String pnr = input.nextLine().trim();
        
        Reservation reservation = reservationDatabase.get(pnr);
        
        if (reservation == null) {
            System.out.println("\nNo reservation found with PNR: " + pnr);
            return;
        }
        
        // Display reservation details
        System.out.println("\n" + repeatChar('-', 60));
        System.out.println("RESERVATION DETAILS");
        System.out.println(repeatChar('-', 60));
        System.out.println("PNR Number: " + reservation.getPnr());
        System.out.println("Passenger Name: " + reservation.getPassengerName());
        System.out.println("Age: " + reservation.getAge());
        System.out.println("Gender: " + reservation.getGender());
        System.out.println("Train Number: " + reservation.getTrainNumber());
        System.out.println("Train Name: " + reservation.getTrainName());
        System.out.println("Class: " + reservation.getClassType());
        System.out.println("Date of Journey: " + reservation.getDateOfJourney());
        System.out.println("From: " + reservation.getFromStation());
        System.out.println("To: " + reservation.getToStation());
        System.out.println(repeatChar('-', 60));
        
        System.out.print("\nDo you want to confirm cancellation? (yes/no): ");
        String confirmation = input.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("yes")) {
            reservationDatabase.remove(pnr);
            System.out.println("\n" + repeatChar('=', 60));
            System.out.println("CANCELLATION SUCCESSFUL!");
            System.out.println(repeatChar('=', 60));
            System.out.println("PNR " + pnr + " has been cancelled.");
            System.out.println("Refund will be processed within 7 working days.");
        } else {
            System.out.println("\nCancellation aborted.");
        }
    }
    
    private static void performLogout() {
        System.out.println("\nLogging out...");
        System.out.println("Thank you for using our reservation system, " + 
                         currentUser.getName() + "!");
        
        currentUser = null;
        
        System.out.println("\n" + repeatChar('=', 50));
        System.out.println("Session terminated successfully.");
        System.out.println("Have a great day!");
        System.out.println(repeatChar('=', 50));
    }
    
    private static void exitApplication() {
        System.out.println("\nThank you for using our Online Reservation System!");
        System.out.println("Goodbye!");
        System.exit(0);
    }
    
    private static String repeatChar(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
