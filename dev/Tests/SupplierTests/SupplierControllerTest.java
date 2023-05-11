package Tests.SupplierTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.TreeMap;

import org.junit.*;

import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.InveontorySuppliers.ReceiptItem;
import BusinessLayer.Suppliers.Contact;
import BusinessLayer.Suppliers.Supplier;
import BusinessLayer.Suppliers.ProductAgreement;
import BusinessLayer.Suppliers.SupplierController;
import BusinessLayer.exceptions.SuppliersException;

public class SupplierControllerTest {

    SupplierController sc = SupplierController.getInstance();

    @Before
    public void setup() throws Exception {
        try {
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

    /**
     * Creation of an 'OnOrder' type supplier for testing purposes
     * 
     * @throws Exception
     */
    public static void createSupplier0() throws SuppliersException {
        String name = "FastAndBest ";
        String phone = "0507164509";
        String bankAccount = "12-128-148258";
        List<String> fields = List.of("Tech", "Cleaning");
        String paymentCondition = "net 30 EOM";
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(100, 0.025);
        amountToDiscount.put(500, 0.03);
        List<String> contactNames = List.of("Dana Grinberg", "Roni Katz");
        List<String> contactPhones = List.of("0525948325", "0535669897");
        int maxSupplyDays = 4;

        SupplierController.getInstance().addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields,
                paymentCondition, amountToDiscount, contactNames, contactPhones, maxSupplyDays);
    }

    public void createProduct() throws SuppliersException {
        Product TaraMilk = new Product(203, "Tara Milk 1.5L", "Tara");
        ProductController.getInstance().addProduct(TaraMilk);
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
    public void deleteExistingSupplierTest() {
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
    public void addFixedDaysSupplierBaseAgreementTest() {
        // to validate creation we try to get this supplier and check if his id, name
        // and phone are correct.

        String name = "AllYouNeed";
        String phone = "0507164588";
        String bankAccount = "09-319-158988";
        List<String> fields = List.of("Meat", "Sweet drinks");
        String paymentCondition = "net 45 EOM";
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, 0.015);
        amountToDiscount.put(90, 0.02);
        List<String> contactNames = List.of("Kevin Monk");
        List<String> contactPhones = List.of("0525869525");
        List<Integer> days = List.of(2, 4);
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
    public void addOnOrderSupplierBaseAgreementTest() {
        // to validate creation we try to get this supplier and check if his id, name
        // and phone are correct.
        String name = "AllYouNeed";
        String phone = "0507164588";
        String bankAccount = "09-319-158988";
        List<String> fields = List.of("Meat", "Sweet drinks");
        String paymentCondition = "net 45 EOM";
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, 0.015);
        amountToDiscount.put(90, 0.02);
        List<String> contactNames = List.of("Kevin Monk");
        List<String> contactPhones = List.of("0525869525");
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
    public void addSelfPickupSupplierBaseAgreementTest() {
        // to validate creation we try to get this supplier and check if his id, name
        // and phone are correct.
        String name = "AllYouNeed";
        String phone = "0507164588";
        String bankAccount = "09-319-158988";
        List<String> fields = List.of("Meat", "Sweet drinks");
        String paymentCondition = "net 45 EOM";
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, 0.015);
        amountToDiscount.put(90, 0.02);
        List<String> contactNames = List.of("Kevin Monk");
        List<String> contactPhones = List.of("0525869525");
        String address = "Shilo 4, Ashkelon";
        try {
            SupplierController.getInstance().addSelfPickupSupplierBaseAgreement(name, phone, bankAccount, fields,
                    paymentCondition,
                    amountToDiscount, contactNames, contactPhones, address);
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
    public void setSupplierPhoneTest() {
        try {
            sc.setSupplierPhone(0, "0500000000");
            assertEquals("0500000000", sc.getSupplierById(0).getPhone());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }

        // test for Non existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.setSupplierPhone(5, "0500000000"));

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
    public void deleteAllSupplierContactsTest() {
        // we check that after deleting all contacts we still have the office contact.
        try {
            sc.deleteAllSupplierContacts(0);
            assertEquals(1, sc.getSupplierById(0).getContacts().size());
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addSupplierProductAgreementTest() {
        // we check that after adding a product agreement, our supplier has the product
        // agreement (by checking the prouct's id in the agreement)
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, 0.03);
        amountToDiscount.put(200, 0.045);
        try {
            sc.addSupplierProductAgreement(0, 203, 33, 500, 24.9, amountToDiscount);
            assertEquals(203,
                    ProductController.getInstance().getProductAgreementsOfSupplier(0).get(0).getProduct().getId());
        } catch (SuppliersException e) {
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
    public void calculateSupplierDiscountTest() {
        try {
            // we make one recipt item for the agreement and check the price of the item
            // before discount and after discount.
            TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
            amountToDiscount.put(50, 0.03);
            amountToDiscount.put(200, 0.045);
            sc.addSupplierProductAgreement(0, 203, 33, 500, 24.9, amountToDiscount);
            ProductAgreement pa = ProductController.getInstance().getProductAgreementsOfSupplier(0).get(0);
            // creation of recipt item.
            ReceiptItem ri = new ReceiptItem(400, pa);
            // we check the price of item before the supplier discount. *Notice that there
            // could be another type of discount before calculating this one.*
            double pricePerUnitBeforeSupplierDiscount = ri.getPricePerUnitAfterDiscount();
            System.out.println(pricePerUnitBeforeSupplierDiscount);
            double predictedPricePerUnitAfterSupplierDiscount = pricePerUnitBeforeSupplierDiscount * (1 - 0.025);
            System.out.println(predictedPricePerUnitAfterSupplierDiscount);
            // puting the single recipt in a list and calculating the supplier discount.
            sc.calculateSupplierDiscount(0, List.of(ri));
            System.out.println(ri.getPricePerUnitAfterDiscount());
            assertEquals(predictedPricePerUnitAfterSupplierDiscount, ri.getPricePerUnitAfterDiscount(), 0.0001);
        } catch (SuppliersException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void getRandomContactOfTest() {
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