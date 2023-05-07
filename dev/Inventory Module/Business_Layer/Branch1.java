package Business_Layer;

public class Branch1 {

  private int branchId;
  private ProductController productController;
  private ReportController1 reportController1;

  public Branch1(int branchId) {
    this.branchId = branchId;
    this.productController = new ProductController();
    this.reportController1 = new ReportController1(productController);
  }

  public int getId() {
    return this.branchId;
  }

  public ProductController getProductController() {
    return productController;
  }

  public ReportController1 getReportController() {
    return reportController1;
  }
}
