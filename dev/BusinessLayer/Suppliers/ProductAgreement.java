package BusinessLayer.Suppliers;

import java.util.Map;
import java.util.TreeMap;

class ProductAgreement {
    private int supplierId;
    private int productSupplierId;
    private int stockAmount;
    private Product product;
    private TreeMap<Integer, Double> amountToPrice;

    public ProductAgreement(int supplierId, Product product, int productSupplierId, int stockAmount,
            TreeMap<Integer, Double> amountToPrice) {
        this.supplierId = supplierId;
        this.productSupplierId = productSupplierId;
        this.stockAmount = stockAmount;
        this.amountToPrice = amountToPrice;
        this.product = product;
    }

    public double getPrice(int amount) {
        return amountToPrice.get(amountToPrice.floorKey(amount));
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

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    // Getter for amount to price map
    public Map<Integer, Double> getAmountToPrice() {
        return amountToPrice;
    }

    public void setAmountToPrice(TreeMap<Integer, Double> amountToPrice) {
        this.amountToPrice = amountToPrice;
        
    }

    public Product getProduct() {
        return product;
    }
}