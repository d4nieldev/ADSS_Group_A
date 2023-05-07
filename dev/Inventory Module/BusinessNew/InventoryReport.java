package BusinessNew;

import java.time.LocalDate;

public class InventoryReport extends Report{
    public InventoryReport(int id, int branchId, LocalDate creationDate) {
        super(id, branchId, creationDate);
    }

    @Override
    public void importReport() {

    }
}
