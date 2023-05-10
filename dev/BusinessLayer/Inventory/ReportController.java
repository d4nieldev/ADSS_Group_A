package BusinessLayer.Inventory;

import java.util.HashMap;
import java.util.List;

public class ReportController {
    private HashMap<Integer, Report> allReports;
    private static ReportController instance = null;

    private ReportController() {
        this.allReports = new HashMap<>();
    }

    public static ReportController getInstance() {
        if (instance == null) {
            instance = new ReportController();
        }
        return instance;
    }

    // TODO : change brnachId to Branch object;
    public HashMap<Integer, Report> getAllReports() {
        return this.allReports;
    }

    public InventoryReport importInventoryReport(int branchId) {

        Branch branch = BranchController.getInstance().getBranchById(branchId);
        InventoryReport report = new InventoryReport(branch);
        allReports.put(report.getId(), report);
        return report;
    }

    public InventoryReport importInventoryReport(int branchId, List<Category> categoryList) {
        Branch branch = BranchController.getInstance().getBranchById(branchId);
        InventoryReport report = new InventoryReport(branch, categoryList);
        allReports.put(report.getId(), report);
        return report;
    }

    public ExpiredAndFlawReport importExpiredAndFlawReport(int branchId) {
        ExpiredAndFlawReport report = new ExpiredAndFlawReport(branchId);
        allReports.put(report.getId(), report);
        return report;
    }

    public DeficientReport importDeficientReport(int branchId) {
        DeficientReport report = new DeficientReport(branchId);
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
