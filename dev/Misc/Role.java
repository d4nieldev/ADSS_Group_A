package Misc;
import java.util.Arrays;
import java.util.List;

//import javax.management.relation.RoleList;

public class Role {

    public final static List<String> listRoles = Arrays.asList(
        "HRMANAGER", "BRANCHMANAGER", "SHIFTMANAGER", "CASHIER", "STOREKEEPER", "DRIVER", "GENERRAL", "CLEANER", "SECURITY");

    public static void addRole(String role){
        if(listRoles.contains(role)){throw new Error("This role is already exsist in the system.");}
        listRoles.add(role);
    }

    public static String getRole(String role){
        role = role.toUpperCase();
        if(!listRoles.contains(role)){throw new Error("This role is not in the system.");}
        return role;
    }
}
