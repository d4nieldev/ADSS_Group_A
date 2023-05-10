package BusinessLayer.Suppliers;

import java.util.Map;
import java.util.TreeMap;

import BusinessLayer.InveontorySuppliers.Discount;
import BusinessLayer.InveontorySuppliers.Product;

public class ProductAgreement {
    private int supplierId;
    private int productSupplierId;
    private double basePrice;
    private int stockAmount;
    private Product product;
    private TreeMap<Integer, Discount> amountToDiscount;

    public ProductAgreement(int supplierId, Product product, int productSupplierId, double basePrice, int stockAmount,
            TreeMap<Integer, Discount> amountToDiscount) {
        this.supplierId = supplierId;
        this.productSupplierId = productSupplierId;
        this.stockAmount = stockAmount;
        this.amountToDiscount = amountToDiscount;
        this.product = product;
        this.basePrice = basePrice;
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
    public Product getProduct() {
        return product;
    }

    // Setter for product base price
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    // Getter for product base price
    public double getBasePrice() {
        return basePrice;
    }
}