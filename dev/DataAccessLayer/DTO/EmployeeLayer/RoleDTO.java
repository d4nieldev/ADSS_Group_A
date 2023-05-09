package DataAccessLayer.DTO.EmployeeLayer;

public class RoleDTO {
    public int roleId;
    public String roleName;
    
    
	public RoleDTO(int roleId, String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }
    
    public int getId(){return roleId;}
    
    public String getRoleName(){return roleName;}
    
    public String fieldsToString() {
        return String.format("(\"%d\",\"%s\")", this.roleId, this.roleName);
    }
}
