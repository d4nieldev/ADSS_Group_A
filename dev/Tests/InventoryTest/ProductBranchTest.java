package Tests.InventoryTest;

import BusinessLayer.Inventory.BranchController;
import BusinessLayer.Inventory.ProductBranch;
import BusinessLayer.Inventory.ProductStatus;
import BusinessLayer.Inventory.SpecificProduct;
import BusinessLayer.InveontorySuppliers.*;
import DataAccessLayer.DTOs.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ProductBranchTest {
    BranchController branchController;
    ProductController productController;
    Branch branch;
    private int price;
    @Before
    public void setUp() throws Exception {
        branchController = BranchController.getInstance();
        productController = ProductController.getInstance();
        branchController.addBranch(1,"testBranch",300);
        branch = branchController.getBranchById(branch.getId());
        CategoryDTO categoryDTO = new CategoryDTO(1,"Milks products");
        ProductDTO productDTO = new ProductDTO(1,"Milk","Tnuva",categoryDTO);
        productController.addNewProduct(productDTO);
        SpecificProductDTO specificProductDTO1 = new SpecificProductDTO(1,1,1,10,"", LocalDate.parse("2023-11-11"));
        SpecificProductDTO specificProductDTO2 = new SpecificProductDTO(2,1,1,10,"", LocalDate.parse("2023-01-11"));
        SpecificProductDTO specificProductDTO3 = new SpecificProductDTO(3,1,1,10,"", LocalDate.parse("2023-11-11"));
        HashMap<Integer,SpecificProductDTO> allSpecific = new HashMap<>();
        allSpecific.put(1,specificProductDTO1);
        allSpecific.put(2,specificProductDTO2);
        allSpecific.put(3,specificProductDTO3);
        this.price = 10;
        ProductBranchDTO productBranchDTO = new ProductBranchDTO(productDTO,branch.getId(),price,50,100,allSpecific);
        ProductBranch productBranch = new ProductBranch(productBranchDTO);
    }

    @Test
    void reportFlawProductsTest() throws Exception {
        // Arrange
        String description = "Report flaw Test";
        branchController.reportFlawProduct(1,1,1,description);
        // Act
        SpecificProduct result = branchController.getBranchById(1).getProductByCode(1).getSpecificById(1);
        // Assert
        assertEquals(ProductStatus.status.IS_FLAW, result.getStatus());
        assertEquals(description, result.getFlawDescription());
    }

    @Test
    public void testApplyDiscount() throws SQLException, SQLException {
        // Arrange
        List<Discount> discountsHistory = new ArrayList<>();
        DiscountDTO discountDTO1 = new DiscountDTO(1,LocalDate.now().minusDays(10),LocalDate.now().plusDays(10),0.1,"Percentage Discount");
        DiscountPercentage discountPercentage1 = new DiscountPercentage(discountDTO1);
        // Act
        ProductBranch productBranch = branchController.getBranchById(1).getProductByCode(1);
        productBranch.applyDiscount(discountPercentage1);
        // Act
        boolean result = productBranch.getPrice() == price * (1 -0.1);
        // Assert
        assertTrue(result);
    }

    @Test
    public void  testGetMaxDiscount() throws Exception {
        // Arrange
        List<Discount> discountsHistory = new ArrayList<>();
        DiscountDTO discountDTO1 = new DiscountDTO(1,LocalDate.now().minusDays(10),LocalDate.now().plusDays(10),0.1,"Percentage Discount");
        DiscountDTO discountDTO2 = new DiscountDTO(1,LocalDate.now().minusDays(10),LocalDate.now().plusDays(10),0.15,"Percentage Discount");
        DiscountDTO discountDTO3 = new DiscountDTO(1,LocalDate.now().minusDays(10),LocalDate.now().plusDays(10),0.2,"Percentage Discount");
        DiscountPercentage discountPercentage1 = new DiscountPercentage(discountDTO1);
        DiscountPercentage discountPercentage2 = new DiscountPercentage(discountDTO2);
        DiscountPercentage discountPercentage3 = new DiscountPercentage(discountDTO3);
        // Act
        ProductBranch productBranch = branchController.getBranchById(1).getProductByCode(1);
        productBranch.applyDiscount(discountPercentage1);
        productBranch.applyDiscount(discountPercentage2);
        productBranch.applyDiscount(discountPercentage3);
        // Act
        boolean result = discountDTO3.equals(productBranch.getCurrentMaxDiscount());
        // Assert
        assertTrue(result);
    }

    @Test
    public void testApplyMaxDiscount() throws Exception {
        // Arrange
        List<Discount> discountsHistory = new ArrayList<>();
        ProductBranch productBranch = branchController.getBranchById(1).getProductByCode(1);
        DiscountDTO discountDTO1 = new DiscountDTO(1,LocalDate.now().minusDays(10),LocalDate.now().plusDays(10),0.1,"Percentage Discount");
        DiscountDTO discountDTO2 = new DiscountDTO(1,LocalDate.now().minusDays(10),LocalDate.now().plusDays(10),0.15,"Percentage Discount");
        DiscountDTO discountDTO3 = new DiscountDTO(1,LocalDate.now().minusDays(10),LocalDate.now().plusDays(10),0.2,"Percentage Discount");
        DiscountPercentage discountPercentage1 = new DiscountPercentage(discountDTO1);
        DiscountPercentage discountPercentage2 = new DiscountPercentage(discountDTO2);
        DiscountPercentage discountPercentage3 = new DiscountPercentage(discountDTO3);
        // Act
        productBranch.applyDiscount(discountPercentage1);
        productBranch.applyDiscount(discountPercentage2);
        productBranch.applyDiscount(discountPercentage3);


        // Assert
        boolean result = productBranch.getPrice() == price * (1 -0.2);
        assertTrue(result);
    }
//    @Test
//    public void testGetAllExpired() throws Exception {
//        // Arrange
//        List<SpecificProduct>
//    }
    }