package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ProductAgreementDTO implements DTO {

    private int supplierId;
    private ProductDTO productDTO;
    private int stockAmount;
    private double basePrice;
    private int productSupplierId;
    private TreeMap<Integer, DiscountDTO> amountToDiscount;

    public ProductAgreementDTO(int supplierId, ProductDTO productDTO, int stockAmount, double basePrice,
            int productSupplierId, TreeMap<Integer, DiscountDTO> amountToDiscount) {
        this.supplierId = supplierId;
        this.productDTO = productDTO;
        this.stockAmount = stockAmount;
        this.basePrice = basePrice;
        this.productSupplierId = productSupplierId;
        this.amountToDiscount = amountToDiscount;
    }


    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierId);
        nameToVal.put("productId", "" + productDTO.getId());
        nameToVal.put("stockAmount", "" + stockAmount);
        nameToVal.put("basePrice", "" + basePrice);
        nameToVal.put("productSupplierId", "" + productSupplierId);
        return nameToVal;
    }


    public int getSupplierId() {
        return supplierId;
    }


    public ProductDTO getProductDTO() {
        return productDTO;
    }


    public int getStockAmount() {
        return stockAmount;
    }


    public double getBasePrice() {
        return basePrice;
    }


    public int getProductSupplierId() {
        return productSupplierId;
    }


    public TreeMap<Integer, DiscountDTO> getAmountToDiscount() {
        return amountToDiscount;
    }

}
