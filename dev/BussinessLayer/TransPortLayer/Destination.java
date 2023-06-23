package BussinessLayer.TransPortLayer;

import DataAccessLayer.DTO.TransportLayer.DestinationDTO;

public class Destination {
    private String address;
    private String phoneNumber;
    private String contactName;
    private Location location;
    private DestinationType destinationType;

    public Destination(String address, String phoneNumber, String contactName, Location location,DestinationType destinationType) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
        this.location = location;
        this.destinationType=destinationType;
    }

    // getters and setters for all private instance variables
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public DestinationType getType() {
        return destinationType;
    }

    public DestinationDTO toDTO() {
        return new DestinationDTO(this.address=address,this.phoneNumber=phoneNumber,
        this.contactName=contactName,this.location=location,
        this.destinationType=destinationType);

        
    }
}
