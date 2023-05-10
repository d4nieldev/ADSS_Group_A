package Tests.ReservationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.Suppliers.SupplierController;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ReservationControllerTest {
    ReservationController rc = ReservationController.getInstance();

    private void createProducts() throws SuppliersException {
        Product product0 = new Product(0, "Product 0", "Manufacturer 0");
        Product product1 = new Product(1, "Product 1", "Manufacturer 1");
        ProductController.getInstance().addProduct(product0);
        ProductController.getInstance().addProduct(product1);
    }

    private void createSupplier0() throws Exception {
        String name = "Supplier 0";
        String phone = "1234567890";
        String bankAccount = "1234567890";
        List<String> fields = List.of("Health", "Food");
        String paymentCondition = "Shotef-30";
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        List<String> contactNames = List.of("John Doe", "Jane Doe");
        List<String> contactPhones = List.of("1524973302", "1678420619");
        List<Integer> days = List.of(2, 5);

        SupplierController.getInstance().addFixedDaysSupplierBaseAgreement(name, phone, bankAccount, fields,
                paymentCondition, amountToDiscount, contactNames, contactPhones, days);

        int supplierId = 0;
        int productShopId = 0;
        int productSupplierId = 123;
        int stockAmount = 100;
        double basePrice = 100.0;
        amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, 0.1);
        amountToDiscount.put(70, 0.3);

        SupplierController.getInstance().addSupplierProductAgreement(supplierId, productShopId, productSupplierId,
                stockAmount, basePrice, amountToDiscount);

        productShopId = 1;
        productSupplierId = 142;
        stockAmount = 50;
        basePrice = 200.0;
        amountToDiscount = new TreeMap<>();
        amountToDiscount.put(10, 0.1);

        SupplierController.getInstance().addSupplierProductAgreement(supplierId, productShopId, productSupplierId,
                stockAmount, basePrice, amountToDiscount);

    }

    private void createSupplier1() throws Exception {
        String name = "Supplier 1";
        String phone = "1234567890";
        String bankAccount = "1234567890";
        List<String> fields = List.of("Health", "Food");
        String paymentCondition = "Shotef-30";
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        List<String> contactNames = List.of("John Doe", "Jane Doe");
        List<String> contactPhones = List.of("1524973302", "1678420619");
        List<Integer> days = List.of(2, 5);

        SupplierController.getInstance().addFixedDaysSupplierBaseAgreement(name, phone, bankAccount, fields,
                paymentCondition, amountToDiscount, contactNames, contactPhones, days);

        int supplierId = 1;
        int productShopId = 0;
        int productSupplierId = 148;
        int stockAmount = 200;
        double basePrice = 90.0;
        amountToDiscount = new TreeMap<>();
        amountToDiscount.put(70, 0.1);

        SupplierController.getInstance().addSupplierProductAgreement(supplierId, productShopId, productSupplierId,
                stockAmount, basePrice, amountToDiscount);

        productShopId = 1;
        productSupplierId = 2;
        stockAmount = 30;
        basePrice = 100;
        amountToDiscount = new TreeMap<>();

        SupplierController.getInstance().addSupplierProductAgreement(supplierId, productShopId, productSupplierId,
                stockAmount, basePrice, amountToDiscount);
    }

    private void clearAllData() {
        ProductController.getInstance().clearData();
        SupplierController.getInstance().clearData();
        ReservationController.getInstance().clearData();
    }

    /**
     * Creates the environment for the tests.
     * 2 products and 2 suppliers. Both produce both products.
     * 
     * for supplier 0 product 0:
     * stockAmount = 100, basePrice = 100.0
     * when buying 50 - 10% discount
     * when buying 70 - 30% discount
     * 
     * for supplier 0 product 1:
     * stockAmount = 50, basePrice = 200.0
     * when buying 10 - 10% discount
     * 
     * for supplier 1 product 0:
     * stockAmount = 200, basePrice = 90.0
     * when buying 70 - 10% discount
     * 
     * for supplier 1 product 1:
     * stockAmount = 30, basePrice = 100.0
     * 
     * @throws Exception
     */
    @Before
    public void setup() throws Exception {
        try {
            createProducts();
            createSupplier0();
            createSupplier1();
        } catch (Exception e) {
            System.out.println("Could not setup environment");
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        clearAllData();
    }

    @Test
    public void makeAutoReservationInsufficientAmountTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(0, 1000);

        // check that the reservation is not possible
        assertThrows(SuppliersException.class, () -> rc.makeDeficiencyReservation(productToAmount, "Ness Ziona"));

        // check that no reservation is made
        assertThrows(SuppliersException.class, () -> rc.getReservationReceipt(0));
    }

    @Test
    public void makeAutoReservationNoSuchProductTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(7, 1);

        // check that the reservation is not possible
        assertThrows(SuppliersException.class, () -> rc.makeDeficiencyReservation(productToAmount, "Ashkelon"));

        // check that no reservation is made
        assertThrows(SuppliersException.class, () -> rc.getReservationReceipt(0));
    }

    @Test
    public void makeAutoReservationNoSplitTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(0, 1); // should order from supplier 1

        try {
            rc.makeDeficiencyReservation(productToAmount, "Haifa");
            assertEquals(0, rc.getSupplierReservations(0).size());
            assertEquals(1, rc.getSupplierReservations(1).size());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void makeAutoReservationSplitTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(0, 150); // should order 150 from supplier 1
        productToAmount.put(1, 80); // should order 50 from supplier 0 and 30 from supplier 1
        try {
            rc.makeDeficiencyReservation(productToAmount, "Tel Aviv");
            assertEquals(1, rc.getSupplierReservations(0).size());
            assertEquals(1, rc.getSupplierReservations(1).size());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }
    }

    /*************************** MANUAL **********************************/
    @Test
    public void makeManualReservationInsufficientAmountTest() {
        Map<Integer, Map<Integer, Integer>> supplierToProductToAmount = new HashMap<>();
        Map<Integer, Integer> supplier0productToAmount = new HashMap<>();
        supplier0productToAmount.put(0, 150);
        supplierToProductToAmount.put(0, supplier0productToAmount);

        // check that the reservation is not possible
        assertThrows(SuppliersException.class, () -> rc.makeManualReservation(supplierToProductToAmount, "Ness Ziona"));

        // check that no reservation is made
        assertThrows(SuppliersException.class, () -> rc.getReservationReceipt(0));
    }

    @Test
    public void makeManualReservationNoSuchProductTest() {
        Map<Integer, Map<Integer, Integer>> supplierToProductToAmount = new HashMap<>();
        Map<Integer, Integer> supplier0productToAmount = new HashMap<>();
        supplier0productToAmount.put(7, 1);
        supplierToProductToAmount.put(0, supplier0productToAmount);

        // check that the reservation is not possible
        assertThrows(SuppliersException.class, () -> rc.makeManualReservation(supplierToProductToAmount, "Ashkelon"));

        // check that no reservation is made
        assertThrows(SuppliersException.class, () -> rc.getReservationReceipt(0));
    }

    @Test
    public void makeManualReservationNoSplitTest() {
        Map<Integer, Map<Integer, Integer>> supplierToProductToAmount = new HashMap<>();
        Map<Integer, Integer> supplier1productToAmount = new HashMap<>();
        supplier1productToAmount.put(0, 150);
        supplier1productToAmount.put(1, 20);
        supplierToProductToAmount.put(1, supplier1productToAmount);

        try {
            rc.makeManualReservation(supplierToProductToAmount, "Haifa");
            assertEquals(0, rc.getSupplierReservations(0).size());
            assertEquals(1, rc.getSupplierReservations(1).size());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void makeManualReservationSplitTest() {
        Map<Integer, Map<Integer, Integer>> supplierToProductToAmount = new HashMap<>();
        Map<Integer, Integer> supplier0productToAmount = new HashMap<>();
        supplier0productToAmount.put(0, 70);
        supplierToProductToAmount.put(0, supplier0productToAmount);
        Map<Integer, Integer> supplier1productToAmount = new HashMap<>();
        supplier1productToAmount.put(0, 150);
        supplier1productToAmount.put(1, 20);
        supplierToProductToAmount.put(1, supplier1productToAmount);

        try {
            rc.makeManualReservation(supplierToProductToAmount, "Tel Aviv");
            assertEquals(1, rc.getSupplierReservations(0).size());
            assertEquals(1, rc.getSupplierReservations(1).size());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }
    }
}
