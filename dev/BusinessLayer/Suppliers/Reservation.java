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
    private List<ReceiptItem> receipt;
    private String destinationBranch;

    public Reservation(int id, int supplier_id, List<ReceiptItem> receipt) {
        this.id = id;
        this.supplierId = supplier_id;
        this.date = new Date();
        this.status = Status.NOTREADY;
        this.receipt = receipt;
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

    public int getSupplierId() {
        return this.supplierId;
    }
}