package Presentation_Layer;

import Service_Layer.BranchService;
import Service_Layer.ProductService;
import Service_Layer.ReportService;
import java.util.Scanner;

public class Program {

  public static void main(String[] args) {
    ProductService productService = new ProductService(1);
    ReportService reportService = new ReportService(productService);
    BranchService branchService = new BranchService();
    System.out.println("We created for you the first branch its id is 1");

    ProductsSystem.getStart();
    Scanner scanner = new Scanner(System.in);

    int choose = scanner.nextInt();
    while (choose != -1) {
      switch (choose) {
        case 1:
          ProductsSystem.addProduct(productService);
          break;
        case 2:
          reportService.importInventoryReport();
          break;
        case 3:
            ProductsSystem.reciveSupply(productService);
          break;
        case 4:
          ProductsSystem.sellProduct(productService);
          break;
        case 5:
          ProductsSystem.setDiscountByCategories(productService);
          break;
        case 6:
          ProductsSystem.setDiscountByProducts(productService);
          break;
        case 7:
          ProductsSystem.reportFlawProduct(productService);
          break;
        case 8:
          ReportSystem.importGeneralProductReport(reportService);
          break;
        case 9:
          ReportSystem.importProductDiscountHistory(reportService);
          break;
        case 10:
          ReportSystem.importExpiredProductReport(reportService);
          break;
        case 11:
          ReportSystem.importFlawProductsReport(reportService);
          break;
        case 12:
          ReportSystem.importInventoryReportByCategories(reportService);
          break;
        case 13:
          ReportSystem.importProductSellPriceReport(reportService);
          break;
        case 14:
          ReportSystem.importShortageReport(reportService);
          break;
        case 15:
          ReportSystem.importFutureExpiredProductds(reportService);
          break;
        case 0:
          ProductsSystem.getMenu();
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
    //

  }
}
