package BusinessNew;

import java.time.LocalDate;
import java.util.HashMap;

public class InventoryReport extends Report{
    private HashMap<Integer,Integer> idToStorageAmount;
    private HashMap<Integer,Integer> idToShelfAmount;

    public InventoryReport(int id, int branchId) {
        super(id, branchId, LocalDate.now());
    }


    @Override
    public void importReport() {

    }
}
