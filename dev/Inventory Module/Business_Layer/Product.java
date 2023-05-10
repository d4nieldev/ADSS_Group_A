package Business_Layer;

public class Product {
    private String name; // The name of the product
    private int code; // The unique code of the product
    private String manufacturer; // The manufacturer of the product
    private Category category; // The category that the product belongs to

    public Product(String name, int code, String manufacturer, Category category) {
        this.name = name;
        this.code = code;
        this.manufacturer = manufacturer;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Category getCategory() {
        return category;
    }

}
