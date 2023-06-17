package PresentationLayer;

import ServiceLayer.EmployeesLayer.ServiceFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

class MainV2 {

    private static ServiceFactory serviceFactory = new ServiceFactory();
    private static HRSystem hrSystem;
    private static MemberSystem memberSystem;
    private static TransportSystem transportSystem;
    private static DriverSystem driverSystem;

    public static void main(String[] args) {

        hrSystem = new HRSystem(serviceFactory);
        memberSystem = new MemberSystem(serviceFactory);
        transportSystem = new TransportSystem(serviceFactory);
        driverSystem = new DriverSystem(serviceFactory);

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

        // hrSystem.branchService.addConstraint(1, 213, 6); // 4
        // hrSystem.branchService.addConstraint(1, 201, 6); // 5
        // hrSystem.branchService.addConstraint(1, 202, 6); // 6
        // hrSystem.branchService.addConstraint(1, 243, 6); // 7
        // hrSystem.branchService.addConstraint(1, 268, 6); // 9
        // hrSystem.branchService.addConstraint(1, 269, 6); // 10
        // hrSystem.branchService.addConstraint(1, 270, 6); // 11
        // hrSystem.branchService.addConstraint(1, 218, 7); // 5
        // hrSystem.branchService.addConstraint(1, 255, 7); // 6
        // hrSystem.branchService.addConstraint(1, 244, 7); // 7
        // hrSystem.branchService.addConstraint(1, 213, 8); // 4
        // hrSystem.branchService.addConstraint(1, 201, 8); // 5
        // hrSystem.branchService.addConstraint(1, 202, 8); // 6
        // hrSystem.branchService.addConstraint(1, 226, 8); // 7
        // hrSystem.branchService.addConstraint(1, 268, 8); // 9
        // hrSystem.branchService.addConstraint(1, 269, 8); // 10
        // hrSystem.branchService.addConstraint(1, 270, 8); // 11
        // hrSystem.branchService.addConstraint(1, 238, 9); // 5
        // hrSystem.branchService.addConstraint(1, 255, 9); // 6
        // hrSystem.branchService.addConstraint(1, 244, 9); // 7
        // hrSystem.branchService.addConstraint(1, 213, 10); // 4
        // hrSystem.branchService.addConstraint(1, 201, 10); // 5
        // hrSystem.branchService.addConstraint(1, 202, 10); // 6
        // hrSystem.branchService.addConstraint(1, 225, 10); // 7
        // hrSystem.branchService.addConstraint(1, 268, 10); // 9
        // hrSystem.branchService.addConstraint(1, 269, 10); // 10
        // hrSystem.branchService.addConstraint(1, 270, 10); // 11
        // hrSystem.branchService.addConstraint(1, 238, 11); // 5
        // hrSystem.branchService.addConstraint(1, 257, 11); // 6
        // hrSystem.branchService.addConstraint(1, 227, 11); // 7
        
        // hrSystem.branchService.addConstraint(2, 214, 20); // 4
        // hrSystem.branchService.addConstraint(2, 204, 20); // 5
        // hrSystem.branchService.addConstraint(2, 205, 20); // 6
        // hrSystem.branchService.addConstraint(2, 209, 20); // 6
        // hrSystem.branchService.addConstraint(2, 206, 20); // 7
        // hrSystem.branchService.addConstraint(2, 271, 20); // 9
        // hrSystem.branchService.addConstraint(2, 207, 21); // 5
        // hrSystem.branchService.addConstraint(2, 258, 21); // 6
        // hrSystem.branchService.addConstraint(2, 228, 21); // 7
        // hrSystem.branchService.addConstraint(2, 214, 22); // 4
        // hrSystem.branchService.addConstraint(2, 207, 22); // 5
        // hrSystem.branchService.addConstraint(2, 205, 22); // 6
        // hrSystem.branchService.addConstraint(2, 209, 22); // 6
        // hrSystem.branchService.addConstraint(2, 206, 22); // 7
        // hrSystem.branchService.addConstraint(2, 271, 22); // 9
        // hrSystem.branchService.addConstraint(2, 204, 23); // 5
        // hrSystem.branchService.addConstraint(2, 258, 23); // 6
        // hrSystem.branchService.addConstraint(2, 228, 23); // 7
        // hrSystem.branchService.addConstraint(2, 214, 24); // 4
        // hrSystem.branchService.addConstraint(2, 207, 24); // 5
        // hrSystem.branchService.addConstraint(2, 209, 24); // 6
        // hrSystem.branchService.addConstraint(2, 205, 24); // 6
        // hrSystem.branchService.addConstraint(2, 206, 24); // 7
        // hrSystem.branchService.addConstraint(2, 271, 24); // 9
        // hrSystem.branchService.addConstraint(2, 204, 25); // 5
        // hrSystem.branchService.addConstraint(2, 258, 25); // 6
        // hrSystem.branchService.addConstraint(2, 228, 25); // 7

        // hrSystem.branchService.addConstraint(3, 215, 34); // 4
        // hrSystem.branchService.addConstraint(3, 219, 34); // 5
        // hrSystem.branchService.addConstraint(3, 210, 34); // 6
        // hrSystem.branchService.addConstraint(3, 246, 34); // 7
        // hrSystem.branchService.addConstraint(3, 247, 34); // 7
        // hrSystem.branchService.addConstraint(3, 272, 34); // 10
        // hrSystem.branchService.addConstraint(3, 220, 35); // 5
        // hrSystem.branchService.addConstraint(3, 211, 35); // 6
        // hrSystem.branchService.addConstraint(3, 229, 35); // 7
        // hrSystem.branchService.addConstraint(3, 230, 35); // 7
        // hrSystem.branchService.addConstraint(3, 215, 36); // 4
        // hrSystem.branchService.addConstraint(3, 219, 36); // 5
        // hrSystem.branchService.addConstraint(3, 260, 36); // 6
        // hrSystem.branchService.addConstraint(3, 246, 36); // 7
        // hrSystem.branchService.addConstraint(3, 247, 36); // 7
        // hrSystem.branchService.addConstraint(3, 272, 36); // 10
        // hrSystem.branchService.addConstraint(3, 220, 37); // 5
        // hrSystem.branchService.addConstraint(3, 261, 37); // 6
        // hrSystem.branchService.addConstraint(3, 229, 37); // 7
        // hrSystem.branchService.addConstraint(3, 230, 37); // 7
        // hrSystem.branchService.addConstraint(3, 215, 38); // 4
        // hrSystem.branchService.addConstraint(3, 239, 38); // 5
        // hrSystem.branchService.addConstraint(3, 210, 38); // 6
        // hrSystem.branchService.addConstraint(3, 246, 38); // 7
        // hrSystem.branchService.addConstraint(3, 247, 38); // 7
        // hrSystem.branchService.addConstraint(3, 272, 38); // 10
        // hrSystem.branchService.addConstraint(3, 240, 39); // 5
        // hrSystem.branchService.addConstraint(3, 260, 39); // 6
        // hrSystem.branchService.addConstraint(3, 229, 39); // 7
        // hrSystem.branchService.addConstraint(3, 230, 39); // 7
        
        // hrSystem.branchService.addConstraint(4, 216, 48); // 4
        // hrSystem.branchService.addConstraint(4, 222, 48); // 5
        // hrSystem.branchService.addConstraint(4, 263, 48); // 6
        // hrSystem.branchService.addConstraint(4, 250, 48); // 7
        // hrSystem.branchService.addConstraint(4, 241, 49); // 5
        // hrSystem.branchService.addConstraint(4, 262, 49); // 6
        // hrSystem.branchService.addConstraint(4, 251, 49); // 7
        // hrSystem.branchService.addConstraint(4, 216, 50); // 4
        // hrSystem.branchService.addConstraint(4, 222, 50); // 5
        // hrSystem.branchService.addConstraint(4, 263, 50); // 6
        // hrSystem.branchService.addConstraint(4, 233, 50); // 7
        // hrSystem.branchService.addConstraint(4, 241, 51); // 5
        // hrSystem.branchService.addConstraint(4, 262, 51); // 6
        // hrSystem.branchService.addConstraint(4, 234, 51); // 7
        // hrSystem.branchService.addConstraint(4, 216, 52); // 4
        // hrSystem.branchService.addConstraint(4, 222, 52); // 5
        // hrSystem.branchService.addConstraint(4, 263, 52); // 6
        // hrSystem.branchService.addConstraint(4, 250, 52); // 7
        // hrSystem.branchService.addConstraint(4, 241, 53); // 5
        // hrSystem.branchService.addConstraint(4, 262, 53); // 6
        // hrSystem.branchService.addConstraint(4, 251, 53); // 7
        
        // hrSystem.branchService.addConstraint(5, 217, 62); // 4
        // hrSystem.branchService.addConstraint(5, 223, 62); // 5
        // hrSystem.branchService.addConstraint(5, 265, 62); // 6
        // hrSystem.branchService.addConstraint(5, 252, 62); // 7
        // hrSystem.branchService.addConstraint(5, 224, 63); // 5
        // hrSystem.branchService.addConstraint(5, 266, 63); // 6
        // hrSystem.branchService.addConstraint(5, 253, 63); // 7
        // hrSystem.branchService.addConstraint(5, 217, 64); // 4
        // hrSystem.branchService.addConstraint(5, 223, 64); // 5
        // hrSystem.branchService.addConstraint(5, 265, 64); // 6
        // hrSystem.branchService.addConstraint(5, 252, 64); // 7
        // hrSystem.branchService.addConstraint(5, 224, 65); // 5
        // hrSystem.branchService.addConstraint(5, 266, 65); // 6
        // hrSystem.branchService.addConstraint(5, 253, 65); // 7
        // hrSystem.branchService.addConstraint(5, 217, 66); // 4
        // hrSystem.branchService.addConstraint(5, 223, 66); // 5
        // hrSystem.branchService.addConstraint(5, 265, 66); // 6
        // hrSystem.branchService.addConstraint(5, 254, 66); // 7
        // hrSystem.branchService.addConstraint(5, 224, 67); // 5
        // hrSystem.branchService.addConstraint(5, 266, 67); // 6
        // hrSystem.branchService.addConstraint(5,  253, 67); // 7
        
        hrSystem.employeeService.logIn(123456789, "HRmanager");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate1 = LocalDate.parse("04-06-2024", formatter);

        HashMap<Integer, Integer> hrmap = new HashMap<>();
        hrmap.put(213, 4);
        hrmap.put(201, 5);
        hrmap.put(202, 6);
        hrmap.put(243, 7);
        hrmap.put(268, 9);
        hrmap.put(269, 10);
        hrmap.put(270, 11);
        hrSystem.branchService.approveFinalShift(123456789, 6, 1, hrmap);
        hrmap.clear();
        hrmap.put(218, 5);
        hrmap.put(255, 6);
        hrmap.put(244, 7);
        hrSystem.branchService.approveFinalShift(123456789, 7, 1, hrmap);
        hrmap.clear();
        hrmap.put(213, 4);
        hrmap.put(201, 5);
        hrmap.put(202, 6);
        hrmap.put(226, 7);
        hrmap.put(268, 9);
        hrmap.put(269, 10);
        hrmap.put(270, 11);
        hrSystem.branchService.approveFinalShift(123456789, 8, 1, hrmap);
        hrmap.clear();
        hrmap.put(238, 5);
        hrmap.put(255, 6);
        hrmap.put(244, 7);
        hrSystem.branchService.approveFinalShift(123456789, 9, 1, hrmap);
        hrmap.clear();
        hrmap.put(214, 4);
        hrmap.put(204, 5);
        hrmap.put(205, 6);
        hrmap.put(209, 6);
        hrmap.put(206, 7);
        hrmap.put(271, 9);
        hrSystem.branchService.approveFinalShift(123456789, 20, 2, hrmap);
        hrmap.clear();
        hrmap.put(207, 5);
        hrmap.put(258, 6);
        hrmap.put(228, 7);
        hrSystem.branchService.approveFinalShift(123456789, 21, 2, hrmap);
        hrmap.clear();
        hrmap.put(214, 4);
        hrmap.put(207, 5);
        hrmap.put(205, 6);
        hrmap.put(209, 6);
        hrmap.put(206, 7);
        hrmap.put(271, 9);
        hrSystem.branchService.approveFinalShift(123456789, 22, 2, hrmap);
        hrmap.clear();
        hrmap.put(204, 5);
        hrmap.put(258, 6);
        hrmap.put(228, 7);
        hrSystem.branchService.approveFinalShift(123456789, 23, 2, hrmap);
        hrmap.clear();
        hrmap.put(215, 4);
        hrmap.put(219, 5);
        hrmap.put(210, 6);
        hrmap.put(246, 7);
        hrmap.put(247, 7);
        hrmap.put(272, 10);
        hrSystem.branchService.approveFinalShift(123456789, 34, 3, hrmap);
        hrmap.clear();
        hrmap.put(220, 5);
        hrmap.put(211, 6);
        hrmap.put(229, 7);
        hrmap.put(230, 7);
        hrSystem.branchService.approveFinalShift(123456789, 35, 3, hrmap);
        hrmap.clear();
        hrmap.put(215, 4);
        hrmap.put(219, 5);
        hrmap.put(260, 6);
        hrmap.put(246, 7);
        hrmap.put(247, 7);
        hrmap.put(272, 10);
        hrSystem.branchService.approveFinalShift(123456789, 36, 3, hrmap);
        hrmap.clear();
        hrmap.put(220, 5);
        hrmap.put(261, 6);
        hrmap.put(229, 7);
        hrmap.put(230, 7);
        hrSystem.branchService.approveFinalShift(123456789, 37, 3, hrmap);
        hrmap.clear();
        hrmap.put(216, 4);
        hrmap.put(222, 5);
        hrmap.put(263, 6);
        hrmap.put(250, 7);
        hrSystem.branchService.approveFinalShift(123456789, 48, 4, hrmap);
        hrmap.clear();
        hrmap.put(241, 5);
        hrmap.put(262, 6);
        hrmap.put(251, 7);
        hrSystem.branchService.approveFinalShift(123456789, 49, 4, hrmap);
        hrmap.clear();
        hrmap.put(216, 4);
        hrmap.put(222, 5);
        hrmap.put(263, 6);
        hrmap.put(233, 7);
        hrSystem.branchService.approveFinalShift(123456789, 50, 4, hrmap);
        hrmap.clear();
        hrmap.put(241, 5);
        hrmap.put(262, 6);
        hrmap.put(234, 7);
        hrSystem.branchService.approveFinalShift(123456789, 51, 4, hrmap);
        hrmap.clear();
        hrmap.put(217, 4);
        hrmap.put(223, 5);
        hrmap.put(265, 6);
        hrmap.put(252, 7);
        hrSystem.branchService.approveFinalShift(123456789, 62, 5, hrmap);
        hrmap.clear();
        hrmap.put(224, 5);
        hrmap.put(266, 6);
        hrmap.put(253, 7);
        hrSystem.branchService.approveFinalShift(123456789, 63, 5, hrmap);
        hrmap.clear();
        hrmap.put(217, 4);
        hrmap.put(223, 5);
        hrmap.put(265, 6);
        hrmap.put(252, 7);
        hrSystem.branchService.approveFinalShift(123456789, 64, 5, hrmap);
        hrmap.clear();
        hrmap.put(224, 5);
        hrmap.put(266, 6);
        hrmap.put(253, 7);
        hrSystem.branchService.approveFinalShift(123456789, 65, 5, hrmap);
        hrmap.clear();









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
                        System.out.println("");
                        break;
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