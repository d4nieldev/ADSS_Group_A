package Business_Layer;

import java.util.*;

public class CategoryController {

    private static List<Category> allCategories;
    private static Hashtable<Integer, Category> categoryDic ;

    private static CategoryController instance = null;

private CategoryController() {
    this.allCategories = new ArrayList<>();
    this.categoryDic = new Hashtable<>();
}


    public static CategoryController getInstance(){
        if(instance == null){
            instance = new CategoryController();
        }
        return instance;
    }
    public Category getCategoryById(int id)
    {
        return categoryDic.get(id);
    }

    /***
     *
     * @param category
     * @return all parent categories of a specific Category
     */
    public List<Category> getAllParenCategories(Category category)
    {
        List<Category> result = new ArrayList<>();
        List<Integer> allCategoriesId = new ArrayList<>();
        allCategoriesId =  category.getAllCategories();
        for(Integer id : allCategoriesId)
        {
            result.add(categoryDic.get(id));
        }
        return result;
    }
    /***
     *
     * @param category
     * @return all Subcategories of a specific Category
     */
    public List<Category> getAllSubCategories(Category category)
    {
        List<Category> result = new ArrayList<>();
     for(Category cat : allCategories)
     {
         List<Category> allParent = getAllParenCategories(cat);
         if(allParent.contains(category))
             result.add(cat);
     }
     return result;
    }

    public List<Category> getAllCategories()
    {
        return allCategories;
    }

    /**
     * @param name
     * @param main
     * /// add new sub category to already exist category
     */
    public void addNewSubCategory(String name , Category main)
    {
        boolean flag = false;
       for(Category cat : allCategories)
       {
           if(cat.getName() == name && cat.getMain().getId() == main.getId()) {
               flag = true;
               System.out.println("this category already exist");
                break;
           }
       }
       if(!flag) {
           Category subCategory = new Category(name, main);
           allCategories.add(subCategory);
           categoryDic.put(subCategory.getId(), subCategory);
       }
    }
    /***
     * @param name
     * ///add new main category
     */
    public void addNewCategory(String name)
    {
        boolean flag = false;
        for(Category cat : allCategories)
        {
            if(cat.getName() == name ) {
                flag = true;
                System.out.println("this category already exist");
                break;
            }
        }

        if(!flag) {
            Category category = new Category(name);
            allCategories.add(category);
            categoryDic.put(category.getId(), category);
        }
    }
    public void addNewCategory(Category category){
        allCategories.add(category);
        categoryDic.put(category.getId(),category);
    }
    public boolean ExistCategory(int id)
    {
        return allCategories.contains(id);
    }

    public List<Category> getCategoriesByIds(List<Integer> ids){
        List<Category> result = new ArrayList<>();
        for(int id : ids){
            Category category = getCategoryById(id);
            result.add(category);
        }
        return result;
    }

    public List<Category> getListAllSubCategories(List<Category> categories) {
        List<Category> result = new ArrayList<>();
        Set<Category> set = new HashSet<>();
        for(Category category : categories){
            List<Category> temp = getAllSubCategories(category);
            set.addAll(temp);
        }

        result.addAll(set);
        return result;
    }
}
