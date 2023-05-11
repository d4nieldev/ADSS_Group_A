package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class DeficiencyReportEntryDTO implements DTO {
    private int reportId;
    private int productId;
    private int missingAmount;

    public DeficiencyReportEntryDTO(int reportId, int productId, int missingAmount) {
        this.reportId = reportId;
        this.productId = productId;
        this.missingAmount = missingAmount;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("reportId", "" + reportId);
        nameToVal.put("productId", "" + productId);
        nameToVal.put("missingAmount", "" + missingAmount);
        return nameToVal;
    }

    public int getReportId() {
        return reportId;
    }

    public int getProductId() {
        return productId;
    }

    public int getMissingAmount() {
        return missingAmount;
    }

}
