package BussinessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruckFacade {
    private Map<String, Truck> trucks;

    public TruckFacade() {
        trucks = new HashMap<>();
    }

    /**
     * singleton (design)
     */
    private static TruckFacade instance = null;

    public static TruckFacade getInstance()
    {
        if(instance==null)
            instance = new TruckFacade();
        return instance;
    }

    public void newTrack(String plateNumber, String model, int weightNeto, int weightMax){
        Truck truck = new Truck(plateNumber, model, weightNeto, weightMax);
        addTruck(truck);
        
    }

    /**
     * add truck to facade
     */
    public void addTruck(Truck truck) {
        trucks.put(truck.getPlateNumber(), truck);
    }

    /**
     * remove truck from facade
     */
    public void removeTruck(String plateNumber) {
        trucks.remove(plateNumber);
    }

    // if we will use this function, we must check first has truck(string plateNum)
    public Truck getTruck(String plateNumber) {
        return trucks.get(plateNumber);
    }

    /**
     * return true if truck exist in facade
     *
     * @return
     */
    public boolean hasTruck(String plateNumber) {
        return trucks.containsKey(plateNumber);
    }


    /**
     * return list of truck that available
     *
     * @return
     */
    public List<Truck> getAvailableTrucks()
    {
        List<Truck> availableTrucks = new ArrayList<>();
        for (Truck truck : trucks.values()) {
            if (truck.isAvailable()) {
                availableTrucks.add(truck);
            }
        }
        return availableTrucks;
    }

    public void setTruckAvailability(String truckNumber,boolean isAvailable) {
        if(hasTruck(truckNumber))
            getTruck(truckNumber).setAvailable(isAvailable);
    }

}
