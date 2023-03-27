package Business_Layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneralProduct {
    private String name;
    private int generalProductId;
    private double price;
    private String manufacturer;
    private int min_quantity;
    private int total_quantity;
    private int shop_quantity;
    private int storage_quantity;
    private Category category;
    private boolean onDiscount;
    private Discount discount;
    private List<Supply> productSupply;


    public GeneralProduct(String name, int generalProductId, double price, String manufacturer, int min_quantity, Category category) {
        this.name = name;
        this.generalProductId = generalProductId;
        this.price = price;
        this.manufacturer = manufacturer;
        this.min_quantity = min_quantity;
        this.total_quantity = 0;
        this.shop_quantity = 0;
        this.storage_quantity = 0;
        this.category = category;
        this.onDiscount = false;
        discount = new Discount();
        this.productSupply = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getGeneralProductId() {
        return generalProductId;
    }

    public List<Supply> getProductSupply() {
        return productSupply;
    }

    public double getPrice() {
        return price;
    }
    public double getCurrentPrice() {
        if(onDiscount)
        return price * (1 - this.discount.getDiscount_percentage()/100);
        else
            return price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getMin_quantity() {
        return min_quantity;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public int getShop_quantity() {
        return shop_quantity;
    }

    public int getStorage_quantity() {
        return storage_quantity;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isOnDiscount() {
        return onDiscount;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setDiscount(Discount discount) {

        this.discount = discount;
    }

    public void setOnDiscount(boolean onDiscount) {
        this.onDiscount = onDiscount;
    }
    public boolean sellProduct()
    {
        if(total_quantity == 0 || total_quantity < 0 )
            return false;
        else {
            total_quantity--;
            shop_quantity--;
            return true;
        }
    }

    @Override
    public String toString() {
        return "GeneralProduct [name=" + name + ", generalProductId=" + generalProductId + ", price=" + price + ", manufacturer=" + manufacturer
                + ", total quantity=" + total_quantity + ", min quantity=" + min_quantity + ", shop quantity=" + shop_quantity + ", storage quantity=" + storage_quantity +
                ", category=" + category + ", onDiscount=" + onDiscount + "]";

    }
}
