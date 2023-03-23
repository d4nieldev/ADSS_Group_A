package Business_Layer;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private Category main;
    private List<Integer> allCategories; // keeps all the categories' id that the general product belongs to.



    public Category(int id,String name,Category category)
    {
        this.id = id;
        this.name = name;
        this.main = category;
        //insert all the categories to the categories list
        Category cat1 = category;
        while (cat1 != null)
        {
            allCategories.add(cat1.id);
            cat1 = cat1.main;
        }
    }
    public Category(int id,String name)
    {
        this.id = id;
        this.name = name;
        this.main = null;
    }

    //getter and setters

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Category getMain() {return main;}
    public void setMain(Category main) {this.main = main;}

    public String getMainId() {
        if(main == null)
            return "NULL";
        return "" + main.getId();
    }

    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", main=" + main + "]";
    }
}