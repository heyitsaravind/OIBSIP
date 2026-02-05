/**
 * User Class - Represents a system user with authentication and profile information
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.time.LocalDateTime;

public class User {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime registrationDate;
    private boolean isActive;
    
    public User(String username, String password, String name, String email, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.registrationDate = LocalDateTime.now();
        this.isActive = true;
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
    
    public String getPhone() {
        return phone;
    }
    
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    
    public boolean isActive() {
        return isActive;
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
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    // Utility methods
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
    public boolean isValidEmail() {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    public boolean isValidPhone() {
        return phone != null && phone.matches("\\d{10}");
    }
    
    @Override
    public String toString() {
        return String.format("User{username='%s', name='%s', email='%s', active=%s}", 
                username, name, email, isActive);
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