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
}