package ServiceLayer;

import BussinessLayer.*;

import java.util.List;

public class TransportService {
    public TransportFacade transportFacade = TransportFacade.getInstance();
    public DriverFacade driverFacade  = DriverFacade.getInstance();


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

    public String changeDate(int id, String date){
        transportFacade.getTransport(id).setDate(date);
        return "The date of" + id + "change to" + date;
    }

    public String changeleavingTime(int id, String leavingTime){
        transportFacade.getTransport(id).setLeavingTime(leavingTime);
        return "The leavingTime of" + id + "change to" + leavingTime;
    }
    public String changetruckNumber(int id, String truckNumber){
        transportFacade.getTransport(id).setTruckNumber(truckNumber);
        return "The truckNumber of" + id + "change to" + truckNumber;
    }
    public String changedriverName(int id, String driverName){
        transportFacade.getTransport(id).setDriverName(driverName);
        return "The driverName of" + id + "change to" + driverName;
    }
    public String changeDriverId(int id, int driverId){
        transportFacade.getTransport(id).setDriverId(driverId);
        return "The driverId of" + id + "change to" + driverId;
    }
    public String changeSource(int id, String source){
        transportFacade.getTransport(id).setSource(source);
        return "The source of" + id + "change to" + source;
    }

    public String changeDestinationList(int id, List<Destination> destinationList){
        transportFacade.getTransport(id).setDestinationList(destinationList);
        return "The destinationList of" + id + "change to" + destinationList;
    }
    public String changeDeliveryList(int id, List<Delivery> deliveryList){
        transportFacade.getTransport(id).setDeliveryList(deliveryList);
        return "The deliveryList of" + id + "change to" + deliveryList;
    }
    public String changeTruckWeightNeto(int id, int truckWeightNeto){
        transportFacade.getTransport(id).setTruckWeightNeto(truckWeightNeto);
        return "The truckWeightNeto of" + id + "change to" + truckWeightNeto;
    }
    public String changeTruckWeightMax(int id, int truckWeightMax){
        transportFacade.getTransport(id).setTruckWeightMax(truckWeightMax);
        return "The truckWeightMax of" + id + "change to" + truckWeightMax;
    }

    public List<Delivery> createDeliveries(List<Destination> sources, List<Destination> dests) {

        return transportFacade.createDeliveries(sources, dests);
    }

    public Destination addDestination(String address, String phoneNumber, String contactName, Location location,DestinationType destinationType){
        return transportFacade.addDestination(address, phoneNumber, contactName, location, destinationType);
    }




}

