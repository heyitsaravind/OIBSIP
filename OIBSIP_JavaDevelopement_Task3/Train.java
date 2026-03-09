public class Train {
    private String trainNumber;
    private String trainName;
    private String fromStation;
    private String toStation;
    
    public Train(String trainNumber, String trainName, String fromStation, String toStation) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.fromStation = fromStation;
        this.toStation = toStation;
    }
    
    public String getTrainNumber() {
        return trainNumber;
    }
    
    public String getTrainName() {
        return trainName;
    }
    
    public String getFromStation() {
        return fromStation;
    }
    
    public String getToStation() {
        return toStation;
    }
}
