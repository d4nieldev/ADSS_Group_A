package BussinessLayer;

import java.util.*;

public class TransportFacade {

    private Map<Integer, Transport> transportMap;
    int id=0;
    private static TransportFacade instance = null;

    private TransportFacade() {
        transportMap = new HashMap<Integer, Transport>();
    }

    public static TransportFacade getInstance()
    {
        if(instance==null)
            instance = new TransportFacade();
        return instance;
    }



    //this function create transport
    public void createTransport(String date, String leavingTime, String truckNumber, String driverName, int driverId, String source,
                                List<Destination> destinationList,List<Delivery> deliveryList,int truckWeightNeto,int truckWeightMax)
    {
        Transport shipment = new Transport(id, date, leavingTime, truckNumber, driverName, driverId,
                destinationList.get(0).getAddress(), destinationList,deliveryList,truckWeightNeto,truckWeightMax);
        System.out.println("The id of transport is:"+ id);
        addTransport(id , shipment);
        id++;
    }

    public void addTransport(int id, Transport transport) {
        transportMap.put(id, transport);
    }

    public Transport getTransport(Integer ide) {
        return transportMap.get(id);
    }

    public void removeTransport(Integer id) {
        transportMap.remove(id);
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
