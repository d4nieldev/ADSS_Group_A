package DataAccessLayer.DTOs;

public class FieldDTO {
    private String name;
    private int supplierId;

    public FieldDTO(String name, int supplierId) {
        this.name = name;
        this.supplierId = supplierId; 
    }
}
