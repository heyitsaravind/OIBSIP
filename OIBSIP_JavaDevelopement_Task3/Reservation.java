public class Reservation {
    private String pnr;
    private String userId;
    private String passengerName;
    private String age;
    private String gender;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String dateOfJourney;
    private String fromStation;
    private String toStation;
    
    public Reservation(String pnr, String userId, String passengerName, String age, 
                      String gender, String trainNumber, String trainName, String classType,
                      String dateOfJourney, String fromStation, String toStation) {
        this.pnr = pnr;
        this.userId = userId;
        this.passengerName = passengerName;
        this.age = age;
        this.gender = gender;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.fromStation = fromStation;
        this.toStation = toStation;
    }
    
    public String getPnr() {
        return pnr;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public String getAge() {
        return age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public String getTrainNumber() {
        return trainNumber;
    }
    
    public String getTrainName() {
        return trainName;
    }
    
    public String getClassType() {
        return classType;
    }
    
    public String getDateOfJourney() {
        return dateOfJourney;
    }
    
    public String getFromStation() {
        return fromStation;
    }
    
    public String getToStation() {
        return toStation;
    }
}
