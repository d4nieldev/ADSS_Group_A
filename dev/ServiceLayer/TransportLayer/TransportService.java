package ServiceLayer.TransportLayer;

import BussinessLayer.EmployeeTransportFacade;
import BussinessLayer.TransPortLayer.Delivery;
import BussinessLayer.TransPortLayer.Destination;
import BussinessLayer.TransPortLayer.DestinationType;
import BussinessLayer.TransPortLayer.Location;
import BussinessLayer.TransPortLayer.TransportFacade;
import BussinessLayer.TransPortLayer.TruckFacade;

import java.time.LocalDate;
import java.util.List;

public class TransportService {
    private TransportFacade transportFacade = TransportFacade.getInstance();
    private TruckFacade truckFacade = TruckFacade.getInstance();
    private EmployeeTransportFacade employeeTransportFacade;

    public TransportService(EmployeeTransportFacade employeeTransportFacade) {
        this.employeeTransportFacade=employeeTransportFacade;
        transportFacade.setEmployeeFacade(employeeTransportFacade);
    }



    /*
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

    */

    /**
     * remove transport from facade and return message
     *
     * @return
     */
    public String removeTransport(int id){
        transportFacade.removeTransport(id);
        return "The transport" + id + "remove from Facade";
    }

    /**
     * change date of transport specific
     *
     * @return
     */
    public String changeDate(int id, LocalDate date){
        transportFacade.getTransport(id).setDate(date);
        return "The date of" + id + "change to" + date;
    }


    /**
     * change leaving Time of transport specific
     *
     * @return
     */
    public String changeLeavingTime(int id, String leavingTime){
        transportFacade.getTransport(id).setLeavingTime(leavingTime);
        return "The leavingTime of" + id + "change to" + leavingTime;
    }


    /**
     * change truck number of transport specific
     *
     * @return
     */
    public String changeTruckNumber(int id, String truckNumber){
        transportFacade.getTransport(id).setTruckNumber(truckNumber);
        return "The truckNumber of" + id + "change to" + truckNumber;
    }

    /**
     * change driver name of transport specific
     *
     * @return
     */
    public String changeDriverName(int id, String driverName){
        transportFacade.getTransport(id).setDriverName(driverName);
        return "The driverName of" + id + "change to" + driverName;
    }


    /**
     * change driver id of transport specific
     *
     * @return
     */
    public String changeDriverId(int id, int driverId){
        transportFacade.getTransport(id).setDriverId(driverId);
        return "The driverId of" + id + "change to" + driverId;
    }

    /**
     * change source of transport specific
     *
     * @return
     */
    public String changeSource(int id, String source){
        transportFacade.getTransport(id).setSource(source);
        return "The source of" + id + "change to" + source;
    }

    /**
     * change destinationList of transport specific
     *
     * @return
     */
    public String changeDestinationList(int id, List<Destination> destinationList){
        transportFacade.getTransport(id).setDestinationList(destinationList);
        return "The destinationList of" + id + "change to" + destinationList;
    }

    /**
     * change delivery list of transport specific
     *
     * @return
     */
    public String changeDeliveryList(int id, List<Delivery> deliveryList){
        transportFacade.getTransport(id).setDeliveryList(deliveryList);
        return "The deliveryList of" + id + "change to" + deliveryList;
    }

    /**
     * change truck weight neto of transport specific
     *
     * @return
     */
    public String changeTruckWeightNeto(int id, int truckWeightNeto){
        transportFacade.getTransport(id).setTruckWeightNeto(truckWeightNeto);
        return "The truckWeightNeto of" + id + "change to" + truckWeightNeto;
    }

    /**
     * change truck weight max of transport specific
     *
     * @return
     */
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




    public void runTheTransports(){
        transportFacade.runTheTransports();
    }

    /**
     * Print document Transport or return that the transport not exist if  the id don't match to any transport
     *
     * @return
     */
    public String printDocumentTransport(Integer id){
        if(!transportFacade.hasTranspot(id))
            return "this transport not exist";
        transportFacade.getTransport(id).printTransportDetails();
        return " ";
    }


    public void createTransports(List<Delivery> deliveries) {
        employeeTransportFacade.createTransports(deliveries);
    }
}

