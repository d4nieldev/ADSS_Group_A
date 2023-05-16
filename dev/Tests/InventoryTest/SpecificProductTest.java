package Tests.InventoryTest;

import BusinessLayer.Inventory.*;
import BusinessLayer.InveontorySuppliers.Branch;

import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import DataAccessLayer.DAOs.ProductsDAO;
import DataAccessLayer.DTOs.CategoryDTO;
import DataAccessLayer.DTOs.ProductBranchDTO;
import DataAccessLayer.DTOs.ProductDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class SpecificProductTest {
    BranchController branchController;
    ProductController productController;
    Branch branch;
//    ProductBranch productBranch;


    @Before
    public void setUp() throws Exception {

        branchController = BranchController.getInstance();
        BranchController.getInstance().addBranch(0, "Ashkelon", 0);
        branch = branchController.getBranchById(0);
        CategoryController.getInstance().addNewCategory("Cat1");
        Category category = CategoryController.getInstance().getCategoryById(0);
        CategoryController.getInstance().addNewCategory("Cat2",category);
        ProductController.getInstance().addProduct(0, "Product 0", "Manufacturer 0", 0);
        ProductController.getInstance().addProduct(1, "Product 1", "Manufacturer 1", 1);
        ProductDTO p1 = ProductsDAO.getInstance().getById(0);
        ProductDTO p2 = ProductsDAO.getInstance().getById(1);
        ProductBranch productBranch01 =branchController.addNewProductBranch(0,0,null,20,5,50);
        ProductBranch productBranch02 =branchController.addNewProductBranch(1,0,null,20,5,50);
        branchController.receiveSupply(0,5,15,LocalDate.now().plusDays(2),0);
        branchController.receiveSupply(0,5,15,LocalDate.now().minusDays(2),0);
     }


    @Test
    public void testSetFlawDescription() throws Exception {
        SpecificProduct sp = branch.getProductByCode(0).getSpecificById(1);
        String description = "Minor flaw";
        sp.setFlawDescription(description);
        assertEquals(description, sp.getFlawDescription());
    }

    @Test
    public void testSetStatus() throws Exception {
        SpecificProduct sp = branch.getProductByCode(0).getSpecificById(1);
        ProductStatus.status status = ProductStatus.status.ON_SHELF;
        sp.setStatus(status);
        assertEquals(status, sp.getStatus());
    }
}