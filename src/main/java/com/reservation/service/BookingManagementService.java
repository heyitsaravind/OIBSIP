package com.reservation.service;

import com.reservation.dao.CustomerDataAccess;
import com.reservation.model.Reservation;
import com.reservation.model.Train;
import com.reservation.model.User;
import com.reservation.util.ConfirmationCodeGenerator;
import com.reservation.util.FareCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Comprehensive Booking Management Service
 * Handles all reservation-related business logic with enhanced features
 */
public class BookingManagementService {
    
    private final CustomerDataAccess customerDAO;
    private final TrainScheduleService trainService;
    private final ReservationDataService reservationService;
    
    public BookingManagementService() {
        this.customerDAO = new CustomerDataAccess();
        this.trainService = new TrainScheduleService();
        this.reservationService = new ReservationDataService();
    }
    
    /**
     * Authenticate user login credentials
     */
    public boolean authenticateCustomer(String loginId, String password) {
        if (loginId == null || password == null || loginId.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        
        return customerDAO.verifyUserCredentials(loginId.trim(), password);
    }
    
    /**
     * Register new customer account with validation
     */
    public boolean registerNewCustomer(User customer) {
        // Validate customer data
        if (!isValidCustomerData(customer)) {
            return false;
        }
        
        // Check if email is already registered
        if (customerDAO.isEmailAlreadyRegistered(customer.getEmailAddress())) {
            System.err.println("Email already registered: " + customer.getEmailAddress());
            return false;
        }
        
        return customerDAO.createUserAccount(customer);
    }
    
    /**
     * Search for available trains between stations
     */
    public List<Train> findAvailableTrains(String originStation, String destinationStation, LocalDate travelDate) {
        if (originStation == null || destinationStation == null || travelDate == null) {
            throw new IllegalArgumentException("Origin, destination, and travel date cannot be null");
        }
        
        if (travelDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Travel date cannot be in the past");
        }
        
        return trainService.searchAvailableTrains(originStation.toUpperCase(), 
                                                destinationStation.toUpperCase());
    }
    
    /**
     * Create new reservation with comprehensive validation
     */
    public String createNewReservation(String customerLoginId, int trainId, String travelerName,
                                     String travelClass, LocalDate travelDate, String originStation, 
                                     String destinationStation) {
        
        // Validate input parameters
        if (!isValidReservationData(customerLoginId, trainId, travelerName, travelClass, travelDate)) {
            return null;
        }
        
        // Get customer details
        Optional<User> customerOpt = customerDAO.findUserByLoginId(customerLoginId);
        if (!customerOpt.isPresent()) {
            System.err.println("Customer not found: " + customerLoginId);
            return null;
        }
        User customer = customerOpt.get();
        
        // Check train availability
        Optional<Train> trainOpt = trainService.findTrainById(trainId);
        if (!trainOpt.isPresent() || !trainOpt.get().hasAvailableSeats(1)) {
            System.err.println("Train not available or no seats remaining");
            return null;
        }
        Train train = trainOpt.get();
        
        // Generate unique confirmation code
        String confirmationCode = ConfirmationCodeGenerator.generateConfirmationCode();
        
        // Calculate dynamic fare
        BigDecimal ticketPrice = FareCalculator.calculateDynamicFare(
            travelClass, originStation, destinationStation, travelDate,
            train.getSeatsRemaining(), train.getTotalCapacity()
        );
        
        // Create reservation object
        Reservation reservation = new Reservation(
            confirmationCode, customer.getAccountId(), trainId, travelerName,
            travelClass, travelDate, originStation.toUpperCase(), 
            destinationStation.toUpperCase(), ticketPrice
        );
        
        // Process reservation
        if (reservationService.processNewReservation(reservation)) {
            // Update train seat availability
            if (trainService.reserveSeats(trainId, 1)) {
                System.out.println("✅ Reservation created successfully!");
                System.out.println("Confirmation Code: " + confirmationCode);
                System.out.println("Ticket Price: " + FareCalculator.formatFare(ticketPrice));
                return confirmationCode;
            } else {
                // Rollback reservation if seat update fails
                reservationService.cancelReservation(confirmationCode);
                System.err.println("Failed to update seat availability");
            }
        }
        
        return null;
    }
    
    /**
     * Retrieve reservation details by confirmation code
     */
    public Optional<Reservation> getReservationDetails(String confirmationCode) {
        if (confirmationCode == null || confirmationCode.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return reservationService.findReservationByCode(confirmationCode.trim().toUpperCase());
    }
    
    /**
     * Cancel existing reservation with validation
     */
    public boolean cancelExistingReservation(String confirmationCode) {
        Optional<Reservation> reservationOpt = getReservationDetails(confirmationCode);
        
        if (!reservationOpt.isPresent()) {
            System.err.println("Reservation not found: " + confirmationCode);
            return false;
        }
        
        Reservation reservation = reservationOpt.get();
        
        if (!reservation.canBeCancelled()) {
            System.err.println("Reservation cannot be cancelled (already cancelled or past travel date)");
            return false;
        }
        
        // Cancel the reservation
        if (reservationService.cancelReservation(confirmationCode)) {
            // Release the reserved seats
            trainService.releaseSeats(reservation.getTrainId(), reservation.getNumberOfPassengers());
            System.out.println("✅ Reservation cancelled successfully!");
            return true;
        }
        
        return false;
    }
    
    /**
     * Get customer profile by login ID
     */
    public Optional<User> getCustomerProfile(String loginId) {
        return customerDAO.findUserByLoginId(loginId);
    }
    
    /**
     * Update customer profile information
     */
    public boolean updateCustomerProfile(User customer) {
        if (!isValidCustomerData(customer)) {
            return false;
        }
        
        return customerDAO.updateUserProfile(customer);
    }
    
    /**
     * Validate customer data
     */
    private boolean isValidCustomerData(User customer) {
        if (customer == null) return false;
        
        if (customer.getLoginId() == null || customer.getLoginId().trim().length() < 3) {
            System.err.println("Login ID must be at least 3 characters long");
            return false;
        }
        
        if (customer.getSecurePassword() == null || customer.getSecurePassword().length() < 6) {
            System.err.println("Password must be at least 6 characters long");
            return false;
        }
        
        if (customer.getEmailAddress() == null || !customer.getEmailAddress().contains("@")) {
            System.err.println("Valid email address is required");
            return false;
        }
        
        if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) {
            System.err.println("Customer name is required");
            return false;
        }
        
        if (customer.getContactNumber() == null || !customer.getContactNumber().matches("\\d{10}")) {
            System.err.println("Valid 10-digit contact number is required");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validate reservation data
     */
    private boolean isValidReservationData(String customerLoginId, int trainId, String travelerName,
                                         String travelClass, LocalDate travelDate) {
        if (customerLoginId == null || customerLoginId.trim().isEmpty()) {
            System.err.println("Customer login ID is required");
            return false;
        }
        
        if (trainId <= 0) {
            System.err.println("Valid train ID is required");
            return false;
        }
        
        if (travelerName == null || travelerName.trim().isEmpty()) {
            System.err.println("Traveler name is required");
            return false;
        }
        
        if (travelClass == null || travelClass.trim().isEmpty()) {
            System.err.println("Travel class is required");
            return false;
        }
        
        if (travelDate == null || travelDate.isBefore(LocalDate.now())) {
            System.err.println("Valid future travel date is required");
            return false;
        }
        
        return true;
    }
    
    /**
     * Get available travel classes with pricing
     */
    public void displayAvailableClasses(String origin, String destination) {
        System.out.println("\n=== Available Travel Classes ===");
        var classesWithFares = FareCalculator.getAvailableClasses(origin, destination);
        
        classesWithFares.forEach((className, fare) -> {
            String displayName = className.replace("_", " ");
            System.out.printf("%-15s : %s\n", displayName, FareCalculator.formatFare(fare));
        });
    }
}