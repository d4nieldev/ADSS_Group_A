package Business_Layer;

import java.util.ArrayList;
import java.util.List;

public class Category1 {
    private int id;
    private String name;
    private Category1 main;
    private List<Integer> allCategories; // keeps all the categories' id that the general product belongs to.



    public Category1(String name, Category1 parentCategory1)
    {
        this.id = Global.getNewCategoryid();
        this.name = name;
        this.main = parentCategory1;
        this.allCategories = new ArrayList<>();
        //insert all the categories to the categories list
        Category1 cat1 = parentCategory1;
        while (cat1 != null)
        {
            allCategories.add(cat1.id);
            cat1 = cat1.main;
        }
    }
    public Category1(String name)
    {
        this.id = Global.getNewCategoryid();
        this.name = name;
        this.main = null;
        this.allCategories = new ArrayList<>();

    }


    //getter and setters

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Category1 getMain() {return main;}
    public void setMain(Category1 main) {this.main = main;}

    public List<Integer> getAllCategories() {return allCategories;}

    public String getMainId() {
        if(main == null)
            return "NULL";
        return "" + main.getId();
    }

    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", main=" + main + "]";
    }
}
