package com.reservation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import com.reservation.model.User;
import com.reservation.util.DatabaseConnectionManager;

/**
 * User Data Access Object with enhanced security and validation
 */
public class CustomerDataAccess {
    
    private final DatabaseConnectionManager dbManager;
    
    public CustomerDataAccess() {
        this.dbManager = DatabaseConnectionManager.getInstance();
    }
    
    /**
     * Authenticate user with secure password verification
     */
    public boolean verifyUserCredentials(String loginId, String password) {
        String query = "SELECT account_id, secure_password, is_active FROM customers WHERE login_id = ?";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, loginId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                boolean isActive = rs.getBoolean("is_active");
                String storedPassword = rs.getString("secure_password");
                
                // Simple password verification (in production, use hashed passwords)
                return isActive && password.equals(storedPassword);
            }
            
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Create new user account with validation
     */
    public boolean createUserAccount(User user) {
        // Check if user already exists
        if (findUserByLoginId(user.getLoginId()).isPresent()) {
            System.err.println("User with login ID already exists: " + user.getLoginId());
            return false;
        }
        
        String insertQuery = "INSERT INTO customers (login_id, secure_password, email_address, " +
                           "customer_name, contact_number, registration_time, is_active) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getLoginId());
            stmt.setString(2, user.getSecurePassword());
            stmt.setString(3, user.getEmailAddress());
            stmt.setString(4, user.getCustomerName());
            stmt.setString(5, user.getContactNumber());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setBoolean(7, true);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setAccountId(generatedKeys.getInt(1));
                    System.out.println("✅ User account created successfully with ID: " + user.getAccountId());
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating user account: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Find user by login ID
     */
    public Optional<User> findUserByLoginId(String loginId) {
        String query = "SELECT * FROM customers WHERE login_id = ? AND is_active = true";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, loginId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                return Optional.of(user);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    /**
     * Update user profile information
     */
    public boolean updateUserProfile(User user) {
        String updateQuery = "UPDATE customers SET email_address = ?, customer_name = ?, " +
                           "contact_number = ? WHERE account_id = ?";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            
            stmt.setString(1, user.getEmailAddress());
            stmt.setString(2, user.getCustomerName());
            stmt.setString(3, user.getContactNumber());
            stmt.setInt(4, user.getAccountId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating user profile: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Map ResultSet to User object
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setAccountId(rs.getInt("account_id"));
        user.setLoginId(rs.getString("login_id"));
        user.setSecurePassword(rs.getString("secure_password"));
        user.setEmailAddress(rs.getString("email_address"));
        user.setCustomerName(rs.getString("customer_name"));
        user.setContactNumber(rs.getString("contact_number"));
        user.setRegistrationTime(rs.getTimestamp("registration_time").toLocalDateTime());
        user.setActive(rs.getBoolean("is_active"));
        return user;
    }
    
    /**
     * Check if email already exists
     */
    public boolean isEmailAlreadyRegistered(String email) {
        String query = "SELECT COUNT(*) FROM customers WHERE email_address = ?";
        
        try (Connection conn = dbManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking email: " + e.getMessage());
        }
        
        return false;
    }
}