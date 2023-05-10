package BusinessLayer.InveontorySuppliers;

import java.time.LocalDate;

public class DiscountPercentage extends Discount {
    private double discountPercentage;

    public DiscountPercentage(LocalDate start_date, LocalDate end_date, double discountPercentage) {
        super(start_date, end_date);
        this.discountPercentage = discountPercentage;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    @Override
    public double getPriceWithDiscount(double price) {
        return price * (1 - discountPercentage);
    }
}
