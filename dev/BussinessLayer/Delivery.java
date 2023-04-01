package BussinessLayer;

import java.util.List;

public class Delivery {
    private Destination source;
    private Destination dest;
    private Status status;
    private List<String> items;
    private int weight;

    public Delivery(Destination source, Destination dest, Status status, List<String> items, int weight) {
        this.source = source;
        this.dest = dest;
        this.status = status;
        this.items = items;
        this.weight = weight;
    }
    public void print() {
        System.out.println("Delivery from " + source.getAddress() + " to " + dest.getAddress());
        System.out.println("Status: " + status);
        System.out.println("Items: " + items);
        System.out.println("Total weight: " + weight + " kg");
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
