package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class AgreementAmountToDiscountDTO implements DTO {
    int supplierId;
    int productId;
    int amount;
    DiscountDTO discount;

    public AgreementAmountToDiscountDTO(int supplierId, int productId, int amount, DiscountDTO discount) {
        this.supplierId = supplierId;
        this.productId = productId;
        this.amount = amount;
        this.discount = discount;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierId);
        nameToVal.put("productId", "" + productId);
        nameToVal.put("amount", "" + amount);
        nameToVal.put("discount", "" + discount.getId());
        return nameToVal;
    }

}
