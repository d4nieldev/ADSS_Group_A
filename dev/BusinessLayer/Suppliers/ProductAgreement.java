package BusinessLayer.Suppliers;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import BusinessLayer.InveontorySuppliers.Discount;
import BusinessLayer.InveontorySuppliers.DiscountFixed;
import BusinessLayer.InveontorySuppliers.DiscountPercentage;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductAgreementDTO;

public class ProductAgreement {
    private int supplierId;
    private int productSupplierId;
    private double basePrice;
    private int stockAmount;
    private int productId;
    private TreeMap<Integer, Discount> amountToDiscount;
    private ProductAgreementDTO dto;

    public ProductAgreement(ProductAgreementDTO dto) {
        this.dto = dto;
        this.supplierId = dto.getSupplierId();
        this.productSupplierId = dto.getProductSupplierId();
        this.basePrice = dto.getBasePrice();
        this.stockAmount = dto.getStockAmount();
        this.productId = dto.getProductDTO().getId();
        this.amountToDiscount = new TreeMap<>();
        for (Integer amount : dto.getAmountToDiscount().keySet()) {
            DiscountDTO discountDTO = dto.getAmountToDiscount().get(amount);
            if (discountDTO.getdType() == "Fixed")
                amountToDiscount.put(amount, new DiscountFixed(discountDTO));
            else if (discountDTO.getdType() == "Precentage")
                amountToDiscount.put(amount, new DiscountPercentage(discountDTO));
        }
    }

    public ProductAgreementDTO getDto() {
        return dto;
    }

    /**
     * Returns the exact price of the all the amount of the product.
     * 
     * @param amount
     * @return
     */
    public double getPrice(int amount) {
        Discount discount;
        Integer key = amountToDiscount.floorKey(amount);
        if (key != null) {
            discount = amountToDiscount.get(key);
            return discount.getPriceWithDiscount(basePrice);
        }
        return basePrice;
    }

    // Getter for supplier id
    public int getSupplierId() {
        return supplierId;
    }

    // Getter for product supplier id
    public int getProductSupplierId() {
        return productSupplierId;
    }

    // Getter for stock amount
    public int getStockAmount() {
        return stockAmount;
    }

    // Setter for stock amount
    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    // Getter for amount to price map
    public Map<Integer, Discount> getAmountToDiscount() {
        return amountToDiscount;
    }

    // Setter for amount to price map
    public void setAmountToDiscount(TreeMap<Integer, Discount> amountToDiscount) {
        this.amountToDiscount = amountToDiscount;

    }

    // Getter for product
    public int getProductId() {
        return productId;
    }

    // Setter for product base price
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    // Getter for product base price
    public double getBasePrice() {
        return basePrice;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("supplierId", supplierId);
        map.put("productSupplierId", productSupplierId);
        map.put("basePrice", basePrice);
        map.put("stockAmount", stockAmount);
        map.put("productId", productId);

        Map<String, String> myAmountToDiscount = new HashMap<>();
        for (Integer amount : amountToDiscount.keySet())
            myAmountToDiscount.put("" + amount, amountToDiscount.get(amount).toString());
        map.put("amountToDiscount", myAmountToDiscount);

        return map;
    }
}