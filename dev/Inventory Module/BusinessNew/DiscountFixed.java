package BusinessNew;


import java.time.LocalDate;

public class DiscountFixed extends Discount {
    private double discountValue;
    public DiscountFixed(LocalDate start_date, LocalDate end_date, double discount_percentage, double discountValue) {
        super(start_date, end_date, discount_percentage);
        this.discountValue = discountValue;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    @Override
    public double getPriceWithDiscount(double price) {
        return Math.max(0, price -discountValue);
    }
}
