package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class ProductDTO implements DTO {
    private int id;
    private String name;
    private String manufacturer;
    private CategoryDTO category;

    public ProductDTO(int id, String name, String manufacturer, CategoryDTO category) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.category = category;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("id", "" + id);
        nameToVal.put("name", name);
        nameToVal.put("manufacturer", manufacturer);
        nameToVal.put("categoryId", "" + category.getId());
        return nameToVal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}
