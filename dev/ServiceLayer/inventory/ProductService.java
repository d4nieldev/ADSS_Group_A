package ServiceLayer.inventory;

import BusinessLayer.Inventory.*;
import BusinessLayer.InveontorySuppliers.Branch;
import BusinessLayer.InveontorySuppliers.Discount;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import DataAccessLayer.DAOs.CategoryDAO;
import DataAccessLayer.DTOs.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class ProductService {

    ProductController productController;
    CategoryController categoryController;
    BranchController branchController;

    public ProductService() {
        this.productController = ProductController.getInstance();
        this.categoryController = CategoryController.getInstance();
        this.branchController = BranchController.getInstance();

    }

    public void addNewProduct(int code, String name, String manufacturer, int categoryId) throws SQLException {
        productController.addProduct(code, name, manufacturer, categoryId);
    }

    public int addNewCategory(String categoryName, int parentId) throws Exception {
        int newId = -1;
        if (parentId != -1) {
            Category parentCategory = categoryController.getCategoryById(parentId);
            if (parentCategory == null)
                throw new Exception("This Parent id doesn't exist");
            newId = categoryController.addNewCategory(categoryName, parentCategory);
        } else {
            newId = categoryController.addNewCategory(categoryName);
        }
        for (Category category : categoryController.getAllCategories()) {
            System.out.println(category.getName());
        }
        return newId;
    }

    public void sellProduct(int branchId, int ProductCode, int SpecificId) throws Exception {
        branchController.sellProduct(branchId, ProductCode, SpecificId);
    }

    public void reportFlawProduct(int branchId, int productCode, int specifcId, String description) throws Exception {
        branchController.reportFlawProduct(branchId, productCode, specifcId, description);
    }

    public void setDiscountByProducts(int branchId, List<Integer> productsToDiscount, Discount discount)
            throws Exception {
        branchController.setDiscountOnProducts(branchId, productsToDiscount, discount);

    }

    public void setDiscountByCategories(int branchId, List<Integer> categories, Discount discount) throws Exception {
        List<ProductBranch> result = branchController.setDiscountOnCategories(branchId, categories, discount);
        // for (ProductBranch productBranch : result){
        // ProductBranchDiscountDTO productBranchDiscountDTO = new
        // ProductBranchDiscountDTO(productBranch.getCode(),branchId,)
    }
}
