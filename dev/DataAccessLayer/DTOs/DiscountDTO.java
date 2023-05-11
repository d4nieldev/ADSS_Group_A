package DataAccessLayer.DTOs;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DiscountDTO implements DTO {
    int id;
    LocalDate startDate;
    LocalDate endDate;
    double val;
    String dType;

    public DiscountDTO(int id, LocalDate startDate, LocalDate endDate, double val, String dType) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.val = val;
        this.dType = dType;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("id", "" + id);
        nameToVal.put("startDate", startDate.toString());
        nameToVal.put("endDate", endDate.toString());
        nameToVal.put("val", "" + val);
        nameToVal.put("dType", dType);
        return nameToVal;
    }

    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getVal() {
        return val;
    }

    public String getdType() {
        return dType;
    }

}
