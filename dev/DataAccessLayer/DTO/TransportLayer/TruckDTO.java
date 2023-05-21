package DataAccessLayer.DTO.TransportLayer;

import java.util.List;

public class TruckDTO {
    private String plateNumber;
    private String model;
    private int weightNeto;
    private int weightMax;
    private boolean isAvailable;

    public TruckDTO(String plateNumber, String model, int weightNeto, int weightMax, boolean isAvailable) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.weightNeto = weightNeto;
        this.weightMax = weightMax;
        this.isAvailable = isAvailable;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getWeightNeto() {
        return weightNeto;
    }

    public void setWeightNeto(int weightNeto) {
        this.weightNeto = weightNeto;
    }

    public int getWeightMax() {
        return weightMax;
    }

    public void setWeightMax(int weightMax) {
        this.weightMax = weightMax;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public String fieldsToString() {
        return String.format("(\"%s\",\"%s\",\"%d\",\"%d\",\"%b\")",
        this.plateNumber, this.model, this.weightNeto, this.weightMax, this.isAvailable);
    }
}
