package Test;

import Business_Layer.Category;
import Business_Layer.CategoryController;
import Business_Layer.ProductController;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CategoryControllerTest {

    ProductController pc;
    CategoryController cc;

    @Before
    public void setUp() {
        this.pc = new ProductController();
        this.cc = CategoryController.getInstance();
        // create sample categories and add them to the dictionary
        Category category1 = new Category("category1", null);
        Category category2 = new Category("category2", category1);
        Category category3 = new Category("category3", category2);
        Category category4 = new Category("category4", category2);
        Category category5 = new Category("category5", category4);

        CategoryController.putCategory(1, category1);
        CategoryController.putCategory(2, category2);
        CategoryController.putCategory(3, category3);
        CategoryController.putCategory(4, category4);
        CategoryController.putCategory(5, category5);


    }
    @Test
    public void testGetAllParenCategories() {

        Category category = CategoryController.getCategoryDic().get(3); // get the sample category with ID 3
        List<Integer> expected = new ArrayList<>();
        expected.add(CategoryController.getCategoryDic().get(2).getId());
        expected.add(CategoryController.getCategoryDic().get(1).getId());

        List<Integer> actual = category.getAllCategories();
//
        assertEquals(expected, actual);
    }
    @Test
    public void testGetCategoriesByIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(3);

        List<Category> expected = new ArrayList<>();
        expected.add(CategoryController.getCategoryDic().get(1));
        expected.add(CategoryController.getCategoryDic().get(3));

        List<Category> actual = cc.getCategoriesByIds(ids);

        assertEquals(expected, actual);
    }





}