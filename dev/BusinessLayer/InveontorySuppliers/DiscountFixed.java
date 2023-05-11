package BusinessLayer.InveontorySuppliers;

import java.time.LocalDate;

import DataAccessLayer.DTOs.DiscountDTO;

public class DiscountFixed extends Discount {

    public DiscountFixed(DiscountDTO dto) {
        super(dto);
    }

    public double getDiscountValue() {
        return val;
    }

    @Override
    public double getPriceWithDiscount(double price) {
        return Math.max(0, price - val);
    }
}
