package com.reservation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton Database Connection Manager
 * Provides centralized database connectivity with connection pooling support
 */
public class DatabaseConnectionManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/train_booking_system";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password"; // Update with your MySQL password
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    
    private static DatabaseConnectionManager instance;
    private static Connection activeConnection;
    
    // Private constructor for singleton pattern
    private DatabaseConnectionManager() {}
    
    /**
     * Get singleton instance of DatabaseConnectionManager
     */
    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }
    
    /**
     * Establish and return database connection
     */
    public Connection establishConnection() throws SQLException {
        try {
            if (activeConnection == null || activeConnection.isClosed()) {
                // Load MySQL driver
                Class.forName(DRIVER_CLASS);
                
                // Set connection properties
                Properties connectionProps = new Properties();
                connectionProps.setProperty("user", DB_USERNAME);
                connectionProps.setProperty("password", DB_PASSWORD);
                connectionProps.setProperty("useSSL", "false");
                connectionProps.setProperty("serverTimezone", "UTC");
                connectionProps.setProperty("allowPublicKeyRetrieval", "true");
                
                // Create connection
                activeConnection = DriverManager.getConnection(DB_URL, connectionProps);
                activeConnection.setAutoCommit(true);
                
                System.out.println("✅ Database connection established successfully!");
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found: " + e.getMessage(), e);
        } catch (SQLException e) {
            System.err.println("❌ Failed to establish database connection: " + e.getMessage());
            throw e;
        }
        return activeConnection;
    }
    
    /**
     * Test database connectivity
     */
    public boolean testConnection() {
        try (Connection testConn = establishConnection()) {
            return testConn != null && !testConn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connectivity test failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Close active database connection
     */
    public void terminateConnection() {
        try {
            if (activeConnection != null && !activeConnection.isClosed()) {
                activeConnection.close();
                activeConnection = null;
                System.out.println("🔒 Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error while closing database connection: " + e.getMessage());
        }
    }
    
    /**
     * Get current connection status
     */
    public boolean isConnectionActive() {
        try {
            return activeConnection != null && !activeConnection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}