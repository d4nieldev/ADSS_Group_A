package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.Branch;
import DataAccessLayer.DAOs.*;
import DataAccessLayer.DTOs.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ReportController {
    private HashMap<Integer, Report> allReports;
    private static ReportController instance = null;
    private InventoryReportEntryDAO inventoryReportEntryDAO;
    private ExpiredAndFlawReportEntryDAO expiredAndFlawReportEntryDAO;
    private DeficiencyReportEntryDAO deficiencyReportEntryDAO;
    private ReportDAO reportDAO;

    private ReportController() {
        this.allReports = new HashMap<>();
        this.inventoryReportEntryDAO = InventoryReportEntryDAO.getInstance();
        this.expiredAndFlawReportEntryDAO = ExpiredAndFlawReportEntryDAO.getInstance();
        this.deficiencyReportEntryDAO = DeficiencyReportEntryDAO.getInstance();
        this.reportDAO = ReportDAO.getInstance();
        this.inventoryReportEntryDAO = InventoryReportEntryDAO.getInstance();
    }
    public static ReportController getInstance(){
        if (instance == null) {
            instance = new ReportController();
        }
        return instance;
    }

    // TODO : change brnachId to Branch object;
    public HashMap<Integer, Report> getAllReports() {
        return this.allReports;
    }

//    public InventoryReport importInventoryReport(int branchId, List<Category> categoryList) {
//        Branch branch = BranchController.getInstance().getBranchById(branchId);
//        InventoryReport report = new InventoryReport(branch, categoryList);
//        allReports.put(report.getId(), report);
//        return report;
//    }

    //TODO: importInventroyReport- regular one without categories filter
//    public InventoryReport importInventoryReport(int branchId) throws SQLException {
//        Branch branch = BranchController.getInstance().getBranchById(branchId);
//        int reportID = Global.getNewReportId();
//        ReportDTO repDTO = new ReportDTO(reportID, branchId, LocalDate.now());
//        reportDAO.insert(repDTO);
//
//    }
    public InventoryReport importInventoryReport(int branchId, List<Category> categoryList) throws Exception {
        Branch branch = BranchController.getInstance().getBranchById(branchId);
        int reportID = Global.getNewReportId();
        ReportDTO repDTO = new ReportDTO(reportID, branchId, LocalDate.now());
        reportDAO.insert(repDTO);

        for (ProductBranch productBranch : branch.getAllProductBranches().values()) {
            int code = productBranch.getCode();
            int shelfAmount = productBranch.getOnShelfProduct().size();
            int storageAmount = productBranch.getTotalAmount() - productBranch.getOnShelfProduct().size();

            InventoryReportEntryDTO invRepEnDTO = new InventoryReportEntryDTO(reportID, code, shelfAmount, storageAmount);
            inventoryReportEntryDAO.insert(invRepEnDTO);
        }

        InventoryReportDTO invRepDTO = inventoryReportEntryDAO.getFullReportById(reportID);
        HashMap<Integer, String> idsToName = branch.getIdsToNameByCategories(categoryList);

        InventoryReport report = new InventoryReport(invRepDTO, idsToName);
        allReports.put(report.getId(), report);
        return report;
    }

//    public ExpiredAndFlawReport importExpiredAndFlawReport(int branchId) {
//        ExpiredAndFlawReport report = new ExpiredAndFlawReport(branchId);
//        allReports.put(report.getId(), report);
//        return report;
//    }

    public ExpiredAndFlawReport importExpiredAndFlawReport(int branchId) throws Exception {
        Branch branch = BranchController.getInstance().getBranchById(branchId);
        int reportID = Global.getNewReportId();
        ReportDTO repDTO = new ReportDTO(reportID, branchId, LocalDate.now());
        reportDAO.insert(repDTO);

        HashMap<Integer, List<SpecificProduct>> productSpecificsExpired = branch.getExpiredProducts();
        for (Integer productCode : productSpecificsExpired.keySet()) {
            for (SpecificProduct specificProduct : productSpecificsExpired.get(productCode)) {
                int specificId = specificProduct.getSpecificId();
                ExpiredAndFlawReportEntryDTO expAndFlawEntDTO = new ExpiredAndFlawReportEntryDTO(reportID, specificId);
                expiredAndFlawReportEntryDAO.insert(expAndFlawEntDTO);
            }
        }
        ExpiredAndFlawReportDTO expAndFlawDTO = expiredAndFlawReportEntryDAO.getFullReportById(reportID);

        ExpiredAndFlawReport report = new ExpiredAndFlawReport(expAndFlawDTO);
        allReports.put(report.getId(), report);
        return report;
    }

//    public DeficientReport importDeficientReport(int branchId) {
//        DeficientReport report = new DeficientReport(branchId);
//        allReports.put(report.getId(), report);
//        return report;
//    }

    public DeficientReport importDeficientReport(int branchId) throws Exception {
        Branch branch = BranchController.getInstance().getBranchById(branchId);
        int reportID = Global.getNewReportId();
        ReportDTO repDTO = new ReportDTO(reportID, branchId, LocalDate.now());
        reportDAO.insert(repDTO);

        HashMap<Integer, ProductBranch> deficiencyProductsBranch = branch.getAllDeficiencyProductsBranch();
        for (Integer productCode : deficiencyProductsBranch.keySet()) {
            ProductBranch deficiencyProduct = deficiencyProductsBranch.get(productCode);
            int missingAmount = deficiencyProduct.getMinQuantity()-deficiencyProduct.getTotalAmount();
            DeficiencyReportEntryDTO defEntDTO = new DeficiencyReportEntryDTO(reportID, deficiencyProduct.getCode(), missingAmount);
            deficiencyReportEntryDAO.insert(defEntDTO);
        }
        DeficiencyReportDTO defDTO = deficiencyReportEntryDAO.getFullReportById(reportID);

        DeficientReport report = new DeficientReport(defDTO);
        allReports.put(report.getId(), report);
        return report;
    }

    public InventoryReport getInventoryReport(int reportId) throws Exception {

        if (allReports.get(reportId) == null || !(allReports.get(reportId) instanceof InventoryReport))
            throw new Exception("this is not Inventory Report");

        return (InventoryReport) allReports.get(reportId);
    }

    public ExpiredAndFlawReport getExpiredAndFlawReport(int reportId) throws Exception {
        if (allReports.get(reportId) == null || !(allReports.get(reportId) instanceof ExpiredAndFlawReport))
            throw new Exception("this is not Expired and flaws Report");

        return (ExpiredAndFlawReport) allReports.get(reportId);
    }

    public DeficientReport getDeficientReport(int reportId) throws Exception {
        if (allReports.get(reportId) == null || !(allReports.get(reportId) instanceof DeficientReport))
            throw new Exception("this is not Expired and flaws Report");

        return (DeficientReport) allReports.get(reportId);
    }


}
