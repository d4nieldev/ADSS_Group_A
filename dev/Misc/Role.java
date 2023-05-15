package Misc;
import DataAccessLayer.DAO.EmployeesLayer.RolesDAO;
import DataAccessLayer.DTO.EmployeeLayer.RoleDTO;

import java.util.ArrayList;
import java.util.List;

//import javax.management.relation.RoleList;

public class Role {

    private int counterRoles = 0;
    private List<instanceRole> roles;

    static private RolesDAO rolesDAO;

    public Role(){
        roles = new ArrayList<instanceRole>();
        rolesDAO = new RolesDAO();
        loadAllRolesFromDB();
    }
    
    public void addRole(String nameRole){
        instanceRole newRole = new instanceRole(counterRoles, nameRole);
        roles.add(counterRoles, newRole);
        rolesDAO.insert(newRole.toDTO());
        counterRoles++;
    }

    public void removeRole(String nameRole){
        int foundIDrole = -1;
        for (instanceRole currrole :
                roles) {
            if(currrole.getName() == nameRole)
                foundIDrole = currrole.getId();
        }
        instanceRole foundRole = roles.get(foundIDrole);
        roles.remove(foundRole);
        rolesDAO.delete("RoleID", foundIDrole);
    }

    public void updateRoleName(String oldName, String newName){
        instanceRole role = getRoleByName(oldName);
        role.setName(newName);
        rolesDAO.update(role.toDTO());
    }

    public instanceRole getRoleById(int id){
        return roles.get(id);
    }

    public instanceRole getRoleByName(String name){
        for (instanceRole instanceRole : roles) {
            if(instanceRole.getName().equals(name)){return instanceRole;}
        }
        throw new Error("Not found a role in the system with that name.");
    }

    private void loadAllRolesFromDB(){
        List<RoleDTO> rolesDTO = rolesDAO.getAll();
        for (RoleDTO roleDTO : rolesDTO) {
            instanceRole newRole = new instanceRole(roleDTO.roleId, roleDTO.roleName);
            roles.add(newRole);
            counterRoles++;
        }
    }
}
