package Presentation_Layer;

import Service_Layer.ProductService;
import Service_Layer.ReportService;

import java.util.Scanner;

public class Program1 {

  public static void main(String[] args) throws Exception {
    ProductService productService = new ProductService();
    ReportService reportService = new ReportService();
//    BranchService branchService = new BranchService();
//    System.out.println("We created for you the first branch its id is 1");



    ProductSystem.getStart();
    Scanner scanner = new Scanner(System.in);

    int choose = scanner.nextInt();
    while (choose != -1) {
      switch (choose) {
//        case 1:
//          ProductsSystem.addProduct(productService);
//          break;
        case 2:
          ProductSystem.addNewCategory(productService );
          break;
//        case 3:
//            ProductsSystem.reciveSupply(productService);
//          break;
        case 4:
          ProductSystem.sellProduct(productService);
          break;
        case 5:
          ProductSystem.setDiscountByCategories(productService);
          break;
        case 6:
          ProductSystem.setDiscountByProducts(productService);
          break;
        case 7:
          ProductSystem.reportFlawProduct(productService);
          break;
        case 8:
          ReportSystem.importInventoryReport(reportService);
          break;
//        case 9:
////          ReportSystem.importProductDiscountHistory(reportService);
//          break;
        case 10:
          ReportSystem.importExpiredAndFlawsReport(reportService);
          break;
//        case 11:
//          ReportSystem.importFlawProductsReport(reportService);
//          break;
//        case 12:
//          ReportSystem.importInventoryReportByCategories(reportService);
//          break;
//        case 13:
//          ReportSystem.importProductSellPriceReport(reportService);
//          break;
        case 14:
          ReportSystem.importDeficientReport(reportService);
          break;
//        case 15:
//          ReportSystem.importFutureExpiredProductds(reportService);
//          break;
        case 16:
          ReportSystem.importInventoryReportByCategories(reportService);
          break;
//        case 17:
//          ReportSystem.importInventoryReportByCategories(reportService);
//          break;
        case 18:
          ReportSystem.importReportByReportId(reportService);
          break;
        case 19:
          ReportSystem.importProductReport(reportService);
          break;
        case 0:
          ProductSystem.getMenu();
          break;
        case -1:
          System.exit(-1);
        default:
          System.out.println("Invalid input");
          break;
      }
      System.out.println("please choose next action, 0 for menu");
      choose = scanner.nextInt();
    }
    // test


  }
}
