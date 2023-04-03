package BussinessLayer;

import java.util.*;

public class TransportFacade {

    private Map<Integer, Transport> transportMap;
    int id=0;
    private static TransportFacade instance=null;

    private TransportFacade() {
        transportMap = new HashMap<Integer, Transport>();
    }

    public static TransportFacade getInstance()
    {
        if(instance==null)
            instance = new TransportFacade();
        return instance;
    }



    //this function create transport. we need in service check the driver
    public void createTransport(Date date, String leavingTime, String truckNumber, String driverName, int driverId, String source,
                                List<Destination> destinationList,List<Delivery> deliveryList,int truckWeightNeto,int truckWeightMax)
    {
        Transport shipment = new Transport(id,date, leavingTime, truckNumber, driverName, driverId,
                destinationList.get(0).getAddress(), destinationList,deliveryList,truckWeightNeto,truckWeightMax);
        addTransport(id , shipment);
        id++;
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


    public void runTheTransports()
    {
        for (Transport transport : transportMap.values()) {
            transport.run();
            transport.printTransportDetails();
        }
    }
}
