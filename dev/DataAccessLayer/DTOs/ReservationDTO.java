package DataAccessLayer.DTOs;

import java.sql.Date;

import BusinessLayer.Suppliers.enums.Status;

public class ReservationDTO {
    private int id;
    private int supplierId;
    private Date date;
    private Status status;
    private String destinationBranch;

    public ReservationDTO(int id, int supplierId, Date date, Status status, String destinationBranch){
        this.id = id;
        this.supplierId = supplierId;
        this.date = date;
        this.status = status;
        this.destinationBranch = destinationBranch;
    }

    public int getId(){
        return id;
    }

    public int getSupplierId(){
        return supplierId;
    }

    public Date getDate(){
        return date;
    }

    public Status getStatus(){
        return status;
    }

    public String getDestinationBranch(){
        return destinationBranch;
    }
    
}
