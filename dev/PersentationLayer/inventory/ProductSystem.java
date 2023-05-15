package PersentationLayer.inventory;

import BusinessLayer.Inventory.Global;
import BusinessLayer.InveontorySuppliers.DiscountFixed;
import BusinessLayer.InveontorySuppliers.DiscountPercentage;
import DataAccessLayer.DTOs.DiscountDTO;
import ServiceLayer.Suppliers.ReservationService;
import ServiceLayer.inventory.ProductService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ProductSystem {
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ProductService productService;

    static {
        try {
            productService = new ProductService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //    static {
//        try {
//            productService = new ProductService();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
    private static ReservationService reservationService = ReservationService.create();

    public static int addNewCategory() {
        int newCategoryId = -1;
        try {
            System.out.println("enter new categoryName");
            String categoryName = reader.readLine();
            System.out.println("is it subCategory? Y/N");
            String answer = reader.readLine().toLowerCase(Locale.ROOT);
            int parentId = -1;
            if (answer.equals("y")) {
                System.out.println("enter category parent id");
                parentId = Integer.parseInt(reader.readLine());
            }
            newCategoryId = productService.addNewCategory(categoryName, parentId);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newCategoryId;
    }

    public static void addNewProduct() {
        int categoryId = -1;
        try {
            System.out.println("please enter product code");
            int code = Integer.parseInt(reader.readLine());
            System.out.println("enter product's name");
            String name = reader.readLine();
            System.out.println("enter product's manufacturer");
            String manufacturer = reader.readLine();
            System.out.println("does the product have new Category? Y/N");
            String answer = reader.readLine().toLowerCase(Locale.ROOT);
            if (answer.equals("y")) {
                categoryId = addNewCategory();
            }

            else {
                System.out.println("enter category id");
                categoryId = Integer.parseInt(reader.readLine());
            }
            productService.addNewProduct(code, name, manufacturer, categoryId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void sellProduct() {
        try {
            System.out.println("please branchId");
            int branchId = Integer.parseInt(reader.readLine());
            System.out.println("enter product code");
            int code = Integer.parseInt(reader.readLine());
            System.out.println("enter SpecificProduct id");
            int specificProductId = Integer.parseInt(reader.readLine());
            productService.sellProduct(branchId, code, specificProductId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setDiscountByCategories() {
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
            if (discountKind == 1) {
                System.out.println("please enter discount fix number");
                double fixNumber = Double.parseDouble(reader.readLine());
                DiscountDTO discountDTO = new DiscountDTO(Global.getNewDiscountId(), LocalDate.parse(startDate),
                        LocalDate.parse(endDate), fixNumber, "Fixed");
                DiscountFixed discount = new DiscountFixed(discountDTO);
                productService.setDiscountByCategories(branchId, lst, discount);
            } else if (discountKind == 2) {
                System.out.println("please enter discount percentage");
                double discountPercentage = Double.parseDouble(reader.readLine());
                DiscountDTO discountDTO = new DiscountDTO(Global.getNewDiscountId(), LocalDate.parse(startDate),
                        LocalDate.parse(endDate), discountPercentage, "Precentage");
                DiscountPercentage discount = new DiscountPercentage(discountDTO);
                productService.setDiscountByCategories(branchId, lst, discount);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void setDiscountByProducts() {
        try {
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
            if (discountKind == 1) {
                System.out.println("please enter discount fix number");
                double fixNumber = Double.parseDouble(reader.readLine());
                DiscountDTO discountDTO = new DiscountDTO(Global.getNewDiscountId(), LocalDate.parse(startDate),
                        LocalDate.parse(endDate), fixNumber, "Fix Discount");
                DiscountFixed discount = new DiscountFixed(discountDTO);
                productService.setDiscountByProducts(branchId, lst, discount);
            } else if (discountKind == 2) {
                System.out.println("please enter discount percentage");
                double discountPercentage = Double.parseDouble(reader.readLine());
                DiscountDTO discountDTO = new DiscountDTO(Global.getNewDiscountId(), LocalDate.parse(startDate),
                        LocalDate.parse(endDate), discountPercentage, "Percentage Discount");
                DiscountPercentage discount = new DiscountPercentage(discountDTO);
                productService.setDiscountByProducts(branchId, lst, discount);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void reportFlawProduct() {
        try {
            System.out.println("Enter branchId");
            int branchId = Integer.parseInt(reader.readLine());
            System.out.println("Enter general product code");
            int code = Integer.parseInt(reader.readLine());
            System.out.println("Enter Specific id");
            int id = Integer.parseInt(reader.readLine());
            System.out.println("Enter flaw description");
            String description = reader.readLine();
            productService.reportFlawProduct(branchId, code, id, description);
        } catch (Exception e) {
            System.out.println("Error occurred - please try again ");
        }
    }

    public static void addPeriodicReservation() {
        try {
            System.out.println("enter desire supplierId");
            int supplierId = Integer.parseInt(reader.readLine());
            System.out.println("enter desire branchId");
            int branchId = Integer.parseInt(reader.readLine());
            System.out.println("enter desire day for reservation to arrive");
            DayOfWeek dayofWeek = DayOfWeek.valueOf(reader.readLine().toUpperCase(Locale.ROOT));
            HashMap<Integer, Integer> productToAmount = getProductToAmount();
            reservationService.addPeriodicReservation(supplierId, branchId, dayofWeek, productToAmount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void changePeriodicReservation() {
        try {
            // System.out.println("enter desire supplierId");
            // int supplierId =Integer.parseInt(reader.readLine());
            // System.out.println("enter desire branchId");
            // int branchId =Integer.parseInt(reader.readLine());
            // System.out.println("enter product code to change amount");
            // int productCode = Integer.parseInt(reader.readLine());
            // reservationService.a(supplierId,branchId,dayofWeek,productToAmount );
            addPeriodicReservation();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static HashMap<Integer, Integer> getProductToAmount() {
        HashMap<Integer, Integer> result = new HashMap<>();
        try {
            int chose = 0;
            while (chose != -1) {
                System.out.println("enter your desire product code . -1 if done");
                chose = Integer.parseInt(reader.readLine());
                if (chose != -1) {
                    System.out.println("enter desire amount for product: " + chose);
                    int desireAmount = Integer.parseInt(reader.readLine());
                    result.put(chose, desireAmount);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static void getMenu() {
        System.out.println("Please choose an action (press 0 for menu):");
        System.out.println("1.  Add new product");
        System.out.println("2.  Add Category");
//        System.out.println("3.  Receive supply");
        System.out.println("3.  Sell product");
        System.out.println("4.  Set discount - categories");
        System.out.println("5.  Set discount - product");
        System.out.println("6.  Report flaw product");
        System.out.println("7.  Import inventory Report");
        System.out.println("8.  Import expired and flaws report");
        System.out.println("9. Import deficiency report");
        System.out.println("10. Import inventory report by categories");
        System.out.println("11. Import report by report id");
        System.out.println("12. Import product report");
        System.out.println("13. Import shortage report");
//        System.out.println("14. Import future expired products");
        System.out.println("-1. close the program");
    }

    public static void getStart() {

        System.out.println("--------------------------------------------------------");
        System.out.println("              Welcome to the Inventory System             ");
        System.out.println("--------------------------------------------------------");
        System.out.println("Please choose an action (press 0 for menu):");
        System.out.println("1.  Add new product");
        System.out.println("2.  Add Category");
//        System.out.println("3.  Receive supply");
        System.out.println("3.  Sell product");
        System.out.println("4.  Set discount - categories");
        System.out.println("5.  Set discount - product");
        System.out.println("6.  Report flaw product");
        System.out.println("7.  Import inventory Report");
        System.out.println("8.  Import expired and flaws report");
        System.out.println("9. Import deficiency report");
        System.out.println("10. Import inventory report by categories");
        System.out.println("11. Import report by report id");
        System.out.println("12. Import product report");
        System.out.println("13. Import shortage report");
//        System.out.println("14. Import future expired products");
        System.out.println("-1. close the program");
        System.out.println("--------------------------------------------------------");

    }

}