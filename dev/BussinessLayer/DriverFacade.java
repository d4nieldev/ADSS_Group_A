package BussinessLayer;

import java.util.HashMap;
import java.util.Map;

public class DriverFacade {
    private Map<Integer, Driver> drivers;

    public DriverFacade() {
        drivers = new HashMap<>();
    }

    public void addDriver(Driver driver) {
        drivers.put(driver.getId(), driver);
    }

    public void removeDriver(int id) {
        drivers.remove(id);
    }

    public Driver getDriverById(int id) {
        return drivers.get(id);
    }

    public Driver getDriverByLicence(String licence) {
        for (Driver driver : drivers.values()) {
            if (driver.getLicence().equals(licence)) {
                return driver;
            }
        }
        return null;
    }

    public boolean isDriverQualified(Driver driver, Truck truck) {
        return driver.isQualified(truck);
    }
}

