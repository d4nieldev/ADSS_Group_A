package Service_Layer;

import BusinessNew.*;
import Business_Layer.GeneralProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ReportServiceNew {
    ReportController reportController;
    ProductController productController;

    public ReportServiceNew() {
        this.reportController = ReportController.getInstance();
    }
    public void importInventoryReport(int branchId) {

        InventoryReport inventoryReport = reportController.importInventoryReport(branchId);

        System.out.println("===============================================");
        System.out.println("          Inventory Report");
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%n", "NO.", "Product Name", "Code", "Shop Amount" ,"Storage Amount");

        Set<Integer> productsCode = inventoryReport.getIdToName().keySet();
        HashMap<Integer,String> codeToName = inventoryReport.getIdToName();
        HashMap<Integer,Integer> shelfAmount = inventoryReport.getIdToShelfAmount();
        HashMap<Integer,Integer> storageAmount = inventoryReport.getIdToStorageAmount();
        int index = 1;
        for (Integer productCode : productsCode) {
            System.out.printf("%-5d%-20s%-10d%-20d%-20d%n", index, codeToName.get(productCode), productCode, shelfAmount.get(productCode), storageAmount.get(productCode));

            index++;
        }

        System.out.println("===============================================");

    }
    public void importInventoryReportByReportId(int reportId) throws Exception {

        InventoryReport inventoryReport = reportController.getReport(reportId);

        System.out.println("===============================================");
        System.out.println("          Inventory Report");
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%n", "NO.", "Product Name", "Code", "Shop Amount" ,"Storage Amount");

        Set<Integer> productsCode = inventoryReport.getIdToName().keySet();
        HashMap<Integer,String> codeToName = inventoryReport.getIdToName();
        HashMap<Integer,Integer> shelfAmount = inventoryReport.getIdToShelfAmount();
        HashMap<Integer,Integer> storageAmount = inventoryReport.getIdToStorageAmount();
        int index = 1;
        for (Integer productCode : productsCode) {
            System.out.printf("%-5d%-20s%-10d%-20d%-20d%n", index, codeToName.get(productCode), productCode, shelfAmount.get(productCode), storageAmount.get(productCode));

            index++;
        }

        System.out.println("===============================================");
    }


    public void importDeficientReport(int branchId) {

        DeficientReport deficientReport = reportController.importDeficientReport(branchId);

        System.out.println("===============================================");
        System.out.println("          Deficiency Report");
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%n", "NO.", "Product Name", "Code", "TotalAmount" ,"MinQuantity", "IdealQuantity");
        Set<Integer> productsCode = deficientReport.getIdToName().keySet();
        HashMap<Integer,String> codeToName = deficientReport.getIdToName();
        HashMap<Integer,Integer> totalAmount = deficientReport.getIdToTotalAmount();
        HashMap<Integer,Integer> minQuantity = deficientReport.getIdToMinQuantity();
        HashMap<Integer,Integer> idealQuantity = deficientReport.getIdToIdealQuantity();

        int index = 1;
        for (Integer productCode : productsCode) {
            System.out.printf("%-5d%-20s%-10d%-20d%-20d%n", index, codeToName.get(productCode), productCode, totalAmount.get(productCode), minQuantity.get(productCode), idealQuantity.get(productCode));

            index++;
        }

        System.out.println("===============================================");

    }
    public void importDeficientReportByReportId(int reportId) throws Exception {

        DeficientReport deficientReport = reportController.getDeficientReport(reportId);

        System.out.println("===============================================");
        System.out.println("          Deficiency Report");
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%n", "NO.", "Product Name", "Code", "TotalAmount" ,"MinQuantity", "IdealQuantity");

        Set<Integer> productsCode = deficientReport.getIdToName().keySet();
        HashMap<Integer,String> codeToName = deficientReport.getIdToName();
        HashMap<Integer,Integer> totalAmount = deficientReport.getIdToTotalAmount();
        HashMap<Integer,Integer> minQuantity = deficientReport.getIdToMinQuantity();
        HashMap<Integer,Integer> idealQuantity = deficientReport.getIdToIdealQuantity();
        int index = 1;
        for (Integer productCode : productsCode) {
            System.out.printf("%-5d%-20s%-10d%-20d%-20d%n", index, codeToName.get(productCode), productCode, totalAmount.get(productCode), minQuantity.get(productCode), idealQuantity.get(productCode));

            index++;
        }

        System.out.println("===============================================");
    }


}
