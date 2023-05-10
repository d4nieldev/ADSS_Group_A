package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class CategoryDTO implements DTO {
    private int id;
    private String name;
    private CategoryDTO parentCategoryDTO; //can be null

    public CategoryDTO(int id, String name, CategoryDTO parentCategoryDTO){
        this.id = id;
        this.name = name;
        this.parentCategoryDTO = parentCategoryDTO;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("id", "" + id);
        nameToVal.put("name", ""+ name);
        nameToVal.put("parent", ""+ getParentId());
        return nameToVal;
    }

    public Integer getParentId() {
        if(parentCategoryDTO==null){
            return null;
        }else{
            return parentCategoryDTO.getId();
        }
    }
    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryDTO getParentCategoryDTO(){
        return parentCategoryDTO;
    }


}
