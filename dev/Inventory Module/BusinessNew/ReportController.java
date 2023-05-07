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
}
