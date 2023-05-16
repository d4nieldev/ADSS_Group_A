package BusinessLayer.InveontorySuppliers;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.exceptions.InventoryException;
import DataAccessLayer.DTOs.CategoryDTO;
import DataAccessLayer.DTOs.ProductDTO;

import java.sql.SQLException;

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

//    public Product(int id, String name, String manufacturer, int categoryId) {
//        this.id = id;
//        this.name = name;
//        this.manufacturer = manufacturer;
//        this.categoryId = categoryDTO.getId();
//        CategoryDTO categoryDTO =
//        this.dto = new ProductDTO(id,name,manufacturer,)
//    }

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

    public Category getCategory() throws SQLException, InventoryException {
        CategoryController categoryController = CategoryController.getInstance();
        return categoryController.getCategoryById(categoryId);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public ProductDTO getDTO() {
        return this.dto;
    }

    public String getCategoryName() throws Exception{
        CategoryController categoryController = CategoryController.getInstance();
        return categoryController.getCategoryById(categoryId).getName();
    }
}
