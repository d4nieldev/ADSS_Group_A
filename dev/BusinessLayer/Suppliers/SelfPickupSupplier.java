package BusinessLayer.Suppliers;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

class SelfPickupSupplier extends Supplier {

    private String address;
    private int maxPreperationDays;

    // Copy constructor
    public SelfPickupSupplier(int id, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition, TreeMap<Integer, Double> amountToDiscount, List<Contact> contacts,
            String address, int maxPreperationDays) {
        super(id, name, phone, bankAcc, fields, paymentCondition, amountToDiscount, contacts);
        this.address = address;
        this.maxPreperationDays = maxPreperationDays;
    }

    // Constructor without contacts, reservation history and fields
    public SelfPickupSupplier(int id, String name, String phone, String bankAcc, String paymentCondition,
            TreeMap<Integer, Double> amountToDiscount, String address) {
        super(id, name, phone, bankAcc, paymentCondition, amountToDiscount);
        this.address = address;
    }

    // Constructor without reservation history and contacts
    public SelfPickupSupplier(int id, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition,
            TreeMap<Integer, Double> amountToDiscount, String address) {
        super(id, name, phone, bankAcc, fields, paymentCondition, amountToDiscount);
        this.address = address;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSupplier Type: Self Pickup Supplier\nAddress: " + address;
    }

    @Override
    public LocalDate getClosestDeliveryDate() {
        return LocalDate.now().plusDays(maxPreperationDays);
    }

}
