package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class OnOrderSuppliersDTO implements DTO {
    SupplierDTO supplierDTO;
    int maxSupplyDays;

    public OnOrderSuppliersDTO(SupplierDTO supplierDTO, int maxSupplyDays) {
        this.supplierDTO = supplierDTO;
        this.maxSupplyDays = maxSupplyDays;
    }

    public SupplierDTO getSuper() {
        return this.supplierDTO;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierDTO.getSupplierId());
        nameToVal.put("maxSupplyDays", "" + maxSupplyDays);
        return nameToVal;
    }

    public SupplierDTO getSupplierDTO() {
        return supplierDTO;
    }

    public int getMaxSupplyDays() {
        return maxSupplyDays;
    }

}
