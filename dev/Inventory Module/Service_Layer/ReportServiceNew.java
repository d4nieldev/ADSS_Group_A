package Service_Layer;

import BusinessNew.*;
import Business_Layer.GeneralProduct;

import java.util.HashMap;
import java.util.List;

public class ReportServiceNew {
    ReportController reportController;

    public ReportServiceNew() {
        this.reportController = ReportController.getInstance();
    }
    public void importInventoryReport(int branchId) {

        InventoryReport inventoryReport = reportController.importInventoryReport(branchId);

        System.out.println("===============================================");
        System.out.println("          Inventory Report");
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-10s%-15s%-15s%-15s%-20s%-15s%n", "NO.", "Product Name", "Code", "Price", "Total Qty", "Min Qty", "Manufacturer", "Category", "Storage Amount" ,"Shop Amount");

        HashMap<Integer,ProductBranch> codeToProduct = inventoryReport.importFullInventory();
        int index = 1;
        for (ProductBranch pb : codeToProduct.values()) {
            System.out.printf("%-5d%-20s%-10d%-15.2f%-15d%-15d%-20s%-20s%-20d%-15s%d", index, pb.getName(), pb.getCode(), pb.getPrice(), pb.getTotalAmount(), pb.getMinQuantity(), pb.getManufacturer(), pb.getCategory().getName().toString(),pb.getStorageAmount(),pb.getShopAmont());

            index++;
        }

        System.out.println("===============================================");

    }
    public void importProductBranchReport(int branchId, int productCode){
        InventoryReport inventoryReport = reportController.importInventoryReport(branchId);

    }

}
