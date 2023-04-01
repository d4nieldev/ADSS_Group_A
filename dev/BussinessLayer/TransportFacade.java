package BussinessLayer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TransportFacade {
    public static int id = 0;
    private Map<String, Transport> transportMap;

    public TransportFacade() {
        transportMap = new HashMap<>();
    }


    //this function match a driver in order to create transport
    /*public Transport createTransport(Date date, String leavingTime, String source){
        if( driver.lincense = 'c')
            driverId = driver.id;
            nameDriver = driver.name;
        String name = "a";
        Transport shipment = new Transport(id,date, leavingTime, truckNumber, driverName, driverId, source);
        addTransport(name , shipment);
    }*/

    public void addTransport(String name, Transport transport) {
        transportMap.put(name, transport);
    }

    public Transport getTransport(String name) {
        return transportMap.get(name);
    }

    public void removeTransport(String name) {
        transportMap.remove(name);
    }

    public Map<String, Transport> getAllTransport() {
        return transportMap;
    }
}
