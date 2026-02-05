/**
 * Train Class - Represents a train with route and booking information
 * Author: Aravind M S
 * Created for: OIBSIP Java Development Internship
 */

public class Train {
    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private double fare;
    private int totalSeats;
    private int availableSeats;
    private boolean isActive;
    
    public Train(String trainNumber, String trainName, String source, String destination,
                 String departureTime, String arrivalTime, double fare, int totalSeats) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fare = fare;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.isActive = true;
    }
    
    // Getters
    public String getTrainNumber() {
        return trainNumber;
    }
    
    public String getTrainName() {
        return trainName;
    }
    
    public String getSource() {
        return source;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public String getDepartureTime() {
        return departureTime;
    }
    
    public String getArrivalTime() {
        return arrivalTime;
    }
    
    public double getFare() {
        return fare;
    }
    
    public int getTotalSeats() {
        return totalSeats;
    }
    
    public int getAvailableSeats() {
        return availableSeats;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    // Setters
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
    
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public void setFare(double fare) {
        this.fare = fare;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    // Booking methods
    public boolean bookSeats(int seatsToBook) {
        if (seatsToBook <= 0 || seatsToBook > availableSeats) {
            return false;
        }
        
        availableSeats -= seatsToBook;
        return true;
    }
    
    public boolean releaseSeats(int seatsToRelease) {
        if (seatsToRelease <= 0 || (availableSeats + seatsToRelease) > totalSeats) {
            return false;
        }
        
        availableSeats += seatsToRelease;
        return true;
    }
    
    public boolean hasAvailableSeats(int requiredSeats) {
        return availableSeats >= requiredSeats;
    }
    
    public int getBookedSeats() {
        return totalSeats - availableSeats;
    }
    
    public double getOccupancyRate() {
        return ((double) getBookedSeats() / totalSeats) * 100;
    }
    
    // Utility methods
    public String getRoute() {
        return source + " → " + destination;
    }
    
    public String getSchedule() {
        return "Dep: " + departureTime + " | Arr: " + arrivalTime;
    }
    
    public boolean isFullyBooked() {
        return availableSeats == 0;
    }
    
    public boolean matchesRoute(String fromStation, String toStation) {
        return source.toLowerCase().contains(fromStation.toLowerCase()) &&
               destination.toLowerCase().contains(toStation.toLowerCase());
    }
    
    @Override
    public String toString() {
        return String.format("Train{number='%s', name='%s', route='%s', fare=%.2f, seats=%d/%d}", 
                trainNumber, trainName, getRoute(), fare, getBookedSeats(), totalSeats);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Train train = (Train) obj;
        return trainNumber.equals(train.trainNumber);
    }
    
    @Override
    public int hashCode() {
        return trainNumber.hashCode();
    }
}