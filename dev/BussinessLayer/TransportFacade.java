package BussinessLayer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TransportFacade {

    private Map<Integer, Transport> transportMap;

    public TransportFacade() {
        transportMap = new HashMap<>();
    }


    //this function create transport. we need in service check the driver
    public void createTransport(int id, Date date, String leavingTime, String truckNumber, String driverName, int driverId, String source){
        Transport shipment = new Transport(id,date, leavingTime, truckNumber, driverName, driverId, source);
        addTransport(id , shipment);
    }

    public void addTransport(int id, Transport transport) {
        transportMap.put(id, transport);
    }

    public Transport getTransport(String name) {
        return transportMap.get(name);
    }

    public void removeTransport(String name) {
        transportMap.remove(name);
    }

    public Map<Integer, Transport> getAllTransport() {
        return transportMap;
    }
}
