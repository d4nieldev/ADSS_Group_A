import Business_Layer.Branch;
import Service_Layer.BranchService;
import Service_Layer.ProductService;
import Service_Layer.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        System.out.println("Hello to Inventory Module");
        BranchService branchService = new BranchService();
        System.out.println("We created for you the first branch its id is 1");
        ProductService productService = new ProductService(1);
        ReportService reportService = new ReportService(productService);

        Scanner scanner = new Scanner(System.in);
        System.out.println("please choose Action");
        System.out.println("1 - add new product");
        System.out.println("2 - import inventory report");
        System.out.println("3 - receive Supply");
        System.out.println("4 - sell product");
        System.out.println("5 - set discount - product");
        System.out.println("6 - set discount - categories");
        System.out.println("7 - import expired product report");
        System.out.println("8 - import general product report");
        System.out.println("9 - import product history discount");
        System.out.println("10 - report flaw product");
        System.out.println("11 - import flaw Report");
        System.out.println("12 - import inventory repor -By categories");
        System.out.println("13 - import buy-sell ");
        int choose = scanner.nextInt();
        while (choose != -1)
        {
            if (choose == 1){

                productService.addNewProduct("Milk", 1,100,"Tnuva",100,100);
                productService.addNewProduct("TestSubCategoey", 3,100,"Tnuva",100,100);
//                productService.addNewProduct("Shoko", 2,10,"Tnuva",100,222);;

            }
            else if(choose == 2)
            {
                reportService.importInventoryReport();

            }
            else if(choose == 3){
                productService.receiveSupply(2,"Shoko",100,5,"2023-04-05","Yotveta");
            }
            else if (choose == 4) {
                System.out.println("enter product code");
                int code = scanner.nextInt();
                System.out.println("enter product id");
                int id = scanner.nextInt();
                productService.sellProduct(code,id);
            }
            else if ( choose == 5){
                List<Integer> lst = new ArrayList<>();
                lst.add(0);
                lst.add(1);
                lst.add(2);
                productService.setDiscountByProducts(lst, LocalDate.parse("2023-04-07"),LocalDate.parse("2023-04-07"),20);
            } else if (choose == 6) {
                List<Integer> ids = new ArrayList<>();
                ids.add(0);
                ids.add(1);
                ids.add(2);
                productService.setDiscountByProducts(ids, LocalDate.parse("2023-04-06"),LocalDate.parse("2023-04-07"),20);
            } else if (choose == 7) {
                reportService.importExpiredProductReport();
            } else if (choose == 8) {
                reportService.importGeneralProductReport(2);
            } else if (choose == 9) {
                reportService.importProductDiscountHistory(2);
            } else if (choose == 10) {
                productService.reportFlawProduct(2,1,"ISSUE WITH THE BOTTLE");
            } else if (choose == 11) {
                reportService.importFlawProductsReport();
            } else if (choose == 12) {
                List<Integer> lst = new ArrayList<>();
                lst.add(0);
//                lst.add(1);
                reportService.importInventoryReportByCategories(lst);
            } else if (choose == 13) {
                reportService.importProductSellPriceReport(2);
            }


            System.out.println("please choose next action");
            choose = scanner.nextInt();

        }





    }
}