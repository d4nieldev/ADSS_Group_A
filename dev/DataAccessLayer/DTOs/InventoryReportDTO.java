package DataAccessLayer.DTOs;

import java.util.Map;

public class InventoryReportDTO extends ReportDTO {
    private Map<Integer, Integer> idToStorageAmount;
    private Map<Integer, Integer> idToShopAmount;

    public InventoryReportDTO(ReportDTO report, Map<Integer, Integer> idToStorageAmount,
            Map<Integer, Integer> idToShopAmount) {
        super(report);
        this.idToStorageAmount = idToStorageAmount;
        this.idToShopAmount = idToShopAmount;
    }

    public Map<Integer, Integer> getIdToStorageAmount() {
        return idToStorageAmount;
    }

    public Map<Integer, Integer> getIdToShopAmount() {
        return idToShopAmount;
    }

}
