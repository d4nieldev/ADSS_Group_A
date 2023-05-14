package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class SuppliersFieldsDTO implements DTO {
    int supplierId;
    String fieldName;

    public SuppliersFieldsDTO(int supplierId, String fieldName) {
        this.supplierId = supplierId;
        this.fieldName = fieldName;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierId);
        nameToVal.put("fieldName", fieldName);
        return nameToVal;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getFieldName() {
        return fieldName;
    }
}