package Business_Layer;

import java.util.Date;

public class Discount {
    private Date start_date;
    private Date end_date;
    private int discount_percentage;

    public Discount(Date start_date, Date end_date, int discount_percentage) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.discount_percentage = discount_percentage;

    }

    public Date getStart_date() {
        return this.start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public int getDiscount_percentage() {
        return discount_percentage;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setDiscount_percentage(int discount_percentage) {
        this.discount_percentage = discount_percentage;
    }
}
