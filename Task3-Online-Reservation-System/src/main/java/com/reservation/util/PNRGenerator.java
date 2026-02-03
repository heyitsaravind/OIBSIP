package com.reservation.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Advanced Confirmation Code Generator
 * Generates unique alphanumeric confirmation codes for reservations
 */
public class ConfirmationCodeGenerator {
    
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String ALLOWED_CHARS = UPPERCASE_LETTERS + NUMBERS;
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom secureRandom = new SecureRandom();
    
    /**
     * Generate a unique confirmation code
     * Format: 2 letters + 4 numbers + 2 letters (e.g., AB1234CD)
     */
    public static String generateConfirmationCode() {
        StringBuilder codeBuilder = new StringBuilder();
        
        // Add 2 random letters
        for (int i = 0; i < 2; i++) {
            codeBuilder.append(UPPERCASE_LETTERS.charAt(secureRandom.nextInt(UPPERCASE_LETTERS.length())));
        }
        
        // Add 4 random numbers
        for (int i = 0; i < 4; i++) {
            codeBuilder.append(NUMBERS.charAt(secureRandom.nextInt(NUMBERS.length())));
        }
        
        // Add 2 more random letters
        for (int i = 0; i < 2; i++) {
            codeBuilder.append(UPPERCASE_LETTERS.charAt(secureRandom.nextInt(UPPERCASE_LETTERS.length())));
        }
        
        return codeBuilder.toString();
    }
    
    /**
     * Generate time-based confirmation code
     * Includes timestamp elements for uniqueness
     */
    public static String generateTimestampBasedCode() {
        LocalDateTime now = LocalDateTime.now();
        String timeComponent = now.format(DateTimeFormatter.ofPattern("HHmm"));
        String randomComponent = generateRandomString(4);
        
        return randomComponent + timeComponent;
    }
    
    /**
     * Generate simple random alphanumeric code
     */
    public static String generateSimpleCode() {
        return generateRandomString(CODE_LENGTH);
    }
    
    /**
     * Generate random string of specified length
     */
    private static String generateRandomString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(ALLOWED_CHARS.charAt(secureRandom.nextInt(ALLOWED_CHARS.length())));
        }
        return result.toString();
    }
    
    /**
     * Validate confirmation code format
     */
    public static boolean isValidConfirmationCode(String code) {
        if (code == null || code.length() != CODE_LENGTH) {
            return false;
        }
        
        return code.matches("[A-Z0-9]{" + CODE_LENGTH + "}");
    }
    
    /**
     * Generate multiple unique codes
     */
    public static String[] generateMultipleCodes(int count) {
        String[] codes = new String[count];
        for (int i = 0; i < count; i++) {
            codes[i] = generateConfirmationCode();
        }
        return codes;
    }
}