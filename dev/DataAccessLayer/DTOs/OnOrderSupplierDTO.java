package DataAccessLayer.DTOs;

import java.util.List;

public class OnOrderSupplierDTO extends SupplierDTO {

    private int maxSupplyDays;
    
    public OnOrderSupplierDTO(int id, String name, String bankAcc, List<FieldDTO> fields, int maxSupplyDays) {
        this.id = id;
        this.name = name;
        this.bankAcc = bankAcc;
        this.fields = fields;
        this.maxSupplyDays=maxSupplyDays;
    }
}
