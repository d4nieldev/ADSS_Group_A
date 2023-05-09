package BusinessNew;

import java.util.HashMap;

public class ProductController {
    public static HashMap<Integer,Product> allProducts;
    private static ProductController instance = null;

    private ProductController(){
        this.allProducts = new HashMap<>();
    }

    public static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
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
