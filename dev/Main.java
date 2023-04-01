import BussinessLayer.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Main

{
    public static void main(String[] args)
    {

        DriverFacade D = makeSomeDrivers();
        TruckFacade T = makeSomeTrucks();

        List<Destination> dests = new ArrayList<Destination>();
        List<Destination> sources = new ArrayList<Destination>();
        makeSomeDestinations1(dests);
        makeSomeDestinations2(sources);

        List<Delivery> deliveries = createDeliveries(sources,dests);

        for (Delivery delivery : deliveries) {
            delivery.print();
        }



        
        
    }
    public static List<Delivery> createDeliveries(List<Destination> sources, List<Destination> dests) {
        List<Delivery> deliveries = new ArrayList<>();
        Random random = new Random();

        for (Destination source : sources) {
            for (Destination dest : dests) {
                if (source != dest) {
                    // create a random list of items and weight for the delivery
                    List<String> items = new ArrayList<>();
                    int numItems = random.nextInt(5) + 1;
                    int weight = 0;
                    for (int i = 0; i < numItems; i++) {
                        String item = "Item " + (i + 1);
                        items.add(item);
                        weight += random.nextInt(10) + 1;
                    }

                    // create a random status for the delivery
                    Status status = Status.PENDING;

                    // create the delivery and add it to the list of deliveries
                    Delivery delivery = new Delivery(source, dest, status, items, weight);
                    deliveries.add(delivery);
                }
            }
        }

        return deliveries;
    }



    public static void printDestinations(ArrayList<Destination> destList) {
        for (Destination d : destList) {
            System.out.println("Address: " + d.getAddress());
            System.out.println("Phone number: " + d.getPhoneNumber());
            System.out.println("Contact name: " + d.getContactName());
            System.out.println("Location: " + d.getLocation());
            System.out.println();
        }
    }

    private static void makeSomeDestinations1(List<Destination> dest)
    {

        dest.add(new Destination("tel aviv", "555-1234", "John Smith", Location.NORTH));
        dest.add(new Destination("raanana", "555-5678", "Jane Doe", Location.SOUTH));
        dest.add(new Destination("ashkelon", "555-9012", "Bob Johnson", Location.CENTER));

    }
    private static void makeSomeDestinations2(List<Destination> dest)
    {

        dest.add(new Destination("cola", "555-1234", "John Smith", Location.NORTH));
        dest.add(new Destination("osem", "555-5678", "Jane Doe", Location.SOUTH));
        dest.add(new Destination("tnuva", "555-9012", "Bob Johnson", Location.CENTER));

    }


    private static TruckFacade makeSomeTrucks()
    {
        Truck T1= new Truck("aaaa","a",200,1000);
        Truck T2= new Truck("bbbb","b",200,1000);
        Truck T3= new Truck("cccc","c",200,1000);
        Truck T4= new Truck("dddd","d",200,1000);
        Truck T5= new Truck("eeee","e",200,1000);
        TruckFacade T = new TruckFacade();
        T.addTruck(T1);
        T.addTruck(T2);
        T.addTruck(T3);
        T.addTruck(T4);
        T.addTruck(T5);
        return T;
    }

    private static DriverFacade makeSomeDrivers()
    {
        Driver D1= new Driver(1,"rotem","a");
        Driver D2= new Driver(2,"kfir","b");
        Driver D3= new Driver(3,"adi","c");
        Driver D4= new Driver(4,"messi","d");
        Driver D5= new Driver(5,"ronaldo","e");
        DriverFacade D = new DriverFacade();
        D.addDriver(D1);
        D.addDriver(D2);
        D.addDriver(D3);
        D.addDriver(D4);
        D.addDriver(D5);
        return D;
    }
}