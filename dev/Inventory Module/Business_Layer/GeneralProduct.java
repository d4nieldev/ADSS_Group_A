package Business_Layer;

import java.util.List;

public class GeneralProduct {
    private String name;
    private int code;
    private double price;
    private String manufacturer;
    private int min_quantity;
    private int total_quantity;
    private int shop_quantity;
    private int storage_quantity;
    private Category category;
    private boolean onDiscount;
    private Discount discount;


    public GeneralProduct(String name, int code, double price, String manufacturer, int min_quantity, Category category) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.manufacturer = manufacturer;
        this.min_quantity = min_quantity;
        this.total_quantity = 0;
        this.shop_quantity = 0;
        this.storage_quantity = 0;
        this.category = category;
        this.onDiscount = false;
        discount = new Discount();
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public double getPrice() {
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


    @Override
    public String toString() {
        return "GeneralProduct [name=" + name + ", code=" + code + ", price=" + price + ", manufacturer=" + manufacturer
                + ", total quantity=" + total_quantity + ", min quantity=" + min_quantity + ", shop quantity=" + shop_quantity + ", storage quantity=" + storage_quantity +
                ", category=" + category + ", onDiscount=" + onDiscount + "]";

    }
}
