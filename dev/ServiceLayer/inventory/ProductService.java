package ServiceLayer.inventory;

import BusinessLayer.Inventory.*;
import BusinessLayer.InveontorySuppliers.Branch;
import BusinessLayer.InveontorySuppliers.Discount;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import DataAccessLayer.DAOs.CategoryDAO;
import DataAccessLayer.DTOs.CategoryDTO;
import DataAccessLayer.DTOs.ProductBranchDTO;
import DataAccessLayer.DTOs.ProductDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ProductService {

    ProductController productController;
    CategoryController categoryController;
    BranchController branchController;



    public ProductService() {
        this.productController = ProductController.getInstance();
        this.categoryController = CategoryController.getInstance();
        this.branchController = BranchController.getInstance();
        try {
            initial();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void initial() throws Exception {
        Branch branch = branchController.getBranchById(1);
        categoryController.addNewCategory("Tnuva"); // id - 0
        Category tnuva = categoryController.getCategoryById(0);
        categoryController.addNewCategory("Milks drinks",tnuva); // id -1
        Category milkDrinks =categoryController.getCategoryById(1);
        CategoryDTO categoryDTO0 = CategoryDAO.getInstance().getById(0);
        CategoryDTO categoryDTO1 = CategoryDAO.getInstance().getById(1);

        ProductDTO p1DTO = new ProductDTO(1,"Milk","Tnuva",categoryDTO1);
        ProductDTO p2DTO = new ProductDTO(2,"Koteg","Tnuva",categoryDTO0);
//        Product p1 = new Product(p1DTO);
//        Product p2 = new Product(p2DTO);
        productController.addNewProduct(p1DTO); // milk
        productController.addNewProduct(p2DTO); // koteg

        branchController.addBranch(1,"branch1",10);

        SpecificProductDTO specificProductDTO1 = new SpecificProductDTO(1,1,1,15, LocalDate.now().plusDays(2)); //Flow
        SpecificProductDTO specificProductDTO2 = new SpecificProductDTO(2,1,1,15, LocalDate.now().plusDays(2));
        SpecificProductDTO specificProductDTO3 = new SpecificProductDTO(3,1,1,15, LocalDate.now().minusDays(2));

        HashMap<Integer,SpecificProductDTO> hashMilk = new HashMap<>();
        hashMilk.put(specificProductDTO1.getSpecificId(),specificProductDTO1);
        hashMilk.put(specificProductDTO2.getSpecificId(),specificProductDTO2);
        hashMilk.put(specificProductDTO3.getSpecificId(),specificProductDTO3);
        ProductBranchDTO productBranchDTO1 = new ProductBranchDTO(p1DTO,1,20,50,100,hashMilk); //milk


        SpecificProductDTO specificProductDTO11 = new SpecificProductDTO(1,2,1,5, LocalDate.now().plusDays(2));
        SpecificProductDTO specificProductDTO22 = new SpecificProductDTO(2,2,1,5, LocalDate.now().plusDays(2));
        SpecificProductDTO specificProductDTO33 = new SpecificProductDTO(3,2,1,5, LocalDate.now().minusDays(2));

        HashMap<Integer,SpecificProductDTO> hashKoteg = new HashMap<>();

        hashKoteg.put(specificProductDTO11.getSpecificId(),specificProductDTO1);
        hashKoteg.put(specificProductDTO22.getSpecificId(),specificProductDTO2);
        hashKoteg.put(specificProductDTO33.getSpecificId(),specificProductDTO3);
        ProductBranchDTO productBranchDTO2 = new ProductBranchDTO(p2DTO,1,5,50,100,hashKoteg);

        ProductBranch productBranchMilk = new ProductBranch(productBranchDTO1);
        ProductBranch productBranchKoteg = new ProductBranch(productBranchDTO2);
        branch.addNewProductBranch(productBranchDTO1);
        branch.addNewProductBranch(productBranchDTO2);


        branch.reportFlawProduct(1,1,"FlowProduct!!!");
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
