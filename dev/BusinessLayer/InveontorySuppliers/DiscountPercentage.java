package BusinessLayer.InveontorySuppliers;

import DataAccessLayer.DTOs.DiscountDTO;

public class DiscountPercentage extends Discount {
    public DiscountPercentage(DiscountDTO dto) {
        super(dto);
    }

    public double getDiscountValue() {
        return val;
    }

    @Override
    public double getPriceWithDiscount(double price) {
        return price * (1 - val);
    }
}
