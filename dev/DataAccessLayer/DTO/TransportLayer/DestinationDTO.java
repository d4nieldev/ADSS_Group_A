package DataAccessLayer.DTO.TransportLayer;

import BussinessLayer.TransPortLayer.DestinationType;
import BussinessLayer.TransPortLayer.Location;

public class DestinationDTO {
    private String address;
    private String phoneNumber;
    private String contactName;
    private Location location;
    private DestinationType destinationType;

    public DestinationDTO(String address, String phoneNumber, String contactName, Location location, DestinationType destinationType) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
        this.location = location;
        this.destinationType = destinationType;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public Location getLocation() {
        return location;
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public String fieldsToString() {
        return String.format("(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")",
         this.address, this.phoneNumber, this.contactName, this.location.name(), this.destinationType.name());
    }
}
