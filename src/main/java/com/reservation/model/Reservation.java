package com.reservation.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {
    private String confirmationCode;
    private int customerId;
    private int trainId;
    private String travelerName;
    private String travelClass;
    private LocalDate travelDate;
    private String boardingStation;
    private String alightingStation;
    private ReservationStatus status;
    private BigDecimal ticketPrice;
    private LocalDateTime bookingTimestamp;
    private String seatNumber;
    private int numberOfPassengers;
    
    // Enum for reservation status
    public enum ReservationStatus {
        CONFIRMED, CANCELLED, WAITING_LIST, REFUNDED
    }
    
    // Default constructor
    public Reservation() {
        this.bookingTimestamp = LocalDateTime.now();
        this.status = ReservationStatus.CONFIRMED;
        this.numberOfPassengers = 1;
    }
    
    // Parameterized constructor
    public Reservation(String confirmationCode, int customerId, int trainId, String travelerName,
                      String travelClass, LocalDate travelDate, String boardingStation, 
                      String alightingStation, BigDecimal ticketPrice) {
        this();
        this.confirmationCode = confirmationCode;
        this.customerId = customerId;
        this.trainId = trainId;
        this.travelerName = travelerName;
        this.travelClass = travelClass;
        this.travelDate = travelDate;
        this.boardingStation = boardingStation;
        this.alightingStation = alightingStation;
        this.ticketPrice = ticketPrice;
    }
    
    // Business methods
    public boolean canBeCancelled() {
        return status == ReservationStatus.CONFIRMED && 
               travelDate.isAfter(LocalDate.now());
    }
    
    public boolean isActive() {
        return status == ReservationStatus.CONFIRMED || 
               status == ReservationStatus.WAITING_LIST;
    }
    
    public void cancelReservation() {
        if (canBeCancelled()) {
            this.status = ReservationStatus.CANCELLED;
        }
    }
    
    // Getter and Setter methods
    public String getConfirmationCode() { 
        return confirmationCode; 
    }
    
    public void setConfirmationCode(String confirmationCode) { 
        this.confirmationCode = confirmationCode; 
    }
    
    public int getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(int customerId) { 
        this.customerId = customerId; 
    }
    
    public int getTrainId() { 
        return trainId; 
    }
    
    public void setTrainId(int trainId) { 
        this.trainId = trainId; 
    }
    
    public String getTravelerName() { 
        return travelerName; 
    }
    
    public void setTravelerName(String travelerName) { 
        if (travelerName != null && !travelerName.trim().isEmpty()) {
            this.travelerName = travelerName.trim();
        }
    }
    
    public String getTravelClass() { 
        return travelClass; 
    }
    
    public void setTravelClass(String travelClass) { 
        this.travelClass = travelClass; 
    }
    
    public LocalDate getTravelDate() { 
        return travelDate; 
    }
    
    public void setTravelDate(LocalDate travelDate) { 
        this.travelDate = travelDate; 
    }
    
    public String getBoardingStation() { 
        return boardingStation; 
    }
    
    public void setBoardingStation(String boardingStation) { 
        if (boardingStation != null) {
            this.boardingStation = boardingStation.trim().toUpperCase();
        }
    }
    
    public String getAlightingStation() { 
        return alightingStation; 
    }
    
    public void setAlightingStation(String alightingStation) { 
        if (alightingStation != null) {
            this.alightingStation = alightingStation.trim().toUpperCase();
        }
    }
    
    public ReservationStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(ReservationStatus status) { 
        this.status = status; 
    }
    
    public BigDecimal getTicketPrice() { 
        return ticketPrice; 
    }
    
    public void setTicketPrice(BigDecimal ticketPrice) { 
        if (ticketPrice != null && ticketPrice.compareTo(BigDecimal.ZERO) >= 0) {
            this.ticketPrice = ticketPrice;
        }
    }
    
    public LocalDateTime getBookingTimestamp() { 
        return bookingTimestamp; 
    }
    
    public void setBookingTimestamp(LocalDateTime bookingTimestamp) { 
        this.bookingTimestamp = bookingTimestamp; 
    }
    
    public String getSeatNumber() { 
        return seatNumber; 
    }
    
    public void setSeatNumber(String seatNumber) { 
        this.seatNumber = seatNumber; 
    }
    
    public int getNumberOfPassengers() { 
        return numberOfPassengers; 
    }
    
    public void setNumberOfPassengers(int numberOfPassengers) { 
        if (numberOfPassengers > 0) {
            this.numberOfPassengers = numberOfPassengers;
        }
    }
    
    @Override
    public String toString() {
        return String.format("Reservation{code='%s', traveler='%s', train=%d, date=%s, status=%s, price=%s}", 
                confirmationCode, travelerName, trainId, travelDate, status, ticketPrice);
    }
}