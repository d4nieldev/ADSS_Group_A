package PresentationLayer.CLI;

import ServiceLayer.EmployeesLayer.ServiceFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

class MainV2 {

    private ServiceFactory serviceFactory;
    private static HRSystem hrSystem;
    private static MemberSystem memberSystem;
    private static TransportSystem transportSystem;
    private static DriverSystem driverSystem;
    private static StoreManagerSystem storeManagerSystem;

    public MainV2(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
    public void run(String role) {

        hrSystem = new HRSystem(serviceFactory);
        memberSystem = new MemberSystem(serviceFactory);
        transportSystem = new TransportSystem(serviceFactory);
        driverSystem = new DriverSystem(serviceFactory);
        storeManagerSystem = new StoreManagerSystem(hrSystem, memberSystem, transportSystem, driverSystem);

        Scanner sc = new Scanner(System.in);
        while (true) {

            // System.out.println("1 - Exit the system\n2 - Login to the system\n");
            System.out.println(getMenu() + "\n");
            int option = Integer.parseInt(sc.nextLine());
            if (option == 1) {
                System.out.print("Hello there, in order to login to the system please enter your Id: ");
                int loginId = Integer.parseInt(sc.nextLine());
                System.out.println("");

                System.out.print("Great, now enter your password: ");
                String loginPassword = sc.nextLine();
                System.out.println("");

                while (true) {
                    try {
                        hrSystem.employeeService.logIn(loginId, loginPassword);
                        String managerRole = hrSystem.employeeService.getManagerType(loginId);
                        if (managerRole.equals(role) || role.equals("MEMBER")) {
                            break;
                        }
                        else {
                            System.out.println("You are not authorized to login to this system, please try again.\n");
                            System.out.println("");
                            hrSystem.employeeService.logOut(loginId);
                            throw new Error("You are not authorized to login to this system, please try again.\n");
                        }
                    } catch (Error e) {
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

                String managerRole = hrSystem.employeeService.getManagerType(loginId);

                switch (managerRole) {
                    case ("HRMANAGER"): {
                        hrSystem.run(loginId);
                        break;
                    }

                    case ("TRANSPORTMANAGER"): {
                        transportSystem.run(loginId);
                        break;
                    }

                    case ("DRIVER"): {
                        driverSystem.run(loginId);
                        break;
                    }

                    case ("BRANCHMANAGER"): {
                        System.out.println("What menu would you like to enter?\n");
                        System.out.println("1 - HR\n2 - Member\n3 - Transport\n4 - Driver\n");
                    
                        int menu = Integer.parseInt(sc.nextLine());
                        System.out.println("");
                        storeManagerSystem.run(loginId, menu);
                        break;
                    }

                    default: {
                        memberSystem.run(loginId);
                        break;
                    }
                }
            }

            else {
                System.out.println("Goodbye, see you next time!");
                break;
            }
        }

        sc.close();
    }

    public static String getMenu() {
        String horizontalLine = "+---------------------------------------+";
        String option1 = "| 0 - Exit the system.                  |";
        String option2 = "| 1 - Login to the system.              |";
        String bottomLine = "+---------------------------------------+";

        StringBuilder sb = new StringBuilder();
        sb.append(horizontalLine).append("\n")
                .append(option1).append("\n")
                .append(option2).append("\n")
                .append(bottomLine);
        return sb.toString();
    }
}





        //////////////// My Beautifual Test Area/////////////////////
        // hrSystem.employeeService.logIn(123456789, "HRmanager");
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("05-06-2003", formatter);
        // hrSystem.branchService.addNewEmployee(123456789,
        //////////////// "printAvailableShiftForEmployeeCheck", "Hatuli", 999, "123", 0,
        //////////////// 0, 0, 0, 0, localDate, "dsf", "cashier", 1);
        // System.out.println(hrSystem.employeeService.printAllEmployees(123456789));
        // System.out.println(hrSystem.branchService.printAllBranches(123456789));
        // System.out.println(hrSystem.employeeService.printAllDrivers(123456789));
        // System.out.println("check");
        // -----------------add new shift-----------------
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("31-05-2023", formatter);
        // HashMap<Integer, Integer> numEmployeesForRole = new HashMap<>();
        // numEmployeesForRole.put(5, 1);
        // numEmployeesForRole.put(6, 1);
        // hrSystem.branchService.addShift(123456789, 1, localDate, 9, 17,
        //////////////// ShiftTime.MORNING, numEmployeesForRole);
        // -----------------submit final shift-----------------
        // HashMap<Integer, Integer> shiftAssign = new HashMap<>();
        // shiftAssign.put(999, 5);
        // hrSystem.branchService.approveFinalShift(123456789, 5, 1, shiftAssign);
        // -----------------add constraint-----------------
        // hrSystem.employeeService.logIn(207, "emp7");
        // memberSystem.branchService.addConstraint(2, 207, 3);
        // -----------------show all available shift for employee-----------------
        // hrSystem.employeeService.logIn(207, "emp7");
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("30-05-2023", formatter);
        // System.out.println(memberSystem.branchService.printAvailableShiftForEmployee(207,
        //////////////// localDate));
        // -----------------delete an employee-----------------
        // hrSystem.employeeService.logIn(123456789, "abc");
        // hrSystem.branchService.deleteEmployee(123456789, 999);
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("30-05-2023", formatter);
        // hrSystem.employeeService.logIn(345, "345");
        // hrSystem.employeeService.AddConstraintDriver(345, localDate);
        //////////////// My Beautifual Test Area/////////////////////

