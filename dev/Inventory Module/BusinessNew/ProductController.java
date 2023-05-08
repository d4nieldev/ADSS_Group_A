package BusinessNew;

import java.util.HashMap;

public class ProductController {
    public static HashMap<Integer,Product> allProducts;

    public ProductController(HashMap<Integer, Product> allProducts) {
        this.allProducts = allProducts;
    }

    public HashMap<Integer, Product> getAllProducts() {
        return allProducts;
    }
    public void addProduct(Product product)
    {
        int generalId = product.getCode();
        allProducts.put(generalId,product);
    }
    public static boolean exist(int code){
        return allProducts.containsKey(code);
    }
}
