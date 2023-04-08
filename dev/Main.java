import Business_Layer.Branch;
import Business_Layer.GeneralProduct;
import Service_Layer.BranchService;
import Service_Layer.ProductService;
import Service_Layer.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

class Main {
    public static ProductService productService = new ProductService(1);
    public  static ReportService reportService = new ReportService(productService);
    public static  Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Hello to Inventory Module");
        BranchService branchService = new BranchService();
        System.out.println("We created for you the first branch its id is 1");


        Scanner scanner = new Scanner(System.in);
        System.out.println("please choose Action - press 0 for showing the menu");
        System.out.println("1  - add new product");
        System.out.println("2  - import inventory report");
        System.out.println("3  - receive Supply");
        System.out.println("4  - sell product");
        System.out.println("5  - set discount - categories");
        System.out.println("6  - set discount - product");
        System.out.println("7  - import expired product report");
        System.out.println("8  - import general product report");
        System.out.println("9  - import product history discount");
        System.out.println("10 - report flaw product");
        System.out.println("11 - import flaw Report");
        System.out.println("12 - import inventory report -By categories");
        System.out.println("13 - import buy-sell ");
        int choose = scanner.nextInt();
        while (choose != -1) {
            if (choose == 1) {
                addProduct();

            } else if (choose == 2) {
                reportService.importInventoryReport();
            }
             else if (choose == 3) {
                 reciveSupply();
            }

            else if (choose == 4) {
                    sellProduct();
            }
            else if ( choose == 5){
                setDiscountByCategories();
            } else if (choose == 6) {
                setDiscountByProducts();
            } else if (choose == 7) {
                reportService.importExpiredProductReport();
            } else if (choose == 8) {
                System.out.println("Enter general product code");
                int code = scanner.nextInt();
                scanner.skip("\n");
                reportService.importGeneralProductReport(code);
            } else if (choose == 9) {
                System.out.println("Enter general product code");
                int code = scanner.nextInt();
                scanner.skip("\n");
                reportService.importProductDiscountHistory(code);
            } else if (choose == 10) {
              reportFlawProduct();
            } else if (choose == 11) {
                reportService.importFlawProductsReport();
            } else if (choose == 12) {
                importInventoryReportByCategories();
            } else if (choose == 13) {
                System.out.println("Enter general product code");
                int code = scanner.nextInt();
                scanner.skip("\n");
                reportService.importProductSellPriceReport(code);
            } else if (choose == 0) {
                System.out.println("please choose Action - press 0 for showing the menu");
                System.out.println("1  - add new product");
                System.out.println("2  - import inventory report");
                System.out.println("3  - receive Supply");
                System.out.println("4  - sell product");
                System.out.println("5  - set discount - categories");
                System.out.println("6  - set discount - product");
                System.out.println("7  - import expired product report");
                System.out.println("8  - import general product report");
                System.out.println("9  - import product history discount");
                System.out.println("10 - report flaw product");
                System.out.println("11 - import flaw Report");
                System.out.println("12 - import inventory report -By categories");
                System.out.println("13 - import buy-sell ");
            }


            System.out.println("please choose next action ,0 for menu");
            choose = scanner.nextInt();

        }

        }


        public static void addProduct(){
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
            productService.addNewProduct(name,code,price,manufacturer,minQuantity,amount,id,categoryName,parentCategory);




        }
    public static void  reciveSupply(){
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
    public static void sellProduct(){
        System.out.println("enter product code");
    int code = scanner.nextInt();
                System.out.println("enter product id");
    int id = scanner.nextInt();
                productService.sellProduct(code,id);
    }
    public static void setDiscountByCategories() {
        List<Integer> lst = new ArrayList<>();
        int chose = 0;
        while (chose != -1) {
            System.out.println("enter your desire category. -1 if done");
            chose = scanner.nextInt();
            scanner.skip("\n");
            if(chose != -1)
            lst.add(chose);
        }
        System.out.println("please enter the discount start date : in format YYYY-MM-DD");
        String startDate = scanner.nextLine();
        System.out.println("please enter the discount end date : in format YYYY-MM-DD");
        String endDate = scanner.nextLine();
        System.out.println("please enter discount percentage");
        double discountPercentage = scanner.nextDouble();
        productService.setDiscountByCategories(lst,LocalDate.parse(startDate),LocalDate.parse(endDate),discountPercentage);

    }
    public static void setDiscountByProducts() {
        List<Integer> lst = new ArrayList<>();
        int chose = 0;
        while (chose != -1) {
            System.out.println("enter your desire product code . -1 if done");
            chose = scanner.nextInt();
            scanner.skip("\n");

            lst.add(chose);
        }
        System.out.println("please enter the discount start date : in format YYYY-MM-DD");
        String startDate = scanner.nextLine();
        System.out.println("please enter the discount end date : in format YYYY-MM-DD");
        String endDate = scanner.nextLine();
        System.out.println("please enter discount percentage");
        double discountPercentage = scanner.nextDouble();
        productService.setDiscountByProducts(lst,LocalDate.parse(startDate),LocalDate.parse(endDate),discountPercentage);

    }
    public static void reportFlawProduct(){
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
    public static void importInventoryReportByCategories(){
        List<Integer> lst = new ArrayList<>();
        int chose = 0 ;
        while (chose != -1) {
            System.out.println("enter your desire category id  . -1 if done");
            chose = scanner.nextInt();
            scanner.skip("\n");
            lst.add(chose);
        }
        reportService.importInventoryReportByCategories(lst);
    }

    }

//    Scanner scanner = new Scanner(System.in);
//
//        int choose = scanner.nextInt();
//        while (choose != -1) {
//            if (choose == 1) {
//                ProductsSystem.addProduct(productService);
//
//            } else if (choose == 2) {
//                reportService.importInventoryReport();
//            }
//            else if (choose == 3) {
//                ProductsSystem.reciveSupply(productService);
//            }
//
//            else if (choose == 4) {
//                ProductsSystem.sellProduct(productService);
//            }
//            else if ( choose == 5){
//                ProductsSystem.setDiscountByCategories(productService);
//            } else if (choose == 6) {
//               ProductsSystem. setDiscountByProducts(productService);
//            } else if (choose == 7) {
//                ReportSystem.importExpiredProductReport(reportService);
//            } else if (choose == 8) {
//                ReportSystem.importGeneralProductReport(reportService);
//            } else if (choose == 9) {
//                ReportSystem.importProductDiscountHistory(reportService);
//            } else if (choose == 10) {
//                ProductsSystem.reportFlawProduct(productService);
//            } else if (choose == 11) {
//                ReportSystem.importFlawProductsReport(reportService);
//            } else if (choose == 12) {
//                ReportSystem.importInventoryReportByCategories(reportService);
//            } else if (choose == 13) {
//                ReportSystem.importProductSellPriceReport(reportService);
//            } else if (choose == 0) {
//                ProductsSystem.getGuide();
//
//                }
//            System.out.println("please choose next action ,0 for menu");
//            choose = scanner.nextInt();
//            }