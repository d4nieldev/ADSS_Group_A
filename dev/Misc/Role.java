package Misc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import javax.management.relation.RoleList;

public class Role {

    static private int counterRoles = 0;
    static private List<instanceRole> roles;

// "HRMANAGER", "BRANCHMANAGER", "SHIFTMANAGER", "CASHIER", "STOREKEEPER", "DRIVER", "GENERRAL", "CLEANER", "SECURITY", "TRANSPORTMANAGER"

    public Role(){
        roles = new ArrayList<instanceRole>();
    }
    
    public static void addRole(String nameRole){
        roles.add(counterRoles, new instanceRole(counterRoles, nameRole));
        counterRoles++;
    }

    public void updateRoleName(String oldName, String newName){
        instanceRole role = getRoleByName(oldName);
        role.setName(newName);
    }

    public static instanceRole getRoleById(int id){
        return roles.get(id);
    }

    public static instanceRole getRoleByName(String name){
        for (instanceRole instanceRole : roles) {
            if(instanceRole.getName().equals(name)){return instanceRole;}
        }
        throw new Error("Not found a role in the system with that name.");
    }
}
