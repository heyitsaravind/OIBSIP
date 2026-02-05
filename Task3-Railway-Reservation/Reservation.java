/**
 * Reservation Class - Represents a train booking reservation
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private String pnr;
    private String userId;
    private String trainNumber;
    private int passengers;
    private LocalDate journeyDate;
    private double totalFare;
    private String status;
    private LocalDateTime bookingTime;
    private LocalDateTime cancellationTime;
    
    public Reservation(String pnr, String userId, String trainNumber, 
                      int passengers, LocalDate journeyDate, double totalFare) {
        this.pnr = pnr;
        this.userId = userId;
        this.trainNumber = trainNumber;
        this.passengers = passengers;
        this.journeyDate = journeyDate;
        this.totalFare = totalFare;
        this.status = "CONFIRMED";
        this.bookingTime = LocalDateTime.now();
        this.cancellationTime = null;
    }
    
    // Getters
    public String getPnr() {
        return pnr;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getTrainNumber() {
        return trainNumber;
    }
    
    public int getPassengers() {
        return passengers;
    }
    
    public LocalDate getJourneyDate() {
        return journeyDate;
    }
    
    public double getTotalFare() {
        return totalFare;
    }
    
    public String getStatus() {
        return status;
    }
    
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    
    public LocalDateTime getCancellationTime() {
        return cancellationTime;
    }
    
    // Status management methods
    public void cancel() {
        this.status = "CANCELLED";
        this.cancellationTime = LocalDateTime.now();
    }
    
    public void confirm() {
        this.status = "CONFIRMED";
    }
    
    public void markAsCompleted() {
        this.status = "COMPLETED";
    }
    
    public void markAsNoShow() {
        this.status = "NO_SHOW";
    }
    
    // Utility methods
    public boolean isActive() {
        return "CONFIRMED".equals(status);
    }
    
    public boolean isCancelled() {
        return "CANCELLED".equals(status);
    }
    
    public boolean isCompleted() {
        return "COMPLETED".equals(status);
    }
    
    public double getFarePerPassenger() {
        return totalFare / passengers;
    }
    
    public String getFormattedBookingTime() {
        return bookingTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    public String getFormattedJourneyDate() {
        return journeyDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }
    
    public boolean isJourneyToday() {
        return journeyDate.equals(LocalDate.now());
    }
    
    public boolean isJourneyInPast() {
        return journeyDate.isBefore(LocalDate.now());
    }
    
    public boolean isJourneyInFuture() {
        return journeyDate.isAfter(LocalDate.now());
    }
    
    public long getDaysUntilJourney() {
        return LocalDate.now().until(journeyDate).getDays();
    }
    
    public boolean canBeCancelled() {
        // Can be cancelled if confirmed and journey is in future
        return isActive() && isJourneyInFuture();
    }
    
    public double calculateCancellationCharges() {
        if (!canBeCancelled()) {
            return totalFare; // Full charges if cannot be cancelled
        }
        
        long daysUntilJourney = getDaysUntilJourney();
        
        if (daysUntilJourney >= 7) {
            return totalFare * 0.05; // 5% charges for 7+ days advance
        } else if (daysUntilJourney >= 3) {
            return totalFare * 0.10; // 10% charges for 3-6 days advance
        } else if (daysUntilJourney >= 1) {
            return totalFare * 0.25; // 25% charges for 1-2 days advance
        } else {
            return totalFare * 0.50; // 50% charges for same day
        }
    }
    
    public double calculateRefundAmount() {
        return totalFare - calculateCancellationCharges();
    }
    
    @Override
    public String toString() {
        return String.format("Reservation{pnr='%s', train='%s', passengers=%d, date='%s', fare=%.2f, status='%s'}", 
                pnr, trainNumber, passengers, getFormattedJourneyDate(), totalFare, status);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Reservation that = (Reservation) obj;
        return pnr.equals(that.pnr);
    }
    
    @Override
    public int hashCode() {
        return pnr.hashCode();
    }
}