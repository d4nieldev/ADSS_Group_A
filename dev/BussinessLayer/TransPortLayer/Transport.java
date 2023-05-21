package BussinessLayer.TransPortLayer;

import DataAccessLayer.DTO.TransportLayer.TransportDTO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.swing.text.html.parser.DTD;

public class Transport {
    private int id;
    private LocalDate date;
    private String leavingTime;
    private String truckNumber;
    private String driverName;
    private int driverId;
    private String source;
    private List<Destination> destinationList;
    private List<Delivery> deliveryList;
    private int truckWeightNeto;
    private int truckWeightMax;
    private List<String> loadedItems;
    private int currentWeight;



    public Transport(int id, LocalDate date, String leavingTime, String truckNumber, String driverName, int driverId, String source,
                     List<Destination> destinationList, List<Delivery> deliveryList, int truckWeightNeto, int truckWeightMax)
    {
        this.id = id;
        this.date = date;
        this.leavingTime = leavingTime;
        this.truckNumber = truckNumber;
        this.driverName = driverName;
        this.driverId = driverId;
        this.source = source;
        this.destinationList=destinationList;
        this.deliveryList=deliveryList;
        this.truckWeightNeto=truckWeightNeto;
        this.truckWeightMax=truckWeightMax;
        this.loadedItems= new ArrayList<String>();
        this.currentWeight= truckWeightNeto;
    }

    public Transport(Transport DTO){
        this.id = DTO.id;
        this.date = DTO.date;
        this.leavingTime = DTO.leavingTime;
        this.truckNumber = DTO.truckNumber;
        this.driverName = DTO.driverName;
        this.driverId = DTO.driverId;
        this.source = DTO.source;
        this.destinationList = DTO.destinationList;
        this.deliveryList = DTO.deliveryList;
        this.truckWeightNeto = DTO.truckWeightNeto;
        this.truckWeightMax = DTO.truckWeightMax;
        this.loadedItems = DTO.loadedItems;
        this.currentWeight = DTO.currentWeight;
    }

    public TransportDTO toDTO(){
        return new TransportDTO(this.id = id,
        this.date = date,
        this.leavingTime = leavingTime,
        this.truckNumber = truckNumber,
        this.driverName = driverName,
        this.driverId = driverId,
        this.source = source,
        this.destinationList=destinationList,
        this.deliveryList=deliveryList,
        this.truckWeightNeto=truckWeightNeto,
        this.truckWeightMax=truckWeightMax,
        this.loadedItems= new ArrayList<String>(),
        this.currentWeight= truckWeightNeto);
    }

    public void addDestination(Destination destination) {
        destinationList.add(destination);
    }

    public void removeDestination(String destinationName) {
        destinationList.remove(destinationName);
    }

    public Destination getDestination(int i)
    {
        return destinationList.get(i);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(String leavingTime) {
        this.leavingTime = leavingTime;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setDriverId(int id) {this.driverId = driverId; }

    public void setDeliveryList(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }

    public void setTruckWeightNeto(int truckWeightNeto) {
        this.truckWeightNeto = truckWeightNeto;
    }

    public void setTruckWeightMax(int truckWeightMax) {
        this.truckWeightMax = truckWeightMax;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Destination> getDestinationList() {
        return destinationList;
    }

    public void setDestinationList(List<Destination> destinations)
    {
        this.destinationList = destinationList;
    }



    /**
     * return list of delivery that not complete
     *
     * @return
     */
    public List<Delivery> run() {
        // Set leaving time to now and date to today
        TruckFacade truckFacade = TruckFacade.getInstance();
        truckFacade.setTruckAvailability(truckNumber,false);
        onTheWay(deliveryList);
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //leavingTime = sdf.format(today);
        //date= today;
        List<Delivery> incompleteDeliveries = new ArrayList<>();
        currentWeight=truckWeightNeto;
        Scanner scanner = new Scanner(System.in);


        for (Destination dest : destinationList) {
            if (dest.getType() == DestinationType.SOURCE) {
                // Get items from the deliveries for this source

                for (Delivery delivery : deliveryList) {
                    if (delivery.getSource().equals(dest)) {
                        loadedItems.addAll(delivery.getItems());
                        System.out.println("You are in a source location, there might be more than one delivery you collect from here, we will weight each one of them.\n "+ "the weight before loading was: " + currentWeight+"\nHow much weight does the truck gained from this delivery?");
                        int newWeight = scanner.nextInt();
                        delivery.setWeight(newWeight);
                        currentWeight = currentWeight+ newWeight;
                        if (currentWeight > truckWeightMax) {
                            incompleteDeliveries.addAll( thinkAgain("overweight",dest.getAddress(),driverName,currentWeight-newWeight,currentWeight,truckWeightMax));
                            return incompleteDeliveries;
                        }
                    }
                }

            } else if (dest.getType() == DestinationType.DESTINATION) {
                // Find delivery with this destination

                for (Delivery d : deliveryList) {
                    if (d.getDestination().equals(dest)) {
                        // Change status of delivery to completed
                        d.setStatus(Status.COMPLETED);
                        // Reduce weight of delivered items from truck weight
                        int deliveredWeight = d.getWeight();
                        currentWeight -= deliveredWeight;
                    }
                }

            }
        }
        System.out.println("Transport " + id + " completed successfully!");
        return incompleteDeliveries;
    }

    /**
     * update the status of delivery in specific transport to "on the way"
     */
    private void onTheWay(List<Delivery> deliveryList) {
        for (Delivery delivery : deliveryList) {
            delivery.setStatus(Status.ON_THE_WAY);
        }
    }

    /**
     * return list of delivery that not complete
     *
     * @return
     */
    private List<Delivery> thinkAgain(String reason, String destination, String driver, int weightBefore, int weightAfter, int maxWeight) {
        System.out.println("Transport didn't complete due to " + reason);
        System.out.println("In destination " + destination + ", driver " + driver + ", you added " + (weightAfter - weightBefore) + " kg to the truck weight.");
        System.out.println("Truck weight before: " + weightBefore + " kg, after: " + weightAfter + " kg, max weight: " + maxWeight + " kg");

        List<Delivery> completedDeliveries = new ArrayList<>();
        List<Delivery> incompleteDeliveries = new ArrayList<>();

        for (Delivery delivery : deliveryList) {
            if (delivery.getStatus() == Status.COMPLETED) {
                completedDeliveries.add(delivery);
            } else {
                incompleteDeliveries.add(delivery);
            }
        }

        System.out.println("Completed deliveries:");
        for (Delivery delivery : completedDeliveries) {
            System.out.println("- " + delivery.getSource().getAddress() + " to " + delivery.getDestination().getAddress() + ", " + delivery.getWeight() + " kg of " + delivery.getItems() + ", status: " + delivery.getStatus());
        }

        System.out.println("Incomplete deliveries:");
        for (Delivery delivery : incompleteDeliveries) {
            System.out.println("- " + delivery.getSource().getAddress() + " to " + delivery.getDestination().getAddress() + ", " + delivery.getWeight() + " kg of " + delivery.getItems() + ", status: " + delivery.getStatus());
        }


        return (incompleteDeliveries);
    }



    /**
     * print document of transport with the list of items (client ask this to driver)
     */
    public void printTransportDetails() {
        System.out.println("Transport Details:");
        System.out.println("ID: " + id);
        System.out.println("Date: " + date);
        System.out.println("Leaving Time: " + leavingTime);
        System.out.println("Truck Number: " + truckNumber);
        System.out.println("Driver Name: " + driverName);
        System.out.println("Driver ID: " + driverId);
        System.out.println("Source: " + source);

        System.out.println("Deliveries:");
        for (Delivery delivery : deliveryList) {
            System.out.println("Delivery ID: " + delivery.getId());
            System.out.println("Delivery Source: " + delivery.getSource().getAddress());
            System.out.println("Delivery Destination: " + delivery.getDestination().getAddress());
            System.out.println("Delivery Status: " + delivery.getStatus());
            System.out.println("Delivery Items: " + delivery.getItems());
            System.out.println("--------------------");
        }
    }


    public int getDriverId() {
        return driverId;
    }

    public List<Delivery> getDeliveryList() {
        return deliveryList;
    }

    public List<String> getLoadedItems() {
        return loadedItems;
    }

    public void setLoadedItems(List<String> loadedItems) {
        this.loadedItems=loadedItems;
    }

    public List<String> getDestinationBranches() {
        List<String> branches = new ArrayList<String>();
        for (Destination destination : destinationList) {
            if (destination.getType() == DestinationType.DESTINATION) {
                branches.add(destination.getAddress());
            }
        }
        return branches;
    }

}
