package BusinessLayer.Suppliers;

import DataAccessLayer.DTOs.DTO;
import DataAccessLayer.DTOs.SelfPickUpSupplierDTO;

import java.time.LocalDate;
import java.util.Map;

class SelfPickupSupplier extends Supplier {

    private String address;
    private int maxPreperationDays;
    private SelfPickUpSupplierDTO selfPickUpSupplierDTO;

    // // Copy constructor
    // public SelfPickupSupplier(int id, String name, String phone, String bankAcc,
    // List<String> fields,
    // String paymentCondition, TreeMap<Integer, Discount> amountToDiscount,
    // List<Contact> contacts,
    // String address, int maxPreperationDays) {
    // super(id, name, phone, bankAcc, fields, paymentCondition, amountToDiscount,
    // contacts);
    // this.address = address;
    // this.maxPreperationDays = maxPreperationDays;
    // }

    public SelfPickupSupplier(SelfPickUpSupplierDTO selfPickUpSupplierDTO) {
        super(selfPickUpSupplierDTO.getSuper());
        this.selfPickUpSupplierDTO = selfPickUpSupplierDTO;
        this.maxPreperationDays = selfPickUpSupplierDTO.getMaxPreperationDays();
        this.address = selfPickUpSupplierDTO.getAddress();
    }

    @Override
    public String toString() {
        return super.toString() + "\nSupplier Type: Self Pickup Supplier\nAddress: " + address;
    }

    @Override
    public LocalDate getClosestDeliveryDate() {
        return LocalDate.now().plusDays(maxPreperationDays);
    }

    @Override
    public Map<String, Object> getMap() {
        Map<String, Object> map = super.getMap();
        map.put("address", address);
        map.put("maxPreperationDays", maxPreperationDays);
        return map;
    }

}
