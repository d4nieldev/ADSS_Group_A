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

        letTheUserMatch(transportServices.createDeliveries(sources,dests),truckService.truckFacade, ds.driverFacade);
        transportServices.transportFacade.runTheTransports();



    }

    /*
    we need to reduce the list of parameter in this function, try to call this controller directly in function
    */
    public static void letTheUserMatch(List<Delivery> deliveries, TruckFacade truckFacade, DriverFacade driverFacade )
    {

        List<Driver> availableDrivers = driverFacade.getAvailableDrivers();
        List<Truck> availableTrucks = truckFacade.getAvailableTrucks();
        List<Delivery> availableDeliveries = new ArrayList<>(deliveries);

        int driverId = 0;
        int truckId = 0;
        int deliveryId = 0;

        while (!availableDeliveries.isEmpty() && !availableDrivers.isEmpty() && !availableTrucks.isEmpty()) {
            System.out.println("\nAvailable drivers:");
            printDrivers(availableDrivers);
            System.out.println("\nAvailable trucks:");
            printTrucks(availableTrucks);
            System.out.println("\nAvailable deliveries:");
            printDeliveries(availableDeliveries);

            Scanner scanner = new Scanner(System.in);

            // Match driver and truck
            boolean matchFound = false;
            Driver driver = null;
            Truck truck = null;
            while (!matchFound) {
                System.out.print("\nEnter the ID of the driver to match: ");
                driverId = scanner.nextInt();
                driver = availableDrivers.get(driverId);

                System.out.print("\nEnter the ID of the truck to match: ");
                truckId = scanner.nextInt();
                truck = availableTrucks.get(truckId);

                if (driver.hasLicenseFor(truck.getModel())) {
                    matchFound = true;
                } else {
                    System.out.println("The driver's license does not match the truck's model. Please try again.");
                }
            }

            // Match deliveries to the driver
            System.out.print("\nEnter the number of deliveries to match for this driver: ");
            int numDeliveries = scanner.nextInt();

            List<Delivery> matchedDeliveries = new ArrayList<>();
            for (int i = 1; i <= numDeliveries; i++) {
                printDeliveries(availableDeliveries);
                System.out.print("\nEnter the ID of the delivery to match: ");
                deliveryId = scanner.nextInt();

                Delivery delivery = availableDeliveries.get(deliveryId);
                matchedDeliveries.add(delivery);
                availableDeliveries.remove(delivery);
                delivery.setStatus(Status.INVITED);
                delivery.setDriver(driver);
                delivery.setTruck(truck);
            }

            // Update available drivers and trucks
            availableDrivers.remove(driver);
            availableTrucks.remove(truck);

            // Print matched driver, truck, and deliveries
            System.out.println("\nMatched driver: " + driver.getName() + " (" + driver.getLicense() + ")");
            System.out.println("Matched truck: " + truck.getPlateNumber() + " (" + truck.getModel() + ")");
            System.out.println("Matched deliveries:");
            printDeliveries(matchedDeliveries);
            Date d = new Date();
            List<Destination> destinationList = transportServices.transportFacade.letTheUserChooseTheOrder(matchedDeliveries);

            transportServices.transportFacade.createTransport("11/1/22","0000",truck.getPlateNumber(),driver.getName(),driver.getId(),"source",
                    destinationList,matchedDeliveries,truck.getWeightNeto(),truck.getWeightMax());
        }

        if (availableTrucks.isEmpty()) {
            System.out.println("\nNo more available trucks.");
        }

        if (availableDrivers.isEmpty()) {
            System.out.println("\nNo more available drivers.");
        }

        if (availableDeliveries.isEmpty()) {
            System.out.println("\nNo more available deliveries.");
        }

    }


    public static void printDrivers(List<Driver> drivers) {
        for (int i = 0; i < drivers.size(); i++) {
            Driver driver = drivers.get(i);
            System.out.println(i + ": " + driver.getName() + " (" + driver.getLicense() + ")");
        }
    }

    public static void printTrucks(List<Truck> trucks) {
        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);
            System.out.println(i + ": " + truck.getPlateNumber() + " (" + truck.getModel() + ")");
        }
    }


    public static void printDeliveries(List<Delivery> deliveries) {
        for (int i = 0; i < deliveries.size(); i++) {
            Delivery delivery = deliveries.get(i);
            System.out.println(i + ": " + delivery.getSource().getAddress() + " (" + delivery.getSource().getLocation() + ") -> "
                    + delivery.getDest().getAddress() + " (" + delivery.getDest().getLocation() + ")");
        }
        System.out.println();
    }

    public static void printDestinations(List<Destination> destList) {
        for (Destination d : destList) {
            System.out.println("Address: " + d.getAddress());
            System.out.println("Phone number: " + d.getPhoneNumber());
            System.out.println("Contact name: " + d.getContactName());
            System.out.println("Location: " + d.getLocation());
            System.out.println();
        }
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
        System.out.println(truckService.addTruck("aaaa","a",200,1000));
        System.out.println(truckService.addTruck("bbbb","b",200,1000));
        System.out.println(truckService.addTruck("cccc","c",200,1000));
        System.out.println(truckService.addTruck("dddd","d",200,1000));
        System.out.println(truckService.addTruck("eeee","e",200,1000));

    }

    private static void makeSomeDrivers()
    {

        System.out.println(ds.addDriver(1,"rotem","a"));
        System.out.println(ds.addDriver(2,"kfir","b"));
        System.out.println(ds.addDriver(3,"adi","c"));
        System.out.println(ds.addDriver(4,"messi","d"));
        System.out.println(ds.addDriver(5,"ronaldo","e"));

    }
    public static void printDeliveryDetails(List<Delivery> deliveries) {
        for (Delivery delivery : deliveries) {
            System.out.println("Delivery #" + delivery.getId());
            System.out.println("Driver: " + delivery.getDriver().getName());
            System.out.println("Truck: " + delivery.getTruck().getPlateNumber() + " (" + delivery.getTruck().getModel() + ")");
            System.out.println("Status: " + delivery.getStatus());
            System.out.println("Source: " + delivery.getSource().getAddress() + " (" + delivery.getSource().getLocation() + ")");
            System.out.println("Destination: " + delivery.getDest().getAddress() + " (" + delivery.getDest().getLocation() + ")");
            System.out.println("Items:");
            for (String item : delivery.getItems()) {
                System.out.println("- " + item );
            }
            System.out.println();
        }
    }
}