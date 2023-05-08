package Test;

import static org.junit.Assert.*;

import Business_Layer.Category1;
import Business_Layer.CategoryController1;
import Business_Layer.ProductController1;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class Category1ControllerTest {

  ProductController1 pc;
  CategoryController1 cc;

  @Before
  public void setUp() {
    this.pc = new ProductController1();
    this.cc = CategoryController1.getInstance();
    // create sample categories and add them to the dictionary
    Category1 category1 = new Category1("category1", null);
    Category1 category12 = new Category1("category2", category1);
    Category1 category13 = new Category1("category3", category12);
    Category1 category14 = new Category1("category4", category12);
    Category1 category15 = new Category1("category5", category14);

    CategoryController1.putCategory(1, category1);
    CategoryController1.putCategory(2, category12);
    CategoryController1.putCategory(3, category13);
    CategoryController1.putCategory(4, category14);
    CategoryController1.putCategory(5, category15);
  }

  @Test
  public void testGetAllParenCategories() {
    Category1 category1 = CategoryController1.getCategoryDic().get(3); // get the sample category with ID 3
    List<Integer> expected = new ArrayList<>();
    expected.add(CategoryController1.getCategoryDic().get(2).getId());
    expected.add(CategoryController1.getCategoryDic().get(1).getId());

    List<Integer> actual = category1.getAllCategories();
    //
    assertEquals(expected, actual);
  }

  @Test
  public void testGetCategoriesByIds() {
    List<Integer> ids = new ArrayList<>();
    ids.add(1);
    ids.add(3);

    List<Category1> expected = new ArrayList<>();
    expected.add(CategoryController1.getCategoryDic().get(1));
    expected.add(CategoryController1.getCategoryDic().get(3));

    List<Category1> actual = cc.getCategoriesByIds(ids);

    assertEquals(expected, actual);
  }
}
