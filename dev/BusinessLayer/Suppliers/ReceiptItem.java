package BusinessLayer.Suppliers;

class ReceiptItem {
    private int amount;
    private double pricePerUnitBeforeDiscount;
    private double pricePerUnitAfterDiscount;
    private String manufacturer;
    // TODO: expiration date

    public ReceiptItem(int amount, double pricePerUnitBeforeDiscount, double pricePerUnitAfterDiscount) {
        this.amount = amount;
        this.pricePerUnitBeforeDiscount = pricePerUnitBeforeDiscount;
        this.pricePerUnitAfterDiscount = pricePerUnitAfterDiscount;
    }
}
