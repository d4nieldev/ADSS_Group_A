package DataAccessLayer.DTOs;

import java.util.Map;

public class DeficiencyReportDTO extends ReportDTO {
    private Map<Integer, ProductBranchDTO> idToProductBranch;
    private Map<Integer, Integer> idToMissingAmount;

    public DeficiencyReportDTO(ReportDTO report, Map<Integer, ProductBranchDTO> idToProductBranch,
            Map<Integer, Integer> idToMissingAmount) {
        super(report);
        this.idToProductBranch = idToProductBranch;
        this.idToMissingAmount = idToMissingAmount;
    }

    public Map<Integer, ProductBranchDTO> getIdToProductBranch() {
        return idToProductBranch;
    }

    public Map<Integer, Integer> getIdToMissingAmount() {
        return idToMissingAmount;
    }

}
