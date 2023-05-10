package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class ContactDTO implements DTO {
    private SupplierDTO supplierDTO;
    private String phone;
    private String name;

    public ContactDTO(SupplierDTO supplierDTO, String phone, String name) {
        this.supplierDTO = supplierDTO;
        this.phone = phone;
        this.name = name;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierDTO.getSupplierId());
        nameToVal.put("phone", phone);
        nameToVal.put("name", name);
        return nameToVal;
    }

}
