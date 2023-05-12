package DataAccessLayer.DTOs;

import java.util.Map;

public class DeficiencyReportDTO extends ReportDTO {
    private Map<Integer, ProductBranchDTO> idToProductBranch;
    private Map<Integer, Integer> idToMissingAmount;
    private ReportDTO reportDTO;

    public DeficiencyReportDTO(ReportDTO reportDTO, Map<Integer, ProductBranchDTO> idToProductBranch,
            Map<Integer, Integer> idToMissingAmount) {
        super(reportDTO);
        this.idToProductBranch = idToProductBranch;
        this.idToMissingAmount = idToMissingAmount;
        this.reportDTO = reportDTO;
    }

    public ReportDTO getReportDTO(){ return reportDTO; }

    public Map<Integer, ProductBranchDTO> getIdToProductBranch() {
        return idToProductBranch;
    }

    public Map<Integer, Integer> getIdToMissingAmount() {
        return idToMissingAmount;
    }

}
