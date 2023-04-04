package Service_Layer;

import Business_Layer.ProductController;

public class ProductService {

ProductController productController;
int branchId;

public ProductService(int branchId){
    this.productController = new ProductController();
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
}
