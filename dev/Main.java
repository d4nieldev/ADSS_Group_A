import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Misc.*;

class Main {
    public static void main(String[] args) {

        ServiceLayer.EmployeesMoudle.EmployeeService service = new ServiceLayer.EmployeesMoudle.EmployeeService();

        Scanner sc = new Scanner(System.in);
        System.out.print("Hello there, in order to login to the syestem please enter your Id: ");
        int loginId = sc.nextInt();
        System.out.println("");

        System.out.print("Great, now enter your password: ");
        String loginPassword = sc.nextLine();
        System.out.println("");

        service.logIn(loginId, loginPassword);
        System.out.println("");

        System.out.print("Please enter your request to the system according to the PDF file: ");
        String option = sc.nextLine();

        while(!option.equals("0")){

            if (option.equals("1")){ // 1 enter new employee
                System.out.println("You choose to enter a new employee, please enter the information of the employee: ");

                System.out.print("First name: ");
                String firstName = sc.nextLine();
                System.out.println("");
                
                System.out.print("Last name: ");
                String lastName = sc.nextLine();
                System.out.println("");

                System.out.print("Id: ");
                int id = sc.nextInt();
                System.out.println("");

                System.out.print("Password: ");
                String password = sc.nextLine();
                System.out.println("");

                System.out.print("Bank number: ");
                int bankNum = sc.nextInt();
                System.out.println("");

                System.out.print("Bank branch number: ");
                int bankBranch = sc.nextInt();
                System.out.println("");

                System.out.print("Bank account number: ");
                int bankAccount = sc.nextInt();
                System.out.println("");

                System.out.print("Salary: ");
                int salary = sc.nextInt();
                System.out.println("");

                System.out.print("Start Date: ");
                System.out.println("please enster the date in that format Date: 05-06-2003");
                String startDate = sc.nextLine();
                System.out.println("");
                
                System.out.print("Bouns: ");
                int bonus = sc.nextInt();
                System.out.println("");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate localDate = LocalDate.parse(startDate, formatter);

                System.out.print("Driver license if he is a driver (null/B/C): ");
                String driverLicenseString = sc.nextLine();
                License driverLicense = License.valueOf(driverLicenseString.toUpperCase()); //may throw an error.


                System.out.print("Role: ");
                String roleString = sc.nextLine();
                Role role = Role.valueOf(roleString.toUpperCase()); //may throw an error.

                service.addEmployee(loginId, firstName, lastName, id, password, bankNum, 
                bankBranch, bankAccount, salary, bonus, localDate, driverLicense, role);

            }

            // // 2 print all employees
            // printAllEmployees();

            // // 3 add shift
            // addShift();

            // // 4 add constraint for employee to shift
            // addConstraint();

            // // 5 print all constraints for some shift
            // printAllConstraints();

            // // 6 HR manager assign emlpoyee to shift
            // assignEmployeeToShift();

            // // 7 delete employee from the system
            // deleteEmployee();


            System.out.print("Please enter your request to the system according to the PDF file: ");
            option = sc.nextLine();
        }

        service.logOut(loginId);
        System.out.println("");
        System.out.print("Thank you for your time. See you next time.");

        //service.logIn(12345, "12345");
        //service.logOut(12345);

        // if (!option.equals("1"))
        // {
        //     throw new Error("Expected 1 but got somthing else. Please try again.");
        // }
        // try{
            
        //     //enter new HR manager           
        //     System.out.print("First name: ");
        //     String firstName = sc.nextLine();
        //     System.out.println("");
            
        //     System.out.print("Last name: ");
        //     String lastName = sc.nextLine();
        //     System.out.println("");

        //     System.out.print("Id: ");
        //     int id = sc.nextInt();
        //     System.out.println("");

        //     System.out.print("Password: ");
        //     String password = sc.nextLine();
        //     System.out.println("");

        //     System.out.print("Bank number: ");
        //     int bankNum = sc.nextInt();
        //     System.out.println("");

        //     System.out.print("Bank branch number: ");
        //     int bankBranch = sc.nextInt();
        //     System.out.println("");

        //     System.out.print("Bank account number: ");
        //     int bankAccount = sc.nextInt();
        //     System.out.println("");

        //     System.out.print("Salary: ");
        //     int salary = sc.nextInt();
        //     System.out.println("");

        //     System.out.print("Start Date: ");
        //     System.out.println("please enster the date in that format Date: 05-06-2003");
        //     String startDate = sc.nextLine();
        //     System.out.println("");
            
        //     System.out.print("Bouns: ");
        //     int bonus = sc.nextInt();
        //     System.out.println("");

        //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //     LocalDate localDate = LocalDate.parse(startDate, formatter);
            
        //     // Scanner year = new Scanner(System.in);
        //     // System.out.print("Month: ");
        //     // Scanner month = new Scanner(System.in);
        //     // System.out.print("Day: ");
        //     // Scanner day = new Scanner(System.in);

        //     System.out.print("Driver license if he is a driver (null/B/C) :");
        //     //Scanner driverLicense = new Scanner(System.in);

        //     Role role = Role.HRmanager;
        //     License driverLicense = License.Null;

        //     service.addEmployee(firstName, lastName, id, password, bankNum, 
        //     bankBranch, bankAccount, salary, bonus, localDate, driverLicense, role);

    //         System.out.print("Please enter your request to the system according to the PDF file: ");
    //         option = sc.nextLine();
            
    //         while(!option.equals("0")){
    //             // // 1 enter new employee
    //             // addEmployee();

    //             // // 2 print all employees
    //             // printAllEmployees();

    //             // // 3 add shift
    //             // addShift();

    //             // // 4 add constraint for employee to shift
    //             // addConstraint();

    //             // // 5 print all constraints for some shift
    //             // printAllConstraints();

    //             // // 6 HR manager assign emlpoyee to shift
    //             // assignEmployeeToShift();

    //             // // 7 delete employee from the system
    //             // deleteEmployee();


    //             // System.out.print("Please enter your request to the system according to the PDF file: ");
    //             // sc = new Scanner(System.in);
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