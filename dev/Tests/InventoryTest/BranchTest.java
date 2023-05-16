package Tests.InventoryTest;

import BusinessLayer.Inventory.*;
import BusinessLayer.InveontorySuppliers.Branch;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.Suppliers.SupplierController;
import DataAccessLayer.DTOs.BranchDTO;
import DataAccessLayer.Repository;
import ServiceLayer.Suppliers.ReservationService;
import org.junit.*;

import static org.junit.Assert.*;
import BusinessLayer.Inventory.BranchController;
import org.junit.Before;
import org.junit.Test;
import BusinessLayer.InveontorySuppliers.ProductController;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;
public class BranchTest {

    private BranchController branchController;
    private ProductController productController;
    private ReservationController reservationController;
    private CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
//        this.branchController = BranchController.getInstance();
//        this.productController = ProductController.getInstance();
//        this.reservationController = ReservationController.getInstance();
//        this.categoryController = CategoryController.getInstance();
//        categoryController.addNewCategory("category1" );
//        Category category1 = categoryController.getCategoryById(0);
//        categoryController.addNewCategory("category2",category1 );
//        Category category2 = categoryController.getCategoryById(1);
//        categoryController.addNewCategory("category3",category1 );
//        Category category3 = categoryController.getCategoryById(2);
//        categoryController.addNewCategory("category4",category3 );
//        Category category4 = categoryController.getCategoryById(3);
//        branchController.addBranch(1,"testBranch" ,1);
//        productController.addProduct(1,"produc1","Manufacturer1",0);
//        productController.addProduct(2,"produc2","Manufacturer2",1);

    }
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
    /***
     * test that combine Inventory and suppliers - suppliers make reservation and send it to branch -> branch receive the reservation and updates its data.
     * @throws SQLException
     */
    @Test
    public void testGetAllProductBranches() throws SQLException {
        // Arrange
        HashMap<Integer,Integer> productToAmount = new HashMap<>();
        productToAmount.put(1,10);
        productToAmount.put(2,30);
        // Act
        reservationController.makeDeficiencyReservation(productToAmount,1 );
        HashMap<Integer, ProductBranch> allProducts = branchController.getBranchById(1).getAllProductBranches();
        // Assert
        assertTrue(allProducts.size() == 2);
    }

    @After
    public void tearDown() {
        clearAllData();
    }
//    @Test
//    public void testMakeDeficiencyReservation(){
//        BranchDTO branchDTO =
//        branchController.getBranchById(1).
//    }
}