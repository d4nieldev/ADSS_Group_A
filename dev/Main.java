import BussinessLayer.*;

import java.util.*;

class Main

{
    public static void main(String[] args)
    {

        DriverFacade driverFacade = makeSomeDrivers();
        TruckFacade truckFacade = makeSomeTrucks();
        List<Destination> dests = makeSomeDestinations();
        List<Destination> sources = makeSomeSources();
        List<Delivery> deliveries = createDeliveries(sources,dests);

        letTheUserChoose(deliveries,truckFacade,driverFacade);



    }
    public static void letTheUserChoose(List<Delivery> deliveries, TruckFacade truckFacade, DriverFacade driverFacade) {
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

                if (driver.getLicense().equals(truck.getModel())) {
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
            }

            // Update available drivers and trucks
            availableDrivers.remove(driver);
            availableTrucks.remove(truck);

            // Print matched driver, truck, and deliveries
            System.out.println("\nMatched driver: " + driver.getName() + " (" + driver.getLicense() + ")");
            System.out.println("Matched truck: " + truck.getPlateNumber() + " (" + truck.getModel() + ")");
            System.out.println("Matched deliveries:");
            printDeliveries(matchedDeliveries);
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

    public static List<Delivery> createDeliveries(List<Destination> sources, List<Destination> dests) {
        List<Delivery> deliveries = new ArrayList<>();
        Random random = new Random();

        for (Destination source : sources) {
            for (Destination dest : dests) {
                if (source != dest) {
                    // create a random list of items and weight for the delivery
                    List<String> items = new ArrayList<>();
                    int numItems = random.nextInt(5) + 1;
                    int weight = 0;
                    for (int i = 0; i < numItems; i++) {
                        String item = "Item " + (i + 1);
                        items.add(item);
                        weight += random.nextInt(10) + 1;
                    }

                    // create a random status for the delivery
                    Status status = Status.PENDING;

                    // create the delivery and add it to the list of deliveries
                    Delivery delivery = new Delivery(deliveries.size()+1,source, dest, status, items, weight);
                    deliveries.add(delivery);
                }
            }
        }

        return deliveries;
    }



    public static void printDestinations(ArrayList<Destination> destList) {
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

        dests.add(new Destination("tel aviv", "555-1234", "John Smith", Location.NORTH));
        dests.add(new Destination("raanana", "555-5678", "Jane Doe", Location.SOUTH));
        dests.add(new Destination("ashkelon", "555-9012", "Bob Johnson", Location.CENTER));
        return dests;

    }
    private static List<Destination> makeSomeSources()
    {
        List<Destination> sources = new ArrayList<Destination>();

        sources.add(new Destination("cola", "555-1234", "John Smith", Location.NORTH));
        sources.add(new Destination("osem", "555-5678", "Jane Doe", Location.SOUTH));
        sources.add(new Destination("tnuva", "555-9012", "Bob Johnson", Location.CENTER));
        return sources;

    }


    private static TruckFacade makeSomeTrucks()
    {
        Truck T1= new Truck("aaaa","a",200,1000);
        Truck T2= new Truck("bbbb","b",200,1000);
        Truck T3= new Truck("cccc","c",200,1000);
        Truck T4= new Truck("dddd","d",200,1000);
        Truck T5= new Truck("eeee","e",200,1000);
        TruckFacade T = new TruckFacade();
        T.addTruck(T1);
        T.addTruck(T2);
        T.addTruck(T3);
        T.addTruck(T4);
        T.addTruck(T5);
        return T;
    }

    private static DriverFacade makeSomeDrivers()
    {
        Driver D1= new Driver(1,"rotem","a");
        Driver D2= new Driver(2,"kfir","b");
        Driver D3= new Driver(3,"adi","c");
        Driver D4= new Driver(4,"messi","d");
        Driver D5= new Driver(5,"ronaldo","e");
        DriverFacade D = new DriverFacade();
        D.addDriver(D1);
        D.addDriver(D2);
        D.addDriver(D3);
        D.addDriver(D4);
        D.addDriver(D5);
        return D;
    }
}