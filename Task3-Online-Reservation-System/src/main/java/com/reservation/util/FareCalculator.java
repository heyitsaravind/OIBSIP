package com.reservation.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Advanced Fare Calculator with dynamic pricing
 * Calculates ticket prices based on class, distance, and demand
 */
public class FareCalculator {
    
    // Base fare rates per class (per 100 km)
    private static final Map<String, BigDecimal> BASE_FARE_RATES = new HashMap<>();
    
    static {
        BASE_FARE_RATES.put("FIRST_AC", new BigDecimal("25.00"));
        BASE_FARE_RATES.put("SECOND_AC", new BigDecimal("18.50"));
        BASE_FARE_RATES.put("THIRD_AC", new BigDecimal("12.75"));
        BASE_FARE_RATES.put("SLEEPER_CLASS", new BigDecimal("8.25"));
        BASE_FARE_RATES.put("GENERAL_CLASS", new BigDecimal("3.50"));
    }
    
    // Distance matrix for major routes (in kilometers)
    private static final Map<String, Integer> ROUTE_DISTANCES = new HashMap<>();
    
    static {
        ROUTE_DISTANCES.put("DELHI-MUMBAI", 1400);
        ROUTE_DISTANCES.put("MUMBAI-DELHI", 1400);
        ROUTE_DISTANCES.put("DELHI-CHANDIGARH", 250);
        ROUTE_DISTANCES.put("CHANDIGARH-DELHI", 250);
        ROUTE_DISTANCES.put("MUMBAI-KOLKATA", 2000);
        ROUTE_DISTANCES.put("KOLKATA-MUMBAI", 2000);
        ROUTE_DISTANCES.put("CHENNAI-BANGALORE", 350);
        ROUTE_DISTANCES.put("BANGALORE-CHENNAI", 350);
        ROUTE_DISTANCES.put("PUNE-MUMBAI", 150);
        ROUTE_DISTANCES.put("MUMBAI-PUNE", 150);
    }
    
    /**
     * Calculate fare based on travel class and route
     */
    public static BigDecimal calculateBaseFare(String travelClass, String origin, String destination) {
        String route = origin.toUpperCase() + "-" + destination.toUpperCase();
        
        // Get base rate for the class
        BigDecimal baseRate = BASE_FARE_RATES.getOrDefault(travelClass.toUpperCase(), 
                                                          BASE_FARE_RATES.get("GENERAL_CLASS"));
        
        // Get distance for the route
        int distance = ROUTE_DISTANCES.getOrDefault(route, 500); // Default 500km
        
        // Calculate fare: (base_rate * distance) / 100
        BigDecimal fare = baseRate.multiply(new BigDecimal(distance))
                                 .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        
        return fare;
    }
    
    /**
     * Calculate dynamic fare with surge pricing
     */
    public static BigDecimal calculateDynamicFare(String travelClass, String origin, 
                                                 String destination, LocalDate travelDate, 
                                                 int availableSeats, int totalSeats) {
        BigDecimal baseFare = calculateBaseFare(travelClass, origin, destination);
        
        // Apply surge pricing based on availability
        BigDecimal surgeFactor = calculateSurgeFactor(availableSeats, totalSeats);
        
        // Apply advance booking discount/premium
        BigDecimal timeFactor = calculateTimeFactor(travelDate);
        
        // Calculate final fare
        BigDecimal finalFare = baseFare.multiply(surgeFactor).multiply(timeFactor);
        
        return finalFare.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculate surge factor based on seat availability
     */
    private static BigDecimal calculateSurgeFactor(int availableSeats, int totalSeats) {
        double occupancyRate = (double) (totalSeats - availableSeats) / totalSeats;
        
        if (occupancyRate >= 0.9) {
            return new BigDecimal("1.5"); // 50% surge for high demand
        } else if (occupancyRate >= 0.7) {
            return new BigDecimal("1.25"); // 25% surge for medium demand
        } else if (occupancyRate >= 0.5) {
            return new BigDecimal("1.1"); // 10% surge for moderate demand
        } else {
            return new BigDecimal("1.0"); // No surge for low demand
        }
    }
    
    /**
     * Calculate time-based pricing factor
     */
    private static BigDecimal calculateTimeFactor(LocalDate travelDate) {
        long daysUntilTravel = ChronoUnit.DAYS.between(LocalDate.now(), travelDate);
        
        if (daysUntilTravel >= 30) {
            return new BigDecimal("0.85"); // 15% early bird discount
        } else if (daysUntilTravel >= 7) {
            return new BigDecimal("0.95"); // 5% advance booking discount
        } else if (daysUntilTravel <= 1) {
            return new BigDecimal("1.2"); // 20% last-minute premium
        } else {
            return new BigDecimal("1.0"); // Standard pricing
        }
    }
    
    /**
     * Get all available travel classes with base fares
     */
    public static Map<String, BigDecimal> getAvailableClasses(String origin, String destination) {
        Map<String, BigDecimal> classesWithFares = new HashMap<>();
        
        for (Map.Entry<String, BigDecimal> entry : BASE_FARE_RATES.entrySet()) {
            String className = entry.getKey();
            BigDecimal fare = calculateBaseFare(className, origin, destination);
            classesWithFares.put(className, fare);
        }
        
        return classesWithFares;
    }
    
    /**
     * Apply discount for senior citizens or children
     */
    public static BigDecimal applyPassengerDiscount(BigDecimal baseFare, String passengerType) {
        switch (passengerType.toUpperCase()) {
            case "SENIOR_CITIZEN":
                return baseFare.multiply(new BigDecimal("0.6")); // 40% discount
            case "CHILD":
                return baseFare.multiply(new BigDecimal("0.5")); // 50% discount
            case "STUDENT":
                return baseFare.multiply(new BigDecimal("0.75")); // 25% discount
            default:
                return baseFare; // No discount for regular passengers
        }
    }
    
    /**
     * Format fare for display
     */
    public static String formatFare(BigDecimal fare) {
        return "₹" + fare.setScale(2, RoundingMode.HALF_UP).toString();
    }
}