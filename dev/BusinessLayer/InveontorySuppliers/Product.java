package BusinessLayer.InveontorySuppliers;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import DataAccessLayer.DTOs.ProductDTO;

public class Product {
    private int id;
    private String name;
    private String manufacturer;
    private int categoryId; // The category that the product belongs to
    private ProductDTO dto;

    public Product(ProductDTO dto) {
        this.dto = dto;
        this.id = dto.getId();
        this.name = dto.getName();
        this.manufacturer = dto.getManufacturer();
        this.categoryId = dto.getCategory().getId();
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

    public int geCategoryId() {
        return categoryId;
    }

    public Category getCategory() {
        CategoryController categoryController = CategoryController.getInstance();
        return categoryController.getCategoryById(categoryId);

    }

    public ProductDTO getDTO() {
        return this.dto;
    }

}
