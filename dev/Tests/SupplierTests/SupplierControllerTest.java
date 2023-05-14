package Tests.SupplierTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.InveontorySuppliers.ReceiptItem;
import BusinessLayer.Suppliers.Contact;
import BusinessLayer.Suppliers.ProductAgreement;
import BusinessLayer.Suppliers.Supplier;
import BusinessLayer.Suppliers.SupplierController;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DTOs.ReceiptItemDTO;

public class SupplierControllerTest {

    SupplierController sc = SupplierController.getInstance();

    @Before
    public void setup() {
        try {
            SupplierController.getInstance().init();
            // ProductController.getInstance().init();
            createSupplier0();
            createProduct();
        } catch (Exception e) {
            System.out.println("Could not setup environment");
            e.printStackTrace();
        }
    }

    @After
    public void clearController() {
        ProductController.getInstance().clearData();
        SupplierController.getInstance().clearData();
    }

    private static <T> List<T> createList(T... args) {
        List<T> lst = new ArrayList<>();
        for (T item : args) {
            lst.add(item);
        }
        return lst;
    }

    /**
     * Creation of an 'OnOrder' type supplier for testing purposes
     * 
     * @throws Exception
     */
    public static void createSupplier0() throws SuppliersException, SQLException {
        String name = "FastAndBest ";
        String phone = "0507164509";
        String bankAccount = "12-128-148258";
        List<String> fields = createList("Tech", "Cleaning");
        String paymentCondition = "net 30 EOM";
        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(100, "0.025%");
        amountToDiscount.put(500, "0.03%");

        List<String> contactNames = createList("Dana Grinberg", "Roni Katz");
        List<String> contactPhones = createList("0525948325", "0535669897");
        int maxSupplyDays = 4;

        SupplierController.getInstance().addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields,
                paymentCondition, amountToDiscount, contactNames, contactPhones, maxSupplyDays);
    }

    public void createProduct() throws SuppliersException, SQLException {
        CategoryController.getInstance().addNewCategory("Diary");
        ProductController.getInstance().addProduct(203, "Tara Milk 1.5L", "Tara", 0);
    }

    @Test
    public void getExistingSupplierByIdTest() {
        // test for existing supplier id in system.
        try {
            Supplier s = sc.getSupplierById(0);
            assertEquals(0, s.getId());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getNotExistingSupplierByIdTest() {
        // test for Non existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.getSupplierById(5));

        // check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 5 + " in the system.", e.getMessage());
    }

    @Test
    public void getNegativeSupplierByIdTest() {
        // test for negative supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.getSupplierById(-1));

        // check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: Supplier with negative id is illegal in the system.", e.getMessage());
    }

    @Test
    public void deleteExistingSupplierTest() throws SQLException {
        // test for existing supplier id in system.
        try {
            sc.deleteSupplier(0);
            Exception e = assertThrows(SuppliersException.class, () -> sc.getSupplierById(0));

            // check that error message is correct.
            assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 0 + " in the system.", e.getMessage());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteNotExistingSupplierTest() {
        // test for not existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.getSupplierById(7));

        // check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 7 + " in the system.", e.getMessage());
    }

    @Test
    public void addFixedDaysSupplierBaseAgreementTest() throws SQLException {
        // to validate creation we try to get this supplier and check if his id, name
        // and phone are correct.

        String name = "AllYouNeed";
        String phone = "0507164588";
        String bankAccount = "09-319-158988";
        List<String> fields = createList("Meat", "Sweet drinks");
        String paymentCondition = "net 60 EOM";
        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, "0.015%");
        amountToDiscount.put(90, "0.02%");
        List<String> contactNames = createList("Kevin Monk");
        List<String> contactPhones = createList("0525869525");
        List<Integer> days = createList(2, 4);
        try {
            SupplierController.getInstance().addFixedDaysSupplierBaseAgreement(name, phone, bankAccount, fields,
                    paymentCondition,
                    amountToDiscount, contactNames, contactPhones, days);
            Supplier s = sc.getSupplierById(1);
            assertEquals(1, s.getId());
            assertEquals("AllYouNeed", s.getName());
            assertEquals("0507164588", s.getPhone());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addOnOrderSupplierBaseAgreementTest() throws SQLException {
        // to validate creation we try to get this supplier and check if his id, name
        // and phone are correct.
        String name = "AllYouNeed";
        String phone = "0507164588";
        String bankAccount = "09-319-158988";
        List<String> fields = createList("Meat", "Sweet drinks");
        String paymentCondition = "net 45 EOM";
        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, "0.015%");
        amountToDiscount.put(90, "0.02%");
        List<String> contactNames = createList("Kevin Monk");
        List<String> contactPhones = createList("0525869525");
        int maxSupplyDays = 7;
        try {
            SupplierController.getInstance().addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields,
                    paymentCondition,
                    amountToDiscount, contactNames, contactPhones, maxSupplyDays);
            Supplier s = sc.getSupplierById(1);
            assertEquals(1, s.getId());
            assertEquals("AllYouNeed", s.getName());
            assertEquals("0507164588", s.getPhone());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addSelfPickupSupplierBaseAgreementTest() throws SQLException {
        // to validate creation we try to get this supplier and check if his id, name
        // and phone are correct.
        String name = "AllYouNeed";
        String phone = "0507164588";
        String bankAccount = "09-319-158988";
        List<String> fields = createList("Meat", "Sweet drinks");
        String paymentCondition = "net 45 EOM";
        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, "0.015%");
        amountToDiscount.put(90, "0.02%");
        List<String> contactNames = createList("Kevin Monk");
        List<String> contactPhones = createList("0525869525");
        String address = "Shilo 4, Ashkelon";
        Integer maxPreparationDays = 3;
        try {
            SupplierController.getInstance().addSelfPickupSupplierBaseAgreement(name, phone, bankAccount, fields,
                    paymentCondition,
                    amountToDiscount, contactNames, contactPhones, address, maxPreparationDays);
            Supplier s = sc.getSupplierById(1);
            assertEquals(1, s.getId());
            assertEquals("AllYouNeed", s.getName());
            assertEquals("0507164588", s.getPhone());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setSupplierNameTest() {
        try {
            sc.setSupplierName(0, "Test");
            assertEquals("Test", sc.getSupplierById(0).getName());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }

        // test for Non existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.setSupplierName(5, "Test"));

        // check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 5 + " in the system.", e.getMessage());

    }

    @Test
    public void setSupplierBankAccountTest() {
        try {
            sc.setSupplierBankAccount(0, "12-4558-6996");
            assertEquals("12-4558-6996", sc.getSupplierById(0).getBankAcc());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }

        // test for Non existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.setSupplierBankAccount(5, "12-4558-6996"));

        // check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 5 + " in the system.", e.getMessage());
    }

    @Test
    public void tryTodeleteOfficeContact() {
        assertThrows(SuppliersException.class, () -> sc.deleteSupplierContact(0, "Office", "0507164509"));
    }

    @Test
    public void addSupplierProductAgreementTest() {
        // we check that after adding a product agreement, our supplier has the product
        // agreement (by checking the prouct's id in the agreement)
        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, "0.03%");
        amountToDiscount.put(200, "0.045%");
        try {
            sc.addSupplierProductAgreement(0, 203, 33, 500, 24.9, amountToDiscount);
            assertEquals(203,
                    ProductController.getInstance().getProductAgreementsOfSupplier(0).get(0).getProductId());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        // test for Non existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class,
                () -> sc.addSupplierProductAgreement(5, 203, 33, 500, 24.9, amountToDiscount));
        // check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 5 + " in the system.", e.getMessage());

        // test for Negative stock amount
        e = assertThrows(SuppliersException.class,
                () -> sc.addSupplierProductAgreement(0, 203, 33, -100, 24.9, amountToDiscount));
        // check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: Stock amount cannot be negative.", e.getMessage());

        // test for Negative base price
        e = assertThrows(SuppliersException.class,
                () -> sc.addSupplierProductAgreement(0, 203, 33, 500, -33.4, amountToDiscount));
        // check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: Base price cannot be negative.", e.getMessage());
    }

    @Test
    public void calculateSupplierDiscountTest() throws SQLException {
        try {
            // we make one recipt item for the agreement and check the price of the item
            // before discount and after discount.
            TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
            amountToDiscount.put(50, "0.03%");
            amountToDiscount.put(200, "0.045%");
            sc.addSupplierProductAgreement(0, 203, 33, 500, 24.9, amountToDiscount);
            ProductAgreement pa = ProductController.getInstance().getProductAgreementsOfSupplier(0).get(0);
            Product p = ProductController.getInstance().getProductById(pa.getProductId());

            // creation of recipt item.
            ReceiptItemDTO riDTO = new ReceiptItemDTO(0, 203, 400, 24.9, 23.7795);
            ReceiptItem ri = new ReceiptItem(riDTO, p);
            // we check the price of item before the supplier discount. *Notice that there
            // could be another type of discount before calculating this one.*
            double pricePerUnitBeforeSupplierDiscount = ri.getPricePerUnitAfterDiscount();
            System.out.println(pricePerUnitBeforeSupplierDiscount);
            double predictedPricePerUnitAfterSupplierDiscount = pricePerUnitBeforeSupplierDiscount * (1 - 0.025);
            System.out.println(predictedPricePerUnitAfterSupplierDiscount);
            // puting the single recipt in a list and calculating the supplier discount.
            sc.calculateSupplierDiscount(0, createList(ri));
            System.out.println(ri.getPricePerUnitAfterDiscount());
            assertEquals(predictedPricePerUnitAfterSupplierDiscount, ri.getPricePerUnitAfterDiscount(), 0.0001);
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void getRandomContactOfTest() throws SQLException {
        // we check that the contact that we recieved exists in the supplier contacts
        // list.
        try {
            Contact c = sc.getRandomContactOf(0);
            assertTrue(sc.getSupplierById(0).getContacts().contains(c));
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }
    }

}