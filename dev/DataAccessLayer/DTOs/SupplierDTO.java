package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierDTO implements DTO {
    protected int id;
    protected String name;
    protected String bankAccount;
    protected String paymentCondition;
    protected List<String> fields;

    public SupplierDTO(int id, String name, String bankAccount, String paymentCondition, List<String> fields) {
        this.id = id;
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentCondition = paymentCondition;
        this.fields = fields;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("id", "" + id);
        nameToVal.put("name", name);
        nameToVal.put("bankAccount", bankAccount);
        nameToVal.put("paymentCondition", paymentCondition);
        return nameToVal;
    }

    public int getSupplierId() {
        return this.id;
    }
}
