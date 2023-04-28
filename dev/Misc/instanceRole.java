package Misc;

public class instanceRole {
    private int id;
    private String nameRole;
    
    public instanceRole(int id, String nameRole){
        this.id = id;
        this.nameRole = nameRole;
    }

    public int getId(){return id;}
    public String getName(){return nameRole;}
    public void setName(String newName){this.nameRole = newName;}
}
