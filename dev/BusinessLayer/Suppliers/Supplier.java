package BusinessLayer.Suppliers;

import java.util.Map;
import java.util.List;
import java.util.Queue;

abstract class Supplier {
    private int id;
    private String name;
    private String phone;
    private String bankAcc;
    private List<String> fields;
    private String paymentCondition;
    private Map<Integer, Double> amountToDiscount;
    private Queue<Integer> reservationHistory;
}
