package BusinessNew;

import Business_Layer.CategoryController1;

import java.util.HashMap;
import java.util.List;

public  class ReportController {
    private HashMap<Integer,Report> allReports;
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
    public InventoryReport importInventoryReport(int branchId){
        InventoryReport report = new InventoryReport(branchId);
        allReports.put(report.getId(),report);
        return report;
    }


    public InventoryReport getReport(int reportId) throws Exception {
        Report report = allReports.get(reportId);
        if(report == null || !(report instanceof InventoryReport))
            throw new Exception("this is not Inventory Report");

        return (InventoryReport) report;
    }
}
