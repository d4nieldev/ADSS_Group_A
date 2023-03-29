package BussinessLayer;

import java.util.HashMap;
import java.util.Map;

public class TruckFacade {
    private Map<String, Truck> trucks;

    public TruckFacade() {
        trucks = new HashMap<>();
    }

    public void addTruck(Truck truck) {
        trucks.put(truck.getPlateNumber(), truck);
    }

    public void removeTruck(String plateNumber) {
        trucks.remove(plateNumber);
    }

    public Truck getTruck(String plateNumber) {
        return trucks.get(plateNumber);
    }

    public boolean hasTruck(String plateNumber) {
        return trucks.containsKey(plateNumber);
    }

    public int countTrucks() {
        return trucks.size();
    }

    public int countTrucksByModel(String model) {
        int count = 0;
        for (Truck truck : trucks.values()) {
            if (truck.getModel().equals(model)) {
                count++;
            }
        }
        return count;
    }

    public int calculateTotalPayload() {
        int totalPayload = 0;
        for (Truck truck : trucks.values()) {
            totalPayload += truck.calculatePayload();
        }
        return totalPayload;
    }
}
