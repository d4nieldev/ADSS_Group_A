package BusinessLayer.InveontorySuppliers;

import java.time.LocalDate;

import DataAccessLayer.DTOs.DiscountDTO;

public abstract class Discount {
    private int discountId;
    private LocalDate start_date;
    private LocalDate end_date;
    double val;
    protected DiscountDTO dto;

    public Discount(DiscountDTO dto) {
        this.dto = dto;
        this.discountId = dto.getId();
        this.start_date = dto.getStartDate();
        this.end_date = dto.getEndDate();
        this.val = dto.getVal();
    }

    public Discount() {
        this.start_date = null;
        this.end_date = null;

    }

    public LocalDate getStart_date() {
        return this.start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public abstract double getPriceWithDiscount(double price);
}
