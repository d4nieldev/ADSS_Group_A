package ServiceLayer.EmployeesMoudle;

public class GradingService {
    private EmployeeService employeeS;
    private ShiftService shiftS;
    private BranchService branchS;
    private serviceFactory serviceFactory;

    public GradingService(){
        serviceFactory = new serviceFactory();
        employeeS = serviceFactory.getEmployeeService();
        shiftS = serviceFactory.getShiftService();
        branchS = serviceFactory.getBranchService();
    }

    // ------------------------------------------- EMPLOYEE SERVICE ------------------------------------------------------------

    public EmployeeService getEmployeeService(){return employeeS;}
    
    public void logIn(int id, String password){
        /*
        try{employeeS.logIn(id, password);}
        catch (Error error){System.out.println(error.toString());}   
         */     
        employeeS.logIn(id, password);
    }
    
    public void logOut(int id){
        /*
        try{employeeS.logOut(id);}
        catch (Error error){System.out.println(error.toString());}    
         */    
        employeeS.logOut(id);
    }
    
    // ------------------------------------------- SHIFT SERVICE ------------------------------------------------------------


    // ------------------------------------------- BRANCH SERVICE ------------------------------------------------------------

}
