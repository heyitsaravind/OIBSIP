/**
 * Advanced Railway Reservation System - RailConnect
 * A comprehensive train booking management system with modern features
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RailwayReservationSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Train> trains = new HashMap<>();
    private static Map<String, Reservation> reservations = new HashMap<>();
    private static User currentUser = null;
    private static int reservationCounter = 1000;
    
    public static void main(String[] args) {
        initializeSystemData();
        displayWelcomeScreen();
        
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }
    
    private static void initializeSystemData() {
        // Initialize sample users
        users.put("user123", new User("user123", "pass123", "Aravind Kumar", "aravind@email.com", "9876543210"));
        users.put("admin", new User("admin", "admin123", "System Admin", "admin@railconnect.com", "1234567890"));
        
        // Initialize sample trains
        trains.put("12345", new Train("12345", "Rajdhani Express", "New Delhi", "Mumbai Central", 
                                    "06:00", "20:30", 1200.0, 100));
        trains.put("67890", new Train("67890", "Shatabdi Express", "Chennai Central", "Bangalore City", 
                                    "14:00", "19:45", 800.0, 80));
        trains.put("11111", new Train("11111", "Duronto Express", "Kolkata", "New Delhi", 
                                    "22:15", "12:30", 1500.0, 120));
        trains.put("22222", new Train("22222", "Garib Rath", "Mumbai Central", "Ahmedabad", 
                                    "08:30", "14:15", 400.0, 150));
        trains.put("33333", new Train("33333", "Jan Shatabdi", "Pune", "Mumbai Central", 
                                    "07:00", "10:30", 200.0, 200));
    }
    
    private static void displayWelcomeScreen() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🚂  WELCOME TO RAILCONNECT - RAILWAY RESERVATION SYSTEM  🚂");
        System.out.println("         Your Journey Begins with a Simple Click!");
        System.out.println("=".repeat(70));
        System.out.println("🎫 Book Tickets • 🔍 Check Availability • 📊 Manage Bookings");
        System.out.println("🌟 Fast • Secure • Reliable Railway Booking Platform");
        System.out.println("📅 Current Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        System.out.println("=".repeat(70));
    }
    
    private static void showLoginMenu() {
        System.out.println("\n🔐 USER AUTHENTICATION");
        System.out.println("=".repeat(35));
        System.out.println("1. 🔑 Login to Account");
        System.out.println("2. 📝 Create New Account");
        System.out.println("3. 👀 View Demo Credentials");
        System.out.println("4. 🚂 Browse Trains (Guest)");
        System.out.println("5. ❌ Exit System");
        System.out.print("\n👉 Select option (1-5): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    performLogin();
                    break;
                case 2:
                    createNewAccount();
                    break;
                case 3:
                    showDemoCredentials();
                    break;
                case 4:
                    browseTrainsAsGuest();
                    break;
                case 5:
                    exitSystem();
                    break;
                default:
                    System.out.println("❌ Invalid option! Please select 1-5.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void performLogin() {
        System.out.println("\n🔑 LOGIN TO YOUR ACCOUNT");
        System.out.println("-".repeat(30));
        
        System.out.print("👤 Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("🔐 Password: ");
        String password = scanner.nextLine().trim();
        
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("\n✅ Login Successful!");
            System.out.println("🎉 Welcome back, " + user.getName() + "!");
            System.out.println("📧 Email: " + user.getEmail());
        } else {
            System.out.println("\n❌ Login Failed!");
            System.out.println("🚫 Invalid username or password. Please try again.");
        }
    }
    
    private static void createNewAccount() {
        System.out.println("\n📝 CREATE NEW ACCOUNT");
        System.out.println("-".repeat(30));
        
        System.out.print("👤 Choose Username: ");
        String username = scanner.nextLine().trim();
        
        if (users.containsKey(username)) {
            System.out.println("❌ Username already exists! Please choose a different one.");
            return;
        }
        
        System.out.print("🔐 Create Password: ");
        String password = scanner.nextLine().trim();
        
        System.out.print("📛 Full Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("📧 Email Address: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("📱 Phone Number: ");
        String phone = scanner.nextLine().trim();
        
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            System.out.println("❌ All fields are required!");
            return;
        }
        
        User newUser = new User(username, password, name, email, phone);
        users.put(username, newUser);
        
        System.out.println("\n✅ Account Created Successfully!");
        System.out.println("🎉 Welcome to RailConnect, " + name + "!");
        System.out.println("🔑 You can now login with your credentials.");
    }
    
    private static void showDemoCredentials() {
        System.out.println("\n👀 DEMO ACCOUNT CREDENTIALS");
        System.out.println("=".repeat(40));
        System.out.println("Username: user123");
        System.out.println("Password: pass123");
        System.out.println("Name: Aravind Kumar");
        System.out.println("=".repeat(40));
        System.out.println("💡 Use these credentials to explore the system!");
    }
    
    private static void browseTrainsAsGuest() {
        System.out.println("\n🚂 AVAILABLE TRAINS (GUEST VIEW)");
        displayAllTrains();
        System.out.println("\n💡 Login to book tickets and access more features!");
    }
    
    private static void showMainMenu() {
        System.out.println("\n🚂 RAILCONNECT MAIN MENU");
        System.out.println("User: " + currentUser.getName() + " (" + currentUser.getUsername() + ")");
        System.out.println("=".repeat(55));
        System.out.println("1. 🔍 Search & Browse Trains");
        System.out.println("2. 🎫 Make Reservation");
        System.out.println("3. 📋 View My Bookings");
        System.out.println("4. ❌ Cancel Reservation");
        System.out.println("5. 👤 My Profile");
        System.out.println("6. 📊 Booking History");
        System.out.println("7. 💳 Payment Methods");
        System.out.println("8. 🚪 Logout");
        System.out.print("\n👉 Select option (1-8): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    searchAndBrowseTrains();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    viewMyBookings();
                    break;
                case 4:
                    cancelReservation();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    viewBookingHistory();
                    break;
                case 7:
                    managePaymentMethods();
                    break;
                case 8:
                    logout();
                    break;
                default:
                    System.out.println("❌ Invalid option! Please select 1-8.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void searchAndBrowseTrains() {
        System.out.println("\n🔍 SEARCH TRAINS");
        System.out.println("=".repeat(30));
        System.out.println("1. 📋 View All Trains");
        System.out.println("2. 🎯 Search by Route");
        System.out.println("3. 🚂 Search by Train Number");
        System.out.println("4. 🔙 Back to Main Menu");
        System.out.print("\n👉 Select option (1-4): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    displayAllTrains();
                    break;
                case 2:
                    searchByRoute();
                    break;
                case 3:
                    searchByTrainNumber();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Invalid option!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
    
    private static void displayAllTrains() {
        System.out.println("\n🚂 ALL AVAILABLE TRAINS");
        System.out.println("=".repeat(80));
        System.out.printf("%-8s %-18s %-15s %-15s %-8s %-8s %-8s %-8s%n", 
                "Number", "Name", "From", "To", "Departure", "Arrival", "Fare", "Seats");
        System.out.println("-".repeat(80));
        
        for (Train train : trains.values()) {
            System.out.printf("%-8s %-18s %-15s %-15s %-8s %-8s ₹%-7.0f %-8d%n",
                    train.getTrainNumber(),
                    train.getTrainName(),
                    train.getSource(),
                    train.getDestination(),
                    train.getDepartureTime(),
                    train.getArrivalTime(),
                    train.getFare(),
                    train.getAvailableSeats()
            );
        }
        System.out.println("=".repeat(80));
    }
    
    private static void searchByRoute() {
        System.out.print("\n🏁 Enter Source Station: ");
        String source = scanner.nextLine().trim();
        
        System.out.print("🎯 Enter Destination Station: ");
        String destination = scanner.nextLine().trim();
        
        System.out.println("\n🔍 SEARCH RESULTS");
        System.out.println("Route: " + source + " → " + destination);
        System.out.println("=".repeat(80));
        
        boolean found = false;
        System.out.printf("%-8s %-18s %-8s %-8s %-8s %-8s%n", 
                "Number", "Name", "Departure", "Arrival", "Fare", "Seats");
        System.out.println("-".repeat(80));
        
        for (Train train : trains.values()) {
            if (train.getSource().toLowerCase().contains(source.toLowerCase()) &&
                train.getDestination().toLowerCase().contains(destination.toLowerCase())) {
                
                System.out.printf("%-8s %-18s %-8s %-8s ₹%-7.0f %-8d%n",
                        train.getTrainNumber(),
                        train.getTrainName(),
                        train.getDepartureTime(),
                        train.getArrivalTime(),
                        train.getFare(),
                        train.getAvailableSeats()
                );
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("❌ No trains found for the specified route.");
            System.out.println("💡 Try different station names or check spelling.");
        }
        System.out.println("=".repeat(80));
    }
    
    private static void searchByTrainNumber() {
        System.out.print("\n🚂 Enter Train Number: ");
        String trainNumber = scanner.nextLine().trim();
        
        Train train = trains.get(trainNumber);
        if (train != null) {
            System.out.println("\n🚂 TRAIN DETAILS");
            System.out.println("=".repeat(50));
            System.out.println("Train Number: " + train.getTrainNumber());
            System.out.println("Train Name: " + train.getTrainName());
            System.out.println("Route: " + train.getSource() + " → " + train.getDestination());
            System.out.println("Departure: " + train.getDepartureTime());
            System.out.println("Arrival: " + train.getArrivalTime());
            System.out.println("Fare: ₹" + train.getFare());
            System.out.println("Available Seats: " + train.getAvailableSeats());
            System.out.println("=".repeat(50));
        } else {
            System.out.println("❌ Train not found! Please check the train number.");
        }
    }
    
    private static void makeReservation() {
        System.out.println("\n🎫 MAKE NEW RESERVATION");
        System.out.println("=".repeat(40));
        
        displayAllTrains();
        
        System.out.print("\n🚂 Enter Train Number: ");
        String trainNumber = scanner.nextLine().trim();
        
        Train train = trains.get(trainNumber);
        if (train == null) {
            System.out.println("❌ Invalid train number!");
            return;
        }
        
        System.out.print("👥 Number of Passengers: ");
        int passengers;
        try {
            passengers = Integer.parseInt(scanner.nextLine().trim());
            if (passengers <= 0 || passengers > 6) {
                System.out.println("❌ Invalid number of passengers! (1-6 allowed)");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
            return;
        }
        
        if (passengers > train.getAvailableSeats()) {
            System.out.println("❌ Not enough seats available!");
            System.out.println("💺 Available seats: " + train.getAvailableSeats());
            return;
        }
        
        System.out.print("📅 Journey Date (DD/MM/YYYY): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate journeyDate;
        
        try {
            journeyDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (journeyDate.isBefore(LocalDate.now())) {
                System.out.println("❌ Journey date cannot be in the past!");
                return;
            }
        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid date format! Use DD/MM/YYYY");
            return;
        }
        
        // Calculate total fare
        double totalFare = train.getFare() * passengers;
        
        System.out.println("\n📋 BOOKING SUMMARY");
        System.out.println("=".repeat(40));
        System.out.println("Train: " + train.getTrainNumber() + " - " + train.getTrainName());
        System.out.println("Route: " + train.getSource() + " → " + train.getDestination());
        System.out.println("Journey Date: " + journeyDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        System.out.println("Passengers: " + passengers);
        System.out.println("Fare per ticket: ₹" + train.getFare());
        System.out.println("Total Fare: ₹" + totalFare);
        System.out.println("=".repeat(40));
        
        System.out.print("\n💳 Confirm booking? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            // Create reservation
            String reservationId = "PNR" + (reservationCounter++);
            Reservation reservation = new Reservation(
                reservationId, currentUser.getUsername(), trainNumber,
                passengers, journeyDate, totalFare
            );
            
            reservations.put(reservationId, reservation);
            train.bookSeats(passengers);
            
            System.out.println("\n✅ BOOKING CONFIRMED!");
            System.out.println("=".repeat(35));
            System.out.println("🎫 PNR Number: " + reservationId);
            System.out.println("📧 Confirmation sent to: " + currentUser.getEmail());
            System.out.println("💰 Amount Paid: ₹" + totalFare);
            System.out.println("📱 SMS sent to: " + currentUser.getPhone());
            System.out.println("=".repeat(35));
            System.out.println("💡 Please save your PNR number for future reference!");
            
        } else {
            System.out.println("❌ Booking cancelled.");
        }
    }
    
    private static void viewMyBookings() {
        System.out.println("\n📋 MY CURRENT BOOKINGS");
        System.out.println("=".repeat(70));
        
        boolean hasBookings = false;
        System.out.printf("%-12s %-8s %-18s %-12s %-10s %-10s%n", 
                "PNR", "Train", "Name", "Date", "Passengers", "Fare");
        System.out.println("-".repeat(70));
        
        for (Reservation reservation : reservations.values()) {
            if (reservation.getUserId().equals(currentUser.getUsername()) && 
                reservation.getStatus().equals("CONFIRMED")) {
                
                Train train = trains.get(reservation.getTrainNumber());
                System.out.printf("%-12s %-8s %-18s %-12s %-10d ₹%-9.0f%n",
                        reservation.getPnr(),
                        reservation.getTrainNumber(),
                        train.getTrainName(),
                        reservation.getJourneyDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        reservation.getPassengers(),
                        reservation.getTotalFare()
                );
                hasBookings = true;
            }
        }
        
        if (!hasBookings) {
            System.out.println("📝 No active bookings found.");
            System.out.println("💡 Book your first ticket to see it here!");
        }
        System.out.println("=".repeat(70));
    }
    
    private static void cancelReservation() {
        System.out.println("\n❌ CANCEL RESERVATION");
        System.out.println("-".repeat(30));
        
        viewMyBookings();
        
        System.out.print("\n🎫 Enter PNR Number to cancel: ");
        String pnr = scanner.nextLine().trim().toUpperCase();
        
        Reservation reservation = reservations.get(pnr);
        if (reservation == null) {
            System.out.println("❌ Invalid PNR number!");
            return;
        }
        
        if (!reservation.getUserId().equals(currentUser.getUsername())) {
            System.out.println("❌ This PNR doesn't belong to your account!");
            return;
        }
        
        if (!reservation.getStatus().equals("CONFIRMED")) {
            System.out.println("❌ This reservation is already cancelled!");
            return;
        }
        
        Train train = trains.get(reservation.getTrainNumber());
        
        System.out.println("\n📋 CANCELLATION DETAILS");
        System.out.println("=".repeat(40));
        System.out.println("PNR: " + reservation.getPnr());
        System.out.println("Train: " + train.getTrainNumber() + " - " + train.getTrainName());
        System.out.println("Passengers: " + reservation.getPassengers());
        System.out.println("Total Fare: ₹" + reservation.getTotalFare());
        
        // Calculate refund (assuming 10% cancellation charges)
        double cancellationCharges = reservation.getTotalFare() * 0.10;
        double refundAmount = reservation.getTotalFare() - cancellationCharges;
        
        System.out.println("Cancellation Charges: ₹" + String.format("%.2f", cancellationCharges));
        System.out.println("Refund Amount: ₹" + String.format("%.2f", refundAmount));
        System.out.println("=".repeat(40));
        
        System.out.print("\n⚠️  Confirm cancellation? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            reservation.cancel();
            train.releaseSeats(reservation.getPassengers());
            
            System.out.println("\n✅ CANCELLATION SUCCESSFUL!");
            System.out.println("💰 Refund of ₹" + String.format("%.2f", refundAmount) + 
                             " will be processed in 3-5 business days.");
            System.out.println("📧 Cancellation confirmation sent to your email.");
        } else {
            System.out.println("❌ Cancellation aborted.");
        }
    }
    
    private static void viewProfile() {
        System.out.println("\n👤 MY PROFILE");
        System.out.println("=".repeat(35));
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Name: " + currentUser.getName());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Phone: " + currentUser.getPhone());
        System.out.println("Member Since: " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMM yyyy")));
        
        // Count bookings
        int totalBookings = 0;
        for (Reservation reservation : reservations.values()) {
            if (reservation.getUserId().equals(currentUser.getUsername())) {
                totalBookings++;
            }
        }
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("=".repeat(35));
    }
    
    private static void viewBookingHistory() {
        System.out.println("\n📊 BOOKING HISTORY");
        System.out.println("=".repeat(70));
        
        boolean hasHistory = false;
        System.out.printf("%-12s %-8s %-18s %-12s %-10s %-12s%n", 
                "PNR", "Train", "Name", "Date", "Passengers", "Status");
        System.out.println("-".repeat(70));
        
        for (Reservation reservation : reservations.values()) {
            if (reservation.getUserId().equals(currentUser.getUsername())) {
                Train train = trains.get(reservation.getTrainNumber());
                System.out.printf("%-12s %-8s %-18s %-12s %-10d %-12s%n",
                        reservation.getPnr(),
                        reservation.getTrainNumber(),
                        train.getTrainName(),
                        reservation.getJourneyDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        reservation.getPassengers(),
                        reservation.getStatus()
                );
                hasHistory = true;
            }
        }
        
        if (!hasHistory) {
            System.out.println("📝 No booking history found.");
        }
        System.out.println("=".repeat(70));
    }
    
    private static void managePaymentMethods() {
        System.out.println("\n💳 PAYMENT METHODS");
        System.out.println("=".repeat(35));
        System.out.println("💰 Available Payment Options:");
        System.out.println("1. 💳 Credit/Debit Card");
        System.out.println("2. 🏦 Net Banking");
        System.out.println("3. 📱 UPI/Digital Wallets");
        System.out.println("4. 💵 Cash on Delivery (COD)");
        System.out.println("=".repeat(35));
        System.out.println("💡 All payments are processed securely!");
        System.out.println("🔒 Your financial information is protected.");
    }
    
    private static void logout() {
        System.out.println("\n🚪 LOGGING OUT...");
        System.out.println("👋 Thank you for using RailConnect, " + currentUser.getName() + "!");
        System.out.println("🚂 Have a safe and pleasant journey!");
        currentUser = null;
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🌟 Visit us again for your travel needs! 🌟");
        System.out.println("=".repeat(50));
    }
    
    private static void exitSystem() {
        System.out.println("\n👋 THANK YOU FOR CHOOSING RAILCONNECT!");
        System.out.println("🚂 Your trusted railway booking partner");
        System.out.println("🌟 Safe travels and see you soon!");
        System.exit(0);
    }
}