package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class SelfPickUpSupplierDTO implements DTO {
    private SupplierDTO supDTO;
    private String address;
    private int maxPreperationDays;

    public SelfPickUpSupplierDTO(SupplierDTO supDTO, String address, int maxPreperationDays) {
        this.supDTO = supDTO;
        this.address = address;
        this.maxPreperationDays = maxPreperationDays;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supDTO.getSupplierId());
        nameToVal.put("address", address);
        nameToVal.put("maxPreperationDays", "" + maxPreperationDays);
        return nameToVal;
    }

    public SupplierDTO getSuper() {
        return this.supDTO;
    }
}
