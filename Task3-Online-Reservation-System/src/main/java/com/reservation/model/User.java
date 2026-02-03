package com.reservation.model;

import java.time.LocalDateTime;

public class User {
    private int accountId;
    private String loginId;
    private String securePassword;
    private String emailAddress;
    private String customerName;
    private String contactNumber;
    private LocalDateTime registrationTime;
    private boolean isActive;
    
    // Default constructor
    public User() {
        this.registrationTime = LocalDateTime.now();
        this.isActive = true;
    }
    
    // Parameterized constructor
    public User(String loginId, String securePassword, String emailAddress, 
                String customerName, String contactNumber) {
        this();
        this.loginId = loginId;
        this.securePassword = securePassword;
        this.emailAddress = emailAddress;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
    }
    
    // Getter and Setter methods with validation
    public int getAccountId() { 
        return accountId; 
    }
    
    public void setAccountId(int accountId) { 
        this.accountId = accountId; 
    }
    
    public String getLoginId() { 
        return loginId; 
    }
    
    public void setLoginId(String loginId) { 
        if (loginId != null && loginId.trim().length() >= 3) {
            this.loginId = loginId.trim();
        }
    }
    
    public String getSecurePassword() { 
        return securePassword; 
    }
    
    public void setSecurePassword(String securePassword) { 
        if (securePassword != null && securePassword.length() >= 6) {
            this.securePassword = securePassword;
        }
    }
    
    public String getEmailAddress() { 
        return emailAddress; 
    }
    
    public void setEmailAddress(String emailAddress) { 
        if (emailAddress != null && emailAddress.contains("@")) {
            this.emailAddress = emailAddress.toLowerCase();
        }
    }
    
    public String getCustomerName() { 
        return customerName; 
    }
    
    public void setCustomerName(String customerName) { 
        if (customerName != null && !customerName.trim().isEmpty()) {
            this.customerName = customerName.trim();
        }
    }
    
    public String getContactNumber() { 
        return contactNumber; 
    }
    
    public void setContactNumber(String contactNumber) { 
        if (contactNumber != null && contactNumber.matches("\\d{10}")) {
            this.contactNumber = contactNumber;
        }
    }
    
    public LocalDateTime getRegistrationTime() { 
        return registrationTime; 
    }
    
    public void setRegistrationTime(LocalDateTime registrationTime) { 
        this.registrationTime = registrationTime; 
    }
    
    public boolean isActive() { 
        return isActive; 
    }
    
    public void setActive(boolean active) { 
        isActive = active; 
    }
    
    @Override
    public String toString() {
        return "User{" +
                "accountId=" + accountId +
                ", loginId='" + loginId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}