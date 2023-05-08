package DataAccessLayer.DTOs;

public class CategoryDTO {
    private int id;
    private String name;
    private Integer parentId; //can be null
    private int childId; //can be null

    public CategoryDTO(int id, String name, Integer parentId){
        
    }
}
