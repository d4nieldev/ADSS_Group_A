package Service_Layer;

import BusinessNew.*;

import java.time.LocalDate;
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
    public void importInventoryReportByCategories(int branchId, List<Category> allCategories) {

        InventoryReport inventoryReport = reportController.importInventoryReport(branchId, allCategories);

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

        InventoryReport inventoryReport = reportController.getInventoryReport(reportId);

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

    public void importExpiredAndFlawsReport(int branchId) {
        ExpiredAndFlawReport expiredAndFlawReport = reportController.importExpiredAndFlawReport(branchId);

        // Print header
        System.out.println("====================================================");
        System.out.println("                     FLOW REPORT                     ");
        System.out.println("====================================================");

        // Print column headers
        System.out.format("%-10s%-20s%-15s%-15s%-25s%-25s%-25s%n", "NO.", "NAME", "Category Name", "Specific ID", "FlawDescription" ,"ExpiredDate");

        // Get all flow and expired products
        Set<Integer> productsCode = expiredAndFlawReport.getIdsToName().keySet();
        HashMap<Integer,String> codeToName = expiredAndFlawReport.getIdsToName();
        HashMap<Integer,HashMap<Integer, LocalDate>> idToExpiredSpecificIdAndDate =expiredAndFlawReport.getIdToExpiredSpecificIdAndDate();
        HashMap<Integer , HashMap<Integer,String>> codeToSpecificDescription = expiredAndFlawReport.getCodeToSpecificDescription();
        HashMap<Integer,String> codeToCategory = expiredAndFlawReport.getCodeToCategory();

        int index = 0;
        // Iterate through each general product and print its flow products
        for (Integer productCode : productsCode) {
            HashMap<Integer, String> flowProducts = codeToSpecificDescription.get(productCode);
            for (int id : flowProducts.keySet()) {
                System.out.format("%-10s%-20s%-15s%-15s%-25s%-25s%-25s%n", index++, codeToName.get(productCode), codeToCategory.get(productCode), id, flowProducts.get(id),"X");
            }
        }

        // Iterate through all expired products and print the same details, with "EXPIRED" as the flow description
        for (Integer productCode : productsCode) {
            HashMap<Integer, LocalDate> expiredDate = idToExpiredSpecificIdAndDate.get(productCode);
            for (int id : expiredDate.keySet()) {
                System.out.format("%-10d%-20s%-15d%-15s%-25s%-25s%-25s%n", index++, codeToName.get(productCode), codeToCategory.get(productCode), id, "X","Expired at: " + expiredDate.get(id));
            }
        }

        // Print footer
        System.out.println("====================================================");
    }

    public void importExpiredAndFlawsReportByReportId(int reportId) throws Exception {
        ExpiredAndFlawReport expiredAndFlawReport = reportController.getExpiredAndFlawReport(reportId);

        // Print header
        System.out.println("====================================================");
        System.out.println("                     FLOW REPORT                     ");
        System.out.println("====================================================");

        // Print column headers
        System.out.format("%-10s%-20s%-15s%-15s%-25s%-25s%-25s%n", "NO.", "NAME", "Category Name", "Specific ID", "FlawDescription" ,"ExpiredDate");

        // Get all flow and expired products
        Set<Integer> productsCode = expiredAndFlawReport.getIdsToName().keySet();
        HashMap<Integer,String> codeToName = expiredAndFlawReport.getIdsToName();
        HashMap<Integer,HashMap<Integer, LocalDate>> idToExpiredSpecificIdAndDate =expiredAndFlawReport.getIdToExpiredSpecificIdAndDate();
        HashMap<Integer , HashMap<Integer,String>> codeToSpecificDescription = expiredAndFlawReport.getCodeToSpecificDescription();
        HashMap<Integer,String> codeToCategory = expiredAndFlawReport.getCodeToCategory();

        int index = 0;
        // Iterate through each general product and print its flow products
        for (Integer productCode : productsCode) {
            HashMap<Integer, String> flowProducts = codeToSpecificDescription.get(productCode);
            for (int id : flowProducts.keySet()) {
                System.out.format("%-10s%-20s%-15s%-15s%-25s%-25s%-25s%n", index++, codeToName.get(productCode), codeToCategory.get(productCode), id, flowProducts.get(id),"X");
            }
        }

        // Iterate through all expired products and print the same details, with "EXPIRED" as the flow description
        for (Integer productCode : productsCode) {
            HashMap<Integer, LocalDate> expiredDate = idToExpiredSpecificIdAndDate.get(productCode);
            for (int id : expiredDate.keySet()) {
                System.out.format("%-10d%-20s%-15d%-15s%-25s%-25s%-25s%n", index++, codeToName.get(productCode), codeToCategory.get(productCode), id, "X","Expired at: " + expiredDate.get(id));
            }
        }

        // Print footer
        System.out.println("====================================================");
    }

}
