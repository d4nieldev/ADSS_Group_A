package ServiceLayer;

import BussinessLayer.DriverFacade;

public class DriverService {

    private DriverFacade driverFacade = DriverFacade.getInstance();

    public String addDriver(int id, String name, String licence){
        driverFacade.newDriver(id, name , licence);
        return "New driver add to facade";
    }

    public String removeDriver(int id){
        driverFacade.removeDriver(id);
        return  "The driver" + id + "remove from the facade";
    }

    public String changeLicence(int id, String newLicence){
        if(!driverFacade.driverExist(id))
            return "this driver not exist";
        else {
            driverFacade.getDriverById(id).setLicense(newLicence);
            return "The licence of" + id + "change to" + newLicence;
        }
    }
}
