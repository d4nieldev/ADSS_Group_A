package DataAccessLayer.DTO.TransportLayer;


import BussinessLayer.TransPortLayer.Delivery;
import BussinessLayer.TransPortLayer.Destination;

import java.util.List;

public class TransportDTO {
    private int id;
    private String date;
    private String leavingTime;
    private String truckNumber;
    private String driverName;
    private int driverId;
    private String source;
    private int truckWeightNeto;
    private int truckWeightMax;
    private String loadedItems;
    private int currentWeight;

    public TransportDTO(int id, String date, String leavingTime, String truckNumber, String driverName, int driverId,
                        String source,   int truckWeightNeto,
                        int truckWeightMax, String loadedItems, int currentWeight) {
        this.id = id;
        this.date = date;
        this.leavingTime = leavingTime;
        this.truckNumber = truckNumber;
        this.driverName = driverName;
        this.driverId = driverId;
        this.source = source;
        this.truckWeightNeto = truckWeightNeto;
        this.truckWeightMax = truckWeightMax;
        this.loadedItems = loadedItems;
        this.currentWeight = currentWeight;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
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


    public int getTruckWeightNeto() {
        return truckWeightNeto;
    }

    public int getTruckWeightMax() {
        return truckWeightMax;
    }

    public String getLoadedItems() {
        return loadedItems;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public String fieldsToString() {
        return String.format("(\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%d\",\"%s\",\"%d\",\"%d\",\"%s\",\"%d\")",
                this.id, this.date, this.leavingTime, this.truckNumber, this.driverName, this.driverId, this.source,
                  this.truckWeightNeto, this.truckWeightMax, this.loadedItems,
                this.currentWeight);
    }
}
