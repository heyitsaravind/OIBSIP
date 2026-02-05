/**
 * User Class - Represents system users (Students and Administrators)
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.time.LocalDateTime;

public class User {
    private String username;
    private String password;
    private String name;
    private String email;
    private String role; // STUDENT or ADMIN
    private LocalDateTime registrationDate;
    private boolean isActive;
    private int loginCount;
    private LocalDateTime lastLogin;
    
    public User(String username, String password, String name, String email, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.registrationDate = LocalDateTime.now();
        this.isActive = true;
        this.loginCount = 0;
        this.lastLogin = null;
    }
    
    // Getters
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getRole() {
        return role;
    }
    
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public int getLoginCount() {
        return loginCount;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    // Setters
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    // Utility methods
    public void recordLogin() {
        this.loginCount++;
        this.lastLogin = LocalDateTime.now();
    }
    
    public boolean isStudent() {
        return "STUDENT".equals(role);
    }
    
    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }
    
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
    public boolean isValidEmail() {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    public String getDisplayRole() {
        return role.equals("ADMIN") ? "Administrator" : "Student";
    }
    
    @Override
    public String toString() {
        return String.format("User{username='%s', name='%s', role='%s', active=%s}", 
                username, name, role, isActive);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        User user = (User) obj;
        return username.equals(user.username);
    }
    
    @Override
    public int hashCode() {
        return username.hashCode();
    }
}