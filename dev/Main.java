import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import ServiceLayer.EmployeesMoudle.GradingService;
import Misc.*;

class Main {
    public static void main(String[] args) {
        ServiceLayer.EmployeesMoudle.GradingService gradingService = new GradingService();
        //ServiceLayer.EmployeesMoudle.EmployeeService employeeService = new ServiceLayer.EmployeesMoudle.EmployeeService();

        Scanner sc = new Scanner(System.in);
        System.out.print("Hello there, in order to login to the system please enter your Id: ");
        int loginId = Integer.parseInt(sc.nextLine());
        System.out.println("");

        System.out.print("Great, now enter your password: ");
        String loginPassword = sc.nextLine();
        System.out.println("");

        gradingService.logIn(loginId, loginPassword);
        System.out.println("");

        System.out.println("[0 - Exit system, 1 - Add employee, 2 - Print all employees, 3 - Add empty shift, 4 - Submit a shift, 5 - Add constraint for some Employee to Shift, 6 - Edit employee, 7 - Delete an employee, 8 - Login, 9 - Logout]");
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
                    String role = Role.getRole(roleString);  //may throw an error.
                    //Role.valueOf(roleString.toUpperCase()); //may throw an error.
                    System.out.println("");
                    
                    System.out.print("Super Branch: ");
                    int superBranch = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Status: ");
                    String status = sc.nextLine();
                    System.out.println("");

                    gradingService.addNewEmployee(loginId, firstName, lastName, id, password, bankNum, 
                    bankBranch, bankAccount, salary, bonus, localDate, driverLicense, role, superBranch, status);

                }

                else if (option.equals("2")){ // 2 print all employees
                    gradingService.printAllEmployees(loginId);
                }

                else if (option.equals("3")){ // 3 add an empty shift
                    System.out.println("You choose to add empty shift.");
                    System.out.println("please enter the following information:");
                    System.out.println("");

                    System.out.print("Branch Id: ");
                    int branchId = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.println("please enster the date in that format Date: 05-06-2003");
                    System.out.print("Shift Date: ");
                    String shiftDate = sc.nextLine();
                    System.out.println("");;

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate localDate = LocalDate.parse(shiftDate, formatter);

                    System.out.print("Start hour: ");
                    int startHour = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("End hour: ");
                    int endHour = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Morning Or Eveing: ");
                    String morningEvningString = sc.nextLine();
                    ShiftTime morningEvningShiftTime = ShiftTime.valueOf(morningEvningString.toUpperCase());
                    System.out.println("");

                    HashMap<String, Integer> numEmployeesForRole = new HashMap<>();

                    System.out.print("Now enter the number of employees for each role. ");
                    System.out.print("Branch Manager: ");
                    int branchManagerNum = Integer.parseInt(sc.nextLine());
                    numEmployeesForRole.put("BRANCHMANAGER", branchManagerNum);
                    System.out.println("");

                    System.out.print("Shift Manager: ");
                    int shiftManagerNum = Integer.parseInt(sc.nextLine());
                    numEmployeesForRole.put("SHIFTMANAGER", shiftManagerNum);
                    System.out.println("");

                    System.out.print("Cashier: ");
                    int chahierNum = Integer.parseInt(sc.nextLine());
                    numEmployeesForRole.put("CHASHIER", chahierNum);
                    System.out.println("");

                    System.out.print("Storekeeper: ");
                    int storeeeperNum = Integer.parseInt(sc.nextLine());
                    numEmployeesForRole.put("STOREKEEPER", storeeeperNum);
                    System.out.println("");

                    System.out.print("Driver: ");
                    int driverNum = Integer.parseInt(sc.nextLine());
                    numEmployeesForRole.put("DRIVER", driverNum);
                    System.out.println("");

                    System.out.println("Generral");
                    int generralNum = Integer.parseInt(sc.nextLine());
                    numEmployeesForRole.put("GENERRAL", generralNum);
                    System.out.println("");

                    System.out.println("Cleaner");
                    int cleanerNum = Integer.parseInt(sc.nextLine());
                    numEmployeesForRole.put("CLEANER", cleanerNum);
                    System.out.println("");

                    System.out.println("Security");
                    int securityNum = Integer.parseInt(sc.nextLine());
                    numEmployeesForRole.put("SECURITY", securityNum);
                    System.out.println("");
       
                    gradingService.addShift(loginId, branchId, localDate, startHour, endHour, morningEvningShiftTime, numEmployeesForRole);
                }
                
                else if (option.equals("4")){ // 4 hr manager submit a shift
                    System.out.println("You choose to submit a final shift.");
                    System.out.println("please enter the following information:");
                    System.out.println("");

                    System.out.print("Shift Id: ");
                    int shiftId = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Branch Id: ");
                    int branchId = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    HashMap<Integer, String> shiftAssign = new HashMap<>();

                    System.out.println("please enter the Id for every employee and then his role, when you are done enter in the Id 000.");
                    System.out.println("Employee Id: ");
                    int employeeId = Integer.parseInt(sc.nextLine());
                    System.out.println("");
                    
                    while (employeeId != 000){
                        System.out.println("Employee role: ");
                        String employeeRole = sc.nextLine();
                        System.out.println("");

                        shiftAssign.put(employeeId, employeeRole);

                        System.out.println("Next employee Id: ");
                        employeeId = Integer.parseInt(sc.nextLine());
                        System.out.println("");
                    }

                    gradingService.approveFinalShift(loginId,shiftId,branchId,shiftAssign);
                }

                else if(option.equals("5")){ // 5 add constraint for an employee to a shift
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

                    gradingService.addConstraint(branch, idEmployee, shift);
                }

                else if (option.equals("6")){ // 6 edit employee
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
                            gradingService.changeFirstName(loginId, idToEdit, firstName);
                        }

                        else if (option.equals("1")){
                            System.out.println("Enter the new last name: ");
                            String lastName = sc.nextLine();
                            System.out.println("");
                            gradingService.changeLastName(loginId, idToEdit, lastName);
                        }

                        else if (option.equals("2")){
                            System.out.println("Enter the new password: ");
                            String password = sc.nextLine();
                            System.out.println("");
                            gradingService.changePassword(loginId, idToEdit, password);
                        }

                        else if (option.equals("3")){
                            System.out.println("Enter the new bank number: ");
                            int bankNumber = Integer.parseInt(sc.nextLine());
                            System.out.println("");
                            gradingService.changeBankNum(loginId, idToEdit, bankNumber);
                        }

                        else if (option.equals("4")){
                            System.out.println("Enter the new bank branch: ");
                            int bankBranch = Integer.parseInt(sc.nextLine());
                            System.out.println("");
                            gradingService.changeBankBranch(loginId, idToEdit, bankBranch);
                        }

                        else if (option.equals("5")){
                            System.out.println("Enter the new bank account: ");
                            int bankAccount = Integer.parseInt(sc.nextLine());
                            System.out.println("");
                            gradingService.changeBankAccount(loginId, idToEdit, bankAccount);
                        }

                        else if (option.equals("6")){
                            System.out.println("Enter the new salary: ");
                            int salary = Integer.parseInt(sc.nextLine());
                            System.out.println("");
                            gradingService.changeSalary(loginId, idToEdit, salary);
                        }

                        else if (option.equals("7")){
                            System.out.println("please enster the date in that format Date: 05-06-2003");
                            System.out.println("Enter the new start date: ");
                            String startDate = sc.nextLine();
                            System.out.println("");

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            LocalDate localDate = LocalDate.parse(startDate, formatter);

                            gradingService.changeStartDate(loginId, idToEdit, localDate);
                        }

                        else if (option.equals("8")){
                            System.out.println("Enter the new driver license: ");
                            String driverLicenseString = sc.nextLine();
                            System.out.println("");

                            License driverLicense = License.valueOf(driverLicenseString.toUpperCase());

                            gradingService.changeDriverLicence(loginId, idToEdit, driverLicense);
                        }

                        System.out.println("Which detail would you like to edit now?");
                        System.out.println("[0 - First name, 1 - Last name, 2 - Password, 3 - Bank number, 4 - Bank branch, 5 - Bank account, 6 - Salary, 7 - Start date, 8 - Driver licence, 9 - Done editing]");
                        option = sc.nextLine();
                    }
                    
                }

                else if (option.equals("7")){ // 7 delete an employee
                    System.out.print("Enter the Id of the employee you wish to delete: ");
                    int idToDelete = Integer.parseInt(sc.nextLine());
                    gradingService.deleteEmployee(loginId, idToDelete);
                    System.out.println("");
                }

                else if (option.equals("8")){ // 8 login
                    System.out.print("Hello there, in order to login to the syestem please enter your Id: ");
                    loginId = Integer.parseInt(sc.nextLine());
                    System.out.println("");

                    System.out.print("Great, now enter your password: ");
                    loginPassword = sc.nextLine();
                    System.out.println("");

                    gradingService.logIn(loginId, loginPassword);
                    System.out.println("");
                }

                else if (option.equals("9")){ // 9 logout
                    gradingService.logOut(loginId);
                    System.out.println("");
                }

                
                System.out.println("");
                System.out.println("[0 - Exit system, 1 - Add employee, 2 - Print all employees, 3 - Add empty shift, 4 - Submit a shift, 5 - Add constraint for some Employee to Shift, 6 - Edit employee, 7 - Delete an employee, 8 - Login, 9 - Logout]");
                System.out.print("Please enter your request to the system according to the PDF file: ");
                option = sc.nextLine();
            }
            catch(Error e) {
                System.out.println(e.toString());
                System.out.print("Please enter AGAIN your request to the system according to the PDF file: ");
                option = sc.nextLine();
            }
        }
        
        System.out.println("");
        System.out.print("Thank you for your time. See you next time.");

        sc.close();

    }
}