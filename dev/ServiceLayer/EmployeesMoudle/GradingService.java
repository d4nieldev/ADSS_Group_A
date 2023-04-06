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

    public void logIn(int id, String password){
        try{employeeS.logIn(id, password);}
        catch (Error error){System.out.println(error.toString());}        
    }
    
    public void logOut(int id){
        try{employeeS.logOut(id);}
        catch (Error error){System.out.println(error.toString());}        
    }
    
}
