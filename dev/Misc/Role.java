package Misc;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Role {

    public final List<String> listRoles = Arrays.asList(
        "HRMANAGER", "BRANCHMANAGER", "SHIFTMANAGER", "CHASHIER", "STOREKEEPER", "DRIVER", "GENERRAL", "CLEANER", "SECURITY");

    public void addRole(String role){
        if(listRoles.contains(role)){throw new Error("This role is already exsist in the system.");}
        listRoles.add(role);
    }
}
