package BusinessLayer.Suppliers;

import java.util.Map;

class ProductAgreement {
    private int supplierId;
    private int productShopId;
    private int productSupplierId;
    private int stockAmount;
    private Map<Integer, Double> amountToPrice;


    //TODO: FINISH PRODUCT AGREEMENT


    //Getter and setter for supplier id
    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    //Getter and setter for product shop id
    public int getProductShopId() {
        return productShopId;
    }

    public void setProductShopId(int productShopId) {
        this.productShopId = productShopId;
    }

    //Getter and setter for product supplier id
    public int getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    //Getter and setter for stock amount
    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    //Getter and setter for amount to price
    public Map<Integer, Double> getAmountToPrice() {
        return amountToPrice;
    }

    public void setAmountToPrice(Map<Integer, Double> amountToPrice) {
        this.amountToPrice = amountToPrice;
    }
}