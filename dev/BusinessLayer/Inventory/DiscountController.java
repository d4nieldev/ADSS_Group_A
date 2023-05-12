package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.Discount;
import DataAccessLayer.DTOs.DiscountDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class DiscountController {
    HashMap<Integer, Discount> idToDiscount;
    private static DiscountController instance = null;

    private DiscountController() {
        this.idToDiscount = new HashMap<>();
    }
    public static DiscountController getInstance() {
        if (instance == null) {
            instance = new DiscountController();
        }
        return instance;
    }

    public void addNewDiscount(Discount discount){
        this.idToDiscount.put(discount.getDiscountId(),discount);
    }

    public Discount getDiscountById(int discountId) {
        return idToDiscount.get(discountId);
    }
}
