package Tests.InventoryTest;

import BusinessLayer.Inventory.*;
import BusinessLayer.InveontorySuppliers.Branch;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.Suppliers.SupplierController;
import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.BranchDTO;
import ServiceLayer.Suppliers.ReservationService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import BusinessLayer.Inventory.BranchController;
import org.junit.Before;
import org.junit.Test;
import BusinessLayer.InveontorySuppliers.ProductController;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
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
    private SupplierController supplierController;

    @Before
    public void setUp() throws Exception {
        this.branchController = BranchController.getInstance();
        this.productController = ProductController.getInstance();
        this.reservationController = ReservationController.getInstance();
        this.categoryController = CategoryController.getInstance();
        this.supplierController = SupplierController.getInstance();

        categoryController.addNewCategory("category1");
        Category category1 = categoryController.getCategoryById(0);
        categoryController.addNewCategory("category2", category1);
        Category category2 = categoryController.getCategoryById(1);
        categoryController.addNewCategory("category3", category1);
        Category category3 = categoryController.getCategoryById(2);
        categoryController.addNewCategory("category4", category3);
        Category category4 = categoryController.getCategoryById(3);
        branchController.addBranch(1, "testBranch", 1);
        productController.addProduct(1, "produc1", "Manufacturer1", 0);
        productController.addProduct(2, "produc2", "Manufacturer2", 1);
        supplierController.addFixedDaysSupplierBaseAgreement("Supplier", "0541825135", "123-4567", createList("Tech"),
                "net 30 EOM", new TreeMap<>(), new ArrayList<>(), new ArrayList<>(), createList(3));
        supplierController.addSupplierProductAgreement(0, 1, 123, 100, 10, new TreeMap<>());
        supplierController.addSupplierProductAgreement(0, 2, 124, 100, 10, new TreeMap<>());
    }

    private static <T> List<T> createList(T... args) {
        List<T> lst = new ArrayList<>();
        for (T item : args) {
            lst.add(item);
        }
        return lst;
    }

    @After
    public void tearDown() {
        branchController.clearData();
        productController.clearData();
        reservationController.clearData();
        categoryController.clearData();
        Repository.getInstance().DELETE_ALL_DATA();
    }

    /***
     * test that combine Inventory and suppliers - suppliers make reservation and
     * send it to branch -> branch receive the reservation and updates its data.
     * 
     * @throws SQLException
     */
    @Test
    public void testGetAllProductBranches() throws SQLException {
        // Arrange
        HashMap<Integer, Integer> productToAmount = new HashMap<>();
        productToAmount.put(1, 10);
        productToAmount.put(2, 30);
        // Act
        reservationController.makeDeficiencyReservation(productToAmount, 1);
        HashMap<Integer, ProductBranch> allProducts = branchController.getBranchById(1).getAllProductBranches();
        // Assert
        assertTrue(allProducts.size() == 2);
    }
    // @Test
    // public void testMakeDeficiencyReservation(){
    // BranchDTO branchDTO =
    // branchController.getBranchById(1).
    // }
}