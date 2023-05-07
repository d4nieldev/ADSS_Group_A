package Test;

import static org.junit.Assert.*;

import Business_Layer.Category1;
import Business_Layer.GeneralProduct;
import Business_Layer.ProductController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class GeneralProductTest {

  ProductController pc;

  @Before
  public void setUp() {
    this.pc = new ProductController();
  }

  @Test
  public void testRemoveItem() {
    // Create a new Product object for testing
    Category1 foodCategory1 = new Category1("Food");
    pc.receiveNewSupply(
      3,
      "Bread",
      1.5,
      20,
      LocalDate.now().plusDays(7),
      "Company C",
      3,
      foodCategory1.getId(),
      null,
      -1
    );
    GeneralProduct gp = pc.getGeneralProductByCode(3);

    // Add the product to the shop
    List<Integer> lst = new ArrayList<>(Arrays.asList(1));

    // Attempt to remove the product from the shop
    boolean removed = gp.removeItem(1, Business_Layer.Enum.Location.SHOP);
    assertFalse(removed);

    gp.transferFromStorageToShop(1, lst);
    removed = gp.removeItem(1, Business_Layer.Enum.Location.SHOP);
    // Check that the product was removed from the shop
    assertTrue(removed);
    assertEquals(0, gp.getShop_quantity());

    // Attempt to remove the product from the storage (which should fail)
    removed = gp.removeItem(1, Business_Layer.Enum.Location.STORAGE);
    ////
    //        // Check that the product was not removed from the storage
    assertFalse(removed);
    assertEquals(19, gp.getStorage_quantity());
    //    }

  }
}
