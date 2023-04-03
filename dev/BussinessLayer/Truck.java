package BussinessLayer;

public class Truck {
    private String plateNumber;
    private String model;
    private int weightNeto;
    private int weightMax;

    public Truck(String plateNumber, String model, int weightNeto, int weightMax) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.weightNeto = weightNeto;
        this.weightMax = weightMax;
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

    public int calculatePayload() {
        return weightMax - weightNeto;
    }

    public boolean canCarryWeight(int weight) {
        return weight <= calculatePayload();
    }

    public String toString() {
        return "Truck: plateNumber=" + plateNumber + ", model=" + model + ", weightNeto=" + weightNeto + ", weightMax=" + weightMax;
    }


    public boolean isAvailable() {
        return true;
    }
}

