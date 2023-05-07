package BusinessNew;

import java.time.LocalDate;

public abstract class Report {
    private int id;
    private int branchId;
    private LocalDate creationDate;

    public Report(int id, int branchId, LocalDate creationDate) {
        this.id = id;
        this.branchId = branchId;
        this.creationDate = creationDate;
    }

    public abstract void importReport();
}
