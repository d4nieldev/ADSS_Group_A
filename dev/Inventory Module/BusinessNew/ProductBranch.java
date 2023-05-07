package BusinessNew;

import java.util.ArrayList;
import java.util.List;

public class ProductBranch {

    private Product product;
    private double price;
    private int idealQuantity;
    private int minQuantity;
    private List<SpecificProduct> allSpecificProducts;
    Discount discount;

    public ProductBranch(Product product ,double price, int idealQuantity, int minQuantity) {
        this.product = product;
        this.price = price;
        this.idealQuantity = idealQuantity;
        this.minQuantity = minQuantity;
        this.allSpecificProducts = new ArrayList<>();
        this.discount = null;
    }


}
