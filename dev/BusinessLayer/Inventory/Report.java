package BusinessLayer.Inventory;

import DataAccessLayer.DTOs.CategoryDTO;
import DataAccessLayer.DTOs.ReportDTO;

import java.time.LocalDate;

public abstract class Report {
    private int id;
    private int branchId;
    private LocalDate creationDate;
    private ReportDTO reportDTO;

    public Report(int id, int branchId, LocalDate creationDate, ReportDTO reportDTO) {
        this.id = id;
        this.branchId = branchId;
        this.creationDate = creationDate;
        this.reportDTO = reportDTO;
    }

    public int getId() {
        return id;
    }

    public int getBranchId() {
        return branchId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public ReportDTO getReportDTO() {
        return reportDTO;
    }
}
