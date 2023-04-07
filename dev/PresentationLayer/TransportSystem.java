package PresentationLayer;

import BussinessLayer.*;
import ServiceLayer.DriverService;
import ServiceLayer.TransportService;
import ServiceLayer.TruckService;

import java.util.*;
import java.util.stream.Collectors;

public class TransportSystem

{

    private  static TransportService transportServices = new TransportService();
    private  static DriverService ds = new DriverService();
    private static TruckService truckService = new TruckService();

    public static void main(String[] args)
    {
        makeSomeDrivers();
        makeSomeTrucks();
        List<Destination> dests = makeSomeDestinations();
        List<Destination> sources = makeSomeSources();
        List<Delivery> deliveries = transportServices.createDeliveries(sources,dests);


        transportServices.letTheUserMatch(deliveries);
        transportServices.runTheTransports();


    }

    private static List<Destination> makeSomeDestinations()
    {
        List<Destination> dests = new ArrayList<Destination>();

        dests.add(transportServices.addDestination("tel aviv", "555-1234", "John Smith", Location.NORTH,DestinationType.DESTINATION));
        dests.add(transportServices.addDestination("raanana", "555-5678", "Jane Doe", Location.SOUTH,DestinationType.DESTINATION));
        //dests.add(new Destination("ashkelon", "555-9012", "Bob Johnson", Location.CENTER,DestinationType.DESTINATION));

        return dests;
    }
    private static List<Destination> makeSomeSources()
    {
        List<Destination> sources = new ArrayList<Destination>();

        sources.add(transportServices.addDestination("cola", "555-1234", "John Smith", Location.NORTH,DestinationType.SOURCE));
        sources.add(transportServices.addDestination("osem", "555-5678", "Jane Doe", Location.SOUTH,DestinationType.SOURCE));
        sources.add(transportServices.addDestination("tnuva", "555-9012", "Bob Johnson", Location.CENTER,DestinationType.SOURCE));

        return sources;
    }
    private static void makeSomeTrucks()
    {
        System.out.println(truckService.addTruck("aaaa","a",200,250));
        System.out.println(truckService.addTruck("bbbb","b",200,1000));
        System.out.println(truckService.addTruck("cccc","c",200,1000));
        System.out.println(truckService.addTruck("dddd","d",200,1000));
        System.out.println(truckService.addTruck("eeee","a",200,1000));

    }
    private static void makeSomeDrivers()
    {
        System.out.println(ds.addDriver(1,"rotem","a"));
        System.out.println(ds.addDriver(2,"kfir","b"));
        System.out.println(ds.addDriver(3,"adi","c"));
        System.out.println(ds.addDriver(4,"messi","d"));
        System.out.println(ds.addDriver(5,"ronaldo","e"));
    }
}