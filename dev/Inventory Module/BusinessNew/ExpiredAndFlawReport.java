package BusinessNew;

import java.time.LocalDate;

public class ExpiredAndFlawReport extends Report{
    public ExpiredAndFlawReport(int id, int branchId, LocalDate creationDate) {
        super(id, branchId, creationDate);
    }

    @Override
    public void importReport() {

    }
}
