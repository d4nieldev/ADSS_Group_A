package BussinessLayer.TransPortLayer;

public class Truck {
    private String plateNumber;
    private String model;
    private int weightNeto;
    private int weightMax;
    private boolean isAvailable;

    public Truck(String plateNumber, String model, int weightNeto, int weightMax) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.weightNeto = weightNeto;
        this.weightMax = weightMax;
        this.isAvailable = true;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getModel() {
        return model;
    }

    public int getWeightNeto() {
        return weightNeto;
    }

    public int getWeightMax() {
        return weightMax;
    }


    public boolean isAvailable() {
        return isAvailable;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setWeightNeto(int weightNeto) {
        this.weightNeto = weightNeto;
    }

    public void setWeightMax(int weightMax) {
        this.weightMax = weightMax;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * return How much weight can be loaded on truck
     *
     * @return
     */
    public int calculatePayload() {
        return weightMax - weightNeto;
    }

    /**
     * return true if weight of products match to weight that can be loaded on truck
     *
     * @return
     */
    public boolean canCarryWeight(int weight) {
        return weight <= calculatePayload();
    }

    public String toString() {
        return "Truck: plateNumber=" + plateNumber + ", model=" + model + ", weightNeto=" + weightNeto + ", weightMax=" + weightMax;
    }

}

