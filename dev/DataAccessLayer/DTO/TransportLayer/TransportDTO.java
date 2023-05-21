package DataAccessLayer.DTO.TransportLayer;


import BussinessLayer.TransPortLayer.Delivery;
import BussinessLayer.TransPortLayer.Destination;

import java.time.LocalDate;
import java.util.List;

public class TransportDTO {
    private int id;
    private LocalDate date;
    private String leavingTime;
    private String truckNumber;
    private String driverName;
    private int driverId;
    private String source;
    private List<Destination> destinationList;
    private List<Delivery> deliveryList;
    private int truckWeightNeto;
    private int truckWeightMax;
    private List<String> loadedItems;
    private int currentWeight;

    public TransportDTO(int id, LocalDate date, String leavingTime, String truckNumber, String driverName, int driverId,
                        String source, List<Destination> destinationList, List<Delivery> deliveryList, int truckWeightNeto,
                        int truckWeightMax, List<String> loadedItems, int currentWeight) {
        this.id = id;
        this.date = date;
        this.leavingTime = leavingTime;
        this.truckNumber = truckNumber;
        this.driverName = driverName;
        this.driverId = driverId;
        this.source = source;
        this.destinationList = destinationList;
        this.deliveryList = deliveryList;
        this.truckWeightNeto = truckWeightNeto;
        this.truckWeightMax = truckWeightMax;
        this.loadedItems = loadedItems;
        this.currentWeight = currentWeight;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLeavingTime() {
        return leavingTime;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getSource() {
        return source;
    }

    public List<Destination> getDestinationList() {
        return destinationList;
    }

    public List<Delivery> getDeliveryList() {
        return deliveryList;
    }

    public int getTruckWeightNeto() {
        return truckWeightNeto;
    }

    public int getTruckWeightMax() {
        return truckWeightMax;
    }

    public List<String> getLoadedItems() {
        return loadedItems;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public String fieldsToString() {
        return String.format("(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")",
                this.id, this.date, this.leavingTime, this.truckNumber, this.driverName, this.driverId, this.source,
                this.destinationList, this.deliveryList, this.truckWeightNeto, this.truckWeightMax, this.loadedItems,
                this.currentWeight);
    }
}
