package DataAccessLayer.DTOs;

import java.util.Map;

public class InventoryReportDTO extends ReportDTO {
    private Map<Integer, Integer> idToStorageAmount;
    private Map<Integer, Integer> idToShopAmount;
    private ReportDTO reportDTO;

    public InventoryReportDTO(ReportDTO reportDTO, Map<Integer, Integer> idToStorageAmount,
            Map<Integer, Integer> idToShopAmount) {
        super(reportDTO.getId(), reportDTO.getBranchId(), reportDTO.getCreatedDate());
        this.idToStorageAmount = idToStorageAmount;
        this.idToShopAmount = idToShopAmount;
        this.reportDTO = reportDTO;
    }

    public ReportDTO getReportDTO() {
        return reportDTO;
    }

    public Map<Integer, Integer> getIdToStorageAmount() {
        return idToStorageAmount;
    }

    public Map<Integer, Integer> getIdToShopAmount() {
        return idToShopAmount;
    }

}
