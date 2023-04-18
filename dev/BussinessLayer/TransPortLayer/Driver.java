package BussinessLayer.TransPortLayer;

public class Driver {
    private int id;
    private String name;
    private String license;
    boolean isAvailable;

    public Driver(int id, String name, String licence) {
        this.id = id;
        this.name = name;
        this.license = licence;
        this.isAvailable = true;
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

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * return true if license of driver match to model truck
     *
     * @return
     */
    public boolean hasLicenseFor(String model) {
        return this.getLicense().equals(model);
    }
}

