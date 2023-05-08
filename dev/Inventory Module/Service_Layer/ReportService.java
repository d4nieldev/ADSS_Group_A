package Service_Layer;

import Business_Layer.ProductController1;
import Business_Layer.ReportController1;

import java.time.LocalDate;
import java.util.List;

public class ReportService {

  ProductController1 productController;
  ReportController1 reportController1;
  int branchId;

  public ReportService(ProductService productService) {
    this.productController = productService.getProductController();
    this.reportController1 = new ReportController1(productController);
    this.branchId = productService.getBranchId();
  }

  /**
   * function for a data report on a specific product
   * @param code
   * @param id
   */
  public void importSpecificProductReport(int code, int id) {
    try {
      reportController1.importSpecificProductReport(code, id);
    } catch (Exception ex) {
      System.out.println(
        "please try again - there is an issue with your choice"
      );
    }
  }

  /**
   * function for a data report on inventory
   */
  public void importInventoryReport() {
    reportController1.importInventoryReport();
  }

  /**
   * function for a data report on a generalProduct
   */
  public void importGeneralProductReport(int code) {
    reportController1.importGeneralProductReport(code);
  }

  public void importFlawProductsReport() {
    reportController1.importFlawReport();
  }

  public void importExpiredProductReport() {
    reportController1.importExpiredProductReport();
  }

  public void importShortageReport() {
    reportController1.importShortageReport();
  }

  public int getBranchId() {
    return branchId;
  }

  public void importProductDiscountHistory(int code) {
    reportController1.getProductDiscountHistoryReport(code);
  }

  public void importInventoryReportByCategories(List<Integer> categoriesIds) {
    reportController1.importInventoryReportByCategoryId(categoriesIds);
  }

  public void importProductSellPriceReport(int code) {
    reportController1.importProductSellPriceReport(code);
  }
  public void importFutureExpiredProduct(LocalDate expiredDate){
    reportController1.importFutureExpiredProducts(expiredDate);
  }
}
