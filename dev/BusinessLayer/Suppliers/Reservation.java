package BusinessLayer.Suppliers;

import java.util.Date;
import java.util.List;

import BusinessLayer.Suppliers.enums.Status;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

class Reservation {
    private int id;
    private int supplierId;
    private Date date;
    private Status status;
    private String destinationBranch;
    private Contact contact;
    private List<ReceiptItem> receipt;

    public Reservation(int id, int supplier_id, List<ReceiptItem> receipt, Contact contact) {
        this.id = id;
        this.supplierId = supplier_id;
        this.date = new Date();
        this.status = Status.NOTREADY;
        this.receipt = receipt;
        this.contact = contact;
    }

    public void cancel() throws SuppliersException {
        if (status == Status.ABORTED)
            throw new SuppliersException("The reservation with id " + id + " is already aborted");
        if (status == Status.CLOSED)
            throw new SuppliersException("The reservation with id " + id + " is closed");
        status = Status.ABORTED;
    }

    public void makeReady() throws SuppliersException {
        if (status == Status.ABORTED)
            throw new SuppliersException("The reservation with id " + id + " is already aborted");
        if (status == Status.READY)
            throw new SuppliersException("The reservation with id " + id + " is already ready");
        if (status == Status.CLOSED)
            throw new SuppliersException("The reservation with id " + id + " is closed");
        status = Status.READY;
    }

    public void close() throws SuppliersException {
        if (status == Status.ABORTED)
            throw new SuppliersException("The reservation with id " + id + " is already aborted");
        if (status == Status.NOTREADY)
            throw new SuppliersException("The reservation with id " + id + " is not ready");
        if (status == Status.CLOSED)
            throw new SuppliersException("The reservation with id " + id + " is already closed");
        status = Status.CLOSED;
    }

    public int getId() {
        return this.id;
    }

    public int getSupplierId() {
        return this.supplierId;
    }

    public List<ReceiptItem> getReceipt() {
        return this.receipt;
    }

    public String getDestination() {
        return this.destinationBranch;
    }

    @Override
    public String toString() {
        String output = "id: " + id + "\n";
        output += "supplier id: " + supplierId + "\n";
        output += "address: " + destinationBranch + "\n";
        output += "date: " + date + "\n";
        output += "status: " + status + "\n";
        output += "contact phone: " + contact.getPhone() + "\n";
        output += "---------------------------------------------------------------------";
        for (ReceiptItem item : receipt)
            output += item.toString() + "\n";
        output += "---------------------------------------------------------------------";
        return output;
    }
}