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
        for (Transport transport : transportMap.values())
        {
            List<Delivery> incompleteDeliveries =transport.run();


            if (incompleteDeliveries.size() > 0)
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
                            //changeDestination();
                            break;
                        case 2:
                            changeTruck(transport);
                            break;
                        case 3:
                            //dropItems();
                            break;
                        default:
                            System.out.println("Invalid choice. Please choose again.");
                    }
                    break;

                }
            }
            else
            {
                transport.printTransportDetails();
            }
        }
    }
    private void changeTruck(Transport transport) {
        Scanner scanner = new Scanner(System.in);

        // Print current driver's details
        DriverFacade driverFacade = DriverFacade.getInstance();
        Driver driver = driverFacade.getDriverById(transport.getId());

        System.out.println("Current driver: " + driver.getName() + ", License: " + driver.getLicense());

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
        if (!newTruck.getModel().equals(driver.getLicense())) {
            System.out.println("The selected truck doesn't match the current driver. Please try again.");
            return;
        }
        truckFacade.setTruckAvailability(transport.getTruckNumber(),true);

        // Set the new truck for the transport
        transport.setTruckNumber(newTruck.getPlateNumber());
        transport.setTruckWeightMax(newTruck.getWeightMax());
        transport.setTruckWeightNeto(newTruck.getWeightNeto());
        truckFacade.setTruckAvailability(newTruck.getPlateNumber(),false);

        // Rerun the transport
        List<Delivery> incompleteDeliveries = transport.run();

        // If there are still incomplete deliveries, repeat the change process
        if (incompleteDeliveries.size() > 0) {
            changeTruck(transport);
        } else {
            transport.printTransportDetails();
        }
    }


}
