package serviceLayer;

import BussinessLayer.TransportFacade;
import BussinessLayer.TruckFacade;

public class TruckService {

    TruckFacade truckFacade = TruckFacade.getInstance();

    public String addTruck(String plateNumber, String model, int weightNeto, int weightMax){
        truckFacade.newTrack(plateNumber, model, weightNeto, weightMax);
        return "New track add to facade";
    }

    public String removeTruck(String plateNumber){
        truckFacade.removeTruck(plateNumber);
        return "The truck" + plateNumber + "remove from Facade";
    }
}
