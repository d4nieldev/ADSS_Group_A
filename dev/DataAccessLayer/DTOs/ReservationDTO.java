package DataAccessLayer.DTOs;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import BusinessLayer.enums.Status;

public class ReservationDTO implements DTO {
    private int id;
    private int supplierId;
    private LocalDate date;
    private Status status;
    private int destinationBranchId;
    private ContactDTO contact;
    private List<ReceiptItemDTO> receipt;

    public ReservationDTO(int id, int supplierId, LocalDate date, Status status, int destinationBranch,
            ContactDTO contact, List<ReceiptItemDTO> receipt) {
        this.id = id;
        this.supplierId = supplierId;
        this.date = date;
        this.status = status;
        this.destinationBranchId = destinationBranch;
        this.contact = contact;
        this.receipt = receipt;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getDestinationBranchId() {
        return destinationBranchId;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public List<ReceiptItemDTO> getReceipt() {
        return receipt;
    }

    public void setReceipt(List<ReceiptItemDTO> receipt) {
        this.receipt = receipt;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("id", "" + id);
        nameToVal.put("supplierId", "" + supplierId);
        nameToVal.put("rDate", date.toString());
        nameToVal.put("status", "" + status.toString());
        nameToVal.put("destinationBranch", "" + destinationBranchId);
        nameToVal.put("contactPhone", "" + contact.getPhone());
        return nameToVal;
    }

}
