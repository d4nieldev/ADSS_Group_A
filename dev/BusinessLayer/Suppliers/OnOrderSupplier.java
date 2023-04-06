package BusinessLayer.Suppliers;

import java.util.List;
import java.util.Map;

class OnOrderSupplier extends Supplier {
    // private LocalDate lastOrderDate;
    private int maxSupplyDays;

    // Copy constructor
    public OnOrderSupplier(int id, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition,
            Map<Integer, Double> amountToDiscount, List<Contact> contacts,
            int maxSupplyDays) {
        super(id, name, phone, bankAcc, fields, paymentCondition, amountToDiscount, contacts);
        this.maxSupplyDays = maxSupplyDays;
    }

    // Constructor without contacts, reservation history and fields
    public OnOrderSupplier(int id, String name, String phone, String bankAcc, String paymentCondition,
        Map<Integer, Double> amountToDiscount, int maxSupplyDays) {
        super(id, name, phone, bankAcc, paymentCondition, amountToDiscount);
        this.maxSupplyDays = maxSupplyDays;
    }

    // Constructor without reservation history and contacts
    public OnOrderSupplier(int id, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition,
            Map<Integer, Double> amountToDiscount, int maxSupplyDays) {
        super(id, name, phone, bankAcc, fields, paymentCondition, amountToDiscount);
        this.maxSupplyDays = maxSupplyDays;
    }

    // // Update last order date
    // public void updateLastOrderDate(LocalDate newLastOrderDate) {
    // this.lastOrderDate = newLastOrderDate;
    // }

    // /**
    // *
    // * @return last date that the order should be delivered
    // */
    // public LocalDate lastDateToReceiveOrder() {
    // return lastOrderDate.plusDays(maxSupplyDays);
    // }

    @Override
    public String toString() {
        return super.toString() + "\nSupplier Type: On Order Supplier\nMax Supply Days: " + maxSupplyDays;
    }
}
