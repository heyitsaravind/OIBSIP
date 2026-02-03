package com.reservation.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.reservation.model.Train;
import com.reservation.util.DatabaseConnectionManager;

/**
 * Train Schedule Management Service
 * Handles train-related operations and schedule management
 */
public class TrainScheduleService {
    
    private final DatabaseConnectionManager dbManager;
    
    public TrainScheduleService() {
        this.dbManager = DatabaseConnectionManager.getInstance();
    }
    
    /**
     * Search for available trains between stations
     */
    public List<Train> searchAvailableTrains(String originStation, String destinationStation) {
        List<Train> availableTrains = new ArrayList<>();
        String query = "SELECT * FROM trains WHERE origin_station = ? AND destination_station = ? " +
                      "AND seats_remaining > 0 AND is_operational = true ORDER BY departure_time";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, originStation.toUpperCase());
            stmt.setString(2, destinationStation.toUpperCase());
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Train train = mapResultSetToTrain(rs);
                availableTrains.add(train);
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching trains: " + e.getMessage());
        }
        
        return availableTrains;
    }
    
    /**
     * Find train by ID
     */
    public Optional<Train> findTrainById(int trainId) {
        String query = "SELECT * FROM trains WHERE train_id = ? AND is_operational = true";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, trainId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Train train = mapResultSetToTrain(rs);
                return Optional.of(train);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding train: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    /**
     * Reserve seats on a train
     */
    public boolean reserveSeats(int trainId, int seatsToReserve) {
        String updateQuery = "UPDATE trains SET seats_remaining = seats_remaining - ? " +
                           "WHERE train_id = ? AND seats_remaining >= ?";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            
            stmt.setInt(1, seatsToReserve);
            stmt.setInt(2, trainId);
            stmt.setInt(3, seatsToReserve);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Reserved " + seatsToReserve + " seat(s) on train " + trainId);
                return true;
            } else {
                System.err.println("❌ Failed to reserve seats - insufficient availability");
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error reserving seats: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Release seats back to train inventory
     */
    public boolean releaseSeats(int trainId, int seatsToRelease) {
        String updateQuery = "UPDATE trains SET seats_remaining = LEAST(seats_remaining + ?, total_capacity) " +
                           "WHERE train_id = ?";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            
            stmt.setInt(1, seatsToRelease);
            stmt.setInt(2, trainId);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Released " + seatsToRelease + " seat(s) back to train " + trainId);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error releasing seats: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Get all operational trains
     */
    public List<Train> getAllOperationalTrains() {
        List<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM trains WHERE is_operational = true ORDER BY train_name";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                trains.add(mapResultSetToTrain(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving trains: " + e.getMessage());
        }
        
        return trains;
    }
    
    /**
     * Check seat availability for a specific train
     */
    public int checkSeatAvailability(int trainId) {
        String query = "SELECT seats_remaining FROM trains WHERE train_id = ? AND is_operational = true";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, trainId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("seats_remaining");
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking seat availability: " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Get unique station list for route planning
     */
    public List<String> getAllStations() {
        List<String> stations = new ArrayList<>();
        String query = "SELECT DISTINCT origin_station FROM trains WHERE is_operational = true " +
                      "UNION SELECT DISTINCT destination_station FROM trains WHERE is_operational = true " +
                      "ORDER BY 1";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                stations.add(rs.getString(1));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving stations: " + e.getMessage());
        }
        
        return stations;
    }
    
    /**
     * Map ResultSet to Train object
     */
    private Train mapResultSetToTrain(ResultSet rs) throws SQLException {
        Train train = new Train();
        train.setTrainId(rs.getInt("train_id"));
        train.setTrainName(rs.getString("train_name"));
        train.setOriginStation(rs.getString("origin_station"));
        train.setDestinationStation(rs.getString("destination_station"));
        train.setDepartureTime(rs.getTime("departure_time").toLocalTime());
        train.setArrivalTime(rs.getTime("arrival_time").toLocalTime());
        train.setTotalCapacity(rs.getInt("total_capacity"));
        train.setSeatsRemaining(rs.getInt("seats_remaining"));
        train.setTrainType(rs.getString("train_type"));
        train.setOperational(rs.getBoolean("is_operational"));
        return train;
    }
    
    /**
     * Display train schedule in formatted table
     */
    public void displayTrainSchedule(List<Train> trains) {
        if (trains.isEmpty()) {
            System.out.println("No trains found for the selected route.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(100));
        System.out.println("                              AVAILABLE TRAINS");
        System.out.println("=".repeat(100));
        System.out.printf("%-8s %-20s %-12s %-12s %-10s %-10s %-8s %-10s\n",
                "Train ID", "Train Name", "Origin", "Destination", "Departure", "Arrival", "Available", "Type");
        System.out.println("-".repeat(100));
        
        for (Train train : trains) {
            System.out.printf("%-8d %-20s %-12s %-12s %-10s %-10s %-8d %-10s\n",
                    train.getTrainId(),
                    train.getTrainName(),
                    train.getOriginStation(),
                    train.getDestinationStation(),
                    train.getDepartureTime(),
                    train.getArrivalTime(),
                    train.getSeatsRemaining(),
                    train.getTrainType());
        }
        System.out.println("=".repeat(100));
    }
}