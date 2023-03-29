package Business_Layer;

import java.time.LocalDate;
import java.util.Date;

public class Discount {
    private LocalDate start_date;
    private LocalDate end_date;
    private double discount_percentage;

    public Discount(LocalDate start_date, LocalDate end_date, double discount_percentage) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.discount_percentage = discount_percentage;

    }
    public Discount()
    {
        this.start_date = null;
        this.end_date = null;
        this.discount_percentage = 0;

    }

    public LocalDate getStart_date() {
        return this.start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public void setDiscount_percentage(int discount_percentage) {
        this.discount_percentage = discount_percentage;
    }
}
