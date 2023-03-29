package BussinessLayer;

public class Driver {
    private int id;
    private String name;
    private String licence;

    public Driver(int id, String name, String licence) {
        this.id = id;
        this.name = name;
        this.licence = licence;
    }

    public boolean isQualified(Truck truck) {
        return truck.getModel().equals(licence) ;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }
}

