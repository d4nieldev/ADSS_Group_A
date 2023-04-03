package serviceLayer;

import BussinessLayer.DriverFacade;
import BussinessLayer.TransportFacade;

public class DriverService {

    DriverFacade driverFacade = DriverFacade.getInstance();

    public String addDriver(int id, String name, String licence){
        driverFacade.newDriver(id, name , licence);
        return "New driver add to facade";
    }

    public String removeDriver(int id){
        driverFacade.removeDriver(id);
        return  "The driver" + id + "remove from the facade";
    }

    public String changeLicence(int id, String newLicence){
        driverFacade.getDriverById(id).setLicense(newLicence);
        return "The licence of" + id + "change to" + newLicence;
    }
}
