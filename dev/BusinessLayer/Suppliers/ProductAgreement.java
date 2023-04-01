package BusinessLayer.Suppliers;

import java.util.Map;
import java.util.TreeMap;

class ProductAgreement {
    private int supplierId;
    private int productShopId;
    private int productSupplierId;
    private int stockAmount;
    private TreeMap<Integer, Double> amountToPrice;
    private String manufacturer;

    public ProductAgreement(int supplierId, int productShopId, int productSupplierId, int stockAmount,
            TreeMap<Integer, Double> amountToPrice, String manufacturer) {
        this.supplierId = supplierId;
        this.productShopId = productShopId;
        this.productSupplierId = productSupplierId;
        this.stockAmount = stockAmount;
        this.amountToPrice = amountToPrice;
        this.manufacturer = manufacturer;
    }

    public double getPrice(int amount) {
        return amountToPrice.get(amountToPrice.floorKey(amount));
    }

    // Getter for supplier id
    public int getSupplierId() {
        return supplierId;
    }

    // Getter for product shop id
    public int getProductShopId() {
        return productShopId;
    }

    // Getter for product supplier id
    public int getProductSupplierId() {
        return productSupplierId;
    }

    // Getter for stock amount
    public int getStockAmount() {
        return stockAmount;
    }

    // Getter for amount to price map
    public Map<Integer, Double> getAmountToPrice() {
        return amountToPrice;
    }
    
    // Getter for manufacturer
    public String getManufacturer() {
        return manufacturer;
    }
}