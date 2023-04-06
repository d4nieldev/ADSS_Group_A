package Service_Layer;

import Business_Layer.Category;
import Business_Layer.CategoryController;
import Business_Layer.GeneralProduct;
import Business_Layer.ProductController;

import java.time.LocalDate;
import java.util.List;

public class ProductService {

ProductController productController;
CategoryController categoryController;
int branchId;

public ProductService(int branchId){
    this.productController = new ProductController();
    this.categoryController = CategoryController.getInstance();
    this.branchId = branchId;
}

    /**
     * function for find specific product's location
     * @param code
     * @param id
     * @return specific product location
     */
    public String getProductLocation(int code,int id){
    String location = productController.findProductLocation(code,id);
    return location;
}

    /**
     * function for find ageneral Product Manufacturer
     * @param code
     * @return product's Manufacturer
     */
    public String getProductManufacturer(int code){
        String manufacturer = productController.getProductManufacturer(code);
        return manufacturer;
}

    /**
     * function for find general product amount
     * @param code
     * @return the product amount
     */
    public int getProductAmount(int code){
        int amount = productController.getProductAmount(code);
        return amount;
}

    /**
     * function for find the product's shelf amount
     * @param code
     * @return the product's shelf amount
     */
    public int getProductShelfAmount(int code){
        int shelfAmount = productController.getProductShelfAmount(code);
        return shelfAmount;
    }
    /**
     * function for find the product's storage amount
     * @param code
     * @return the product's storage amount
     */
    public int getProductStorageAmount(int code){
        int storageAmount = productController.getProductStorageAmount(code);
        return storageAmount;
    }

    public ProductController getProductController() {return this.productController;}
    public int getBranchId() {
        return branchId;
    }

    /**
     * set discount on specific products
     * @param productsDiscount
     * @param startDate
     * @param endDate
     * @param discountPercentage
     */
    public void setDiscountByProducts(List<Integer> productsDiscount, LocalDate startDate,LocalDate endDate, double discountPercentage){
        List<GeneralProduct> generalProducts = productController.getProductsByCode(productsDiscount);
        productController.setDiscountOnProducts(generalProducts,startDate,endDate,discountPercentage);

    }

    /**
     * set discount on all the products belong to the given categories
     * @param categories
     * @param startDate
     * @param endDate
     * @param discountPercentage
     */
    public void setDiscountByCategories(List<Integer> categories, LocalDate startDate,LocalDate endDate, double discountPercentage){
        List<Category> categoriesList = categoryController.getCategoriesByIds(categories);
        List<Category> generalProducts = categoryController.getListAllSubCategories(categoriesList);
        productController.setDiscountOnCategory(generalProducts,startDate,endDate,discountPercentage);

    }

    public void addNewProduct(String name, int code, double price, String manufacturer, int min_quantity,int total_quantity){
        productController.addNewGeneralProduct(name,code,price,manufacturer,min_quantity,total_quantity);
    }

    public void receiveSupply(int code,String name, double price, int amount, LocalDate expiredDate, String manufacturer){
        productController.receiveSupply( code, name,  price,  amount,  expiredDate,  manufacturer);

    }

    public void changeProductMinQuantity(int code,int minQuantity){
        productController.setProductMinQuantity(code,minQuantity);
    }

    public void receiveSupply(int code,String name,double price,int amount, String expiredDate,String manufacturer){
        productController.receiveSupply(code,name,price,amount,LocalDate.parse(expiredDate),manufacturer);

        System.out.println("Supply added successfully");
    }

    public void sellProduct(int code, int id){
        productController.sellProduct(code,id);
        System.out.println("the sell been successful");
    }

    public void reportFlawProduct(int code,int id, String description){
        productController.reportFlowProduct(code,id,description);
    }








}
