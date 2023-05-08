package BusinessNew;

import java.time.LocalDate;

public abstract class Discount {

    private int discountId;
    private LocalDate start_date;
    private LocalDate end_date;


    public Discount(LocalDate start_date, LocalDate end_date, double discount_percentage) {
        //add GlobalId
        this.start_date = start_date;
        this.end_date = end_date;
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
