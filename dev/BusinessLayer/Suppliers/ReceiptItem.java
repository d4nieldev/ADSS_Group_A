package BusinessLayer.Suppliers;

public class ReceiptItem {
    private int amount;
    private double pricePerUnitBeforeDiscount;
    private double pricePerUnitAfterDiscount;
    Product product;
    // TODO: expiration date

    public ReceiptItem(int amount, ProductAgreement agreement) {
        this.amount = amount;
        this.pricePerUnitBeforeDiscount = agreement.getPrice(0) / amount;
        this.pricePerUnitAfterDiscount = agreement.getPrice(amount) / amount;
        this.product = agreement.getProduct();
    }

    public int getAmount() {
        return this.amount;
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

    @Override
    public String toString() {
        return String.format("%d %s %d %d %d %d", product.getId(), product.getName(), amount,
                pricePerUnitBeforeDiscount * amount, getDiscount() * amount, pricePerUnitAfterDiscount * amount);
    }
}
