package BusinessLayer.Suppliers;

import BusinessLayer.InveontorySuppliers.Discount;
import DataAccessLayer.DTOs.OnOrderSuppliersDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

class OnOrderSupplier extends Supplier {
    // private LocalDate lastOrderDate;
    private int maxSupplyDays;
    private OnOrderSuppliersDTO OnOrderSupplierDTO;

    // // Copy constructor
    // public OnOrderSupplier(int id, String name, String phone, String bankAcc, List<String> fields,
    //                        String paymentCondition,
    //                        TreeMap<Integer, Discount> amountToDiscount, List<Contact> contacts,
    //                        int maxSupplyDays) {
    //     super(id, name, phone, bankAcc, fields, paymentCondition, amountToDiscount, contacts);
    //     this.maxSupplyDays = maxSupplyDays;
    // }

    public OnOrderSupplier(OnOrderSuppliersDTO OnOrderSupplierDTO){
        super(OnOrderSupplierDTO.getSupplierDTO());
        this.OnOrderSupplierDTO = OnOrderSupplierDTO;
        this.maxSupplyDays = OnOrderSupplierDTO.getMaxSupplyDays();
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

    @Override
    public LocalDate getClosestDeliveryDate() {
        return LocalDate.now().plusDays(maxSupplyDays);
    }
}
