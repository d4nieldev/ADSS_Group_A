package BusinessNew;

public class Category {
    private int id;
    private String name;
    private Category parent;

    public Category(String name, Category parentCategory){
        this.id = Global.getNewCategoryid();
        this.name = name;
        this.parent = parentCategory;
    }

    public Category(String name){
        this.id = Global.getNewCategoryid();
        this.name = name;
        this.parent = null;
    }

}
