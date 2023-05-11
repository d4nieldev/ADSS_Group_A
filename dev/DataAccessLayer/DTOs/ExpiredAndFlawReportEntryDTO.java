package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class ExpiredAndFlawReportEntryDTO implements DTO {
    private int reportId;
    private int specificId;

    public ExpiredAndFlawReportEntryDTO(int reportId, int specificId) {
        this.reportId = reportId;
        this.specificId = specificId;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("reportId", "" + reportId);
        nameToVal.put("specificId", "" + specificId);
        return nameToVal;
    }

    public int getReportId() {
        return reportId;
    }

    public int getSpecificId() {
        return specificId;
    }

}
