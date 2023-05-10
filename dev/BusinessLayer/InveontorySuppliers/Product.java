package BusinessLayer.InveontorySuppliers;

import BusinessLayer.Inventory.Category;

public class Product {
    private int id;
    private String name;
    private String manufacturer;
    private Category category; // The category that the product belongs to

    public Product(int id, String name, String manufacturer, Category category) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Category getCategory() {
        return category;
    }
}
