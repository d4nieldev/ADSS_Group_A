package ServiceLayer.inventory;

import BusinessLayer.Inventory.BranchController;
import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.InveontorySuppliers.Discount;
import BusinessLayer.InveontorySuppliers.ProductController;


import java.sql.SQLException;
import java.util.List;

public class ProductService {

    ProductController productController;
    CategoryController categoryController;
    BranchController branchController;


    public ProductService() throws Exception {
        this.productController = ProductController.getInstance();
        this.categoryController = CategoryController.getInstance();
        this.branchController = BranchController.getInstance();
//        categoryController.addNewCategory("Tnuva");
//        Category tnuva = categoryController.getCategoryById(0);
//        categoryController.addNewCategory("MIlks drinks",tnuva);
//        Category milkDrinks =categoryController.getCategoryById(1);
//        Product p1 = new Product("Milk",1,"Tnuva",milkDrinks);
//        Product p2 = new Product("Koteg",2,"Tnuva",tnuva);
//        productController.addProduct(p1);
//        productController.addProduct(p2);
//        branchController.addBranch(1,"brnach1");
//        Branch branch = branchController.getBranchById(1);
//        branch.addNewProductBranch(p1,20,50,48);
//        branch.addNewProductBranch(p2,20,50,48);
//        branch.receiveSupply(1);
//        branch.receiveSupply(2);
//        branch.reportFlawProduct(1,1,"FlowProduct!!!");
    }
    public void addNewProduct(int code,String name, String manufacturer, int categoryId) throws SQLException {
    productController.addProduct(code,name,manufacturer,categoryId);
    }
    public int addNewCategory(String categoryName, int parentId) throws Exception {
       int newId = -1;
        if(parentId != -1){
            Category parentCategory = categoryController.getCategoryById(parentId);
            if(parentCategory == null)
                throw new Exception("This Parent id doesn't exist");
            newId = categoryController.addNewCategory(categoryName,parentCategory);
        }
        else {
             newId =  categoryController.addNewCategory(categoryName);
        }
        for(Category category : categoryController.getAllCategories()){
            System.out.println(category.getName());
        }
        return newId;
    }
    public void sellProduct(int branchId,int ProductCode, int SpecificId) throws Exception {
        branchController.sellProduct(branchId,ProductCode,SpecificId);
    }

    public void reportFlawProduct(int branchId,int productCode,int specifcId, String description) throws Exception {
        branchController.reportFlawProduct(branchId,productCode,specifcId,description);
    }

    public void setDiscountByProducts(int branchId, List<Integer> productsToDiscount, Discount discount) throws Exception {
        branchController.setDiscountOnProducts(branchId,productsToDiscount,discount);


    }
    public void setDiscountByCategories(int branchId,List<Integer> categories, Discount discount) throws Exception {
    branchController.setDiscountOnCategories(branchId,categories,discount);

    }









}
