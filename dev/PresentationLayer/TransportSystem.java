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
        System.out.println("wellcome");
        makeSomeDrivers();
        makeSomeTrucks();
        List<Destination> dests = makeSomeDestinations();
        List<Destination> sources = makeSomeSources();
        List<Delivery> deliveries = transportServices.createDeliveries(sources,dests);



        transportServices.letTheUserMatch(deliveries);
        transportServices.runTheTransports();


    }

    /**
     * Makes default list of destination or scan this from user
     *
     * @return
     */
    private static List<Destination> makeSomeDestinations() {
        List<Destination> dests = new ArrayList<Destination>();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to enter the destinations yourself? (Y/N)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            while (true) {
                System.out.println("Enter destination name (or 'done' to stop):");
                String name = scanner.nextLine();
                if (name.equalsIgnoreCase("done")) {
                    break;
                }

                System.out.println("Enter destination phone number:");
                String phoneNumber = scanner.nextLine();

                System.out.println("Enter contact name:");
                String contactName = scanner.nextLine();

                System.out.println("Enter destination location (NORTH/SOUTH/CENTER):");
                String locationStr = scanner.nextLine();
                Location location = Location.valueOf(locationStr.toUpperCase());

                dests.add(new Destination(name, phoneNumber, contactName, location, DestinationType.DESTINATION));
            }
        } else {
            dests.add(transportServices.addDestination("tel aviv", "555-1234", "John Smith", Location.NORTH, DestinationType.DESTINATION));
            dests.add(transportServices.addDestination("raanana", "555-5678", "Jane Doe", Location.SOUTH, DestinationType.DESTINATION));
            dests.add(new Destination("ashkelon", "555-9012", "Bob Johnson", Location.CENTER, DestinationType.DESTINATION));
        }

        return dests;
    }


    /**
     * Makes default list of sources or scan this from user
     *
     * @return
     */
    private static List<Destination> makeSomeSources() {
        List<Destination> sources = new ArrayList<Destination>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to enter the sources manually? (Y/N)");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("Y")) {
            // Ask the user to enter the sources
            System.out.println("Enter the number of sources:");
            int numSources = scanner.nextInt();

            for (int i = 0; i < numSources; i++) {
                System.out.println("Enter source name:");
                String name = scanner.next();
                System.out.println("Enter source phone number:");
                String phone = scanner.next();
                System.out.println("Enter source contact name:");
                String contact = scanner.next();
                System.out.println("Enter source location (NORTH, SOUTH, CENTER):");
                Location location = Location.valueOf(scanner.next().toUpperCase());

                sources.add(transportServices.addDestination(name, phone, contact, location, DestinationType.SOURCE));
            }
        } else {
            // Use default sources
            sources.add(transportServices.addDestination("cola", "555-1234", "John Smith", Location.NORTH, DestinationType.SOURCE));
            sources.add(transportServices.addDestination("osem", "555-5678", "Jane Doe", Location.SOUTH, DestinationType.SOURCE));
            sources.add(transportServices.addDestination("tnuva", "555-9012", "Bob Johnson", Location.CENTER, DestinationType.SOURCE));
        }

        return sources;
    }


    /**
     * Makes default trucks in facade or scan this from user
     *
     * @return
     */
    private static void makeSomeTrucks() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to create your own trucks? (Y/N)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            while (true) {
                System.out.println("Enter the plate number of the truck:");
                String make = scanner.nextLine();

                System.out.println("Enter the model of the truck:");
                String model = scanner.nextLine();

                System.out.println("Enter the weight neto of the truck:");
                int weightCapacity = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Enter the maximum weight of the truck:");
                int maxSpeed = scanner.nextInt();
                scanner.nextLine();

                System.out.println(truckService.addTruck(make, model, weightCapacity, maxSpeed));

                System.out.println("Do you want to add another truck? (Y/N)");
                choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("N")) {
                    break;
                }
            }
        } else {
            System.out.println(truckService.addTruck("aaaa", "a", 200, 250));
            System.out.println(truckService.addTruck("bbbb", "b", 200, 1000));
            System.out.println(truckService.addTruck("cccc", "c", 200, 1000));
            System.out.println(truckService.addTruck("dddd", "d", 200, 1000));
            System.out.println(truckService.addTruck("eeee", "a", 200, 1000));
        }
    }


    /**
     * Makes default drivers in facade or scan this from user
     *
     * @return
     */
    private static void makeSomeDrivers() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to create the drivers yourself? (y/n)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("y")) {
            // Let the user create the drivers
            while (true) {
                System.out.println("Enter driver ID (integer):");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                System.out.println("Enter driver name:");
                String name = scanner.nextLine();

                System.out.println("Enter driver license number:");
                String licenseNumber = scanner.nextLine();

                System.out.println(ds.addDriver(id, name, licenseNumber));

                System.out.println("Do you want to add another driver? (y/n)");
                choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("n")) {
                    break;
                }
            }
        } else {
            // Use the default drivers
            System.out.println(ds.addDriver(1,"rotem","a"));
            System.out.println(ds.addDriver(2,"kfir","b"));
            System.out.println(ds.addDriver(3,"adi","c"));
            System.out.println(ds.addDriver(4,"messi","d"));
            System.out.println(ds.addDriver(5,"ronaldo","e"));
        }
    }

}