package Tests.ProductTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import BusinessLayer.Inventory.Category;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.Suppliers.SupplierController;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ProductControllerTest {

    @Before
    public void setUp() throws Exception {
        try {
            createSupplierWithTwoProductAgreements();
        } catch (Exception e) {
            System.out.println("Could not setup environment");
            e.printStackTrace();
        }

    }

    private void createSupplierWithTwoProductAgreements() throws Exception {
        try {
            createSupplier0();
            createTwoProducts();
            addTwoAgreementsToSupplier();
        } catch (Exception e) {
           throw e;
        }
    }

    private void createSupplier0() {
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

    private void createTwoProducts() throws SuppliersException {
        Product p1 = new Product(17, "Gumi Gum", "Statboy", new Category("IceCream"));
        ProductController.getInstance().addProduct(p1);
        Product p2 = new Product(52, "Shokobo", "Jordi", new Category("IceCream"));
        ProductController.getInstance().addProduct(p2);
    }

    private void addTwoAgreementsToSupplier() throws Exception {
        TreeMap<Integer, Double> amountToDiscount1 = new TreeMap<>();
        amountToDiscount1.put(50, 0.015);
        amountToDiscount1.put(200, 0.026);

        TreeMap<Integer, Double> amountToDiscount2 = new TreeMap<>();
        amountToDiscount2.put(70, 0.006);
        amountToDiscount2.put(110, 0.01);
        try {
            SupplierController.getInstance().addSupplierProductAgreement(0, 17, 505, 300, 12.3, amountToDiscount1);
            SupplierController.getInstance().addSupplierProductAgreement(0, 52, 506, 400, 7.6, amountToDiscount2);
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void deleteAllSupplierAgreementsTest() {
        try {
            ProductController.getInstance().deleteAllSupplierAgreements(0);
            // now we check how many product agreements the supplier has (should be 0)
            assertEquals(0, ProductController.getInstance().getProductAgreementsOfSupplier(0).size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}