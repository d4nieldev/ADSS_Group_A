package Tests.InventoryTest;//package InventoryTest;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.InveontorySuppliers.ProductController;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

  ProductController productController;
  CategoryController categoryController;

  @Before
  public void setUp() throws SQLException {
    this.productController = ProductController.getInstance();
    this.categoryController = CategoryController.getInstance();
    // create sample categories and add them to the dictionary
      categoryController.addNewCategory("category1" );
    Category category1 = categoryController.getCategoryById(0);
      categoryController.addNewCategory("category2",category1 );
    Category category2 = categoryController.getCategoryById(1);
      categoryController.addNewCategory("category3",category1 );
    Category category3 = categoryController.getCategoryById(2);
      categoryController.addNewCategory("category4",category3 );
    Category category4 = categoryController.getCategoryById(3);

//    CategoryController.putCategory(1, category1);
//    CategoryController.putCategory(2, category12);
//    CategoryController.putCategory(3, category13);
//    CategoryController.putCategory(4, category14);
//    CategoryController.putCategory(5, category15);
  }

  @Test
  public void testGetAllParenCategories() {
      // Arrange
    Category category1 = categoryController.getCategoryById(0); // get the sample category with ID 3
    Category category2 = categoryController.getCategoryById(2); // get the sample category with ID 3
    Category category4 = categoryController.getCategoryById(3);
    List<Category> expected = new ArrayList<>();
    expected.add(category1);
    expected.add(category2);
    // Act
    List<Category> actual = category4.getAllParentCategories();
    // Assert
    assertEquals(expected.size(), actual.size());
  }

  @Test
  public void testGetCategoriesByIds() {
    List<Integer> ids = new ArrayList<>();
    ids.add(1);
    ids.add(3);

    List<Category> actual = categoryController.getCategoriesByIds(ids);

    assertEquals(ids.size(), actual.size());
  }
}
