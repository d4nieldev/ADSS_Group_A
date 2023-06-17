package PresentationLayer.CLI;

import BussinessLayer.TransPortLayer.Delivery;
import BussinessLayer.TransPortLayer.Destination;
import BussinessLayer.TransPortLayer.DestinationType;
import BussinessLayer.TransPortLayer.Location;
import ServiceLayer.EmployeesLayer.ServiceFactory;
import ServiceLayer.TransportLayer.TransportService;
import ServiceLayer.TransportLayer.TruckService;

import java.util.*;

public class TransportSystem

{

    private static TransportService transportService;
    private static TruckService truckService;

    public TransportSystem(ServiceFactory serviceFactory) {
        transportService = serviceFactory.getTransportService();
        truckService = serviceFactory.getTruckService();
    }

    public void run(int loginId) {
        System.out.println("Welcome to the Transport System!");
        List<Destination> dests=null ;
        List<Destination> sources= null ;
        List<Delivery> deliveries =null;
    
        Scanner sc = new Scanner(System.in);
    
        boolean continueChoosing = true;
        while (continueChoosing) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Make Trucks");
            System.out.println("2. Make Sources and dests");
            System.out.println("3. Make Deliveries");
            System.out.println("4. Create Transport");
            System.out.println("5. Run Transports");
            System.out.println("6. Change Trucks");
            System.out.println("7. Change Transports");
            System.out.println("8. Exit");
    
            int choice = sc.nextInt();
            sc.nextLine(); // consume the newline character
    
            switch (choice) {
                case 1:
                    makeSomeTrucks();
                    break;
                case 2:
                dests = makeSomeDestinations();
                sources = makeSomeSources();
                
                    break;
                case 3:
                    deliveries = transportService.createDeliveries(sources, dests);
                    break;
                case 4:
                    transportService.createTransports(loginId, deliveries);
                    break;
                case 5:
                    transportService.runTheTransports();
                    break;
                case 6:
                    changeTruckService();
                    break;
                case 7:
                    changeTransportService();
                    break;
                case 8:
                    continueChoosing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    
        System.out.println("Thank you for using the Transport System!");
    }
    

    private static void changeTruckService() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What action would you like to perform?");
        System.out.println("1. Add a new truck");
        System.out.println("2. Remove a truck");
        System.out.println("3. Update a truck's details");
        System.out.println("4. Check if a truck is available");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Enter the truck's plate number:");
                String plateNumber = scanner.next();
                System.out.println("Enter the truck's model:");
                String model = scanner.next();
                System.out.println("Enter the truck's net weight:");
                int weightNeto = scanner.nextInt();
                System.out.println("Enter the truck's maximum weight:");
                int weightMax = scanner.nextInt();
                String message = truckService.addTruck(plateNumber, model, weightNeto, weightMax);
                System.out.println(message);
                break;
            case 2:
                System.out.println("Enter the plate number of the truck you want to remove:");
                plateNumber = scanner.next();
                message = truckService.removeTruck(plateNumber);
                System.out.println(message);
                break;
            case 3:
                System.out.println("Enter the plate number of the truck you want to update:");
                plateNumber = scanner.next();
                System.out.println("What would you like to update?");
                System.out.println("1. Plate number");
                System.out.println("2. Model");
                System.out.println("3. Net weight");
                System.out.println("4. Maximum weight");
                System.out.println("5. Availability");
                int updateChoice = scanner.nextInt();
                switch (updateChoice) {
                    case 1:
                        System.out.println("Enter the new plate number:");
                        String newPlateNumber = scanner.next();
                        message = truckService.setPlateNumber(plateNumber, newPlateNumber);
                        System.out.println(message);
                        break;
                    case 2:
                        System.out.println("Enter the new model:");
                        String newModel = scanner.next();
                        message = truckService.setModel(plateNumber, newModel);
                        System.out.println(message);
                        break;
                    case 3:
                        System.out.println("Enter the new net weight:");
                        int newWeightNeto = scanner.nextInt();
                        message = truckService.setWeightNeto(plateNumber, newWeightNeto);
                        System.out.println(message);
                        break;
                    case 4:
                        System.out.println("Enter the new maximum weight:");
                        int newWeightMax = scanner.nextInt();
                        message = truckService.setWeightMax(plateNumber, newWeightMax);
                        System.out.println(message);
                        break;
                    case 5:
                        System.out.println("Is the truck now available? (true/false)");
                        boolean isAvailable = scanner.nextBoolean();
                        message = truckService.setAvailable(plateNumber, isAvailable);
                        System.out.println(message);
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
                break;
            case 4:
                System.out.println("Enter the plate number of the truck you want to check:");
                plateNumber = scanner.next();
                message = truckService.isAvailable(plateNumber);
                System.out.println(message);
                break;
            default:
                System.out.println("Invalid choice");
        }

    }

    private static void changeTransportService() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter transport ID:");
        int id = scanner.nextInt();

        System.out.println("What do you want to change?");
        System.out.println("1. Date");
        System.out.println("2. Leaving time");
        System.out.println("3. Truck number");
        System.out.println("4. Driver name");
        System.out.println("5. Driver ID");
        System.out.println("6. Source");
        System.out.println("7. Destination list");
        System.out.println("8. Delivery list");
        System.out.println("9. Truck weight neto");
        System.out.println("10. Truck weight max");

        int choice = scanner.nextInt();

        switch (choice) {
            // case 1:
            //     System.out.println("Enter new date:");
            //     String date = scanner.next();
            //     System.out.println(transportService.changeDate(id, date));
            //     break;
            case 2:
                System.out.println("Enter new leaving time:");
                String leavingTime = scanner.next();
                System.out.println(transportService.changeLeavingTime(id, leavingTime));
                break;
            case 3:
                System.out.println("Enter new truck number:");
                String truckNumber = scanner.next();
                System.out.println(transportService.changeTruckNumber(id, truckNumber));
                break;
            case 4:
                System.out.println("Enter new driver name:");
                String driverName = scanner.next();
                System.out.println(transportService.changeDriverName(id, driverName));
                break;
            case 5:
                System.out.println("Enter new driver ID:");
                int driverId = scanner.nextInt();
                System.out.println(transportService.changeDriverId(id, driverId));
                break;
            case 6:
                System.out.println("Enter new source:");
                String source = scanner.next();
                System.out.println(transportService.changeSource(id, source));
                break;
            case 7:
            case 8:
                System.out.println("you should open the program from the beginning");
                break;
            case 9:
                System.out.println("Enter new truck weight neto:");
                int truckWeightNeto = scanner.nextInt();
                System.out.println(transportService.changeTruckWeightNeto(id, truckWeightNeto));
                break;
            case 10:
                System.out.println("Enter new truck weight max:");
                int truckWeightMax = scanner.nextInt();
                System.out.println(transportService.changeTruckWeightMax(id, truckWeightMax));
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }

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
            dests.add(transportService.addDestination("Tel Aviv", "555-1234", "John Smith", Location.NORTH,
                    DestinationType.DESTINATION));
            dests.add(transportService.addDestination("Yafo", "555-5678", "Jane Doe", Location.SOUTH,
                    DestinationType.DESTINATION));
            dests.add(new Destination("Haifa", "555-9012", "Bob Johnson", Location.CENTER,
                    DestinationType.DESTINATION));
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

                sources.add(transportService.addDestination(name, phone, contact, location, DestinationType.SOURCE));
            }
        } else {
            // Use default sources
            sources.add(transportService.addDestination("cola", "555-1234", "John Smith", Location.NORTH,
                    DestinationType.SOURCE));
            sources.add(transportService.addDestination("osem", "555-5678", "Jane Doe", Location.SOUTH,
                    DestinationType.SOURCE));
            sources.add(transportService.addDestination("tnuva", "555-9012", "Bob Johnson", Location.CENTER,
                    DestinationType.SOURCE));
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
            System.out.println(truckService.addTruck("aaaa", "B", 200, 250));
            System.out.println(truckService.addTruck("bbbb", "B", 200, 1000));
            System.out.println(truckService.addTruck("cccc", "C", 200, 1000));
            System.out.println(truckService.addTruck("dddd", "B", 200, 1000));
            System.out.println(truckService.addTruck("eeee", "NULL", 200, 1000));
        }

    }

}