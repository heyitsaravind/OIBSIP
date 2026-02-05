package com.reservation.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.reservation.model.Reservation;
import com.reservation.util.DatabaseConnectionManager;

/**
 * Reservation Data Management Service
 * Handles all reservation database operations
 */
public class ReservationDataService {
    
    private final DatabaseConnectionManager dbManager;
    
    public ReservationDataService() {
        this.dbManager = DatabaseConnectionManager.getInstance();
    }
    
    /**
     * Process and save new reservation
     */
    public boolean processNewReservation(Reservation reservation) {
        String insertQuery = "INSERT INTO reservations (confirmation_code, customer_id, train_id, " +
                           "traveler_name, travel_class, travel_date, boarding_station, alighting_station, " +
                           "status, ticket_price, booking_timestamp, number_of_passengers) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, reservation.getConfirmationCode());
            stmt.setInt(2, reservation.getCustomerId());
            stmt.setInt(3, reservation.getTrainId());
            stmt.setString(4, reservation.getTravelerName());
            stmt.setString(5, reservation.getTravelClass());
            stmt.setDate(6, Date.valueOf(reservation.getTravelDate()));
            stmt.setString(7, reservation.getBoardingStation());
            stmt.setString(8, reservation.getAlightingStation());
            stmt.setString(9, reservation.getStatus().name());
            stmt.setBigDecimal(10, reservation.getTicketPrice());
            stmt.setTimestamp(11, Timestamp.valueOf(reservation.getBookingTimestamp()));
            stmt.setInt(12, reservation.getNumberOfPassengers());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    System.out.println("✅ Reservation processed successfully with ID: " + generatedKeys.getInt(1));
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error processing reservation: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Find reservation by confirmation code
     */
    public Optional<Reservation> findReservationByCode(String confirmationCode) {
        String query = "SELECT * FROM reservations WHERE confirmation_code = ?";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, confirmationCode);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Reservation reservation = mapResultSetToReservation(rs);
                return Optional.of(reservation);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding reservation: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    /**
     * Cancel reservation by confirmation code
     */
    public boolean cancelReservation(String confirmationCode) {
        String updateQuery = "UPDATE reservations SET status = 'CANCELLED' WHERE confirmation_code = ? " +
                           "AND status = 'CONFIRMED'";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            
            stmt.setString(1, confirmationCode);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Reservation cancelled: " + confirmationCode);
                return true;
            } else {
                System.err.println("❌ Reservation not found or already cancelled: " + confirmationCode);
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error cancelling reservation: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get customer's reservation history
     */
    public List<Reservation> getCustomerReservations(int customerId) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE customer_id = ? ORDER BY booking_timestamp DESC";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving customer reservations: " + e.getMessage());
        }
        
        return reservations;
    }
    
    /**
     * Get reservations by travel date
     */
    public List<Reservation> getReservationsByDate(LocalDate travelDate) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE travel_date = ? AND status = 'CONFIRMED' " +
                      "ORDER BY booking_timestamp";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setDate(1, Date.valueOf(travelDate));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving reservations by date: " + e.getMessage());
        }
        
        return reservations;
    }
    
    /**
     * Update reservation status
     */
    public boolean updateReservationStatus(String confirmationCode, Reservation.ReservationStatus newStatus) {
        String updateQuery = "UPDATE reservations SET status = ? WHERE confirmation_code = ?";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            
            stmt.setString(1, newStatus.name());
            stmt.setString(2, confirmationCode);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating reservation status: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get reservation statistics
     */
    public void displayReservationStatistics() {
        String statsQuery = "SELECT " +
                          "COUNT(*) as total_reservations, " +
                          "SUM(CASE WHEN status = 'CONFIRMED' THEN 1 ELSE 0 END) as confirmed, " +
                          "SUM(CASE WHEN status = 'CANCELLED' THEN 1 ELSE 0 END) as cancelled, " +
                          "SUM(ticket_price) as total_revenue " +
                          "FROM reservations";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(statsQuery);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                System.out.println("\n=== Reservation Statistics ===");
                System.out.println("Total Reservations: " + rs.getInt("total_reservations"));
                System.out.println("Confirmed: " + rs.getInt("confirmed"));
                System.out.println("Cancelled: " + rs.getInt("cancelled"));
                System.out.println("Total Revenue: ₹" + rs.getBigDecimal("total_revenue"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving statistics: " + e.getMessage());
        }
    }
    
    /**
     * Map ResultSet to Reservation object
     */
    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setConfirmationCode(rs.getString("confirmation_code"));
        reservation.setCustomerId(rs.getInt("customer_id"));
        reservation.setTrainId(rs.getInt("train_id"));
        reservation.setTravelerName(rs.getString("traveler_name"));
        reservation.setTravelClass(rs.getString("travel_class"));
        reservation.setTravelDate(rs.getDate("travel_date").toLocalDate());
        reservation.setBoardingStation(rs.getString("boarding_station"));
        reservation.setAlightingStation(rs.getString("alighting_station"));
        reservation.setStatus(Reservation.ReservationStatus.valueOf(rs.getString("status")));
        reservation.setTicketPrice(rs.getBigDecimal("ticket_price"));
        reservation.setBookingTimestamp(rs.getTimestamp("booking_timestamp").toLocalDateTime());
        reservation.setSeatNumber(rs.getString("seat_number"));
        reservation.setNumberOfPassengers(rs.getInt("number_of_passengers"));
        return reservation;
    }
    
    /**
     * Display reservation details in formatted output
     */
    public void displayReservationDetails(Reservation reservation) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    RESERVATION DETAILS");
        System.out.println("=".repeat(60));
        System.out.printf("Confirmation Code    : %s\n", reservation.getConfirmationCode());
        System.out.printf("Traveler Name        : %s\n", reservation.getTravelerName());
        System.out.printf("Train ID             : %d\n", reservation.getTrainId());
        System.out.printf("Travel Class         : %s\n", reservation.getTravelClass().replace("_", " "));
        System.out.printf("Travel Date          : %s\n", reservation.getTravelDate());
        System.out.printf("Route                : %s → %s\n", 
                reservation.getBoardingStation(), reservation.getAlightingStation());
        System.out.printf("Status               : %s\n", reservation.getStatus());
        System.out.printf("Ticket Price         : ₹%.2f\n", reservation.getTicketPrice());
        System.out.printf("Booking Date         : %s\n", reservation.getBookingTimestamp().toLocalDate());
        System.out.printf("Passengers           : %d\n", reservation.getNumberOfPassengers());
        
        if (reservation.getSeatNumber() != null) {
            System.out.printf("Seat Number          : %s\n", reservation.getSeatNumber());
        }
        
        System.out.println("=".repeat(60));
    }
}