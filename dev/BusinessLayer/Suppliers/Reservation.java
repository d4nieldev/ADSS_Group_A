package BusinessLayer.Suppliers;

import java.util.Date;
import java.util.List;

import BusinessLayer.Suppliers.enums.Status;

class Reservation {
    private int id;
    private int supplier_id;
    private Date date;
    private Status status;
    private List<ReceiptItem> receipt;

    public Reservation(int id, int supplier_id, Date date, Status status, List<ReceiptItem> receipt) {
        this.id = id;
        this.supplier_id = supplier_id;
        this.date = date;
        this.status = status;
        this.receipt = receipt;
    }
}