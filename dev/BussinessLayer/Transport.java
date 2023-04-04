package BussinessLayer;

import java.text.SimpleDateFormat;
import java.util.*;

public class Transport {
    private int id;
    private String date;
    private String leavingTime;
    private String truckNumber;
    private String driverName;
    private int driverId;
    private String source;
    private List<Destination> destinationList;
    private List<Delivery> deliveryList;
    private int truckWeightNeto;
    private int truckWeightMax;



    public Transport(int id, String date, String leavingTime, String truckNumber, String driverName, int driverId, String source,
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

        int truckWeight = truckWeightNeto;
        for (Destination dest : destinationList) {
            if (dest.getType() == DestinationType.SOURCE) {
                // Get items from the deliveries for this source
                List<String> sourceItems = new ArrayList<>();
                for (Delivery delivery : deliveryList) {
                    if (delivery.getSource().equals(dest)) {
                        sourceItems.addAll(delivery.getItems());
                    }
                }

                // Add weight of source items to truck weight
                int sourceWeight = sourceItems.stream().mapToInt(String::length).sum();
                truckWeight += sourceWeight;

                if (truckWeight > truckWeightMax) {
                    incompleteDeliveries.addAll( thinkAgain("weight too much",dest.getAddress(),driverName,truckWeight-sourceWeight,truckWeight,truckWeightMax));
                    return incompleteDeliveries;
                }
            } else if (dest.getType() == DestinationType.DESTINATION) {
                // Find delivery with this destination
                Delivery delivery = null;
                for (Delivery d : deliveryList) {
                    if (d.getDestination().equals(dest)) {
                        delivery = d;
                        break;
                    }
                }

                // Change status of delivery to completed
                delivery.setStatus(Status.COMPLETED);

                // Reduce weight of delivered items from truck weight
                int deliveredWeight = delivery.getWeight();
                truckWeight -= deliveredWeight;
            }
        }
        System.out.println("Transport " + id + " completed successfully!");
        return incompleteDeliveries;
    }

    private void onTheWay(List<Delivery> deliveryList) {
        for (Delivery delivery : deliveryList) {
            delivery.setStatus(Status.ON_THE_WAY);
        }
    }

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
            System.out.println("- " + delivery.getSource() + " to " + delivery.getDestination() + ", " + delivery.getWeight() + " kg of " + delivery.getItems() + ", status: " + delivery.getStatus());
        }

        System.out.println("Incomplete deliveries:");
        for (Delivery delivery : incompleteDeliveries) {
            System.out.println("- " + delivery.getSource() + " to " + delivery.getDestination() + ", " + delivery.getWeight() + " kg of " + delivery.getItems() + ", status: " + delivery.getStatus());
        }


        return (incompleteDeliveries);
    }




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


}
