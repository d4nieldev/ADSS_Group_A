package BusinessLayer.Suppliers;

import java.util.Map;
import java.util.TreeMap;

class ProductAgreement {
    private int supplierId;
    private int productShopId;
    private int productSupplierId;
    private int stockAmount;
    private TreeMap<Integer, Double> amountToPrice;

    public ProductAgreement(int supplierId, int productShopId, int productSupplierId, int stockAmount,
            TreeMap<Integer, Double> amountToPrice) {
        this.supplierId = supplierId;
        this.productShopId = productShopId;
        this.productSupplierId = productSupplierId;
        this.stockAmount = stockAmount;
        this.amountToPrice = amountToPrice;
    }

    public double getPrice(int amount) {
        return amountToPrice.get(amountToPrice.floorKey(amount));
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public int getSupplierId() {
        return supplierId;
    }
}