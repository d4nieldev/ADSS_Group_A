package BussinessLayer.TransPortLayer;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import BussinessLayer.EmployeeTransportFacade;
import BussinessLayer.EmployeesLayer.Driver;

public class TransportFacade {

    private Map<Integer, Transport> transportMap;
    private EmployeeTransportFacade employeeTransportFacade=null;

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


    public void createTransport(LocalDate date, String leavingTime, String truckNumber, String driverName, int driverId, String source,
                                List<Destination> destinationList, List<Delivery> deliveryList, int truckWeightNeto, int truckWeightMax)
    {
        Transport shipment = new Transport(id, date, leavingTime, truckNumber, driverName, driverId,
                destinationList.get(0).getAddress(), destinationList,deliveryList,truckWeightNeto,truckWeightMax);
        System.out.println("The id of transport is:"+ id);
        addTransport(id , shipment);
        id++;
    }

    /**
     * add transport to facade
     */
    public void addTransport(int id, Transport transport) {
        transportMap.put(id, transport);
    }

    /**
     * return true if truck exist in facade
     *
     * @return
     */
    public  Boolean hasTranspot(Integer id){return transportMap.containsKey(id);}

    public Transport getTransport(Integer ide) {
        return transportMap.get(id);
    }

    /**
     * remove transport from facade
     */
    public void removeTransport(Integer id) {
        transportMap.remove(id);
    }

    /**
     * remove the process of create transport by user
     */
    public void runTheTransports()
    {
        for (Transport transport : transportMap.values())
        {
            List<Delivery> incompleteDeliveries = transport.run();

            while (!incompleteDeliveries.isEmpty())
            {
                // Change the status of incomplete deliveries to WAIT_FOR_CHANGE
                for (Delivery delivery : incompleteDeliveries)
                {
                    delivery.setStatus(Status.WAIT_FOR_CHANGE);
                }

                // Ask the user what to do next
                Scanner scanner = new Scanner(System.in);

                while (true)
                {
                    System.out.println("What would you like to do?");
                    System.out.println("1. Change a destination");
                    System.out.println("2. Change the truck");
                    System.out.println("3. Drop some items");

                    int choice = scanner.nextInt();

                    switch (choice)
                    {
                        case 1:
                            changeDestination(transport);
                            break;
                        case 2:
                            changeTruck(transport);
                            break;
                        case 3:
                            dropItems(transport);
                            break;
                        default:
                            System.out.println("Invalid choice. Please choose again.");
                    }

                    incompleteDeliveries = transport.run();
                    if (incompleteDeliveries.isEmpty())
                    {
                        break;
                    }
                }
            }

            transport.printTransportDetails();
        }
    }

    /**
     * remove item from transport
     */
    private void dropItems(Transport transport) {
        List<String> loadedItems = transport.getLoadedItems();
        Scanner scanner = new Scanner(System.in);

        // Print the list of loaded items
        System.out.println("Loaded items:");
        for (int i = 0; i < loadedItems.size(); i++) {
            System.out.println((i + 1) + ". " + loadedItems.get(i));
        }

        // Ask the user which items they want to remove
        System.out.println("Which items do you want to remove? (Enter the number, separate by comma)");
        String input = scanner.nextLine();
        String[] indicesToRemove = input.split(",");

        // Remove the selected items from the list
        List<String> itemsToRemove = new ArrayList<>();
        for (String index : indicesToRemove) {
            int i = Integer.parseInt(index.trim()) - 1;
            if (i >= 0 && i < loadedItems.size()) {
                itemsToRemove.add(loadedItems.get(i));
            }
        }
        loadedItems.removeAll(itemsToRemove);
        transport.setLoadedItems(loadedItems);

        System.out.println("Updated list of loaded items:");
        for (int i = 0; i < loadedItems.size(); i++) {
            System.out.println((i + 1) + ". " + loadedItems.get(i));
        }
    }


    /**
     * change the list of destination
     */
    private void changeDestination(Transport transport) {
        List<Destination> destinationList = transport.getDestinationList();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            // Print the list of destinations
            System.out.println("List of Destinations:");
            for (int i = 0; i < destinationList.size(); i++) {
                System.out.println((i + 1) + ". " + destinationList.get(i).getAddress());
            }

            // Ask the user what they want to do
            System.out.println("What would you like to do?");
            System.out.println("1. change the order of destinations");
            System.out.println("2. Remove a destination from the list");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:

                    changingOrder(transport);

                    break;
                case 2:

                    removingDestination(transport);

                    // If the user made a change to the destinations, break the loop and run the transport again
                    break;

                default:
                    System.out.println("Invalid choice. Please choose again.");
            }

            // If the user made a change to the destinations, break the loop and run the transport again
                break;

        }
    }

    /**
     * remove destination from transport
     */
    private void removingDestination(Transport transport)
    {
        List<Destination> destinationList = transport.getDestinationList();
        List<Delivery> deliveryList = transport.getDeliveryList();
        Scanner scanner = new Scanner(System.in);
        // Ask the user which destination they want to remove
        System.out.println("Which destination do you want to remove? (Enter the number)");
        int indexToRemove = scanner.nextInt() - 1;

        // Remove the destination from the list
        Destination destinationToRemove = destinationList.remove(indexToRemove);

        // Remove any deliveries with the chosen destination as the source or destination
        List<Delivery> deliveriesToRemove = new ArrayList<>();
        for (Delivery delivery : deliveryList) {
            if (delivery.getSource().equals(destinationToRemove) || delivery.getDestination().equals(destinationToRemove)) {
                deliveriesToRemove.add(delivery);
            }
        }
        deliveryList.removeAll(deliveriesToRemove);
        transport.setDeliveryList(deliveryList);
    }

    /**
     * update the order od delivery
     */
    public void changingOrder(Transport transport) {
        List<Destination> destinationList = transport.getDestinationList();
        Scanner scanner = new Scanner(System.in);

        // Print the list of destinations
        System.out.println("Current list of Destinations:");
        for (int i = 0; i < destinationList.size(); i++) {
            System.out.println((i + 1) + ". " + destinationList.get(i).getAddress());
        }

        // Ask the user for the new order of destinations
        System.out.println("Enter the new order of destinations separated by commas:");
        String input = scanner.nextLine();
        String[] inputArr = input.split(",");

        // Create a new list of destinations in the specified order
        List<Destination> newDestinationList = new ArrayList<>();
        for (String str : inputArr) {
            int index = Integer.parseInt(str.trim()) - 1;
            newDestinationList.add(destinationList.get(index));
        }

        // Update the transport's list of destinations
        transport.setDestinationList(newDestinationList);

        System.out.println("The order of destinations has been updated.");
    }


    /**
     * return list of destination that user want transport for them
     * @return
     */
    public List<Destination> letTheUserChooseTheOrder(List<Delivery> matchedDeliveries) {
        // Create a list of all destinations without duplicates
        List<Destination> allDestinations = new ArrayList<>();
        for (Delivery delivery : matchedDeliveries) {
            allDestinations.add(delivery.getSource());
            allDestinations.add(delivery.getDest());
        }
        List<Destination> uniqueDestinations = allDestinations.stream().distinct().collect(Collectors.toList());

        // Print all destinations with indexes for the user to choose from
        System.out.println("Please choose the order of destinations:");
        for (int i = 0; i < uniqueDestinations.size(); i++) {
            Destination destination = uniqueDestinations.get(i);
            System.out.println(i + ": " + destination.getAddress() + " (" + destination.getLocation() + ")");
        }

        // Ask user to input the order of destinations
        List<Destination> chosenOrder = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the index of the next destination or enter -1 to finish the order:");
        int index = scanner.nextInt();
        while (index != -1) {
            if (index >= 0 && index < uniqueDestinations.size()) {
                Destination destination = uniqueDestinations.get(index);
                if (!chosenOrder.contains(destination)) {
                    chosenOrder.add(destination);
                    System.out.println("Added " + destination.getAddress() + " to the order.");
                } else {
                    System.out.println("Destination already added to the order.");
                }
            } else {
                System.out.println("Invalid index.");
            }
            System.out.println("Enter the index of the next destination or enter -1 to finish the order:");
            index = scanner.nextInt();
        }

        // Print the chosen order
        System.out.println("Chosen order of destinations:");
        for (int i = 0; i < chosenOrder.size(); i++) {
            Destination destination = chosenOrder.get(i);
            System.out.println(i + ": " + destination.getAddress() + " (" + destination.getLocation() + ")");
        }

        return chosenOrder;
    }


    /**
     * change truck that match to transport
     */
    private void changeTruck(Transport transport) {
        Scanner scanner = new Scanner(System.in);

            Driver driver = employeeTransportFacade.getDriverById(transport.getDriverId());
            System.out.println("Current driver: " + driver.getFirstName() +" "+ driver.getLastName()+ ", License: " + driver.getDriverLicense());

            // Print available trucks
            System.out.println("Available trucks: ");
            TruckFacade truckFacade = TruckFacade.getInstance();
            List<Truck> trucks = truckFacade.getAvailableTrucks();
            for (int i = 0; i < trucks.size(); i++) {
                Truck truck = trucks.get(i);
                System.out.println((i + 1) + ". Plate number: " + truck.getPlateNumber() + ", Model: " + truck.getModel());
            }

            // Ask user to select a new truck
            System.out.print("Please enter the number of the truck you want to select: ");
            int truckIndex = scanner.nextInt();
            if (truckIndex < 1 || truckIndex > trucks.size()) {
                System.out.println("Invalid choice. Please try again.");
                return;
            }

            // Verify that the selected truck has the same driver as the current transport
            Truck newTruck = trucks.get(truckIndex - 1);
            if (!newTruck.getModel().equals(driver.getDriverLicense())) {
                System.out.println("The selected truck doesn't match the current driver. Please try again.");
                return;
            }
            truckFacade.setTruckAvailability(transport.getTruckNumber(), true);

            // Set the new truck for the transport
            transport.setTruckNumber(newTruck.getPlateNumber());
            transport.setTruckWeightMax(newTruck.getWeightMax());
            transport.setTruckWeightNeto(newTruck.getWeightNeto());
            truckFacade.setTruckAvailability(newTruck.getPlateNumber(), false);

            // Rerun the transport



    }

    /**
     * return list of delivery
     * @return
     */
    public List<Delivery> createDeliveries(List<Destination> sources, List<Destination> dests) {
        List<Delivery> deliveries = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter '1' to enter deliveries manually, or '2' to use default values:");
        int choice = scanner.nextInt();

        if (choice == 2) {
            List<String> firstList = Arrays.asList("pepsi", "diet", "zero");
            List<String> secondList = Arrays.asList("bamba", "bisli");
            List<String> thirdList = Arrays.asList("cheese", "milk", "butter", "milki");

            Delivery delivery1 = new Delivery(1, sources.get(0), dests.get(0), Status.PENDING, firstList);
            Delivery delivery2 = new Delivery(2, sources.get(0), dests.get(1), Status.PENDING, firstList);
            Delivery delivery3 = new Delivery(3, sources.get(1), dests.get(0), Status.PENDING, secondList);
            Delivery delivery4 = new Delivery(4, sources.get(1), dests.get(1), Status.PENDING, secondList);
            Delivery delivery5 = new Delivery(5, sources.get(2), dests.get(0), Status.PENDING, thirdList);
            Delivery delivery6 = new Delivery(6, sources.get(2), dests.get(1), Status.PENDING, thirdList);

            deliveries.add(delivery1);
            deliveries.add(delivery2);
            deliveries.add(delivery3);
            deliveries.add(delivery4);
            deliveries.add(delivery5);
            deliveries.add(delivery6);

        } else if (choice == 1) {
            System.out.println("Enter the number of deliveries you want to create:");
            int numDeliveries = scanner.nextInt();

            for (int i = 0; i < numDeliveries; i++) {
                System.out.println("Delivery " + (i + 1) + ":");
                System.out.println("Choose a source (enter the number):");

                for (int j = 0; j < sources.size(); j++) {
                    System.out.println((j + 1) + ": " + sources.get(j).getAddress());
                }
                int sourceIndex = scanner.nextInt() - 1;
                Destination source = sources.get(sourceIndex);

                System.out.println("Choose a destination (enter the number):");
                for (int j = 0; j < dests.size(); j++) {
                    System.out.println((j + 1) + ": " + dests.get(j).getAddress());
                }
                int destIndex = scanner.nextInt() - 1;
                Destination dest = dests.get(destIndex);

                System.out.println("Enter the items to deliver (separated by commas):");
                String itemsInput = scanner.next();
                List<String> items = Arrays.asList(itemsInput.split(","));

                Delivery delivery = new Delivery(i + 1, source, dest, Status.PENDING, items);
                deliveries.add(delivery);
            }
        } else {
            System.out.println("Invalid choice.");
        }

        return deliveries;
    }

    /**
     * return new destination
     * @return
     */
    public Destination addDestination(String address, String phoneNumber, String contactName, Location location,DestinationType destinationType){
        return new Destination(address, phoneNumber, contactName, location, destinationType);
    }


    /**
     * make match of all choise of user
     */
    public void letTheUserMatch(LocalDate transportDate, List<Delivery> deliveries, List<Driver> availableDrivers, List<Truck> availableTrucks)
    {

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
            System.out.println("\nMatched driver: " + driver.getFirstName()+" "+driver.getLastName() + " (" + driver.getDriverLicense() + ")");
            System.out.println("Matched truck: " + truck.getPlateNumber() + " (" + truck.getModel() + ")");
            System.out.println("Matched deliveries:");
            printDeliveries(matchedDeliveries);
            Date d = new Date();
            List<Destination> destinationList = letTheUserChooseTheOrder(matchedDeliveries);

            createTransport(transportDate,"0000",truck.getPlateNumber(),driver.getFirstName(),driver.getId(),"source",
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

    private void printDrivers(List<Driver> drivers) {
        for (int i = 0; i < drivers.size(); i++) {
            Driver driver = drivers.get(i);
            System.out.println(i + ": " + driver.getFirstName() + " (" + driver.getDriverLicense() + ")");
        }
    }

    private void printTrucks(List<Truck> trucks) {
        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);
            System.out.println(i + ": " + truck.getPlateNumber() + " (" + truck.getModel() + ")");
        }
    }


    private void printDeliveries(List<Delivery> deliveries) {
        for (int i = 0; i < deliveries.size(); i++) {
            Delivery delivery = deliveries.get(i);
            System.out.println(i + ": " + delivery.getSource().getAddress() + " (" + delivery.getSource().getLocation() + ") -> "
                    + delivery.getDest().getAddress() + " (" + delivery.getDest().getLocation() + ")");
        }
        System.out.println();
    }

    public Map<Integer, Transport> getTransportMap() {
        return transportMap;
    }

    public void setEmployeeFacade(EmployeeTransportFacade employeeTransportFacade) {
        this.employeeTransportFacade=employeeTransportFacade;
    }

    public String getTransportsByDate(LocalDate date) {
        StringBuilder sb = new StringBuilder();
        sb.append("Transport ID, Date, Driver Name, Truck Number, Source, # of Deliveries, # of Destinations\n");
        sb.append("--------------------------------------------------------------------------\n");

        for (Transport transport : transportMap.values()) {
            if (transport.getDate().equals(date.toString())) {
                int numDeliveries = transport.getDeliveryList().size();
                int numDestinations = transport.getDestinationList().size();

                sb.append(String.format("%-12d %-12s %-15s %-12s %-10s %-16d %-16d\n",
                        transport.getId(),
                        transport.getDate(),
                        transport.getDriverName(),
                        transport.getTruckNumber(),
                        transport.getSource(),
                        numDeliveries,
                        numDestinations));
                sb.append("--------------------------------------------------------------------------\n");
            }
        }

        return sb.toString();
    }

    public List<Integer> getDriversByDate(LocalDate transportDate) {
        List<Integer> drivers = new ArrayList<Integer>();
        for (Transport transport : transportMap.values()) {
            if (transport.getDate().equals(transportDate)) {
                drivers.add(transport.getDriverId());
            }
        }
        return drivers;
    }


    public List<String> getBranchesByDateAndDriverId(LocalDate transportDate, int driverId) {
        List<String> branches = new ArrayList<String>();
        for (Transport transport : transportMap.values()) {
            if (transport.getDate().equals(transportDate) && transport.getDriverId() == driverId) {
                branches.addAll(transport.getDestinationBranches());
            }
        }
        return branches;
    }

}
