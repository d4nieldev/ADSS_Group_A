//package InventoryTest;
//
//import Business_Layer.*;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//
//public class ProductController1Test {
//
//  ProductController1 pc;
//  private ByteArrayOutputStream outContent;
//
//  @Before
//  public void setUp() {
//    this.pc = new ProductController1();
//    this.outContent = new ByteArrayOutputStream();
//  }
//
//  @Test
//  public void testAddNewGeneralProduct() {
//    String name = "test";
//    int code = 1;
//    double price = 12;
//    String manufacturer = "Tnuva";
//    int minQuanitity = 3;
//    int totalQuantity = 10;
//
//    Category1 category1 = new Category1("Milk");
//    GeneralProduct gp = new GeneralProduct(
//      name,
//      code,
//      price,
//      manufacturer,
//      minQuanitity,
//            category1
//    );
//    pc.addNewGeneralProduct(gp, totalQuantity);
//
//    assertEquals(code, gp.getCode());
//    assertEquals(name, gp.getName());
//    assertEquals(manufacturer, gp.getManufacturer());
//    assertEquals(minQuanitity, gp.getMin_quantity());
//    assertEquals(totalQuantity, gp.getTotal_quantity());
//    assertEquals(price, gp.getPrice(), 0);
//  }
//
//  @Test
//  public void testGetGeneralProductByCode() {
//    int code = 1;
//    String name = "test";
//    double price = 12;
//    String manufacturer = "Tnuva";
//    int minQuanitity = 3;
//    int totalQuantity = 10;
//
//    GeneralProduct gp = pc.getGeneralProductByCode(code);
//    assertNull(gp);
//
//    Category1 category1 = new Category1("Milk");
//    gp =
//      new GeneralProduct(
//        name,
//        code,
//        price,
//        manufacturer,
//        minQuanitity,
//              category1
//      );
//    pc.addNewGeneralProduct(gp, totalQuantity);
//    GeneralProduct newGp = pc.getGeneralProductByCode(code);
//    assertEquals(code, newGp.getCode());
//    assertEquals(name, newGp.getName());
//    assertEquals(manufacturer, newGp.getManufacturer());
//    assertEquals(minQuanitity, newGp.getMin_quantity());
//    assertEquals(totalQuantity, newGp.getTotal_quantity());
//    assertEquals(price, newGp.getPrice(), 0);
//  }
//
//  @Test
//  public void testReceiveNewSupply() {
//    int code = 1234;
//    String name = "Test Product";
//    double price = 10.0;
//    int amount = 100;
//    LocalDate expiredDate = LocalDate.of(2023, 1, 1);
//    String manufacturer = "Test Manufacturer";
//    int minQuantity = 10;
//    int categoryId = 0;
//    String categoryName = "Test Category";
//    int categoryParentId = -1;
//
//    pc.receiveNewSupply(
//      code,
//      name,
//      price,
//      amount,
//      expiredDate,
//      manufacturer,
//      minQuantity,
//      categoryId,
//      categoryName,
//      categoryParentId
//    );
//
//    List<GeneralProduct> allGeneralProducts = pc.getAllGeneralProducts();
//    assertEquals(1, allGeneralProducts.size());
//
//    GeneralProduct gp = allGeneralProducts.get(0);
//    assertEquals(code, gp.getCode());
//    assertEquals(name, gp.getName());
//    assertEquals(price, gp.getPrice(), 0.001);
//    assertEquals(manufacturer, gp.getManufacturer());
//    assertEquals(minQuantity, gp.getMin_quantity());
//
//    Category1 category1 = gp.getCategory();
//    assertNotNull(category1);
//    assertEquals(categoryName, category1.getName());
//    assertNull(category1.getMain());
//  }
//
//  @Test
//  public void testTransferFromStorageToShopWithCategories() {
//    // Set up test data
//    CategoryController1 categoryController1 = CategoryController1.getInstance();
//    Category1 foodCategory1 = new Category1("Food");
//    Category1 dairyCategory1 = new Category1("Dairy", foodCategory1);
//    categoryController1.addNewCategory(foodCategory1);
//    categoryController1.addNewCategory(dairyCategory1);
//
//    pc.receiveNewSupply(
//      1,
//      "Milk",
//      2.5,
//      10,
//      LocalDate.now().plusMonths(1),
//      "Company A",
//      2,
//      dairyCategory1.getId(),
//      null,
//      -1
//    );
//    pc.receiveNewSupply(
//      2,
//      "Cheese",
//      4.5,
//      5,
//      LocalDate.now().plusMonths(2),
//      "Company B",
//      1,
//      dairyCategory1.getId(),
//      null,
//      -1
//    );
//    pc.receiveNewSupply(
//      3,
//      "Bread",
//      1.5,
//      20,
//      LocalDate.now().plusDays(7),
//      "Company C",
//      3,
//      foodCategory1.getId(),
//      null,
//      -1
//    );
//
//    // Test transferring products to shop
//    List<Integer> transferredIds = pc.transferFromStorageToShop(1, 5);
//
//    // Verify results
//    assertEquals(5, transferredIds.size());
//    GeneralProduct milkProduct = pc.getGeneralProductByCode(1);
//    assertEquals(5, milkProduct.getShop_quantity());
//    assertEquals(5, milkProduct.getStorage_quantity());
//    assertEquals(5, milkProduct.getProductSupply().get(0).getShopAmount());
//    assertEquals(5, milkProduct.getProductSupply().get(0).getStorageAmount());
//  }
//
//  @Test
//  public void testReturnProduct() {
//    Category1 foodCategory1 = new Category1("Food");
//    Category1 dairyCategory1 = new Category1("Dairy", foodCategory1);
//    // Add a new general product with code 1
//    pc.receiveNewSupply(
//      1,
//      "Milk",
//      2.5,
//      10,
//      LocalDate.now().plusMonths(1),
//      "Company A",
//      2,
//      dairyCategory1.getId(),
//      null,
//      -1
//    );
//    // Sell the product with ID 1 from the shop
//    pc.sellProduct(1, 0);
//    // Return the product to the shop
//    pc.returnProduct(1, 0);
//    // Verify that the product was returned to the shop
//    assertTrue(pc.getGeneralProductByCode(1).getOnShelf().contains(0));
//  }
//
//  @Test
//  public void testSellProduct() {
//    Category1 foodCategory1 = new Category1("Food");
//    Category1 dairyCategory1 = new Category1("Dairy", foodCategory1);
//    // Add a new general product with code 1
//    pc.receiveNewSupply(
//      1,
//      "Milk",
//      2.5,
//      10,
//      LocalDate.now().plusMonths(1),
//      "Company A",
//      2,
//      dairyCategory1.getId(),
//      null,
//      -1
//    );
//    // Sell the product with ID 1 from the shop
//    pc.sellProduct(1, 0);
//
//    // Verify that the product was took from the shop
//    assertFalse(pc.getGeneralProductByCode(1).getOnShelf().contains(0));
//    assertFalse(pc.getGeneralProductByCode(1).getOnStorage().contains(0));
//  }
//
//  @Test
//  public void testGetSupplyByCodeId() {
//    Category1 foodCategory1 = new Category1("Food");
//    pc.receiveNewSupply(
//      3,
//      "Bread",
//      1.5,
//      20,
//      LocalDate.now().plusDays(7),
//      "Company C",
//      3,
//      foodCategory1.getId(),
//      null,
//      -1
//    );
//    Supply sp = pc.getSupplyByCodeId(3, 0);
//    // Add some products to the Supply
//
//    List<Integer> ids = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
//    sp.setIds((ArrayList<Integer>) ids);
//
//    // Get the Supply by code and id
//    Supply result = pc.getSupplyByCodeId(3, 3);
//
//    // Check that the correct Supply is returned
//    assertNotNull(result);
//    assertEquals(sp, result);
//  }
//
//  @Test
//  public void testAlertForMinimumQuantity() {
//    Category1 foodCategory1 = new Category1("Food");
//    pc.receiveNewSupply(
//      3,
//      "Bread",
//      1.5,
//      5,
//      LocalDate.now().plusDays(7),
//      "Company C",
//      2,
//      foodCategory1.getId(),
//      null,
//      -1
//    );
//    GeneralProduct gp = pc.getGeneralProductByCode(3);
//    // Create a GeneralProduct with a minimum quantity of 10 and a total quantity of 5
//    List<Integer> ids = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
//    pc.sellProduct(3, 1);
//
//    // Create a ProductController and call AlertForMinimumQuantity with the test product
//
//
//    // Verify that the console output contains the expected warning message
//    assertEquals("", outContent.toString().trim());
//  }
//}
