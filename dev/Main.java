import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.swing.text.AbstractDocument.BranchElement;

import Misc.*;

class Main {
    public static void main(String[] args) {

        ServiceLayer.EmployeesMoudle.EmployeeService employeeService = new ServiceLayer.EmployeesMoudle.EmployeeService();

        Scanner sc = new Scanner(System.in);
        System.out.print("Hello there, in order to login to the syestem please enter your Id: ");
        int loginId = Integer.parseInt(sc.nextLine());
        System.out.println("");

        System.out.print("Great, now enter your password: ");
        String loginPassword = sc.nextLine();
        System.out.println("");

        employeeService.logIn(loginId, loginPassword);
        System.out.println("");

        System.out.println("[0 - Exit system, 1 - Add employee, 2 - print all employees, 7 - Delete an employee, 8 - Login, 9 - Logout, 10 - Edit employee]");
        System.out.print("Please enter your request to the system according to the PDF file: ");
        String option = sc.nextLine();

        while(!option.equals("0")){
            try{
                if (option.equals("1")){ // 1 enter new employee
                    System.out.println("You choose to enter a new employee, please enter the information of the employee: ");

                    System.out.print("First name: ");
                    String firstName = sc.nextLine();
                    System.out.println("");
                    
                    System.out.print("Last name: ");
                    String lastName = sc.nextLine();
                    System.out.println("");

                    System.out.print("Id: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Password: ");
                    String password = sc.nextLine();
                    System.out.println("");

                    System.out.print("Bank number: ");
                    int bankNum = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Bank branch number: ");
                    int bankBranch = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Bank account number: ");
                    int bankAccount = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Salary: ");
                    int salary = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.println("please enster the date in that format Date: 05-06-2003");
                    System.out.print("Start Date: ");
                    String startDate = sc.nextLine();
                    System.out.println("");
                    
                    System.out.print("Bouns: ");
                    int bonus = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate localDate = LocalDate.parse(startDate, formatter);

                    System.out.print("Driver license if he is a driver (null/B/C): ");
                    String driverLicenseString = sc.nextLine();
                    License driverLicense = License.valueOf(driverLicenseString.toUpperCase()); //may throw an error.
                    System.out.println("");

                    System.out.print("Role: ");
                    String roleString = sc.nextLine();
                    Role role = Role.valueOf(roleString.toUpperCase()); //may throw an error.
                    System.out.println("");
                    
                    System.out.print("Super Branch: ");
                    int superBranch = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    employeeService.addEmployee(loginId, firstName, lastName, id, password, bankNum, 
                    bankBranch, bankAccount, salary, bonus, localDate, driverLicense, role, superBranch);

                }

                else if (option.equals("2")){ // 2 print all employees
                    employeeService.printAllEmployees(loginId);
                }

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

                else if (option.equals("7")){ // 7 delete an employee
                    System.out.print("Enter the Id of the employee you wish to delete: ");
                    int idToDelete = Integer.parseInt(sc.nextLine());
                    employeeService.deleteEmployee(loginId, idToDelete);
                    System.out.println("");
                }

                else if (option.equals("8")){ // 8 login
                    System.out.print("Hello there, in order to login to the syestem please enter your Id: ");
                    loginId = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Great, now enter your password: ");
                    loginPassword = sc.nextLine();
                    System.out.println("");

                    employeeService.logIn(loginId, loginPassword);
                    System.out.println("");
                }

                else if (option.equals("9")){ // 9 logout
                    employeeService.logOut(loginId);
                    System.out.println("");
                }

                else if (option.equals("10")){
                    System.out.print("Enter the Id of the employee you wish to edit: ");
                    int idToEdit = Integer.parseInt(sc.nextLine());
                    System.out.println("You choose to edit an employee, which detail would you like to edit?\n");
                    System.out.println("[0 - First name, 1 - Last name, 2 - Password, 3 - Bank number, 4 - Bank branch, 5 - Bank account, 6 - Salary, 7 - Start date, 8 - Driver licence, 9 - Done editing]");
                    option = sc.nextLine();
                    
                    while(!option.equals("9")){
                        if (option.equals("0")){
                            System.out.println("Enter the new first name: ");
                            String firstName = sc.nextLine();
                            System.out.println("");
                            employeeService.changeFirstName(loginId, idToEdit, firstName);
                        }

                        else if (option.equals("1")){
                            System.out.println("Enter the new last name: ");
                            String lastName = sc.nextLine();
                            System.out.println("");
                            employeeService.changeLastName(loginId, idToEdit, lastName);
                        }

                        else if (option.equals("2")){
                            System.out.println("Enter the new password: ");
                            String password = sc.nextLine();
                            System.out.println("");
                            employeeService.changePassword(loginId, idToEdit, password);
                        }

                        else if (option.equals("3")){
                            System.out.println("Enter the new bank number: ");
                            int bankNumber = Integer.parseInt(sc.nextLine());
                            System.out.println("");
                            employeeService.changeBankNum(loginId, idToEdit, bankNumber);
                        }

                        else if (option.equals("4")){
                            System.out.println("Enter the new bank branch: ");
                            int bankBranch = Integer.parseInt(sc.nextLine());
                            System.out.println("");
                            employeeService.changeBankBranch(loginId, idToEdit, bankBranch);
                        }

                        else if (option.equals("5")){
                            System.out.println("Enter the new bank account: ");
                            int bankAccount = Integer.parseInt(sc.nextLine());
                            System.out.println("");
                            employeeService.changeBankAccount(loginId, idToEdit, bankAccount);
                        }

                        else if (option.equals("6")){
                            System.out.println("Enter the new salary: ");
                            int salary = Integer.parseInt(sc.nextLine());
                            System.out.println("");
                            employeeService.changeSalary(loginId, idToEdit, salary);
                        }

                        else if (option.equals("7")){
                            System.out.println("please enster the date in that format Date: 05-06-2003");
                            System.out.println("Enter the new start date: ");
                            String startDate = sc.nextLine();
                            System.out.println("");

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            LocalDate localDate = LocalDate.parse(startDate, formatter);

                            employeeService.changeStartDate(loginId, idToEdit, localDate);
                        }

                        else if (option.equals("8")){
                            System.out.println("Enter the new driver license: ");
                            String driverLicenseString = sc.nextLine();
                            System.out.println("");

                            License driverLicense = License.valueOf(driverLicenseString.toUpperCase());

                            employeeService.changeDriverLicence(loginId, idToEdit, driverLicense);
                        }

                        System.out.println("Which detail would you like to edit not?");
                        System.out.println("[0 - First name, 1 - Last name, 2 - Password, 3 - Bank number, 4 - Bank branch, 5 - Bank account, 6 - Salary, 7 - Start date, 8 - Driver licence, 9 - Done editing]");
                        option = sc.nextLine();
                    }
                    
                }
                
                System.out.println("");
                System.out.println("[0 - Exit system, 1 - Add employee, 2 - print all employees, 7 - Delete an employee, 8 - Login, 9 - Logout, 10 - Edit employee]");
                System.out.print("Please enter your request to the system according to the PDF file: ");
                option = sc.nextLine();
            }
            catch(Error e) {System.out.println(e.toString()); break;} //I think we need to delete the break and add the options again.
        }

        employeeService.logOut(loginId);
        System.out.println("");
        System.out.print("Thank you for your time. See you next time.");

        sc.close();

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