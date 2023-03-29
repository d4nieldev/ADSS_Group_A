package BussinessLayer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Transport {
    private int id;
    private Date date;
    private String leavingTime;
    private String truckNumber;
    private String driverName;
    private int driverId;
    private String source;
    private Map<String, Destination> destinationList;

    public Transport(int id, Date date, String leavingTime, String truckNumber, String driverName, int driverId, String source) {
        this.id = id;
        this.date = date;
        this.leavingTime = leavingTime;
        this.truckNumber = truckNumber;
        this.driverName = driverName;
        this.driverId = driverId;
        this.source = source;
        this.destinationList = new HashMap<>();
    }

    public void addDestination(String destinationName, Destination destination) {
        destinationList.put(destinationName, destination);
    }

    public void removeDestination(String destinationName) {
        destinationList.remove(destinationName);
    }

    public Destination getDestination(String destinationName) {
        return destinationList.get(destinationName);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(String leavingTime) {
        this.leavingTime = leavingTime;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, Destination> getDestinationList() {
        return destinationList;
    }

    public void setDestinationList(Map<String, Destination> destinationList) {
        this.destinationList = destinationList;
    }
}
