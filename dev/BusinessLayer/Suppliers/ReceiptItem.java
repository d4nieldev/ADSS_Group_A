package BusinessLayer.Suppliers;

class ReceiptItem {
    private int productId;
    private int amount;
    private double pricePerUnitBeforeDiscount;
    private double pricePerUnitAfterDiscount;
    private String manufacturer;
    // TODO: expiration date

    public ReceiptItem(int amount, ProductAgreement agreement) {
        this.amount = amount;
        this.pricePerUnitBeforeDiscount = agreement.getPrice(0);
        this.pricePerUnitAfterDiscount = agreement.getPrice(amount);
        this.productId = agreement.getProductShopId();
    }

    public int getAmount() {
        return this.amount;
    }

    public double getPricePerUnitAfterDiscount() {
        return this.pricePerUnitAfterDiscount;
    }

    public void setPricePerUnitAfterDiscount(double newPrice) {
        this.pricePerUnitAfterDiscount = newPrice;
    }

    @Override
    public String toString() {
        return String.format("%d %d %d %d", productId, amount, pricePerUnitBeforeDiscount * amount,
                pricePerUnitAfterDiscount * amount);
    }
}
