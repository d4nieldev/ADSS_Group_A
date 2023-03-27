package BussinessLayer;

import java.util.Date;
import java.util.List;

public class Trasnport {

    int id;
    Date date;
    String leavingTime;
    String truckNumber;
    String driverName;
    String source;
    List<Destination> destinationList;

    public Trasnport(int id, Date date, String leavingTime, String truckNumber, String driverName,String source, List<Destination> destinationList)
    {
        this.id = id;
        this.date = date;
        this.leavingTime = leavingTime;
        this.truckNumber = truckNumber;
        this.driverName = driverName;
        this.source = source;
        this.destinationList = destinationList ;
    }



}
