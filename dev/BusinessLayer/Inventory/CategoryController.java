package BusinessLayer.Inventory;

import DataAccessLayer.DAOs.CategoryDAO;
import DataAccessLayer.DTOs.CategoryDTO;

import java.sql.SQLException;
import java.util.*;

import BusinessLayer.exceptions.InventoryException;

public class CategoryController {
    private List<Category> allCategories;
    private Hashtable<Integer, Category> categoryDic;
    private static CategoryController instance = null;
    private CategoryDAO categoryDAO;

    private CategoryController() {
        this.allCategories = new ArrayList<>();
        this.categoryDic = new Hashtable<>();
        this.categoryDAO = CategoryDAO.getInstance();

    }

    public List<Category> getAllCategories() throws SQLException {
        if(allCategories.size() == 0)
            loadAllCategories();
        return allCategories;
    }

    public static CategoryController getInstance() {
        if (instance == null) {
            instance = new CategoryController();
        }
        return instance;
    }

    public int addNewCategory(String name, Category parentCategory) throws SQLException {
        CategoryDTO CatDTO = new CategoryDTO(Global.getNewCategoryid(), name, parentCategory.getCategoryDTO());
        categoryDAO.insert(CatDTO);
        Category category = new Category(CatDTO);
        allCategories.add(category);
        categoryDic.put(category.getId(), category);
        return category.getId();
    }

    public int addNewCategory(String name) throws SQLException {
        CategoryDTO CatDTO = new CategoryDTO(name);
        categoryDAO.insert(CatDTO);
        Category category = new Category(CatDTO);
        allCategories.add(category);
        categoryDic.put(category.getId(), category);
        return category.getId();
    }

    public void clearData() {
        allCategories.clear();
        categoryDic.clear();
    }

    // public void addNewCategory(String name) {
    // boolean flag = false;
    // for (Category cat : allCategories) {
    // if (cat.getName() == name) {
    // flag = true;
    // System.out.println("this category already exist");
    // break;
    // }
    // }
    //
    // if (!flag) {
    // Category category = new Category(name);
    // allCategories.add(category);
    // categoryDic.put(category.getId(), category);
    // }
    // }

    // public void addNewCategory(String name, Category parentCategory) { // + add
    // variable : List<Category> - represent
    // // all its children
    // boolean flag = false;
    // for (Category cat : allCategories) {
    // if (cat.getName() == name && cat.getParentCategory().getId() ==
    // parentCategory.getId()) {// so category is
    // // already exist
    // flag = true;
    // System.out.println("this category already exist");
    // break;
    // }
    // }
    // if (!flag) { // so category is new category
    // // **/
    // // TODO check if it ia a leaf category - if so , just add it and connect its
    // // father
    // Category subCategory = new Category(name, parentCategory);
    // allCategories.add(subCategory);
    // categoryDic.put(subCategory.getId(), subCategory);
    // }
    // }

    public List<Category> getAllParentCategories(Category category) {
        List<Category> result = new ArrayList<>();
        result = category.getAllParentCategories();
        return result;
    }

    public List<Category> getAllSubCategories(Category category) throws SQLException {
        List<Category> result = new ArrayList<>();
        result.add(category);
        if(allCategories.isEmpty()){
            loadAllCategories();
        }
        for (Category cat : allCategories) {
            List<Category> allParent = getAllParentCategories(cat);
//            if (allParent.contains(category))
//                result.add(cat);
            for(Category category1 : allParent){
                if(category1.getId() == category.getId())
                    result.add(cat);
            }
        }
        return result;
    }

    private void loadAllCategories() throws SQLException {
        List<CategoryDTO> categoryDTOS = categoryDAO.selectAll();
        for (CategoryDTO categoryDTO : categoryDTOS){
            Category category = new Category(categoryDTO);
            allCategories.add(category);
        }
    }

    public Category getCategoryById(int id) throws SQLException, InventoryException {
        if (id < 0)
            throw new InventoryException("Category with negative id is illegal in the system.");
        if (categoryDic.containsKey(id)) {
            return categoryDic.get(id);
        } else {
            Category c;
            try {
                c = LoadCategoryFromData(id);
            } catch (SQLException e) {
                throw new InventoryException("A database error occurred while loading category " + id);
            }
            if (c != null) {
                return c;
            } else {
                throw new InventoryException("There is no category with id " + id + " in the system.");
            }
        }
    }

    private Category LoadCategoryFromData(int id) throws SQLException {
        CategoryDTO res = categoryDAO.getById(id);
        if (res != null) {
            return new Category(res);
        }
        return null;
    }

    public List<Category> getCategoriesByIds(List<Integer> ids) throws Exception {
        List<Category> result = new ArrayList<>();
        for (int id : ids) {
            Category category = getCategoryById(id);
            result.add(category);
        }
        return result;
    }

    public List<Category> getListAllSubCategoriesByIds(List<Integer> categoriesIds) throws Exception {
        List<Category> categoryList = getCategoriesByIds(categoriesIds);
        return getListAllSubCategories(categoryList);
    }

    public List<Category> getListAllSubCategories(List<Category> categories) throws SQLException {
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
