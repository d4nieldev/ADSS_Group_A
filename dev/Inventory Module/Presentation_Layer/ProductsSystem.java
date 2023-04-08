package Presentation_Layer;

import Business_Layer.GeneralProduct;
import Service_Layer.ProductService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductsSystem {
    public  static Scanner scanner = new Scanner(System.in);
    public static void addProduct(ProductService productService){
        try{
        int id = -1;
        System.out.println("insert product code");
        int code = scanner.nextInt();
        scanner.skip("\n");
        System.out.println("insert Product name");
        String name = scanner.nextLine();
        System.out.println("insert product price");
        double price = scanner.nextInt();
        scanner.skip("\n");
        System.out.println("insert product amount");
        int amount = scanner.nextInt();
        scanner.skip("\n");
        System.out.println("insert Min quantity");
        int minQuantity = scanner.nextInt();
        scanner.skip("\n");
        System.out.println("insert product manufacturer");
        String manufacturer = scanner.nextLine();
        String categoryName = "";
        System.out.println("is the product's category exist? enter y/n");
        String answer = scanner.nextLine();
        if (answer.toLowerCase().equals("y")){
            System.out.println("please enter product lowest category id");
            // Read the user's input as a string
            id = scanner.nextInt();
        }


        else {
            System.out.println("enter new CategoryName");
            categoryName = scanner.nextLine();
        }
        System.out.println("if category is sub category enter its parent category, otherwise -1");
        int parentCategory = scanner.nextInt();
        scanner.skip("\n");

            productService.addNewProduct(name, code, price, manufacturer, minQuantity, amount, id, categoryName, parentCategory);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }




    }
    public static void  reciveSupply(ProductService productService){
        try{
        System.out.println("insert product code");
        int code = scanner.nextInt();
        scanner.skip("\n");
        System.out.println("insert Product name");
        String name = scanner.nextLine();
        System.out.println("insert product price");
        double price = scanner.nextInt();
        scanner.skip("\n");
        System.out.println("insert product amount");
        int amount = scanner.nextInt();
        scanner.skip("\n");
        System.out.println("insert product manufacturer");
        String manufacturer = scanner.nextLine();
        System.out.println("please enter the product expired date : in format YYYY-MM-DD");
        String expireDate = scanner.nextLine();

        int id = -1;

        System.out.println("is the product's category exist? enter y/n");
        String answer = scanner.nextLine();
        String categoryName = "";
        if (answer.equals("y")) {
            System.out.println("please enter product lowest category id");
            // Read the user's input as a string
            id = scanner.nextInt();
            scanner.skip("\n");
        } else {
            System.out.println("enter new CategoryName");
            categoryName = scanner.nextLine();
        }
        GeneralProduct gp = productService.getProductByCode(code);
        if (gp != null) {
            productService.receiveExistSupply(code, name, price, amount, expireDate, manufacturer, id);
        } else {
            System.out.println("enter min Quantity for product");
            int minQuantity = scanner.nextInt();
            scanner.skip("\n");
            System.out.println("if category is sub category enter its parent category, otherwise -1");
            int parentCategory = scanner.nextInt();
            scanner.skip("\n");
            productService.receiveNewtSupply(code, name, price, amount, expireDate, manufacturer, minQuantity, id, categoryName, parentCategory);

        }
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }
    public static void sellProduct(ProductService productService){
        try{
        System.out.println("enter product code");
        int code = scanner.nextInt();
        System.out.println("enter product id");
        int id = scanner.nextInt();
        productService.sellProduct(code,id);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }
    public static void setDiscountByCategories(ProductService productService) {
        try {
            List<Integer> lst = new ArrayList<>();
            int chose = 0;
            while (chose != -1) {
                System.out.println("enter your desire category. -1 if done");
                chose = scanner.nextInt();
                scanner.skip("\n");
                if (chose != -1)
                    lst.add(chose);
            }
            System.out.println("please enter the discount start date : in format YYYY-MM-DD");
            String startDate = scanner.nextLine();
            System.out.println("please enter the discount end date : in format YYYY-MM-DD");
            String endDate = scanner.nextLine();
            System.out.println("please enter discount percentage");
            double discountPercentage = scanner.nextDouble();
            productService.setDiscountByCategories(lst, LocalDate.parse(startDate), LocalDate.parse(endDate), discountPercentage);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }

    }
    public static void setDiscountByProducts(ProductService productService) {
        try{
        List<Integer> lst = new ArrayList<>();
        int chose = 0;
        while (chose != -1) {
            System.out.println("enter your desire product code . -1 if done");
            chose = scanner.nextInt();
            scanner.skip("\n");
            if (chose != -1) {
                lst.add(chose);
            }
        }
        System.out.println("please enter the discount start date : in format YYYY-MM-DD");
        String startDate = scanner.nextLine();
        System.out.println("please enter the discount end date : in format YYYY-MM-DD");
        String endDate = scanner.nextLine();
        System.out.println("please enter discount percentage");
        double discountPercentage = scanner.nextDouble();
        productService.setDiscountByProducts(lst,LocalDate.parse(startDate),LocalDate.parse(endDate),discountPercentage);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }

    }
    public static void reportFlawProduct(ProductService productService){
        System.out.println("Enter general product code");
        int code = scanner.nextInt();
        scanner.skip("\n");
        System.out.println("Enter product id");
        int id = scanner.nextInt();
        scanner.skip("\n");
        System.out.println("Enter flaw description");
        String description = scanner.nextLine();
        productService.reportFlawProduct(code,id,description);
    }
    public static void getMenu() {
        System.out.println("Please choose an action (press 0 for menu):");
        System.out.println("1.  Add new product");
        System.out.println("2.  Import inventory report");
        System.out.println("3.  Receive supply");
        System.out.println("4.  Sell product");
        System.out.println("5.  Set discount - categories");
        System.out.println("6.  Set discount - product");
        System.out.println("7.  Report flaw product");
        System.out.println("8.  Import general product report");
        System.out.println("9.  Import product history discount");
        System.out.println("10. Import expired product report");
        System.out.println("11. Import flaw report");
        System.out.println("12. Import inventory report by categories");
        System.out.println("13. Import buy-sell");
        System.out.println("14. Import shortage report");
    }

    public static void getStart(){

            System.out.println("--------------------------------------------------------");
            System.out.println("              Welcome to the Inventory System             ");
            System.out.println("--------------------------------------------------------");
            System.out.println("Please choose an action (press 0 for menu):");
            System.out.println("1.  Add new product");
            System.out.println("2.  Import inventory report");
            System.out.println("3.  Receive supply");
            System.out.println("4.  Sell product");
            System.out.println("5.  Set discount - categories");
            System.out.println("6.  Set discount - product");
            System.out.println("7.  Report flaw product");
            System.out.println("8.  Import general product report");
            System.out.println("9.  Import product history discount");
            System.out.println("10. Import expired product report");
            System.out.println("11. Import flaw report");
            System.out.println("12. Import inventory report by categories");
            System.out.println("13. Import buy-sell");
            System.out.println("14. Import shortage report");
            System.out.println("--------------------------------------------------------");

    }

}
