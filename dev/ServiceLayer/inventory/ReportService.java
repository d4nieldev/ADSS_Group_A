package ServiceLayer.inventory;

import BusinessLayer.Inventory.*;
import BusinessLayer.InveontorySuppliers.ProductController;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReportService {
    ReportController reportController;
    ProductController productController;
    CategoryController categoryController;
    BranchController branchController;

    public ReportService() {
        this.productController = ProductController.getInstance();
        this.reportController = ReportController.getInstance();
        this.categoryController = CategoryController.getInstance();
        this.branchController = BranchController.getInstance();
    }
    public void importInventoryReport(int branchId) throws Exception {

        InventoryReport inventoryReport = reportController.importInventoryReport(branchId);

        System.out.println("===============================================");
        System.out.println("          Inventory Report");
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%n", "NO.", "Product Name", "Code", "Shop Amount" ,"Storage Amount");

        Set<Integer> productsCode = inventoryReport.getIdToName().keySet();
        HashMap<Integer,String> codeToName = inventoryReport.getIdToName();
        Map<Integer,Integer> shelfAmount = inventoryReport.getIdToShelfAmount();
        Map<Integer,Integer> storageAmount = inventoryReport.getIdToStorageAmount();
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

    public void importInventoryReportByCategories(int branchId, List<Integer> categoriesId) throws Exception {

        List<Category> allCategories = categoryController.getListAllSubCategoriesByIds(categoriesId);
        InventoryReport inventoryReport = reportController.importInventoryReport(branchId, allCategories);

        System.out.println("===============================================");
        System.out.println("          Inventory Report");
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%n", "NO.", "Product Name", "Code", "Shop Amount" ,"Storage Amount");

        Set<Integer> productsCode = inventoryReport.getIdToName().keySet();
        HashMap<Integer,String> codeToName = inventoryReport.getIdToName();
        Map<Integer,Integer> shelfAmount = inventoryReport.getIdToShelfAmount();
        Map<Integer,Integer> storageAmount = inventoryReport.getIdToStorageAmount();
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
        Map<Integer,Integer> shelfAmount = inventoryReport.getIdToShelfAmount();
        Map<Integer,Integer> storageAmount = inventoryReport.getIdToStorageAmount();
        int index = 1;
        for (Integer productCode : productsCode) {
            System.out.printf("%-5d%-20s%-20d%-20d%-20d%n", index, codeToName.get(productCode), productCode, shelfAmount.get(productCode), storageAmount.get(productCode));

            index++;
        }

        System.out.println("===============================================");
    }

    public void importExpiredAndFlawsReport(int branchId) throws Exception {
        ExpiredAndFlawReport expiredAndFlawReport = reportController.importExpiredAndFlawReport(branchId);

        // Print header
        System.out.println("====================================================");
        System.out.println("                     FLOW REPORT                     ");
        System.out.println("====================================================");

        // Print column headers
        System.out.format("%-10s%-20s%-15s%-20s%-25s%-25s%n", "NO.", "NAME", "Category Name", "Specific ID", "FlawDescription" ,"ExpiredDate");

        // Get all flow and expired products
        HashMap<Integer,ProductBranch> products = expiredAndFlawReport.getProducts();
        HashMap<Integer, HashMap<Integer, LocalDate>> idToExpiredSpecificIdAndDate = expiredAndFlawReport.getIdToExpiredSpecificIdAndDate();
        HashMap<Integer, HashMap<Integer, String>> codeToSpecificDescription = expiredAndFlawReport.getCodeToSpecificDescription();

        int index = 0;
        // Iterate through each general product and print its flow products
        for (Integer productCode : products.keySet()) {
            HashMap<Integer, String> flowProducts = codeToSpecificDescription.get(productCode);
            for (int id : flowProducts.keySet()) {
                System.out.format("%-10d%-20s%-15s%-20d%-25s%-25s%n", index++, products.get(productCode).getName(), products.get(productCode).getCategoryName(), id, flowProducts.get(id),"X");
            }
        }

        // Iterate through all expired products and print the same details, with "EXPIRED" as the flow description
        for (Integer productCode : products.keySet()) {
            HashMap<Integer, LocalDate> expiredDate = idToExpiredSpecificIdAndDate.get(productCode);
            for (int id : expiredDate.keySet()) {
                System.out.format("%-10d%-20s%-15s%-20d%-25s%-25s%n", index++, products.get(productCode).getName(), products.get(productCode).getCategoryName(), id, "X","Expired at: " + expiredDate.get(id));
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
        HashMap<Integer,ProductBranch> products = expiredAndFlawReport.getProducts();
        HashMap<Integer, HashMap<Integer, LocalDate>> idToExpiredSpecificIdAndDate = expiredAndFlawReport.getIdToExpiredSpecificIdAndDate();
        HashMap<Integer, HashMap<Integer, String>> codeToSpecificDescription = expiredAndFlawReport.getCodeToSpecificDescription();

        int index = 0;
        // Iterate through each general product and print its flow products
        for (Integer productCode : products.keySet()) {
            HashMap<Integer, String> flowProducts = codeToSpecificDescription.get(productCode);
            for (int id : flowProducts.keySet()) {
                System.out.format("%-10d%-20s%-15s%-20d%-25s%-25s%n", index++, products.get(productCode).getName(), products.get(productCode).getCategory(), id, flowProducts.get(id),"X");
            }
        }

        // Iterate through all expired products and print the same details, with "EXPIRED" as the flow description
        for (Integer productCode : products.keySet()) {
            HashMap<Integer, LocalDate> expiredDate = idToExpiredSpecificIdAndDate.get(productCode);
            for (int id : expiredDate.keySet()) {
                System.out.format("%-10d%-20s%-15s%-20d%-25s%-25s%n", index++, products.get(productCode).getName(), products.get(productCode).getCategory(), id, "X","Expired at: " + expiredDate.get(id));
            }
        }

        // Print footer
        System.out.println("====================================================");
    }

    public void importDeficientReport(int branchId) throws Exception {

        DeficientReport deficientReport = reportController.importDeficientReport(branchId);

        System.out.println("===============================================");
        System.out.println("          Deficiency Report");
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%n", "NO.", "Product Name", "Code", "TotalAmount" ,"MinQuantity", "IdealQuantity");

        HashMap<Integer,ProductBranch> products = deficientReport.getProducts();
        Map<Integer, Integer> idToMissingAmount = deficientReport.getIdToMissingAmount();

        int index = 1;
        for (Integer productCode : products.keySet()) {
            System.out.printf("%-5d%-20s%-10d%-20d%-20d%-20d%n", index, products.get(productCode).getName(), productCode, idToMissingAmount.get(productCode));

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

        HashMap<Integer,ProductBranch> products = deficientReport.getProducts();
        Map<Integer, Integer> idToMissingAmount = deficientReport.getIdToMissingAmount();

        int index = 1;
        for (Integer productCode : products.keySet()) {
            System.out.printf("%-5d%-20s%-10d%-20d%-20d%-20d%n", index, products.get(productCode).getName(), productCode, idToMissingAmount.get(productCode));

            index++;
        }

        System.out.println("===============================================");
    }


    public void importProductReport(int branchId,int productCode) throws Exception {
        ProductBranch productBranch = branchController.getBranchById(branchId).getProductByCode(productCode);

        System.out.println("===============================================");
        System.out.println("          ProductBranch Report"                 );
        System.out.println("===============================================");
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%n", "NO.", "Product Name", "Code", "Manufacturer","Shop Amount" ,"Storage Amount");



            System.out.printf("%-5d%-20s%-20d%-20s%-20d%-20d%n", 1, productBranch.getName(), productCode, productBranch.getManufacturer(),productBranch.getOnShelfProduct().size(), productBranch.getTotalAmount() - productBranch.getOnShelfProduct().size());



        System.out.println("===============================================");
    }
}
