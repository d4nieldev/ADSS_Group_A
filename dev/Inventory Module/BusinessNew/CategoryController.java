package BusinessNew;

import Business_Layer.Category1;

import java.util.*;

public class CategoryController {
    private  List<Category> allCategories;
    private  Hashtable<Integer, Category> categoryDic;
    private static CategoryController instance = null;

    private CategoryController() {
        this.allCategories = new ArrayList<>();
        this.categoryDic = new Hashtable<>();
    }

    public static CategoryController getInstance() {
        if (instance == null) {
            instance = new CategoryController();
        }
        return instance;
    }

    public Category getCategoryById(int id) {
        return categoryDic.get(id);
    }

    public void addNewCategory(String name) {
        boolean flag = false;
        for (Category cat : allCategories) {
            if (cat.getName() == name) {
                flag = true;
                System.out.println("this category already exist");
                break;
            }
        }

        if (!flag) {
            Category category = new Category(name);
            allCategories.add(category);
            categoryDic.put(category.getId(), category);
        }
    }

    public void addNewCategory(String name, Category parentCategory) { // + add variable : List<Category> - represent all its children
        boolean flag = false;
        for (Category cat : allCategories) {
            if (cat.getName() == name && cat.getParentCategorie().getId() == parentCategory.getId()) {//so category is already exist
                flag = true;
                System.out.println("this category already exist");
                break;
            }
        }
        if (!flag) { //so category is new category
            //**/
            // TODO check if it ia a leaf category - if so , just add it and connect its father
            Category subCategory = new Category(name, parentCategory);
            allCategories.add(subCategory);
            categoryDic.put(subCategory.getId(), subCategory);
        }
    }

    public List<Category> getAllParentCategories(Category category) {
        List<Category> result = new ArrayList<>();
        result = category.getAllParentCategories();
        return result;
    }

    public List<Category> getAllSubCategories(Category category) {
        List<Category> result = new ArrayList<>();
        result.add(category);
        for (Category cat : allCategories) {
            List<Category> allParent = getAllParentCategories(cat);
            if (allParent.contains(category)) result.add(cat);
        }
        return result;
    }

    public boolean ExistCategory(int id) {
        return allCategories.contains(id);
    }

    public List<Category> getCategoriesByIds(List<Integer> ids) {
        List<Category> result = new ArrayList<>();
        for (int id : ids) {
            Category category = getCategoryById(id);
            result.add(category);
        }
        return result;
    }
    public List<Category> getListAllSubCategoriesByIds(List<Integer> categoriesIds){
        List<Category> categoryList = getCategoriesByIds(categoriesIds);
        return getListAllSubCategories(categoryList);
    }

    public List<Category> getListAllSubCategories(List<Category> categories) {
        List<Category> result = new ArrayList<>();
        Set<Category> set = new HashSet<>();
        for (Category category : categories) {
            List<Category> temp = getAllSubCategories(category);
            set.addAll(temp);
        }
        result.addAll(set);
        return result;
    }


}
