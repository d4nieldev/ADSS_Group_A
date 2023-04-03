package serviceLayer;

import BussinessLayer.Driver;
import BussinessLayer.DriverFacade;
import BussinessLayer.TransportFacade;

import java.util.Date;

/*public class TransportService {
    TransportFacade transportFacade = new TransportFacade();
    DriverFacade driverFacade = new DriverFacade();

    public String createTransport(int id, Date date, String leavingTime, String truckNumber, String driverName, int driverId, String source){
       if(!driverFacade.getDriverById(driverId))
           return "driver not exist";
        Driver driver = driverFacade.getDriverById(driverId);
        if(!driver.hasLicenseFor(truckNumber))
            return  "not ilegall";
        transportFacade.createTransport(id, leavingTime,  truckNumber, driverName, driverId, source);
        return true;



    }


}*/

