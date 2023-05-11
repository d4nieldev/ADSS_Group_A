package BusinessLayer.InveontorySuppliers;

import java.time.LocalDate;

import DataAccessLayer.DTOs.ReceiptItemDTO;

public class ReceiptItem {
    private int amount;
    private double pricePerUnitBeforeDiscount;
    private double pricePerUnitAfterDiscount;
    private Product product;
    private LocalDate expiredDate;

    public ReceiptItem(ReceiptItemDTO dto) {
        this.amount = dto.getAmount();
        this.pricePerUnitBeforeDiscount = dto.getPricePerUnitBeforeDiscount();
        this.pricePerUnitAfterDiscount = dto.getPricePerUnitAfterDiscount();
        this.product = ProductController.getInstance().getProductById(dto.getProductId());
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

    public LocalDate getExpiredDate() {
        return this.expiredDate;
    }

    @Override
    public String toString() {
        return String.format("%d %s %d %d %d %d", product.getId(), product.getName(), amount,
                pricePerUnitBeforeDiscount * amount, getDiscount() * amount, pricePerUnitAfterDiscount * amount);
    }
}
