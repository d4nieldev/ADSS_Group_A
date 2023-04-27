package PresentationLayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import BussinessLayer.TransPortLayer.Delivery;
import BussinessLayer.TransPortLayer.Destination;
import BussinessLayer.TransPortLayer.DestinationType;
import BussinessLayer.TransPortLayer.Location;
import ServiceLayer.TransportLayer.DriverService;
import ServiceLayer.TransportLayer.TransportService;
import ServiceLayer.TransportLayer.TruckService;

import Misc.*;
import ServiceLayer.EmployeesLayer.*;

class MainV2 {

    private static TransportService transportServices = new TransportService();
    private static DriverService driverService = new DriverService();
    private static TruckService truckService = new TruckService();

    public static void main(String[] args) {

        ServiceLayer.EmployeesLayer.GradingService gradingService = new GradingService();

        Scanner sc = new Scanner(System.in);

        System.out.print("Hello there, in order to login to the system please enter your Id: ");
        int loginId = Integer.parseInt(sc.nextLine());
        System.out.println("");

        System.out.print("Great, now enter your password: ");
        String loginPassword = sc.nextLine();
        System.out.println("");

        while (true){
            try {
                gradingService.logIn(loginId, loginPassword);
                System.out.println("");
                break;
            }
            catch (Error e) {
                System.out.println(e.toString());
                System.out.println();
                System.out.print("Enter your Id again: ");
                loginId = Integer.parseInt(sc.nextLine());
                System.out.println("");

                System.out.print("Enter your password again: ");
                loginPassword = sc.nextLine();
                System.out.println("");
            }
        }

        System.out.println("Wellcome to the system\n");
        
        String managerRole = gradingService.getManagerType(loginId);

        switch (managerRole){
            case("HRMANAGER") : {

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

                            System.out.print("Initialize Bouns: ");
                            int InitializeBonus = Integer.parseInt(sc.nextLine());
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
                                    bankBranch, bankAccount, salary, InitializeBonus, localDate, driverLicense, role, superBranch, status);

                        }

                        else if (option.equals("2")){ // 2 print all employees
                            System.out.println(gradingService.printAllEmployees(loginId));
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
            }
                

            case("TRANSPORTMANAGER") : {

                makeSomeDrivers();
                makeSomeTrucks();
                List<Destination> dests = makeSomeDestinations();
                List<Destination> sources = makeSomeSources();
                List<Delivery> deliveries = transportServices.createDeliveries(sources, dests);

                transportServices.letTheUserMatch(deliveries);
                transportServices.runTheTransports();

                Scanner scanner = new Scanner(System.in);
                boolean continueChoosing = true;
                while (continueChoosing) {
                    System.out.println("\nWhat would you like to change?");
                    System.out.println("1. Drivers");
                    System.out.println("2. Trucks");
                    System.out.println("3. Transports");
                    System.out.println("4. Exit");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume the newline character

                    switch (choice) {
                        case 1:
                            changeDriverService();
                            break;
                        case 2:
                            changeTruckService();
                            break;
                        case 3:
                            changeTransportService();
                            break;
                        case 4:
                            continueChoosing = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }

                System.out.println("Thank you for using the Transport System!");
            }
        }

        System.out.println("");
        System.out.print("Thank you for your time. See you next time.");

        sc.close();

    }

    private static void changeDriverService() {
        Scanner scanner = new Scanner(System.in);
        boolean isDone = false;

        while (!isDone) {
            System.out.println("What would you like to do with the driver service?");
            System.out.println("1. Add new driver");
            System.out.println("2. Remove a driver");
            System.out.println("3. Change driver's name");
            System.out.println("4. Change driver's license");
            System.out.println("5. Change driver's availability");
            System.out.println("6. Print driver's license type");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter driver's ID:");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter driver's name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter driver's license type:");
                    String license = scanner.nextLine();
                    System.out.println(driverService.addDriver(id, name, license));
                    break;
                case 2:
                    System.out.println("Enter driver's ID:");
                    int idToRemove = scanner.nextInt();
                    System.out.println(driverService.removeDriver(idToRemove));
                    break;
                case 3:
                    System.out.println("Enter driver's ID:");
                    int idToChangeName = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter driver's new name:");
                    String newName = scanner.nextLine();
                    System.out.println(driverService.changeName(idToChangeName, newName));
                    break;
                case 4:
                    System.out.println("Enter driver's ID:");
                    int idToChangeLicense = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter driver's new license type:");
                    String newLicense = scanner.nextLine();
                    System.out.println(driverService.changeLicence(idToChangeLicense, newLicense));
                    break;
                case 5:
                    System.out.println("Enter driver's ID:");
                    int idToChangeAvailability = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter driver's new availability (true/false):");
                    boolean newAvailability = scanner.nextBoolean();
                    System.out.println(driverService.setAvailable(idToChangeAvailability, newAvailability));
                    break;
                case 6:
                    System.out.println("Enter driver's ID:");
                    int idToPrintLicense = scanner.nextInt();
                    System.out.println(driverService.printDriverLicense(idToPrintLicense));
                    break;
                case 7:
                    isDone = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        }
    }


    private static void changeTruckService() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What action would you like to perform?");
        System.out.println("1. Add a new truck");
        System.out.println("2. Remove a truck");
        System.out.println("3. Update a truck's details");
        System.out.println("4. Check if a truck is available");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Enter the truck's plate number:");
                String plateNumber = scanner.next();
                System.out.println("Enter the truck's model:");
                String model = scanner.next();
                System.out.println("Enter the truck's net weight:");
                int weightNeto = scanner.nextInt();
                System.out.println("Enter the truck's maximum weight:");
                int weightMax = scanner.nextInt();
                String message = truckService.addTruck(plateNumber, model, weightNeto, weightMax);
                System.out.println(message);
                break;
            case 2:
                System.out.println("Enter the plate number of the truck you want to remove:");
                plateNumber = scanner.next();
                message = truckService.removeTruck(plateNumber);
                System.out.println(message);
                break;
            case 3:
                System.out.println("Enter the plate number of the truck you want to update:");
                plateNumber = scanner.next();
                System.out.println("What would you like to update?");
                System.out.println("1. Plate number");
                System.out.println("2. Model");
                System.out.println("3. Net weight");
                System.out.println("4. Maximum weight");
                System.out.println("5. Availability");
                int updateChoice = scanner.nextInt();
                switch (updateChoice) {
                    case 1:
                        System.out.println("Enter the new plate number:");
                        String newPlateNumber = scanner.next();
                        message = truckService.setPlateNumber(plateNumber, newPlateNumber);
                        System.out.println(message);
                        break;
                    case 2:
                        System.out.println("Enter the new model:");
                        String newModel = scanner.next();
                        message = truckService.setModel(plateNumber, newModel);
                        System.out.println(message);
                        break;
                    case 3:
                        System.out.println("Enter the new net weight:");
                        int newWeightNeto = scanner.nextInt();
                        message = truckService.setWeightNeto(plateNumber, newWeightNeto);
                        System.out.println(message);
                        break;
                    case 4:
                        System.out.println("Enter the new maximum weight:");
                        int newWeightMax = scanner.nextInt();
                        message = truckService.setWeightMax(plateNumber, newWeightMax);
                        System.out.println(message);
                        break;
                    case 5:
                        System.out.println("Is the truck now available? (true/false)");
                        boolean isAvailable = scanner.nextBoolean();
                        message = truckService.setAvailable(plateNumber, isAvailable);
                        System.out.println(message);
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
                break;
            case 4:
                System.out.println("Enter the plate number of the truck you want to check:");
                plateNumber = scanner.next();
                message = truckService.isAvailable(plateNumber);
                System.out.println(message);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private static void changeTransportService() {
        Scanner scanner = new Scanner(System.in);
        TransportService transportService = new TransportService();

        System.out.println("Enter transport ID:");
        int id = scanner.nextInt();

        System.out.println("What do you want to change?");
        System.out.println("1. Date");
        System.out.println("2. Leaving time");
        System.out.println("3. Truck number");
        System.out.println("4. Driver name");
        System.out.println("5. Driver ID");
        System.out.println("6. Source");
        System.out.println("7. Destination list");
        System.out.println("8. Delivery list");
        System.out.println("9. Truck weight neto");
        System.out.println("10. Truck weight max");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Enter new date:");
                String date = scanner.next();
                System.out.println(transportService.changeDate(id, date));
                break;
            case 2:
                System.out.println("Enter new leaving time:");
                String leavingTime = scanner.next();
                System.out.println(transportService.changeLeavingTime(id, leavingTime));
                break;
            case 3:
                System.out.println("Enter new truck number:");
                String truckNumber = scanner.next();
                System.out.println(transportService.changeTruckNumber(id, truckNumber));
                break;
            case 4:
                System.out.println("Enter new driver name:");
                String driverName = scanner.next();
                System.out.println(transportService.changeDriverName(id, driverName));
                break;
            case 5:
                System.out.println("Enter new driver ID:");
                int driverId = scanner.nextInt();
                System.out.println(transportService.changeDriverId(id, driverId));
                break;
            case 6:
                System.out.println("Enter new source:");
                String source = scanner.next();
                System.out.println(transportService.changeSource(id, source));
                break;
            case 7:
            case 8:
                System.out.println("you should open the program from the beginning");
                break;
            case 9:
                System.out.println("Enter new truck weight neto:");
                int truckWeightNeto = scanner.nextInt();
                System.out.println(transportService.changeTruckWeightNeto(id, truckWeightNeto));
                break;
            case 10:
                System.out.println("Enter new truck weight max:");
                int truckWeightMax = scanner.nextInt();
                System.out.println(transportService.changeTruckWeightMax(id, truckWeightMax));
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }

        scanner.close();
    }
}