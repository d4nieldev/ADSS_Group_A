package Business_Layer;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private Category parentCategory;

    public Category(String name, Category parentCategory){
        this.id = Business_Layer.Global.getNewCategoryid();
        this.name = name;
        this.parentCategory = parentCategory;

    }

    public Category(String name){
        this.id = Global.getNewCategoryid();
        this.name = name;
        this.parentCategory = null;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Category getParentCategorie() {return parentCategory;}
    public void setParentCategorie(Category parentCategorie) {this.parentCategory = parentCategory;}

    public String getParentparentCategoryId() {
        if(parentCategory == null)
            return "NULL";
        return "" + parentCategory.getId();
    }

    public List<Category> getAllParentCategories() {
        List<Category> result = new ArrayList<>();
        Category cat = parentCategory;
        while (cat != null)
        {
            result.add(cat);
            cat = cat.parentCategory;
        }
        return result;
    }

}
