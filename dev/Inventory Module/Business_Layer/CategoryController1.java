package Business_Layer;

import java.util.*;

public class CategoryController1 {

  private static List<Category1> allCategories;
  private static Hashtable<Integer, Category1> categoryDic;

  private static CategoryController1 instance = null;

  private CategoryController1() {
    this.allCategories = new ArrayList<>();
    this.categoryDic = new Hashtable<>();
  }

  public static CategoryController1 getInstance() {
    if (instance == null) {
      instance = new CategoryController1();
    }
    return instance;
  }

  public static void putCategory(int id, Category1 category1) {
    categoryDic.put(id, category1);
  }

  public Category1 getCategoryById(int id) {
    return categoryDic.get(id);
  }

  /***
   *
   * @param category1
   * @return all parent categories of a specific Category
   */
  public List<Category1> getAllParenCategories(Category1 category1) {
    List<Category1> result = new ArrayList<>();
    List<Integer> allCategoriesId = new ArrayList<>();
    allCategoriesId = category1.getAllCategories();
    for (Integer id : allCategoriesId) {
      result.add(categoryDic.get(id));
    }
    return result;
  }

  /***
   *
   * @param category1
   * @return all Subcategories of a specific Category
   */
  public List<Category1> getAllSubCategories(Category1 category1) {
    List<Category1> result = new ArrayList<>();
    result.add(category1);
    for (Category1 cat : allCategories) {
      List<Category1> allParent = getAllParenCategories(cat);
      if (allParent.contains(category1)) result.add(cat);
    }
    return result;
  }

  public List<Category1> getAllCategories() {
    return allCategories;
  }

  /**
   * @param name
   * @param main
   * /// add new sub category to already exist category
   */
  public void addNewSubCategory(String name, Category1 main) {
    boolean flag = false;
    for (Category1 cat : allCategories) {
      if (cat.getName() == name && cat.getMain().getId() == main.getId()) {
        flag = true;
        System.out.println("this category already exist");
        break;
      }
    }
    if (!flag) {
      Category1 subCategory1 = new Category1(name, main);
      allCategories.add(subCategory1);
      categoryDic.put(subCategory1.getId(), subCategory1);
    }
  }

  /***
   * @param name
   * ///add new main category
   */
  public void addNewCategory(String name) {
    boolean flag = false;
    for (Category1 cat : allCategories) {
      if (cat.getName() == name) {
        flag = true;
        System.out.println("this category already exist");
        break;
      }
    }

    if (!flag) {
      Category1 category1 = new Category1(name);
      allCategories.add(category1);
      categoryDic.put(category1.getId(), category1);
    }
  }

  public static Hashtable<Integer, Category1> getCategoryDic() {
    return categoryDic;
  }

  public int addNewCategory(Category1 category1) {
    allCategories.add(category1);
    categoryDic.put(category1.getId(), category1);
    return category1.getId();
  }

  public boolean ExistCategory(int id) {
    return allCategories.contains(id);
  }

  public List<Category1> getCategoriesByIds(List<Integer> ids) {
    List<Category1> result = new ArrayList<>();
    for (int id : ids) {
      Category1 category1 = getCategoryById(id);
      result.add(category1);
    }
    return result;
  }

  public List<Category1> getListAllSubCategories(List<Category1> categories) {
    List<Category1> result = new ArrayList<>();
    Set<Category1> set = new HashSet<>();
    for (Category1 category1 : categories) {
      List<Category1> temp = getAllSubCategories(category1);
      set.addAll(temp);
    }

    result.addAll(set);
    return result;
  }
}
