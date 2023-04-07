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

    public double getTotalBeforeDiscount() {
        double sum = 0.0;
        for (ReceiptItem item : receipt)
            sum += item.getPricePerUnitBeforeDiscount() * item.getAmount();

        return sum;
    }

    public double getTotalAfterDiscount() {
        double sum = 0.0;
        for (ReceiptItem item : receipt)
            sum += item.getPricePerUnitAfterDiscount() * item.getAmount();

        return sum;
    }

    public static String reservationsToString(List<Reservation> reservations) {
        String output = "";
        double totalBeforeDiscount = 0.0;
        double totalAfterDiscount = 0.0;

        for (Reservation r : reservations) {
            output += baseToString(r, false) + "\n";
            totalBeforeDiscount += r.getTotalBeforeDiscount();
            totalAfterDiscount += r.getTotalAfterDiscount();
        }

        output += "======================================================================";
        output += "Total before discount: " + totalBeforeDiscount + "\n";
        output += "Total after discount: " + totalAfterDiscount + "\n";
        output += "Saved: " + (totalAfterDiscount - totalBeforeDiscount) + "\n";

        return output;

    }

    private static String baseToString(Reservation r, boolean total) {
        double totalBeforeDiscount = r.getTotalBeforeDiscount();
        double totalAfterDiscount = r.getTotalAfterDiscount();

        String output = "";
        output += "======================================================================";
        output += "id: " + r.id + "\n";
        output += "supplier id: " + r.supplierId + "\n";
        output += "address: " + r.destinationBranch + "\n";
        output += "date: " + r.date + "\n";
        output += "status: " + r.status + "\n";
        output += "contact phone: " + r.contact.getPhone() + "\n";
        output += "---------------------------------------------------------------------";
        for (ReceiptItem item : r.receipt)
            output += item.toString() + "\n";
        if (total) {
            output += "---------------------------------------------------------------------";
            output += "Total before discount: " + totalBeforeDiscount + "\n";
            output += "Total after discount: " + totalAfterDiscount + "\n";
            output += "Saved: " + (totalAfterDiscount - totalBeforeDiscount) + "\n";
        }
        output += "======================================================================";
        return output;
    }

    @Override
    public String toString() {
        return baseToString(this, true);
    }
}