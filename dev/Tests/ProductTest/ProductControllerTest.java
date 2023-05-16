package Tests.ProductTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.Suppliers.SupplierController;
import BusinessLayer.exceptions.SuppliersException;

public class ProductControllerTest {

    @Before
    public void setUp() throws Exception {
        createSupplierWithTwoProductAgreements();
    }

    private void createSupplierWithTwoProductAgreements() throws Exception {
        createSupplier0();
        createTwoProducts();
        addTwoAgreementsToSupplier();
    }

    private void createSupplier0() throws SuppliersException, SQLException {
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

    private static <T> List<T> createList(T... args) {
        List<T> lst = new ArrayList<>();
        for (T item : args) {
            lst.add(item);
        }
        return lst;
    }

    private void createTwoProducts() throws SuppliersException, SQLException {
        CategoryController.getInstance().addNewCategory("IceCream");
        ProductController.getInstance().addProduct(17, "Gumi Gum", "Statboy", 0);
        ProductController.getInstance().addProduct(52, "Shokobo", "Jordi", 0);
    }

    private void addTwoAgreementsToSupplier() throws Exception {
        TreeMap<Integer, String> amountToDiscount1 = new TreeMap<>();
        amountToDiscount1.put(50, "0.015%");
        amountToDiscount1.put(200, "0.026%");

        TreeMap<Integer, String> amountToDiscount2 = new TreeMap<>();
        amountToDiscount2.put(70, "0.006%");
        amountToDiscount2.put(110, "0.01%");
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