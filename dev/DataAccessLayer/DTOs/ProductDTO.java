package DataAccessLayer.DTOs;

public class ProductDTO {
    private int id;
    private String name;
    private String manufacturer;

    public ProductDTO(int id, String name, String manufacturer){
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
    }
}
