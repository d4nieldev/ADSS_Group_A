package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class SupplierAmountToDiscountDTO implements DTO {
    private int supplierId;
    private int amount;
    private DiscountDTO discount;

    public SupplierAmountToDiscountDTO(int supplierId, int amount, DiscountDTO discount) {
        this.supplierId = supplierId;
        this.amount = amount;
        this.discount = discount;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierId);
        nameToVal.put("amount", "" + amount);
        nameToVal.put("discount", "" + discount.getId());
        return nameToVal;
    }

}
