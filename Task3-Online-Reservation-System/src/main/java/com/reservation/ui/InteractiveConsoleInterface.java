package com.reservation.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.reservation.model.Reservation;
import com.reservation.model.Train;
import com.reservation.model.User;
import com.reservation.service.BookingManagementService;

/**
 * Interactive Console Interface for Train Booking System
 * Provides a user-friendly command-line interface with enhanced features
 */
public class InteractiveConsoleInterface {
    
    private final BookingManagementService bookingService;
    private final Scanner inputScanner;
    private String currentUserLoginId;
    private boolean isSystemRunning;
    
    // Console colors for better UX
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    
    public InteractiveConsoleInterface() {
        this.bookingService = new BookingManagementService();
        this.inputScanner = new Scanner(System.in);
        this.isSystemRunning = true;
    }
    
    /**
     * Start the interactive console application
     */
    public void launchApplication() {
        displayWelcomeBanner();
        
        while (isSystemRunning) {
            try {
                if (currentUserLoginId == null) {
                    showAuthenticationMenu();
                } else {
                    showMainApplicationMenu();
                }
            } catch (Exception e) {
                System.err.println(RED + "An unexpected error occurred: " + e.getMessage() + RESET);
                System.out.println("Please try again...");
            }
        }
        
        displayGoodbyeMessage();
        inputScanner.close();
    }
    
    /**
     * Display welcome banner
     */
    private void displayWelcomeBanner() {
        System.out.println(CYAN + "\n" + "=".repeat(70));
        System.out.println("              🚂 TRAIN BOOKING MANAGEMENT SYSTEM 🚂");
        System.out.println("                     Welcome to RailConnect");
        System.out.println("=".repeat(70) + RESET);
        System.out.println(BLUE + "Your one-stop solution for train reservations and travel planning" + RESET);
    }
    
    /**
     * Show authentication menu for login/registration
     */
    private void showAuthenticationMenu() {
        System.out.println(YELLOW + "\n📋 AUTHENTICATION MENU" + RESET);
        System.out.println("1. 🔐 Login to Existing Account");
        System.out.println("2. 📝 Create New Account");
        System.out.println("3. ❌ Exit Application");
        System.out.print(CYAN + "Select your choice (1-3): " + RESET);
        
        int choice = getIntegerInput();
        
        switch (choice) {
            case 1:
                handleUserLogin();
                break;
            case 2:
                handleUserRegistration();
                break;
            case 3:
                isSystemRunning = false;
                break;
            default:
                System.out.println(RED + "❌ Invalid choice! Please select 1, 2, or 3." + RESET);
        }
    }
    
    /**
     * Show main application menu for logged-in users
     */
    private void showMainApplicationMenu() {
        System.out.println(YELLOW + "\n🎯 MAIN MENU - Welcome " + currentUserLoginId + "!" + RESET);
        System.out.println("1. 🔍 Search & Book Trains");
        System.out.println("2. 📋 View Reservation Details");
        System.out.println("3. ❌ Cancel Reservation");
        System.out.println("4. 👤 View Profile");
        System.out.println("5. 📊 View Available Classes & Pricing");
        System.out.println("6. 🚪 Logout");
        System.out.print(CYAN + "Select your choice (1-6): " + RESET);
        
        int choice = getIntegerInput();
        
        switch (choice) {
            case 1:
                handleTrainSearchAndBooking();
                break;
            case 2:
                handleViewReservationDetails();
                break;
            case 3:
                handleReservationCancellation();
                break;
            case 4:
                handleViewProfile();
                break;
            case 5:
                handleViewPricingInfo();
                break;
            case 6:
                handleUserLogout();
                break;
            default:
                System.out.println(RED + "❌ Invalid choice! Please select 1-6." + RESET);
        }
    }
    
    /**
     * Handle user login process
     */
    private void handleUserLogin() {
        System.out.println(BLUE + "\n🔐 USER LOGIN" + RESET);
        System.out.print("Enter Login ID: ");
        String loginId = inputScanner.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String password = inputScanner.nextLine();
        
        if (bookingService.authenticateCustomer(loginId, password)) {
            currentUserLoginId = loginId;
            System.out.println(GREEN + "✅ Login successful! Welcome back, " + loginId + "!" + RESET);
        } else {
            System.out.println(RED + "❌ Invalid credentials. Please check your login ID and password." + RESET);
        }
    }
    
    /**
     * Handle user registration process
     */
    private void handleUserRegistration() {
        System.out.println(BLUE + "\n📝 CREATE NEW ACCOUNT" + RESET);
        
        System.out.print("Choose a Login ID (min 3 characters): ");
        String loginId = inputScanner.nextLine().trim();
        
        System.out.print("Create Password (min 6 characters): ");
        String password = inputScanner.nextLine();
        
        System.out.print("Enter Email Address: ");
        String email = inputScanner.nextLine().trim();
        
        System.out.print("Enter Full Name: ");
        String fullName = inputScanner.nextLine().trim();
        
        System.out.print("Enter Contact Number (10 digits): ");
        String contactNumber = inputScanner.nextLine().trim();
        
        User newUser = new User(loginId, password, email, fullName, contactNumber);
        
        if (bookingService.registerNewCustomer(newUser)) {
            System.out.println(GREEN + "✅ Account created successfully! You can now login." + RESET);
        } else {
            System.out.println(RED + "❌ Registration failed. Please check your details and try again." + RESET);
        }
    }
    
    /**
     * Handle train search and booking process
     */
    private void handleTrainSearchAndBooking() {
        System.out.println(BLUE + "\n🔍 SEARCH & BOOK TRAINS" + RESET);
        
        System.out.print("Enter Origin Station: ");
        String origin = inputScanner.nextLine().trim();
        
        System.out.print("Enter Destination Station: ");
        String destination = inputScanner.nextLine().trim();
        
        System.out.print("Enter Travel Date (YYYY-MM-DD): ");
        String dateStr = inputScanner.nextLine().trim();
        
        try {
            LocalDate travelDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            
            List<Train> availableTrains = bookingService.findAvailableTrains(origin, destination, travelDate);
            
            if (availableTrains.isEmpty()) {
                System.out.println(YELLOW + "⚠️ No trains available for the selected route and date." + RESET);
                return;
            }
            
            // Display available trains
            displayTrainOptions(availableTrains);
            
            System.out.print(CYAN + "Enter Train ID to book (or 0 to go back): " + RESET);
            int selectedTrainId = getIntegerInput();
            
            if (selectedTrainId == 0) return;
            
            // Find selected train
            Optional<Train> selectedTrain = availableTrains.stream()
                    .filter(train -> train.getTrainId() == selectedTrainId)
                    .findFirst();
            
            if (!selectedTrain.isPresent()) {
                System.out.println(RED + "❌ Invalid Train ID selected." + RESET);
                return;
            }
            
            // Proceed with booking
            proceedWithBooking(selectedTrain.get(), travelDate, origin, destination);
            
        } catch (DateTimeParseException e) {
            System.out.println(RED + "❌ Invalid date format. Please use YYYY-MM-DD format." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "❌ " + e.getMessage() + RESET);
        }
    }
    
    /**
     * Proceed with the booking process
     */
    private void proceedWithBooking(Train selectedTrain, LocalDate travelDate, String origin, String destination) {
        System.out.println(BLUE + "\n📋 BOOKING DETAILS" + RESET);
        
        System.out.print("Enter Traveler Name: ");
        String travelerName = inputScanner.nextLine().trim();
        
        // Show available classes
        bookingService.displayAvailableClasses(origin, destination);
        
        System.out.print(CYAN + "Enter Travel Class (e.g., FIRST_AC, SECOND_AC, SLEEPER_CLASS): " + RESET);
        String travelClass = inputScanner.nextLine().trim().toUpperCase();
        
        System.out.println(YELLOW + "\n📋 BOOKING SUMMARY" + RESET);
        System.out.println("Train: " + selectedTrain.getTrainName() + " (" + selectedTrain.getTrainId() + ")");
        System.out.println("Route: " + origin + " → " + destination);
        System.out.println("Date: " + travelDate);
        System.out.println("Traveler: " + travelerName);
        System.out.println("Class: " + travelClass.replace("_", " "));
        
        System.out.print(CYAN + "Confirm booking? (Y/N): " + RESET);
        String confirmation = inputScanner.nextLine().trim();
        
        if ("Y".equalsIgnoreCase(confirmation) || "YES".equalsIgnoreCase(confirmation)) {
            String confirmationCode = bookingService.createNewReservation(
                    currentUserLoginId, selectedTrain.getTrainId(), travelerName,
                    travelClass, travelDate, origin, destination
            );
            
            if (confirmationCode != null) {
                System.out.println(GREEN + "🎉 Booking successful!" + RESET);
                System.out.println(YELLOW + "📋 Your Confirmation Code: " + confirmationCode + RESET);
                System.out.println(BLUE + "💡 Please save this code for future reference." + RESET);
            } else {
                System.out.println(RED + "❌ Booking failed. Please try again." + RESET);
            }
        } else {
            System.out.println(YELLOW + "❌ Booking cancelled." + RESET);
        }
    }
    
    /**
     * Handle viewing reservation details
     */
    private void handleViewReservationDetails() {
        System.out.println(BLUE + "\n📋 VIEW RESERVATION DETAILS" + RESET);
        System.out.print("Enter Confirmation Code: ");
        String confirmationCode = inputScanner.nextLine().trim();
        
        Optional<Reservation> reservationOpt = bookingService.getReservationDetails(confirmationCode);
        
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            displayReservationInfo(reservation);
        } else {
            System.out.println(RED + "❌ No reservation found with the provided confirmation code." + RESET);
        }
    }
    
    /**
     * Handle reservation cancellation
     */
    private void handleReservationCancellation() {
        System.out.println(BLUE + "\n❌ CANCEL RESERVATION" + RESET);
        System.out.print("Enter Confirmation Code: ");
        String confirmationCode = inputScanner.nextLine().trim();
        
        Optional<Reservation> reservationOpt = bookingService.getReservationDetails(confirmationCode);
        
        if (!reservationOpt.isPresent()) {
            System.out.println(RED + "❌ No reservation found with the provided confirmation code." + RESET);
            return;
        }
        
        Reservation reservation = reservationOpt.get();
        
        if (reservation.getStatus() == Reservation.ReservationStatus.CANCELLED) {
            System.out.println(YELLOW + "⚠️ This reservation is already cancelled." + RESET);
            return;
        }
        
        // Display reservation details
        displayReservationInfo(reservation);
        
        System.out.print(CYAN + "Are you sure you want to cancel this reservation? (Y/N): " + RESET);
        String confirmation = inputScanner.nextLine().trim();
        
        if ("Y".equalsIgnoreCase(confirmation) || "YES".equalsIgnoreCase(confirmation)) {
            if (bookingService.cancelExistingReservation(confirmationCode)) {
                System.out.println(GREEN + "✅ Reservation cancelled successfully!" + RESET);
            } else {
                System.out.println(RED + "❌ Failed to cancel reservation. Please try again." + RESET);
            }
        } else {
            System.out.println(YELLOW + "❌ Cancellation aborted." + RESET);
        }
    }
    
    /**
     * Handle viewing user profile
     */
    private void handleViewProfile() {
        Optional<User> userOpt = bookingService.getCustomerProfile(currentUserLoginId);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println(BLUE + "\n👤 USER PROFILE" + RESET);
            System.out.println("Login ID: " + user.getLoginId());
            System.out.println("Name: " + user.getCustomerName());
            System.out.println("Email: " + user.getEmailAddress());
            System.out.println("Contact: " + user.getContactNumber());
            System.out.println("Registration Date: " + user.getRegistrationTime().toLocalDate());
            System.out.println("Account Status: " + (user.isActive() ? "Active" : "Inactive"));
        } else {
            System.out.println(RED + "❌ Unable to retrieve profile information." + RESET);
        }
    }
    
    /**
     * Handle viewing pricing information
     */
    private void handleViewPricingInfo() {
        System.out.println(BLUE + "\n📊 TRAVEL CLASS PRICING" + RESET);
        System.out.print("Enter Origin Station: ");
        String origin = inputScanner.nextLine().trim();
        
        System.out.print("Enter Destination Station: ");
        String destination = inputScanner.nextLine().trim();
        
        bookingService.displayAvailableClasses(origin, destination);
    }
    
    /**
     * Handle user logout
     */
    private void handleUserLogout() {
        currentUserLoginId = null;
        System.out.println(GREEN + "✅ Logged out successfully! Thank you for using RailConnect." + RESET);
    }
    
    /**
     * Display train options in a formatted table
     */
    private void displayTrainOptions(List<Train> trains) {
        System.out.println(YELLOW + "\n🚂 AVAILABLE TRAINS" + RESET);
        System.out.println("=".repeat(90));
        System.out.printf("%-8s %-20s %-12s %-12s %-10s %-10s %-8s\n",
                "Train ID", "Train Name", "Origin", "Destination", "Departure", "Arrival", "Available");
        System.out.println("-".repeat(90));
        
        for (Train train : trains) {
            System.out.printf("%-8d %-20s %-12s %-12s %-10s %-10s %-8d\n",
                    train.getTrainId(),
                    train.getTrainName(),
                    train.getOriginStation(),
                    train.getDestinationStation(),
                    train.getDepartureTime(),
                    train.getArrivalTime(),
                    train.getSeatsRemaining());
        }
        System.out.println("=".repeat(90));
    }
    
    /**
     * Display reservation information
     */
    private void displayReservationInfo(Reservation reservation) {
        System.out.println(YELLOW + "\n📋 RESERVATION INFORMATION" + RESET);
        System.out.println("=".repeat(50));
        System.out.println("Confirmation Code: " + reservation.getConfirmationCode());
        System.out.println("Traveler Name: " + reservation.getTravelerName());
        System.out.println("Train ID: " + reservation.getTrainId());
        System.out.println("Travel Class: " + reservation.getTravelClass().replace("_", " "));
        System.out.println("Travel Date: " + reservation.getTravelDate());
        System.out.println("Route: " + reservation.getBoardingStation() + " → " + reservation.getAlightingStation());
        System.out.println("Status: " + reservation.getStatus());
        System.out.println("Ticket Price: ₹" + reservation.getTicketPrice());
        System.out.println("Booking Date: " + reservation.getBookingTimestamp().toLocalDate());
        System.out.println("=".repeat(50));
    }
    
    /**
     * Get integer input with error handling
     */
    private int getIntegerInput() {
        try {
            String input = inputScanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(RED + "❌ Please enter a valid number." + RESET);
            return -1;
        }
    }
    
    /**
     * Display goodbye message
     */
    private void displayGoodbyeMessage() {
        System.out.println(CYAN + "\n" + "=".repeat(50));
        System.out.println("    Thank you for using RailConnect! 🚂");
        System.out.println("         Have a safe journey!");
        System.out.println("=".repeat(50) + RESET);
    }
}