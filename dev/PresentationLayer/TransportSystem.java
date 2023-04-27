package PresentationLayer;

import BussinessLayer.*;
import BussinessLayer.TransPortLayer.Delivery;
import BussinessLayer.TransPortLayer.Destination;
import BussinessLayer.TransPortLayer.DestinationType;
import BussinessLayer.TransPortLayer.Location;
import ServiceLayer.TransportLayer.DriverService;
import ServiceLayer.TransportLayer.TransportService;
import ServiceLayer.TransportLayer.TruckService;
import TestEmployee.UnitTest;

import java.util.*;
import java.util.stream.Collectors;


public class TransportSystem

{

    private static TransportService transportServices = new TransportService();
    private static DriverService driverService = new DriverService();
    private static TruckService truckService = new TruckService();


        public static void main(String[] args)
        {

            System.out.println("Welcome to the Transport System!");

            makeSomeDrivers();
            makeSomeTrucks();
            List<Destination> dests = makeSomeDestinations();
            List<Destination> sources = makeSomeSources();
            List<Delivery> deliveries = transportServices.createDeliveries(sources, dests);

            transportServices.letTheUserMatch(deliveries);
            transportServices.runTheTransports();

            Scanner scanner = new Scanner(System.in);
            boolean continueChoosing = true;
            while (continueChoosing) {
                System.out.println("\nWhat would you like to change?");
                System.out.println("1. Drivers");
                System.out.println("2. Trucks");
                System.out.println("3. Transports");
                System.out.println("4. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                switch (choice) {
                    case 1:
                        changeDriverService();
                        break;
                    case 2:
                        changeTruckService();
                        break;
                    case 3:
                        changeTransportService();
                        break;
                    case 4:
                        continueChoosing = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            System.out.println("Thank you for using the Transport System!");
        }

    private static void changeDriverService() {
        Scanner scanner = new Scanner(System.in);
        boolean isDone = false;

        while (!isDone) {
            System.out.println("What would you like to do with the driver service?");
            System.out.println("1. Add new driver");
            System.out.println("2. Remove a driver");
            System.out.println("3. Change driver's name");
            System.out.println("4. Change driver's license");
            System.out.println("5. Change driver's availability");
            System.out.println("6. Print driver's license type");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter driver's ID:");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter driver's name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter driver's license type:");
                    String license = scanner.nextLine();
                    System.out.println(driverService.addDriver(id, name, license));
                    break;
                case 2:
                    System.out.println("Enter driver's ID:");
                    int idToRemove = scanner.nextInt();
                    System.out.println(driverService.removeDriver(idToRemove));
                    break;
                case 3:
                    System.out.println("Enter driver's ID:");
                    int idToChangeName = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter driver's new name:");
                    String newName = scanner.nextLine();
                    System.out.println(driverService.changeName(idToChangeName, newName));
                    break;
                case 4:
                    System.out.println("Enter driver's ID:");
                    int idToChangeLicense = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter driver's new license type:");
                    String newLicense = scanner.nextLine();
                    System.out.println(driverService.changeLicence(idToChangeLicense, newLicense));
                    break;
                case 5:
                    System.out.println("Enter driver's ID:");
                    int idToChangeAvailability = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter driver's new availability (true/false):");
                    boolean newAvailability = scanner.nextBoolean();
                    System.out.println(driverService.setAvailable(idToChangeAvailability, newAvailability));
                    break;
                case 6:
                    System.out.println("Enter driver's ID:");
                    int idToPrintLicense = scanner.nextInt();
                    System.out.println(driverService.printDriverLicense(idToPrintLicense));
                    break;
                case 7:
                    isDone = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        }
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
        TransportService transportService = new TransportService();

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
            case 1:
                System.out.println("Enter new date:");
                String date = scanner.next();
                System.out.println(transportService.changeDate(id, date));
                break;
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

        scanner.close();
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

                System.out.println(driverService.addDriver(id, name, licenseNumber));

                System.out.println("Do you want to add another driver? (y/n)");
                choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("n")) {
                    break;
                }
            }
        } else {
            // Use the default drivers
            System.out.println(driverService.addDriver(1,"rotem","a"));
            System.out.println(driverService.addDriver(2,"kfir","b"));
            System.out.println(driverService.addDriver(3,"adi","c"));
            System.out.println(driverService.addDriver(4,"messi","d"));
            System.out.println(driverService.addDriver(5,"ronaldo","e"));
        }
    }

}