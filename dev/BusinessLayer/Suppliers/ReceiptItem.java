package BusinessLayer.Suppliers;

import BusinessLayer.InveontorySuppliers.Product;

public class ReceiptItem {
    private int amount;
    private double pricePerUnitBeforeDiscount;
    private double pricePerUnitAfterDiscount;
    private Product product;
    // TODO: expiration date

    public ReceiptItem(int amount, ProductAgreement agreement) {
        this.amount = amount;
        this.pricePerUnitBeforeDiscount = agreement.getBasePrice();
        this.pricePerUnitAfterDiscount = agreement.getPrice(amount) / amount;
        this.product = agreement.getProduct();
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = Math.max(amount, 0);
    }

    public double getPricePerUnitAfterDiscount() {
        return this.pricePerUnitAfterDiscount;
    }

    public double getPricePerUnitBeforeDiscount() {
        return this.pricePerUnitBeforeDiscount;
    }

    public void setPricePerUnitAfterDiscount(double newPrice) {
        this.pricePerUnitAfterDiscount = newPrice;
    }

    public double getDiscount() {
        return pricePerUnitAfterDiscount - pricePerUnitBeforeDiscount;
    }

    public Product getProduct() {
        return this.product;
    }

    @Override
    public String toString() {
        return String.format("%d %s %d %d %d %d", product.getId(), product.getName(), amount,
                pricePerUnitBeforeDiscount * amount, getDiscount() * amount, pricePerUnitAfterDiscount * amount);
    }
}
