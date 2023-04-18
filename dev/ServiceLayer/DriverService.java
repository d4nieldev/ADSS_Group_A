package ServiceLayer;

import BussinessLayer.DriverFacade;

public class DriverService {

    private DriverFacade driverFacade = DriverFacade.getInstance();

    /**
     * create new driver and return message
     *
     * @return
     */
    public String addDriver(int id, String name, String licence){
        driverFacade.newDriver(id, name , licence);
        return "New driver add to facade";
    }

    /**
     * remove driver from facade and return message
     *
     * @return
     */
    public String removeDriver(int id){
        driverFacade.removeDriver(id);
        return  "The driver" + id + "remove from the facade";
    }

    /**
     * set license of driver
     *
     * @return
     */
    public String changeLicence(int id, String newLicence){
        if(!driverFacade.driverExist(id))
            return "this driver not exist";
        else {
            driverFacade.getDriverById(id).setLicense(newLicence);
            return "The licence of" + id + "change to" + newLicence;
        }
    }


    /**
     * set name of driver by id
     *
     * @return
     */
    public String changeName(int id, String news){
        if (!driverFacade.driverExist(id))
            return "there no driver with this id";
        else {
            driverFacade.getDriverById(id).setName(news);
            return "success";
        }
    }

    /**
     * set id of driver
     *
     * @return
     */
    public String changeName(int id, int news){
        if (!driverFacade.driverExist(id))
            return "there no driver with this id";
        else {
            driverFacade.getDriverById(id).setId(news);
            return "success";
        }
    }

    /**
     * set status of available of driver by id
     *
     * @return
     */
    public String setAvailable(int id, boolean news){
        if (!driverFacade.driverExist(id))
            return "there no driver with this id";
        else {
            driverFacade.getDriverById(id).setAvailable(news);
            return "success";
        }
    }

    /**
     * print kind license of driver by id
     *
     * @return
     */
    public String printDriverLicense(int id){
        if (!driverFacade.driverExist(id))
            return "there no driver with this id";
        return driverFacade.getDriverById(id).getLicense();
    }


}
