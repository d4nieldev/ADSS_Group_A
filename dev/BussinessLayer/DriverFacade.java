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

    /**
     * singleton (design)
     */
    private static DriverFacade instance = null;
    public static DriverFacade getInstance()
    {
        if(instance==null)
            instance = new DriverFacade();
        return instance;
    }

    public void newDriver(int id, String name, String licence){
        Driver driver = new Driver(id, name, licence);
        addDriver(driver);
    }

    /**
     * add driver to facade
     */
    public void addDriver(Driver driver) {
        drivers.put(driver.getId(), driver);
    }

    /**
     * remove driver from facade
     */
    public void removeDriver(int id) {
        drivers.remove(id);
    }

    public Driver getDriverById(int id) {
        return drivers.get(id);
    }

    /**
     * return true if driver exist in facade
     *
     * @return
     */
    public Boolean driverExist(int id){
        if(this.drivers.containsKey(id))
            return true;
        return false;
    }



    /**
     * return a list of available drivers in facade
     *
     * @return
     */
    public List<Driver> getAvailableDrivers()
    {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }
}

