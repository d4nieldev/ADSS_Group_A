package BusinessLayer.InveontorySuppliers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import BusinessLayer.Suppliers.Contact;
import BusinessLayer.enums.Status;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DTOs.ReservationDTO;

public class Reservation {
    private int id;
    private int supplierId;
    private LocalDate date;
    private Status status;
    private int destinationBranchId;
    private Contact contact;
    private List<ReceiptItem> receipt;

    private ReservationDTO dto;

    public Reservation(ReservationDTO dto, Contact contact, List<ReceiptItem> receipt) {
        this.dto = dto;
        this.id = dto.getId();
        this.supplierId = dto.getSupplierId();
        this.date = dto.getDate();
        this.status = dto.getStatus();
        this.destinationBranchId = dto.getDestinationBranchId();
        this.contact = contact;
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

    public int getId() {
        return this.id;
    }

    public int getSupplierId() {
        return this.supplierId;
    }

    public List<ReceiptItem> getReceipt() {
        return this.receipt;
    }

    public Integer getDestination() {
        return this.destinationBranchId;
    }

    public ReservationDTO getDto() {
        return dto;
    }

    public double getPriceBeforeDiscount() {
        double sum = 0.0;
        for (ReceiptItem item : receipt)
            sum += item.getPricePerUnitBeforeDiscount() * item.getAmount();

        return sum;
    }

    public double getPriceAfterDiscount() {
        double sum = 0.0;
        for (ReceiptItem item : receipt)
            sum += item.getPricePerUnitAfterDiscount() * item.getAmount();

        return sum;
    }

    public void addReceiptItem(ReceiptItem item) {
        this.receipt.add(item);
        this.dto.getReceipt().add(item.getDTO());
    }

    public int getTotalAmount() {
        int amount = 0;
        for (ReceiptItem item : receipt)
            amount += item.getAmount();
        return amount;
    }

    public void floorReservation(Map<Integer, Integer> productToAmount) {
        for (ReceiptItem item : receipt) {
            int productId = item.getProduct().getId();
            if (productToAmount.containsKey(productId)) {
                int amount = Math.min(item.getAmount(), productToAmount.get(productId));
                item.setAmount(amount);
                item.getDTO().setAmount(amount);
            }
        }

        receipt = receipt.stream()
                .filter(item -> productToAmount.containsKey(item.getProduct().getId()) && item.getAmount() > 0)
                .collect(Collectors.toList());
        dto.setReceipt(receipt.stream().map(item -> item.getDTO()).collect(Collectors.toList()));
    }

    public static String reservationsToString(List<Reservation> reservations) {
        String output = "";
        double totalBeforeDiscount = 0.0;
        double totalAfterDiscount = 0.0;

        for (Reservation r : reservations) {
            output += baseToString(r, false) + "\n";
            totalBeforeDiscount += r.getPriceBeforeDiscount();
            totalAfterDiscount += r.getPriceAfterDiscount();
        }

        output += "======================================================================";
        output += "Total before discount: " + totalBeforeDiscount + "\n";
        output += "Total after discount: " + totalAfterDiscount + "\n";
        output += "Saved: " + (totalAfterDiscount - totalBeforeDiscount) + "\n";

        return output;

    }

    private static String baseToString(Reservation r, boolean total) {
        double totalBeforeDiscount = r.getPriceBeforeDiscount();
        double totalAfterDiscount = r.getPriceAfterDiscount();

        String output = "";
        output += "======================================================================";
        output += "id: " + r.id + "\n";
        output += "supplier id: " + r.supplierId + "\n";
        output += "destinationBranchId: " + r.destinationBranchId + "\n";
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