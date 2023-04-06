package Tests.SupplierTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import BusinessLayer.Suppliers.Product;
import BusinessLayer.Suppliers.ProductController;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.Suppliers.SupplierController;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ReservationControllerTest {
    ReservationController rc = ReservationController.getInstance();

    private void createProducts() {
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
    public void makeReservationInsufficientAmountTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(0, 1000);

        // check that the reservation is not possible
        assertThrows(SuppliersException.class, () -> rc.makeReservation(productToAmount));

        // check that no reservation is made
        assertThrows(SuppliersException.class, () -> rc.getReservationReceipt(0));
    }

    @Test
    public void makeReservationNoSuchProductTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(7, 1);

        // check that the reservation is not possible
        assertThrows(SuppliersException.class, () -> rc.makeReservation(productToAmount));

        // check that no reservation is made
        assertThrows(SuppliersException.class, () -> rc.getReservationReceipt(0));
    }

    @Test
    public void makeReservationNoSplitTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(0, 1); // should order from supplier 1

        try {
            rc.makeReservation(productToAmount);
            assertEquals(0, rc.getSupplierReservations(0).size());
            assertEquals(1, rc.getSupplierReservations(1).size());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void makeReservationSplitTest() {
        Map<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(0, 150); // should order 150 from supplier 1
        productToAmount.put(1, 80); // should order 50 from supplier 0 and 30 from supplier 1
        try {
            rc.makeReservation(productToAmount);
            assertEquals(1, rc.getSupplierReservations(0).size());
            assertEquals(1, rc.getSupplierReservations(1).size());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }
    }
}
