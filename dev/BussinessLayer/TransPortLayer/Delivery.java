package BussinessLayer.TransPortLayer;

import java.util.List;

public class Delivery {
    private int id;
    private Destination source;
    private Destination dest;
    private Status status;
    private List<String> items;
    private int weight;

    private Driver driver;
    private Truck truck;

    public Delivery(int id,Destination source, Destination dest, Status status, List<String> items) {
        this.id = id;
        this.source = source;
        this.dest = dest;
        this.status = status;
        this.items = items;
        truck= null;
        driver = null;
        weight=0;
    }

    /**
     * print record of delivery
     */

    public void deliveryPrint() {
        System.out.println("Delivery from " + source.getAddress() + " to " + dest.getAddress());
        System.out.println("Status: " + status);
        System.out.println("Items: " + items);

        System.out.println("------------------------------");
    }


    public Destination getSource() {
        return source;
    }

    public void setSource(Destination source) {
        this.source = source;
    }

    public Destination getDest() {
        return dest;
    }

    public void setDest(Destination dest) {
        this.dest = dest;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public int getId() { return id;
    }

    public Driver getDriver() {
        return driver;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Destination getDestination() {
        return dest;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight=weight;
    }
}
