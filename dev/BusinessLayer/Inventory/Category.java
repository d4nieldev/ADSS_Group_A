package BusinessLayer.Inventory;

import DataAccessLayer.DTOs.CategoryDTO;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private Category parentCategory;
    private CategoryDTO categoryDTO;

    public Category(CategoryDTO categoryDTO) {
        this.id = categoryDTO.getId();
        this.name = categoryDTO.getName();
        CategoryController catCon = CategoryController.getInstance();
        this.parentCategory = catCon.getCategoryById(categoryDTO.getParentCategoryDTO().getId());
        this.categoryDTO = categoryDTO;
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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategorie(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getParentparentCategoryId() {
        if (parentCategory == null)
            return "NULL";
        return "" + parentCategory.getId();
    }

    public List<Category> getAllParentCategories() {
        List<Category> result = new ArrayList<>();
        Category cat = parentCategory;
        while (cat != null) {
            result.add(cat);
            cat = cat.parentCategory;
        }
        return result;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

}
