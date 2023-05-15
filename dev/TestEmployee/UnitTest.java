package TestEmployee;

import BussinessLayer.TransPortLayer.Destination;
import BussinessLayer.TransPortLayer.Transport;
import BussinessLayer.TransPortLayer.TransportFacade;

public class UnitTest {

    //private DriverFacade driverFacade;
    

    @BeforeEach
    public void setUp() {
        //driverFacade = new DriverFacade();
       // driverFacade.getDrivers().put(1, new Driver(1, "rotem", "ABC123"));
        //driverFacade.getDrivers().put(2, new Driver(2, "kfir", "DEF456"));
    }
    /* 

    @Test
    public void testDriverExist() {
        boolean result = driverFacade.driverExist(1);
        Assertions.assertTrue(result);

        result = driverFacade.driverExist(3);
        Assertions.assertFalse(result);
    }

    @Test
    void testGetAvailableDrivers() {
        // create some drivers
        Driver driver1 = new Driver(1, "John", "ABC123");
        Driver driver2 = new Driver(2, "Rotem", "DEF456");
        Driver driver3 = new Driver(3, "Ben", "GHI789");

        // set some drivers as available
        driver1.setAvailable(true);
        driver2.setAvailable(false);
        driver3.setAvailable(true);

        // create a DriverFacade instance and add the drivers
        DriverFacade facade = new DriverFacade();
        facade.addDriver(driver1);
        facade.addDriver(driver2);
        facade.addDriver(driver3);

        // test the getAvailableDrivers method
        List<Driver> availableDrivers = facade.getAvailableDrivers();
        assertEquals(2, availableDrivers.size());
        assertTrue(availableDrivers.contains(driver1));
        assertFalse(availableDrivers.contains(driver2));
        assertTrue(availableDrivers.contains(driver3));
    }

    @Test
    public void testDriverExistWithEmptyDrivers() {
        driverFacade.getDrivers().clear();
        boolean result = driverFacade.driverExist(1);
        Assertions.assertFalse(result);
    }

    @Test
    void testGetAvailableDriversWithNoAvailableDrivers() {
        // create some drivers
        Driver driver1 = new Driver(1, "John", "ABC123");
        Driver driver2 = new Driver(2, "Rotem", "DEF456");
        Driver driver3 = new Driver(3, "Ben", "GHI789");

        // set all drivers as unavailable
        driver1.setAvailable(false);
        driver2.setAvailable(false);
        driver3.setAvailable(false);

        // create a DriverFacade instance and add the drivers
        DriverFacade facade = new DriverFacade();
        facade.addDriver(driver1);
        facade.addDriver(driver2);
        facade.addDriver(driver3);

        // test the getAvailableDrivers method
        List<Driver> availableDrivers = facade.getAvailableDrivers();
        assertEquals(0, availableDrivers.size());
    }
    */

    @Test
    void testDeliveryPrint() {
        List<String> items = new ArrayList<>();
        items.add("Item 1");
        items.add("Item 2");
        Destination source = new Destination("123 St", "555-1234", "John", Location.SOUTH, DestinationType.SOURCE);
        Destination dest = new Destination("456 St", "555-5678", "Jane", Location.SOUTH, DestinationType.DESTINATION);
        Delivery delivery = new Delivery(1, source, dest, Status.PENDING, items);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        delivery.deliveryPrint();
        String expectedOutput = "Delivery from 123 St to 456 St\nStatus: PENDING\nItems: [Item 1, Item 2]\n------------------------------\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testDeliveryPrintWithEmptyItems() {
        List<String> items = new ArrayList<>();
        Destination source = new Destination("123 Main St", "555-1234", "John Smith", Location.SOUTH, DestinationType.SOURCE);
        Destination dest = new Destination("456 Market St", "555-5678", "Jane Doe", Location.SOUTH, DestinationType.DESTINATION);
        Delivery delivery = new Delivery(1, source, dest, Status.PENDING, items);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        delivery.deliveryPrint();
        String expectedOutput = "Delivery from 123 Main St to 456 Market St\nStatus: PENDING\nItems: []\n------------------------------\n";
        assertEquals(expectedOutput, outContent.toString());
    }
    @Test
    public void testRemoveTransport() {
        // Create a new transport and add it to the facade
        TransportFacade transportFacade = TransportFacade.getInstance();
        Transport transport = new Transport(1, "2023-04-01", "12:00", "TR-123", "John Doe", 1, "Factory A",
                new ArrayList<Destination>(), new ArrayList<Delivery>(), 5000, 10000);
        transportFacade.addTransport(transport.getId(),transport);

        // Ensure the transport was added
        assertTrue(transportFacade.getTransportMap().containsKey(1));

        // Remove the transport
        transportFacade.removeTransport(1);

        // Ensure the transport was removed
        assertFalse(transportFacade.getTransportMap().containsKey(1));
    }

    @Test
    public void testChangingOrderValidInput() {
        // Create a transport object with destinations
        Transport transport = new Transport(1, "2023-04-01", "10:00", "TRUCK001", "John Doe", 1, "Los Angeles",
                Arrays.asList(
                        new Destination("San Francisco", "123-456-7890", "Jane Smith", Location.CENTER, DestinationType.SOURCE),
                        new Destination("Sacramento", "123-456-7890", "Jane Smith", Location.CENTER, DestinationType.SOURCE),
                        new Destination("San Diego", "123-456-7890", "Jane Smith", Location.CENTER, DestinationType.SOURCE)
                ),
                new ArrayList<Delivery>(), 5000, 10000);

        // Redirect system input stream to provide input to the scanner
        ByteArrayInputStream in = new ByteArrayInputStream("1,3,2\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Call the changingOrder method
        TransportFacade.getInstance().changingOrder(transport);

        // Verify that the order of destinations has been updated correctly
        List<Destination> expected = Arrays.asList(
                new Destination("San Francisco", "123-456-7890", "Jane Smith", Location.CENTER, DestinationType.SOURCE),
                new Destination("Sacramento", "123-456-7890", "Jane Smith", Location.CENTER, DestinationType.SOURCE),
                new Destination("San Diego", "123-456-7890", "Jane Smith", Location.CENTER, DestinationType.SOURCE)
        );
        List<Destination> actual = transport.getDestinationList();
        assertEquals(expected, actual);
    }

    @Test
    public void testChangingOrderWithInvalidInput() {
        // Create a transport with a list of destinations
        List<Destination> destinationList = new ArrayList<>();
        destinationList.add(new Destination("123 Main St.", "555-1234", "John Smith", Location.CENTER, DestinationType.SOURCE));
        destinationList.add(new Destination("456 Elm St.", "555-5678", "Jane Doe", Location.CENTER, DestinationType.SOURCE));
        Transport transport = new Transport(1, "2023-04-01", "10:00", "ABC123", "John Doe", 1, "789 Oak St.", destinationList, new ArrayList<Delivery>(), 1000, 5000);

        // Create a mock Scanner object with invalid input
        Scanner scanner = new Scanner("1, 3, 5, abc\n");

        // Set the System.in to use the mock Scanner object
        InputStream inputStream = System.in;
        System.setIn(inputStream);

        // Call the changingOrder method with the mock Scanner object
        TransportFacade transportFacade = TransportFacade.getInstance();
        transportFacade.changingOrder(transport);

        // Check that the transport's list of destinations has not been changed
        assertEquals(destinationList, transport.getDestinationList());
    }









}
