package BussinessLayer.TransPortLayer;

import DataAccessLayer.DAO.TransportLayer.TruckDAO;
import DataAccessLayer.DTO.TransportLayer.TruckDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TruckFacade {
    private Map<String, Truck> trucks;
    private TruckDAO truckDAO;


    public TruckFacade() {
        trucks = new HashMap<>();
        truckDAO = new TruckDAO();
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
        truckDAO.insert(truck.toDTO());
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
        //todo
        List<Truck> trucksNew = getAllTrucks();
        for (Truck truck : trucksNew) {
            trucks.put(truck.getPlateNumber(), truck);
        }


        List<Truck> availableTrucks = new ArrayList<>();
        for (Truck truck : trucks.values()) {
            if (!truck.isAvailable()) {
                availableTrucks.add(truck);
            }
        }
        return availableTrucks;
    }


    public List<Truck>  getAllTrucks() {
        List<TruckDTO> trucksDTO = truckDAO.getAll();
        List<Truck> trucks = new LinkedList<>();
        for (TruckDTO truckDTO : trucksDTO) {
            trucks.add(new Truck(truckDTO));
        }
        return trucks;
    }
    




    public void setTruckAvailability(String truckNumber,boolean isAvailable) {
        if(hasTruck(truckNumber))
            getTruck(truckNumber).setAvailable(isAvailable);
    }

}
