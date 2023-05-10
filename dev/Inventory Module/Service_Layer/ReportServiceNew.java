package Service_Layer;

import BusinessNew.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ReportServiceNew {
    ReportController reportController;
    ProductController productController;
    CategoryController categoryController;

    public ReportServiceNew() {
        this.productController = ProductController.getInstance();
        this.reportController = ReportController.getInstance();
        this.categoryController = CategoryController.getInstance();
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

    public void importReportByReportId(int ReportId) throws Exception {
        if(!(reportController.getAllReports().containsKey(ReportId))){

        }
        else if(reportController.getAllReports().get(ReportId) instanceof InventoryReport){
            importInventoryReportByReportId(ReportId);
        }
        else if(reportController.getAllReports().get(ReportId) instanceof ExpiredAndFlawReport){
            importExpiredAndFlawsReportByReportId(ReportId);
        }
        else if(reportController.getAllReports().get(ReportId) instanceof DeficientReport){
            importDeficientReportByReportId(ReportId);
        }
    }

    public void importInventoryReportByCategories(int branchId, List<Integer> categoriesId) {

        List<Category> allCategories = categoryController.getListAllSubCategoriesByIds(categoriesId);
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
            System.out.printf("%-5d%-20s%-20d%-20d%-20d%n", index, codeToName.get(productCode), productCode, shelfAmount.get(productCode), storageAmount.get(productCode));

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
            System.out.printf("%-5d%-20s%-20d%-20d%-20d%n", index, codeToName.get(productCode), productCode, shelfAmount.get(productCode), storageAmount.get(productCode));

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
        System.out.format("%-10s%-20s%-15s%-20s%-25s%-25s%n", "NO.", "NAME", "Category Name", "Specific ID", "FlawDescription" ,"ExpiredDate");

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
                System.out.format("%-10d%-20s%-15s%-20d%-25s%-25s%n", index++, codeToName.get(productCode), codeToCategory.get(productCode), id, flowProducts.get(id),"X");
            }
        }

        // Iterate through all expired products and print the same details, with "EXPIRED" as the flow description
        for (Integer productCode : productsCode) {
            HashMap<Integer, LocalDate> expiredDate = idToExpiredSpecificIdAndDate.get(productCode);
            for (int id : expiredDate.keySet()) {
                System.out.format("%-10d%-20s%-15s%-20d%-25s%-25s%n", index++, codeToName.get(productCode), codeToCategory.get(productCode), id, "X","Expired at: " + expiredDate.get(id));
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
        System.out.format("%-10s%-20s%-15s%-20s%-25s%-25s%n", "NO.", "NAME", "Category Name", "Specific ID", "FlawDescription" ,"ExpiredDate");

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
                System.out.format("%-10d%-20s%-15s%-20d%-25s%-25s%n", index++, codeToName.get(productCode), codeToCategory.get(productCode), id, flowProducts.get(id),"X");
            }
        }

        // Iterate through all expired products and print the same details, with "EXPIRED" as the flow description
        for (Integer productCode : productsCode) {
            HashMap<Integer, LocalDate> expiredDate = idToExpiredSpecificIdAndDate.get(productCode);
            for (int id : expiredDate.keySet()) {
                System.out.format("%-10d%-20s%-15s%-20d%-25s%-25s%n", index++, codeToName.get(productCode), codeToCategory.get(productCode), id, "X","Expired at: " + expiredDate.get(id));
            }
        }

        // Print footer
        System.out.println("====================================================");
    }

    public void importDeficientReport(int branchId) {

        DeficientReport deficientReport = reportController.importDeficientReport(branchId);

        System.out.println("===============================================");
        System.out.println("          Deficiency Report");
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%n", "NO.", "Product Name", "Code", "TotalAmount" ,"MinQuantity", "IdealQuantity");
        Set<Integer> productsCode = deficientReport.getIdToName().keySet();
        HashMap<Integer,String> codeToName = deficientReport.getIdToName();
        HashMap<Integer,Integer> totalAmount = deficientReport.getIdToTotalAmount();
        HashMap<Integer,Integer> minQuantity = deficientReport.getIdToMinQuantity();
        HashMap<Integer,Integer> idealQuantity = deficientReport.getIdToIdealQuantity();

        int index = 1;
        for (Integer productCode : productsCode) {
            System.out.printf("%-5d%-20s%-10d%-20d%-20d%-20d%n", index, codeToName.get(productCode), productCode, totalAmount.get(productCode), minQuantity.get(productCode), idealQuantity.get(productCode));

            index++;
        }

        System.out.println("===============================================");

    }
    public void importDeficientReportByReportId(int reportId) throws Exception {

        DeficientReport deficientReport = reportController.getDeficientReport(reportId);

        System.out.println("===============================================");
        System.out.println("          Deficiency Report");
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%ns", "NO.", "Product Name", "Code", "TotalAmount" ,"MinQuantity", "IdealQuantity");

        Set<Integer> productsCode = deficientReport.getIdToName().keySet();
        HashMap<Integer,String> codeToName = deficientReport.getIdToName();
        HashMap<Integer,Integer> totalAmount = deficientReport.getIdToTotalAmount();
        HashMap<Integer,Integer> minQuantity = deficientReport.getIdToMinQuantity();
        HashMap<Integer,Integer> idealQuantity = deficientReport.getIdToIdealQuantity();
        int index = 1;
        for (Integer productCode : productsCode) {
            System.out.printf("%-5d%-20s%-10d%-20d%-20d%-20d%n", index, codeToName.get(productCode), productCode, totalAmount.get(productCode), minQuantity.get(productCode), idealQuantity.get(productCode));

            index++;
        }

        System.out.println("===============================================");
    }


}
