package InventoryTest;

import BusinessLayer.Inventory.BranchController;
import BusinessLayer.Inventory.ProductBranch;
import BusinessLayer.Inventory.ProductStatus;
import BusinessLayer.Inventory.SpecificProduct;
import BusinessLayer.InveontorySuppliers.Branch;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import DataAccessLayer.DTOs.CategoryDTO;
import DataAccessLayer.DTOs.ProductBranchDTO;
import DataAccessLayer.DTOs.ProductDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;
import org.junit.After;
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
        productController = ProductController.getInstance();
        branchController.addBranch(1,"testBranch",300);
        branch = branchController.getBranchById(branch.getId());
        CategoryDTO categoryDTO = new CategoryDTO(1,"Milks products");
        ProductDTO productDTO = new ProductDTO(1,"Milk","Tnuva",categoryDTO);
        productController.addProduct(productDTO);
        SpecificProductDTO specificProductDTO1 = new SpecificProductDTO(1,1,1,10,"", LocalDate.parse("2023-11-11"));
        SpecificProductDTO specificProductDTO2 = new SpecificProductDTO(2,1,1,10,"", LocalDate.parse("2023-01-11"));
        SpecificProductDTO specificProductDTO3 = new SpecificProductDTO(3,1,1,10,"", LocalDate.parse("2023-11-11"));
        HashMap<Integer,SpecificProductDTO> allSpecific = new HashMap<>();
        allSpecific.put(1,specificProductDTO1);
        allSpecific.put(2,specificProductDTO2);
        allSpecific.put(3,specificProductDTO3);
        ProductBranchDTO productBranchDTO = new ProductBranchDTO(productDTO,branch.getId(),15,50,100,allSpecific);
        ProductBranch productBranch = new ProductBranch(productBranchDTO);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetFlawDescription() throws Exception {
        SpecificProduct sp = branch.getProductByCode(1).getSpecificById(1);
        String description = "Minor flaw";
        sp.setFlawDescription(description);
        assertEquals(description, sp.getFlawDescription());
    }

    @Test
    void testSetStatus() throws Exception {
        SpecificProduct sp = branch.getProductByCode(1).getSpecificById(1);
        ProductStatus.status status = ProductStatus.status.ON_SHELF;
        sp.setStatus(status);
        assertEquals(status, sp.getFlawDescription());
    }
}