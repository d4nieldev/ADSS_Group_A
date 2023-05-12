package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class ReceiptItemDTO implements DTO {
    private int reservationId;
    private int productId;
    private int amount;
    private double pricePerUnitBeforeDiscount;
    private double pricePerUnitAfterDiscount;

    public ReceiptItemDTO(int reservationId, int productId, int amount, double pricePerUnitBeforeDiscount,
            double pricePerUnitAfterDiscount) {
        this.reservationId = reservationId;
        this.productId = productId;
        this.amount = amount;
        this.pricePerUnitBeforeDiscount = pricePerUnitBeforeDiscount;
        this.pricePerUnitAfterDiscount = pricePerUnitAfterDiscount;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("reservationId", "" + reservationId);
        nameToVal.put("productId", "" + productId);
        nameToVal.put("amount", "" + amount);
        nameToVal.put("pricePerUnitBeforeDiscount", "" + pricePerUnitBeforeDiscount);
        nameToVal.put("pricePerUnitAfterDiscount", "" + pricePerUnitAfterDiscount);
        return nameToVal;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPricePerUnitBeforeDiscount() {
        return pricePerUnitBeforeDiscount;
    }

    public double getPricePerUnitAfterDiscount() {
        return pricePerUnitAfterDiscount;
    }

}
