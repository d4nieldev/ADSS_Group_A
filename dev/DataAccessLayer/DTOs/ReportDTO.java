package DataAccessLayer.DTOs;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReportDTO implements DTO {
    private int id;
    private int branchId;
    private LocalDate createdDate;

    public ReportDTO(int id, int branchId, LocalDate createdDate) {
        this.id = id;
        this.branchId = branchId;
        this.createdDate = createdDate;
    }

    public ReportDTO(ReportDTO reportDto) {
        this.id = reportDto.id;
        this.branchId = reportDto.branchId;
        this.createdDate = reportDto.createdDate;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("id", "" + id);
        nameToVal.put("branchId", "" + branchId);
        nameToVal.put("createdDate", createdDate.toString());
        return nameToVal;
    }

    public int getId() {
        return id;
    }

    public int getBranchId() {
        return branchId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

}
