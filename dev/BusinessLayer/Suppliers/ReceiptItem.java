package BusinessLayer.Suppliers;

class ReceiptItem {
    private int amount;
    private double pricePerUnitBeforeDiscount;
    private double pricePerUnitAfterDiscount;
    private String manufacturer;
    // TODO: expiration date

    public ReceiptItem(int amount, ProductAgreement agreement) {
        this.amount = amount;
        this.pricePerUnitBeforeDiscount = agreement.getPrice(0);
        this.pricePerUnitAfterDiscount = agreement.getPrice(amount);
    }

    public int getAmount() {
        return this.amount;
    }
}
