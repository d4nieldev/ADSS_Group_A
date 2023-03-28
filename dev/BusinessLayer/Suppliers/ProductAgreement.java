package BusinessLayer.Suppliers;

import java.util.Map;

class ProductAgreement {
    private int supplierId;
    private int productShopId;
    private int productSupplierId;
    private int stockAmount;
    private Map<Integer, Double> amountToPrice;

    public double getPrice(int amount) {
        return -1;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public int getSupplierId() {
        return supplierId;
    }
}