package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class ContactDTO implements DTO {
    private int supplierId;
    private String phone;
    private String name;

    public ContactDTO(int supplierId, String phone, String name) {
        this.supplierId = supplierId;
        this.phone = phone;
        this.name = name;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierId);
        nameToVal.put("phone", phone);
        nameToVal.put("name", name);
        return nameToVal;
    }

}
