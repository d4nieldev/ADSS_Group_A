package PresentationLayer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

import Misc.*;
import ServiceLayer.EmployeesLayer.BranchService;
import ServiceLayer.EmployeesLayer.EmployeeService;
import ServiceLayer.EmployeesLayer.ShiftService;
import ServiceLayer.EmployeesLayer.serviceFactory;

class HRSystem {

    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    public HRSystem(serviceFactory serviceFactory) {
        employeeService = serviceFactory.getEmployeeService();
        shiftService = serviceFactory.getShiftService();
        branchService = serviceFactory.getBranchService();
    }

    public void run(int loginId) {
        Scanner sc = new Scanner(System.in);
        System.out.println("0 - Go back\n1 - Add employee (not driver).\n2 - Add driver.\n3 - Print all branches.\n4 - Print all employees (drivers not included)\n5 - Print all drivers\n6 - Add empty shift\n7 - Submit a shift\n8 - Add constraint for some Employee to Shift\n9 - Edit employee\n10 - Delete an employee\n");
        String option = sc.nextLine();

        while(!option.equals("0")){
            try{
                if (option.equals("1")){ // 1 enter new employee
                    System.out.println("You choose to enter a new employee, please enter the information of the employee: \n");

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

                    System.out.print("Initialize Bouns: ");
                    int InitializeBonus = Integer.parseInt(sc.nextLine());
                    System.out.println("");
                    
                    System.out.print("Terms of Employment: ");
                    String tempsEmployment = sc.nextLine();
                    System.out.println("");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate localDate = LocalDate.parse(startDate, formatter);

                    System.out.print("Role: ");
                    String roleString = sc.nextLine();
                    //Integer role = Role.getRoleByName(roleString.toUpperCase()).getId();  //may throw an error.
                    //Role.valueOf(roleString.toUpperCase()); //may throw an error.
                    System.out.println("");

                    System.out.print("Super Branch: ");
                    int superBranch = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    branchService.addNewEmployee(loginId, firstName, lastName, id, password, bankNum,
                        bankBranch, bankAccount, salary, InitializeBonus, localDate, tempsEmployment, roleString, superBranch);
                }

                else if (option.equals("2")){ // 2 enter new driver

                    System.out.println("You choose to enter a new driver, please enter the information of the employee: \n");

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

                    System.out.print("Initialize Bouns: ");
                    int InitializeBonus = Integer.parseInt(sc.nextLine());
                    System.out.println("");
                    
                    System.out.print("Terms of Employment: ");
                    String tempsEmployment = sc.nextLine();
                    System.out.println("");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate localDate = LocalDate.parse(startDate, formatter);

                    System.out.print("Driver license if he is a driver (null/B/C): ");
                    String driverLicenseString = sc.nextLine();
                    License driverLicense = License.valueOf(driverLicenseString.toUpperCase()); //may throw an error.
                    System.out.println("");

                    branchService.addNewDriver(loginId, firstName, lastName, id, password, bankNum,
                        bankBranch, bankAccount, salary, InitializeBonus, localDate, tempsEmployment, driverLicense);
                }

                else if (option.equals("3")){ // 3 print all branches
                    System.out.println(branchService.printAllBranches(loginId));
                }

                else if (option.equals("4")){ // 4 print all employees
                    System.out.println(employeeService.printAllEmployees(loginId));
                }

                else if (option.equals("5")){ // 5 print all drivers
                    System.out.println(employeeService.printAllDrivers(loginId));
                }

                // else if (option.equals("6")){ // 6 add an empty shift
                //     System.out.println("You choose to add empty shift.");
                //     System.out.println("please enter the following information:");
                //     System.out.println("");

                //     System.out.print("Branch Id: ");
                //     int branchId = Integer.parseInt(sc.nextLine());
                //     System.out.println("");

                //     System.out.println("please enster the date in that format Date: 05-06-2003");
                //     System.out.print("Shift Date: ");
                //     String shiftDate = sc.nextLine();
                //     System.out.println("");;

                //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                //     LocalDate localDate = LocalDate.parse(shiftDate, formatter);

                //     System.out.print("Start hour: ");
                //     int startHour = Integer.parseInt(sc.nextLine());
                //     System.out.println("");

                //     System.out.print("End hour: ");
                //     int endHour = Integer.parseInt(sc.nextLine());
                //     System.out.println("");

                //     System.out.print("Morning Or Eveing: ");
                //     String morningEvningString = sc.nextLine();
                //     ShiftTime morningEvningShiftTime = ShiftTime.valueOf(morningEvningString.toUpperCase());
                //     System.out.println("");

                //     HashMap<Integer, Integer> numEmployeesForRole = new HashMap<>();

                //     System.out.print("Now enter the number of employees for each role. ");
                //     System.out.print("Branch Manager: ");
                //     int branchManagerNum = Integer.parseInt(sc.nextLine());
                //     numEmployeesForRole.put(Role.getRoleByName("BRANCHMANAGER").getId(), branchManagerNum);
                //     System.out.println("");

                //     System.out.print("Shift Manager: ");
                //     int shiftManagerNum = Integer.parseInt(sc.nextLine());
                //     numEmployeesForRole.put(Role.getRoleByName("SHIFTMANAGER").getId(), shiftManagerNum);
                //     System.out.println("");

                //     System.out.print("Cashier: ");
                //     int chahierNum = Integer.parseInt(sc.nextLine());
                //     numEmployeesForRole.put(Role.getRoleByName("CHASHIER").getId(), chahierNum);
                //     System.out.println("");

                //     System.out.print("Storekeeper: ");
                //     int storeeeperNum = Integer.parseInt(sc.nextLine());
                //     numEmployeesForRole.put(Role.getRoleByName("STOREKEEPER").getId(), storeeeperNum);
                //     System.out.println("");

                //     System.out.print("Driver: ");
                //     int driverNum = Integer.parseInt(sc.nextLine());
                //     numEmployeesForRole.put(Role.getRoleByName("DRIVER").getId(), driverNum);
                //     System.out.println("");

                //     System.out.println("Generral");
                //     int generralNum = Integer.parseInt(sc.nextLine());
                //     numEmployeesForRole.put(Role.getRoleByName("GENERRAL").getId(), generralNum);
                //     System.out.println("");

                //     System.out.println("Cleaner");
                //     int cleanerNum = Integer.parseInt(sc.nextLine());
                //     numEmployeesForRole.put(Role.getRoleByName("CLEANER").getId(), cleanerNum);
                //     System.out.println("");

                //     System.out.println("Security");
                //     int securityNum = Integer.parseInt(sc.nextLine());
                //     numEmployeesForRole.put(Role.getRoleByName("SECURITY").getId(), securityNum);
                //     System.out.println("");

                //     branchService.addShift(loginId, branchId, localDate, startHour, endHour, morningEvningShiftTime, numEmployeesForRole);
                // }

                else if (option.equals("7")){ // 7 hr manager submit a shift
                    System.out.println("You choose to submit a final shift.");
                    System.out.println("please enter the following information:");
                    System.out.println("");

                    System.out.print("Shift Id: ");
                    int shiftId = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Branch Id: ");
                    int branchId = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    HashMap<Integer, Integer> shiftAssign = new HashMap<>();

                    System.out.println("please enter the Id for every employee and then his role, when you are done enter in the Id 000.");
                    System.out.println("Employee Id: ");
                    int employeeId = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    while (employeeId != 000){
                        System.out.println("Employee role: ");
                        int employeeRole = Integer.parseInt(sc.nextLine());
                        System.out.println("");

                        shiftAssign.put(employeeId, employeeRole);

                        System.out.println("Next employee Id: ");
                        employeeId = Integer.parseInt(sc.nextLine());
                        System.out.println("");
                    }

                    branchService.approveFinalShift(loginId,shiftId,branchId,shiftAssign);
                }

                else if(option.equals("8")){ // 8 add constraint for an employee to a shift
                    System.out.println("You choose to add constraint for an employee to a shift.");
                    System.out.println("please enter the following information:");
                    System.out.println("");

                    System.out.print("Branch Id: ");
                    int branch = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Employee Id: ");
                    int idEmployee = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Shift Id: ");
                    int shift = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    branchService.addConstraint(branch, idEmployee, shift);
                }

                else if (option.equals("9")){ // 9 edit employee
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

                        System.out.println("Which detail would you like to edit now?");
                        System.out.println("[0 - First name, 1 - Last name, 2 - Password, 3 - Bank number, 4 - Bank branch, 5 - Bank account, 6 - Salary, 7 - Start date, 8 - Driver licence, 9 - Done editing]");
                        option = sc.nextLine();
                    }

                }

                else if (option.equals("10")){ // 10 delete an employee
                    System.out.print("Enter the Id of the employee you wish to delete: ");
                    int idToDelete = Integer.parseInt(sc.nextLine());
                    branchService.deleteEmployee(loginId, idToDelete);
                    System.out.println("");
                }

                System.out.println("");
                System.out.println("0 - Go back\n1 - Add employee (not driver).\n2 - Add driver.\n3 - Print all branches.\n4 - Print all employees (drivers not included)\n5 - Print all drivers\n6 - Add empty shift\n7 - Submit a shift\n8 - Add constraint for some Employee to Shift\n9 - Edit employee\n10 - Delete an employee\n");
            }
            catch(Error e) {
                System.out.println("This error happened: \n");
                System.out.println(e.toString());
                System.out.println("Please choose again: \n");
                System.out.println("0 - Go back\n1 - Add employee\n2 - Print all employees (drivers not included)\n3 - Print all drivers\n4 - Add empty shift\n5 - Submit a shift\n6 - Add constraint for some Employee to Shift\n7 - Edit employee\n8 - Delete an employee\n");

                option = sc.nextLine();
            }

            System.out.println("0 - Go back\n1 - Add employee (not driver).\n2 - Add driver.\n3 - Print all branches.\n4 - Print all employees (drivers not included)\n5 - Print all drivers\n6 - Add empty shift\n7 - Submit a shift\n8 - Add constraint for some Employee to Shift\n9 - Edit employee\n10 - Delete an employee\n");
            option = sc.nextLine();
        }

        employeeService.logOut(loginId);

        sc.close();
    }
}