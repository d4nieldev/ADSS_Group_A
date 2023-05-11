package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class FixedDaysSupplierDTO implements DTO {
    SupplierDTO supplierDTO;
    int dayOfSupply;

    public FixedDaysSupplierDTO(SupplierDTO supplierDto, int dayOfSupply) {
        this.supplierDTO = supplierDto;
        this.dayOfSupply = dayOfSupply;
    }

    public SupplierDTO getSuper() {
        return this.supplierDTO;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierDTO.getSupplierId());
        nameToVal.put("dayOfSupply", "" + dayOfSupply);
        return nameToVal;
    }

    public SupplierDTO getSupplierDTO() {
        return supplierDTO;
    }

    public void setSupplierDTO(SupplierDTO supplierDTO) {
        this.supplierDTO = supplierDTO;
    }

    public int getDayOfSupply() {
        return dayOfSupply;
    }

    public void setDayOfSupply(int dayOfSupply) {
        this.dayOfSupply = dayOfSupply;
    }

}
