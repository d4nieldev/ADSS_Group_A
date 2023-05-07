package BusinessNew;


import java.time.LocalDate;

public class DiscountPercentage extends Discount{
    private double discountPercentage;

    public DiscountPercentage(LocalDate start_date, LocalDate end_date, double discount_percentage, double discountPercentage) {
        super(start_date, end_date, discount_percentage);
        this.discountPercentage = discountPercentage;
    }
}
