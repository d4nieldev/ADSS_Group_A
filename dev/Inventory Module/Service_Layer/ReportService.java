package Service_Layer;

import Business_Layer.ProductController;
import Business_Layer.ReportController;

public class ReportService {

ProductController productController;
ReportController reportController;
int branchId;

public ReportService(ProductService productService){
    this.productController = productService.getProductController();
    this.reportController = new ReportController(productController);
    this.branchId = productService.getBranchId();

}

    /**
     * function for a data report on a specific product
     * @param code
     * @param id
     */
    public void importSpecificProductReport(int code,int id)
    {
        reportController.importSpecificProductReport(code,id);
    }

    /**
     * function for a data report on inventory
     */
    public void importInventoryReport(){
        reportController.importInventoryReport();
    }

    /**
     * function for a data report on a generalProduct
     */
    public void importGeneralProductReport(int code) {
        reportController.importGeneralProductReport(code);
    }
    public void importFlawProductsReport(){
        reportController.importFlawReport();
    }

    public void importExpiredProductReport(){
        reportController.importExpiredProductReport();
    }

    public void  importShortageReport(){
        reportController.importShortageReport();
    }

    public int getBranchId() {return branchId;}
}
