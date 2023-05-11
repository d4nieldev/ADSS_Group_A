package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class InventoryReportEntryDTO implements DTO {
    private int reportId;
    private int productId;
    private int shelfAmount;
    private int storageAmount;

    public InventoryReportEntryDTO(int reportId, int productId, int shelfAmount, int storageAmount) {
        this.reportId = reportId;
        this.productId = productId;
        this.shelfAmount = shelfAmount;
        this.storageAmount = storageAmount;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("reportId", "" + reportId);
        nameToVal.put("productId", "" + productId);
        nameToVal.put("shelfAmount", "" + shelfAmount);
        nameToVal.put("storageAmount", "" + storageAmount);
        return nameToVal;
    }

    public int getReportId() {
        return reportId;
    }

    public int getProductId() {
        return productId;
    }

    public int getShelfAmount() {
        return shelfAmount;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

}
