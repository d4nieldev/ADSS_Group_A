package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelfPickUpSupplierDTO implements DTO {
    private SupplierDTO supDTO;
    private String address;
    private int maxPreperationDays;

    public SelfPickUpSupplierDTO(int id, String name, String bankAcc, List<String> fields, String paymentCondition,
            String address, int maxPreperationDays) {
        this.supDTO = new SupplierDTO(id, name, bankAcc, paymentCondition, fields);
        this.address = address;
        this.maxPreperationDays = maxPreperationDays;
    }

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
