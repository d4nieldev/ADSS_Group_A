package BussinessLayer;

import java.util.*;

public class TransportFacade {

    private Map<Integer, Transport> transportMap;
    int id=0;

    public TransportFacade() {
        transportMap = new HashMap<>();
    }


    //this function create transport. we need in service check the driver
    public void createTransport(int id, Date date, String leavingTime, String truckNumber, String driverName, int driverId, String source)
    {
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

    public void addTransport(Driver driver, Truck truck, List<Delivery> matchedDeliveries)
    {
        Date d= new Date();
        Transport transport = new Transport(id,d,"0000",truck.getPlateNumber(), driver.getName(), driver.getId(), "source");

        addTransport(id,transport);
        id++;
        transport.setDestinationList(this.makeDestinationMap(matchedDeliveries));

    }

    private List<Destination> makeDestinationMap(List<Delivery> matchedDeliveries)
    {
        return  new ArrayList<Destination>();

    }
}
