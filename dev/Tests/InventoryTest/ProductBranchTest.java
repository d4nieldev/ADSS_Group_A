package Tests.InventoryTest;

import BusinessLayer.Inventory.*;
import BusinessLayer.InveontorySuppliers.*;
import DataAccessLayer.Repository;
import DataAccessLayer.DAOs.ProductsDAO;
import DataAccessLayer.DTOs.*;

import org.junit.After;
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
    CategoryController categoryController;
    Branch branch;
//.
    @Before
    public void setUp() throws Exception {
        branchController = BranchController.getInstance();
        productController = ProductController.getInstance();
        categoryController = CategoryController.getInstance();
        branchController.addBranch(0, "Ashkelon", 0);
        branch = branchController.getBranchById(0);
        categoryController.addNewCategory("Cat1");
        Category category = categoryController.getCategoryById(0);
        categoryController.addNewCategory("Cat2", category);
        productController.addProduct(0, "Product 0", "Manufacturer 0", 0);
        productController.addProduct(1, "Product 1", "Manufacturer 1", 1);
        ProductDTO p1 = ProductsDAO.getInstance().getById(0);
        ProductDTO p2 = ProductsDAO.getInstance().getById(1);
        ProductBranch productBranch01 = branchController.addNewProductBranch(0, 0, null, null, 0, false, 20, 5, 50);
        ProductBranch productBranch02 = branchController.addNewProductBranch(1, 0, null, null, 0, false, 20, 5, 50);
        branchController.receiveSupply(0, 5, 15, LocalDate.now().plusDays(2), 0);
        branchController.receiveSupply(0, 5, 15, LocalDate.now().minusDays(2), 0);
    }

    @After
    public void tearDown() {
        branchController.clearData();
        productController.clearData();
        categoryController.clearData();
        Repository.getInstance().DELETE_ALL_DATA();
    }

    @Test
    public void reportFlawProductsTest() throws Exception {
        // Arrange
        String description = "Report flaw Test";
        branchController.reportFlawProduct(0, 0, 1, description);
        // Act
        SpecificProduct result = branchController.getBranchById(0).getProductByCode(0).getSpecificById(1);
        // Assert
        assertEquals(ProductStatus.status.IS_FLAW, result.getStatus());
        assertEquals(description, result.getFlawDescription());
    }

    @Test
    public void testApplyDiscount() throws Exception {
        // Arrange
        List<Discount> discountsHistory = new ArrayList<>();
        DiscountDTO discountDTO1 = new DiscountDTO(1, LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), 0.1,
                "Percentage Discount");
        DiscountPercentage discountPercentage1 = new DiscountPercentage(discountDTO1);
        // Act
        ProductBranch productBranch = branchController.getBranchById(0).getProductByCode(0);
        double price = productBranch.getPrice();
        productBranch.applyDiscount(discountPercentage1);
        // Act
        double current = productBranch.getPrice();
        double desire = price * (1 - 0.1);
        boolean result = current == desire;
        // Assert
        assertTrue(result);
    }

    @Test
    public void testGetMaxDiscount() throws Exception {
        // Arrange
        List<Discount> discountsHistory = new ArrayList<>();
        DiscountDTO discountDTO1 = new DiscountDTO(1, LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), 0.1,
                "Percentage Discount");
        DiscountDTO discountDTO2 = new DiscountDTO(2, LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), 0.15,
                "Percentage Discount");
        DiscountDTO discountDTO3 = new DiscountDTO(3, LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), 0.2,
                "Percentage Discount");
        DiscountPercentage discountPercentage1 = new DiscountPercentage(discountDTO1);
        DiscountPercentage discountPercentage2 = new DiscountPercentage(discountDTO2);
        DiscountPercentage discountPercentage3 = new DiscountPercentage(discountDTO3);
        // Act
        ProductBranch productBranch = branchController.getBranchById(0).getProductByCode(1);
        productBranch.applyDiscount(discountPercentage1);
        productBranch.applyDiscount(discountPercentage2);
        productBranch.applyDiscount(discountPercentage3);
        // Act
        double resultVal = productBranch.getCurrentMaxDiscount().getVal();
        double desire = discountPercentage3.getVal();
        boolean result = resultVal == desire;
        // Assert
        assertTrue(result);
    }

    @Test
    public void testApplyMaxDiscount() throws Exception {
        // Arrange
        List<Discount> discountsHistory = new ArrayList<>();
        ProductBranch productBranch = branchController.getBranchById(0).getProductByCode(1);
        DiscountDTO discountDTO1 = new DiscountDTO(1, LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), 0.1,
                "Percentage Discount");
        DiscountDTO discountDTO2 = new DiscountDTO(1, LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), 0.15,
                "Percentage Discount");
        DiscountDTO discountDTO3 = new DiscountDTO(1, LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), 0.2,
                "Percentage Discount");
        DiscountPercentage discountPercentage1 = new DiscountPercentage(discountDTO1);
        DiscountPercentage discountPercentage2 = new DiscountPercentage(discountDTO2);
        DiscountPercentage discountPercentage3 = new DiscountPercentage(discountDTO3);
        // Act
        double price = productBranch.getPrice();
        productBranch.applyDiscount(discountPercentage1);
        productBranch.applyDiscount(discountPercentage2);
        productBranch.applyDiscount(discountPercentage3);

        // Assert
        double resultVal = productBranch.getDiscount().getVal();
        double desire = discountPercentage3.getVal();
        boolean result = resultVal == desire;
        assertTrue(result);
    }

    @Test
    public void testGetAllExpired() throws Exception {
        // Arrange
        ProductBranch productBranch = branchController.getBranchById(0).getProductByCode(0);
        // Act
        List<SpecificProduct> allExpired = productBranch.getAllExpired();
        SpecificProduct expired = productBranch.getSpecificById(5);
        SpecificProduct result = allExpired.get(0);
        // Assert
        assertEquals(expired, result);
    }

    @Test
    public void testReceiveSupply() throws Exception {
        // Arrange
        ProductBranch productBranch = branchController.getBranchById(0).getProductByCode(0);
        // Act
        int totalAMount = productBranch.getTotalAmount();
        int amount = 3;
        productBranch.receiveSupply(amount, 5, LocalDate.now().plusMonths(1), 1);
        int result = productBranch.getTotalAmount();
        // Assert
        assertEquals(totalAMount + amount, result);

    }

    @Test
    public void testGetOnStorage() throws Exception {
        // Arrange
        ProductBranch productBranch = branchController.getBranchById(0).getProductByCode(0);
        // Act
        int currentAmountOnStorage = productBranch.getTotalAmount() - productBranch.getOnShelfProduct().size();
        int amount = 3;
        productBranch.receiveSupply(amount, 5, LocalDate.now().plusMonths(1), 1);
        int result = productBranch.getTotalAmount() - productBranch.getOnShelfProduct().size();
        // Assert
        assertEquals(currentAmountOnStorage + amount, result);
    }
}