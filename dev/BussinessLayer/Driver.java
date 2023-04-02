package BussinessLayer;

public class Driver {
    private int id;
    private String name;
    private String license;

    public Driver(int id, String name, String licence) {
        this.id = id;
        this.name = name;
        this.license = licence;
    }

    public boolean isQualified(Truck truck) {
        return truck.getModel().equals(license) ;
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

    public String getLicense() {
        return license;
    }

    public void setLicense(String licence) {
        this.license = licence;
    }

    public boolean isAvailable() {
        return true;
    }

    public boolean hasLicenseFor(String model) {
        return true;
    }
}

