package com.reservation.model;

import java.time.LocalTime;

public class Train {
    private int trainId;
    private String trainName;
    private String originStation;
    private String destinationStation;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int totalCapacity;
    private int seatsRemaining;
    private String trainType; // EXPRESS, PASSENGER, SUPERFAST
    private boolean isOperational;
    
    // Default constructor
    public Train() {
        this.isOperational = true;
    }
    
    // Parameterized constructor
    public Train(int trainId, String trainName, String originStation, String destinationStation, 
                 LocalTime departureTime, LocalTime arrivalTime, int totalCapacity, String trainType) {
        this();
        this.trainId = trainId;
        this.trainName = trainName;
        this.originStation = originStation;
        this.destinationStation = destinationStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalCapacity = totalCapacity;
        this.seatsRemaining = totalCapacity;
        this.trainType = trainType;
    }
    
    // Business methods
    public boolean hasAvailableSeats(int requestedSeats) {
        return seatsRemaining >= requestedSeats && isOperational;
    }
    
    public boolean reserveSeats(int seatsToReserve) {
        if (hasAvailableSeats(seatsToReserve)) {
            seatsRemaining -= seatsToReserve;
            return true;
        }
        return false;
    }
    
    public void releaseSeats(int seatsToRelease) {
        if (seatsRemaining + seatsToRelease <= totalCapacity) {
            seatsRemaining += seatsToRelease;
        }
    }
    
    // Getter and Setter methods
    public int getTrainId() { 
        return trainId; 
    }
    
    public void setTrainId(int trainId) { 
        this.trainId = trainId; 
    }
    
    public String getTrainName() { 
        return trainName; 
    }
    
    public void setTrainName(String trainName) { 
        if (trainName != null && !trainName.trim().isEmpty()) {
            this.trainName = trainName.trim();
        }
    }
    
    public String getOriginStation() { 
        return originStation; 
    }
    
    public void setOriginStation(String originStation) { 
        if (originStation != null && !originStation.trim().isEmpty()) {
            this.originStation = originStation.trim().toUpperCase();
        }
    }
    
    public String getDestinationStation() { 
        return destinationStation; 
    }
    
    public void setDestinationStation(String destinationStation) { 
        if (destinationStation != null && !destinationStation.trim().isEmpty()) {
            this.destinationStation = destinationStation.trim().toUpperCase();
        }
    }
    
    public LocalTime getDepartureTime() { 
        return departureTime; 
    }
    
    public void setDepartureTime(LocalTime departureTime) { 
        this.departureTime = departureTime; 
    }
    
    public LocalTime getArrivalTime() { 
        return arrivalTime; 
    }
    
    public void setArrivalTime(LocalTime arrivalTime) { 
        this.arrivalTime = arrivalTime; 
    }
    
    public int getTotalCapacity() { 
        return totalCapacity; 
    }
    
    public void setTotalCapacity(int totalCapacity) { 
        if (totalCapacity > 0) {
            this.totalCapacity = totalCapacity;
        }
    }
    
    public int getSeatsRemaining() { 
        return seatsRemaining; 
    }
    
    public void setSeatsRemaining(int seatsRemaining) { 
        if (seatsRemaining >= 0 && seatsRemaining <= totalCapacity) {
            this.seatsRemaining = seatsRemaining;
        }
    }
    
    public String getTrainType() { 
        return trainType; 
    }
    
    public void setTrainType(String trainType) { 
        this.trainType = trainType; 
    }
    
    public boolean isOperational() { 
        return isOperational; 
    }
    
    public void setOperational(boolean operational) { 
        isOperational = operational; 
    }
    
    @Override
    public String toString() {
        return String.format("Train{id=%d, name='%s', route='%s→%s', departure=%s, arrival=%s, available=%d/%d}", 
                trainId, trainName, originStation, destinationStation, 
                departureTime, arrivalTime, seatsRemaining, totalCapacity);
    }
}