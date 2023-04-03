package serviceLayer;

import BussinessLayer.*;

import java.util.Date;
import java.util.List;

public class TransportService {
    TransportFacade transportFacade = TransportFacade.getInstance();
    DriverFacade driverFacade  = DriverFacade.getInstance();


    //we need check the license of driver
    public String createTransport(String date, String leavingTime, String truckNumber, String driverName, int driverId, String source,
                                  List<Destination> destinationList, List<Delivery> deliveryList, int truckWeightNeto, int truckWeightMax){
       if(!driverFacade.driverExist(driverId))
           return "driver not exist";
        Driver driver = driverFacade.getDriverById(driverId);
        if(!driver.hasLicenseFor(truckNumber))
            return  "The driver does not have a license to drive a truck";
        transportFacade.createTransport(date, leavingTime, truckNumber, driverName, driverId, source,
                destinationList, deliveryList, truckWeightNeto, truckWeightMax);
        return "Transport was created successfully";



    }


}

