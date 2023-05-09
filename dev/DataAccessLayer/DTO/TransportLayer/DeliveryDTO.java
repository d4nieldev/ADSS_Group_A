package DataAccessLayer.DTO.TransportLayer;

import BussinessLayer.TransPortLayer.Destination;
import BussinessLayer.TransPortLayer.Status;

import java.util.List;

public class DeliveryDTO {
    private int id;
    private Destination source;
    private Destination destination;
    private Status status;
    private List<String> items;
    private int weight;

    public DeliveryDTO(int id, Destination source, Destination destination, Status status, List<String> items, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.status = status;
        this.items = items;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public Destination getSource() {
        return source;
    }

    public Destination getDestination() {
        return destination;
    }

    public Status getStatus() {
        return status;
    }

    public List<String> getItems() {
        return items;
    }

    public int getWeight() {
        return weight;
    }

    public String fieldsToString() {
        return String.format("(%d,%s,%s,%s,%s,%d)", id, source, destination, status, items, weight);
    }
}
