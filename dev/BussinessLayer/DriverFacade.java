package BussinessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverFacade {
    private Map<Integer, Driver> drivers;

    public DriverFacade() {
        drivers = new HashMap<>();
    }
    private static DriverFacade instance = null;
    public static DriverFacade getInstance()
    {
        if(instance==null)
            instance = new DriverFacade();
        return instance;
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

    public Boolean driverExist(int id){
        if(this.drivers.containsKey(id))
            return true;
        return false;
    }

    public Driver getDriverByLicence(String licence) {
        for (Driver driver : drivers.values()) {
            if (driver.getLicense().equals(licence)) {
                return driver;
            }
        }
        return null;
    }

    public boolean isDriverQualified(Driver driver, Truck truck) {
        return driver.isQualified(truck);
    }

    public List<Driver> getAvailableDrivers()
    {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable()) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }
}

