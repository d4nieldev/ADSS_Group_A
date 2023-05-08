package DataAccessLayer.DTOs;

import java.util.List;

public class SelfPickUpSupplierDTO extends SupplierDTO {

    private String address;
    private int maxPreperationDays;
    
    public SelfPickUpSupplierDTO(int id, String name, String bankAcc, List<FieldDTO> fields, String address, int maxPreperationDays) {
        this.id = id;
        this.name = name;
        this.bankAcc = bankAcc;
        this.fields = fields;
        this.address = address;
        this.maxPreperationDays = maxPreperationDays;
    }

}
