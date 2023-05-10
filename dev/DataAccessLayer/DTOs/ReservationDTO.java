package DataAccessLayer.DTOs;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import BusinessLayer.enums.Status;

public class ReservationDTO implements DTO{
    private int id;
    private SupplierDTO supplierDTO;
    private LocalDate date;
    private Status status;
    private BranchDTO branchDTO;
    

    public ReservationDTO(int id, SupplierDTO supplierDTO, LocalDate date,  Status status, BranchDTO branchDTO){
        this.id = id;
        this.supplierDTO = supplierDTO;
        this.date = date;
        this.status = status;
        this.branchDTO = branchDTO;
    }

    public int getId(){
        return id;
    }

    public SupplierDTO getSupplierDTO(){
        return supplierDTO;
    }

    public LocalDate getDate(){
        return date;
    }

    public Status getStatus(){
        return status;
    }

    public BranchDTO getBranchDTO(){
        return branchDTO;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("id", "" + id);
        nameToVal.put("supplierId", ""+ getSupplierDTO().getSupplierId());
        nameToVal.put("rDate", ""+ date.toString());
        nameToVal.put("status", ""+ status.toString());
        nameToVal.put("destinationBranch", ""+ getBranchDTO().getBranchId());
        return nameToVal;
    }
    
}
