package BusinessNew;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class InventoryReport extends Report {


    private HashMap<Integer, Integer> idToStorageAmount;
    private HashMap<Integer, Integer> idToShelfAmount;
    private HashMap<Integer,String> idToName;

    public InventoryReport(int branchId) {
        super(Global.getNewReportId(), branchId, LocalDate.now());
        //TODO: set all variables
    }

    public HashMap<Integer, String> getIdToName() {
        return idToName;
    }

    public HashMap<Integer, Integer> getIdToStorageAmount() {
        return idToStorageAmount;
    }

    public HashMap<Integer, Integer> getIdToShelfAmount() {
        return idToShelfAmount;
    }
}

