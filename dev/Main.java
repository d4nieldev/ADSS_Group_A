import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        //System.out.println("Hello world!");

        ServiceLayer.EmployeesMoudle.EmployeeService service = new ServiceLayer.EmployeesMoudle.EmployeeService();
        //service.logIn(12345, "12345");
        //service.logOut(12345);

        System.out.print("Please enter your request to the system according to the PDF file: ");
        Scanner sc = new Scanner(System.in);
        if (!sc.equals("1"))
        {
            throw new Error("Expected 1 but got somthing else. Please try again.");
        }
        try{
        //enter new HR manager
            
        System.out.print("First name: ");
        Scanner firstName = new Scanner(System.in);
        System.out.print("Last name: ");
        Scanner lastName = new Scanner(System.in);
        System.out.print("Id: ");
        Scanner id = new Scanner(System.in);
        System.out.print("Password: ");
        Scanner password = new Scanner(System.in);
        System.out.print("Bank number: ");
        Scanner bankNum = new Scanner(System.in);
        System.out.print("Bank branch number: ");
        Scanner bankBranch = new Scanner(System.in);
        System.out.print("Bank account number: ");
        Scanner bankAccount = new Scanner(System.in);
        System.out.print("Salary: ");
        Scanner salary = new Scanner(System.in);
        System.out.print("Start Date: Year:");
        Scanner year = new Scanner(System.in);
        System.out.print("Month: ");
        Scanner month = new Scanner(System.in);
        System.out.print("Day: ");
        Scanner day = new Scanner(System.in);
        System.out.print("Driver license if he is a driver (null/B/C) :");
        Scanner driverLicense = new Scanner(System.in);

         ServiceLayer.EmployeesMoudle.EmployeeService.addEmployee(firstName, lastName, id, password, bankNum, bankBranch, bankAccount, salary, year, month, day, driverLicense, "HRmanager");

    //         System.out.print("Please enter your request to the system according to the PDF file: ");
    //         sc = new Scanner(System.in);
            
    //         while(!sc.equals("0")){
    //             // 1 enter new employee
    //             addEmployee();

    //             // 2 print all employees
    //             printAllEmployees();

    //             // 3 add shift
    //             addShift();

    //             // 4 add constraint for employee to shift
    //             addConstraint();

    //             // 5 print all constraints for some shift
    //             printAllConstraints();

    //             // 6 HR manager assign emlpoyee to shift
    //             assignEmployeeToShift();

    //             // 7 delete employee from the system
    //             deleteEmployee();


    //             System.out.print("Please enter your request to the system according to the PDF file: ");
    //             sc = new Scanner(System.in);
    //         }
    //     }
    //     catch (Error e) {System.out.println(e.toString());}
            
    //     System.out.print("Thank you for your time. See you next time.");
    // }

    // public static void addEmployee() {
    //     System.out.print("First name: ");
    //     Scanner firstName = new Scanner(System.in);
    //     System.out.print("Last name: ");
    //     Scanner lastName = new Scanner(System.in);
    //     System.out.print("Id: ");
    //     Scanner id = new Scanner(System.in);
    //     System.out.print("Password: ");
    //     Scanner password = new Scanner(System.in);
    //     System.out.print("Bank number: ");
    //     Scanner bankNum = new Scanner(System.in);
    //     System.out.print("Bank branch number: ");
    //     Scanner bankBranch = new Scanner(System.in);
    //     System.out.print("Bank account number: ");
    //     Scanner bankAccount = new Scanner(System.in);
    //     System.out.print("Salary: ");
    //     Scanner salary = new Scanner(System.in);
    //     System.out.print("Start Date: Year:");
    //     Scanner year = new Scanner(System.in);
    //     System.out.print("Month: ");
    //     Scanner month = new Scanner(System.in);
    //     System.out.print("Day: ");
    //     Scanner day = new Scanner(System.in);
    //     System.out.print("Driver license if he is a driver (null/B/C) :");
    //     Scanner driverLicense = new Scanner(System.in);
    //     System.out.print("Role (branchManager, shiftManager, chashier, storekeeper, driver, generral, cleaner, security): ");
    //     Scanner role = new Scanner(System.in);

    //     ServiceLayer.EmployeesMoudle.EmployeeService.addEmployee(firstName, lastName, id, password, bankNum, bankBranch, bankAccount, salary, year, month, day, driverLicense, role);
    // }

    // public static void printAllEmployees() {
    //     ServiceLayer.EmployeesMoudle.EmployeeService.printAllEmployees();
    // }
    
    // public static void addShift() {
    //     ServiceLayer.EmployeesMoudle.ShiftService.addShift();
    // }
    
    // public static void addConstraint() {
    //     ServiceLayer.EmployeesMoudle.??????;
    // }
    
    // public static void printAllConstraints() {
    //     ServiceLayer.EmployeesMoudle.ShiftService.printAllConstraints();
    // }
    
    // public static void assignEmployeeToShift() {
    // }
    // ServiceLayer.EmployeesMoudle.??????;
    
    // public static void deleteEmployee() {
    //     ServiceLayer.EmployeesMoudle.EmployeeService.deleteEmployee();
    // }

    }
}