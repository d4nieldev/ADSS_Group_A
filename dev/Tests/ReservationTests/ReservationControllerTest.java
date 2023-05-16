package Tests.ReservationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import BusinessLayer.Inventory.BranchController;
import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.InveontorySuppliers.Reservation;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.Suppliers.SupplierController;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.Repository;

public class ReservationControllerTest {
    ReservationController rc = ReservationController.getInstance();

    private void createProducts() throws Exception {
        BranchController.getInstance().addBranch(0, "Ashkelon", 80);
        CategoryController.getInstance().addNewCategory("Cat1");
        CategoryController.getInstance().addNewCategory("Cat2");
        ProductController.getInstance().addProduct(0, "Product 0", "Manufacturer 0", 0);
        ProductController.getInstance().addProduct(1, "Product 1", "Manufacturer 1", 1);
        // TODO: how we add product branch??
    }

    private void createSupplier0() throws Exception {
        String name = "Supplier 0";
        String phone = "1234567890";
        String bankAccount = "1234567890";
        List<String> fields = createList("Health", "Food");
        String paymentCondition = "net 30 EOM";
        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        List<String> contactNames = createList("John Doe", "Jane Doe");
        List<String> contactPhones = createList("1524973302", "1678420619");
        Integer maxSupplyDays = 1;

        SupplierController.getInstance().addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields,
                paymentCondition, amountToDiscount, contactNames, contactPhones, maxSupplyDays);

        int supplierId = 0;
        int productShopId = 0;
        int productSupplierId = 123;
        int stockAmount = 100;
        double basePrice = 100.0;
        amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, "0.1%");
        amountToDiscount.put(70, "0.3%");

        SupplierController.getInstance().addSupplierProductAgreement(supplierId, productShopId, productSupplierId,
                stockAmount, basePrice, amountToDiscount);

        productShopId = 1;
        productSupplierId = 142;
        stockAmount = 50;
        basePrice = 200.0;
        amountToDiscount = new TreeMap<>();
        amountToDiscount.put(10, "0.1%");

        SupplierController.getInstance().addSupplierProductAgreement(supplierId, productShopId, productSupplierId,
                stockAmount, basePrice, amountToDiscount);

    }

    private int getDay() {
        switch (LocalDate.now().getDayOfWeek()) {
            case SUNDAY:
                return 1;
            case MONDAY:
                return 2;
            case TUESDAY:
                return 3;
            case WEDNESDAY:
                return 4;
            case THURSDAY:
                return 5;
            case FRIDAY:
                return 6;
            case SATURDAY:
                return 7;
            default:
                return 0;
        }
    }

    private void createSupplier1() throws Exception {
        String name = "Supplier 1";
        String phone = "1234567890";
        String bankAccount = "1234567890";
        List<String> fields = createList("Health", "Food");
        String paymentCondition = "net 30 EOM";
        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        List<String> contactNames = createList("John Doe", "Jane Doe");
        List<String> contactPhones = createList("1524973302", "1678420619");
        List<Integer> days = createList((getDay() + 3) % 7); // make sure this supplier is slower

        SupplierController.getInstance().addFixedDaysSupplierBaseAgreement(name, phone, bankAccount, fields,
                paymentCondition, amountToDiscount, contactNames, contactPhones, days);

        int supplierId = 1;
        int productShopId = 0;
        int productSupplierId = 148;
        int stockAmount = 200;
        double basePrice = 90.0;
        amountToDiscount = new TreeMap<>();
        amountToDiscount.put(70, "0.1%");

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

    private static <T> List<T> createList(T... args) {
        List<T> lst = new ArrayList<>();
        for (T item : args) {
            lst.add(item);
        }
        return lst;
    }

    private void clearAllData() {
        ProductController.getInstance().clearData();
        SupplierController.getInstance().clearData();
        ReservationController.getInstance().clearData();
        Repository.getInstance().DELETE_ALL_DATA();
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
        assertThrows(SuppliersException.class, () -> rc.makeDeficiencyReservation(productToAmount, 0));

        // check that no reservation is made
        assertThrows(SuppliersException.class, () -> rc.getReservationReceipt(0));

    }

    @Test
    public void makeAutoReservationNoSuchProductTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(7, 1);

        // check that the reservation is not possible
        assertThrows(SuppliersException.class, () -> rc.makeDeficiencyReservation(productToAmount, 0));

        // check that no reservation is made
        assertThrows(SuppliersException.class, () -> rc.getReservationReceipt(0));
    }

    @Test
    public void makeAutoReservationNoSplitTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(0, 1); // should order from supplier 1 (he is faster)
        try {
            rc.makeDeficiencyReservation(productToAmount, 0);
            assertEquals(0, rc.getSupplierReservations(0).size());
            assertEquals(1, rc.getSupplierReservations(1).size());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void makeAutoReservationSplitTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(0, 150); // should order 150 from supplier 1 (because he is faster)
        productToAmount.put(1, 80); // should order 50 from supplier 0 and 30 from supplier 1 (this is the only
                                    // option)
        try {
            rc.makeDeficiencyReservation(productToAmount, 0);
            assertEquals(1, rc.getSupplierReservations(0).size());
            Reservation r0 = rc.getSupplierReservations(0).get(0);
            assertEquals(50, r0.getTotalAmount());
            assertEquals(1, rc.getSupplierReservations(1).size());
            Reservation r1 = rc.getSupplierReservations(1).get(0);
            assertEquals(180, r1.getTotalAmount());
        } catch (Exception e) {
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
        assertThrows(SuppliersException.class, () -> rc.makeManualReservation(supplierToProductToAmount, 0));

        // check that no reservation is made
        assertThrows(SuppliersException.class, () -> rc.getReservationReceipt(0));
    }

    @Test
    public void makeManualReservationNoSuchProductTest() {
        Map<Integer, Map<Integer, Integer>> supplierToProductToAmount = new HashMap<>();
        Map<Integer, Integer> supplier0productToAmount = new HashMap<>();
        supplier0productToAmount.put(7, 1);
        supplierToProductToAmount.put(0, supplier0productToAmount);
        try {
            rc.makeManualReservation(supplierToProductToAmount, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // check that the reservation is not possible
        // assertThrows(SuppliersException.class, () -> );

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
            rc.makeManualReservation(supplierToProductToAmount, 0);
            assertEquals(0, rc.getSupplierReservations(0).size());
            assertEquals(1, rc.getSupplierReservations(1).size());
        } catch (Exception e) {
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
            rc.makeManualReservation(supplierToProductToAmount, 0);
            assertEquals(1, rc.getSupplierReservations(0).size());
            assertEquals(1, rc.getSupplierReservations(1).size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
