package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class ProductBranchDiscountDTO implements DTO {
    private int productId;
    private int branchId;
    private DiscountDTO discount;

    public ProductBranchDiscountDTO(int productId, int branchId, DiscountDTO discount) {
        this.productId = productId;
        this.branchId = branchId;
        this.discount = discount;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("productId", "" + productId);
        nameToVal.put("branchId", "" + branchId);
        nameToVal.put("discountId", "" + discount.getId());
        return nameToVal;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public DiscountDTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountDTO discount) {
        this.discount = discount;
    }

}
