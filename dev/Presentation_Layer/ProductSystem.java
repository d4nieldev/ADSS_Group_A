package Presentation_Layer;
import BusinessLayer.Inventory.Global;
import BusinessLayer.InveontorySuppliers.DiscountFixed;
import BusinessLayer.InveontorySuppliers.DiscountPercentage;

import DataAccessLayer.DTOs.DiscountDTO;
import Service_Layer.ProductService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ProductSystem {
//    public  static Scanner scanner = new Scanner(System.in);
    public  static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void addNewCategory(ProductService productService) {
        try {
            System.out.println("enter new categoryName");
            String categoryName = reader.readLine();
            System.out.println("is it subCategory? Y/N");
            String answer = reader.readLine().toLowerCase(Locale.ROOT);
            int parentId = -1;
            if (answer.equals( "y")) {
                System.out.println("enter category parent id");
                parentId = Integer.parseInt(reader.readLine());
            }
            productService.addNewCategory(categoryName, parentId);

        } catch (Exception e) {
            System.out.println("Error occurred - please try again ");
        }
    }
    public static void sellProduct(ProductService productService){
        try{
            System.out.println("enter product code");
            int code = Integer.parseInt(reader.readLine());
            System.out.println("enter product id");
            int id = Integer.parseInt(reader.readLine());
            productService.sellProduct(code,id,1);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }
            public static void setDiscountByCategories(ProductService productService) {
            try {
                List<Integer> lst = new ArrayList<>();
                System.out.println("please branchId");
                int branchId = Integer.parseInt(reader.readLine());
                int chose = 0;
                while (chose != -1) {
                    System.out.println("enter your desire category. -1 if done");
                    chose = Integer.parseInt(reader.readLine());
                    if (chose != -1)
                        lst.add(chose);
                }
                System.out.println("please enter the discount start date : in format YYYY-MM-DD");
                String startDate = reader.readLine();
                System.out.println("please enter the discount end date : in format YYYY-MM-DD");
                String endDate = reader.readLine();
                System.out.println("choose discount kind: 1 - fixedDiscount, 2 - PercentageDiscount");
                int discountKind = Integer.parseInt(reader.readLine());
                if(discountKind == 1) {
                    System.out.println("please enter discount fix number");
                    double fixNumber = Double.parseDouble(reader.readLine());
                    DiscountDTO discountDTO = new DiscountDTO(Global.getNewDiscountId(),LocalDate.parse(startDate),LocalDate.parse(endDate),fixNumber,"Fix Discount");
                    DiscountFixed discount = new DiscountFixed(discountDTO);
                    productService.setDiscountByCategories(branchId,lst,discount);
                }
                else if(discountKind == 2){
                    System.out.println("please enter discount percentage");
                    double discountPercentage = Double.parseDouble(reader.readLine());
                    DiscountDTO discountDTO = new DiscountDTO(Global.getNewDiscountId(),LocalDate.parse(startDate),LocalDate.parse(endDate),discountPercentage,"Percentage Discount");
                    DiscountPercentage discount = new DiscountPercentage(discountDTO);
                    productService.setDiscountByCategories(branchId,lst,discount);
                }
            }
            catch (Exception e){
                System.out.println("Error occurred - please try again ");
            }

        }
        public static void setDiscountByProducts(ProductService productService) {
            try{
                List<Integer> lst = new ArrayList<>();
                int chose = 0;
                System.out.println("please branchId");
                int branchId = Integer.parseInt(reader.readLine());
                while (chose != -1) {
                    System.out.println("enter your desire product code . -1 if done");
                    chose = Integer.parseInt(reader.readLine());
                    if (chose != -1) {
                        lst.add(chose);
                    }
                }
                System.out.println("please enter the discount start date : in format YYYY-MM-DD");
                String startDate = reader.readLine();
                System.out.println("please enter the discount end date : in format YYYY-MM-DD");
                String endDate = reader.readLine();
                System.out.println("choose discount kind: 1 - fixedDiscount, 2 - PercentageDiscount");
                int discountKind = Integer.parseInt(reader.readLine());
                if(discountKind == 1) {
                    System.out.println("please enter discount fix number");
                    double fixNumber = Double.parseDouble(reader.readLine());
                    DiscountDTO discountDTO = new DiscountDTO(Global.getNewDiscountId(),LocalDate.parse(startDate),LocalDate.parse(endDate),fixNumber,"Fix Discount");
                    DiscountFixed discount = new DiscountFixed(discountDTO);
                    productService.setDiscountByProducts(branchId,lst,discount);
                }
                else if(discountKind == 2){
                    System.out.println("please enter discount percentage");
                    double discountPercentage = Double.parseDouble(reader.readLine());
                    DiscountDTO discountDTO = new DiscountDTO(Global.getNewDiscountId(),LocalDate.parse(startDate),LocalDate.parse(endDate),discountPercentage,"Percentage Discount");
                    DiscountPercentage discount = new DiscountPercentage(discountDTO);
                    productService.setDiscountByProducts(branchId,lst,discount);
                }

            }
            catch (Exception e){
                System.out.println("Error occurred - please try again ");
            }

        }
    public static void reportFlawProduct(ProductService productService){
        try {
            System.out.println("Enter branchId");
            int branchId = Integer.parseInt(reader.readLine());
            System.out.println("Enter general product code");
            int code = Integer.parseInt(reader.readLine());
            System.out.println("Enter Specific id");
            int id = Integer.parseInt(reader.readLine());
            System.out.println("Enter flaw description");
            String description = reader.readLine();
            productService.reportFlawProduct(branchId,code, id, description);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
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
        System.out.println("15. Import future expired products");
        System.out.println("-1. close the program");
    }

    public static void getStart(){

        System.out.println("--------------------------------------------------------");
        System.out.println("              Welcome to the Inventory System             ");
        System.out.println("--------------------------------------------------------");
        System.out.println("Please choose an action (press 0 for menu):");
        System.out.println("1.  Add new product");
        System.out.println("2.  Add Category");
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
        System.out.println("15. Import future expired products");
        System.out.println("-1. close the program");
        System.out.println("--------------------------------------------------------");

    }


}
