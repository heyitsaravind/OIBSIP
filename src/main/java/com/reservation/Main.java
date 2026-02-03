package com.reservation;

import com.reservation.ui.InteractiveConsoleInterface;
import com.reservation.util.DatabaseConnectionManager;

/**
 * Main Application Entry Point
 * Train Booking Management System - RailConnect
 */
public class TrainBookingApplication {
    
    public static void main(String[] args) {
        System.out.println("🚀 Starting RailConnect - Train Booking System...");
        
        // Test database connectivity
        DatabaseConnectionManager dbManager = DatabaseConnectionManager.getInstance();
        if (!dbManager.testConnection()) {
            System.err.println("❌ Database connection failed. Please check your database configuration.");
            System.err.println("💡 Make sure MySQL is running and the database 'train_booking_system' exists.");
            return;
        }
        
        // Launch the interactive console interface
        try {
            InteractiveConsoleInterface consoleApp = new InteractiveConsoleInterface();
            consoleApp.launchApplication();
        } catch (Exception e) {
            System.err.println("❌ Application error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up database connection
            dbManager.terminateConnection();
        }
    }
}