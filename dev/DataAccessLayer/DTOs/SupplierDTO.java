package DataAccessLayer.DTOs;

import java.util.List;

public abstract class SupplierDTO {
    protected int id;
    protected String name;
    protected String bankAcc;
    protected List<FieldDTO> fields;
    protected String paymentCondition;
    
}
