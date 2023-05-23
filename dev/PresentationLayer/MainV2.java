package PresentationLayer;

import ServiceLayer.EmployeesLayer.serviceFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Misc.License;
import Misc.Role;
import Misc.ShiftTime;

class MainV2 {

    private static serviceFactory serviceFactory = new serviceFactory();
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
        hrSystem.employeeService.logIn(123456789, "HRmanager");
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("10-05-2023", formatter);
         hrSystem.branchService.addNewDriver(123456789, "driver", "twelve", 112, "driver12",
          12, 12, 121212, 35, 1000, localDate, "driver temps",
           License.B);
         hrSystem.branchService.addNewDriver(123456789, "driver", "seven", 107, "driver7",
          7, 7, 7777, 35, 1000, localDate, "driver temps",
           License.C);
         hrSystem.branchService.addNewDriver(123456789, "driver", "eigth", 108, "driver8",
          8, 8, 8888, 35, 1000, localDate, "driver temps",
           License.C);
         hrSystem.branchService.addNewDriver(123456789, "driver", "nine", 109, "driver9",
          9, 9, 9999, 35, 1000, localDate, "driver temps",
           License.B);



           hrSystem.branchService.addNewEmployee(123456789, "emp13", "name13", 213, "emp13",
            13, 13, 131313, 35, 1000, localDate, "employee temps",
             "BRANCHMANAGER", 1);
             hrSystem.branchService.addNewEmployee(123456789, "emp14", "name14", 214, "emp14",
              14, 14, 14141414, 35, 1000, localDate, "employee temps",
               "BRANCHMANAGER", 2);
             hrSystem.branchService.addNewEmployee(123456789, "emp15", "name15", 215, "emp15",
              15, 15, 15151515, 35, 1000, localDate, "employee temps",
               "BRANCHMANAGER", 3);
             hrSystem.branchService.addNewEmployee(123456789, "emp16", "name16", 216, "emp16",
              16, 16, 16161616, 35, 1000, localDate, "employee temps",
               "BRANCHMANAGER", 4);
             hrSystem.branchService.addNewEmployee(123456789, "emp17", "name17", 217, "emp17",
              17, 17, 17171717, 35, 1000, localDate, "employee temps",
               "BRANCHMANAGER", 5);
             hrSystem.branchService.addNewEmployee(123456789, "emp18", "name18", 218, "emp18",
              18, 18, 18181818, 35, 1000, localDate, "employee temps",
               "SHIFTMANAGER", 1);
             hrSystem.branchService.addNewEmployee(123456789, "emp19", "name19", 219, "emp19",
              19, 19, 19191919, 35, 1000, localDate, "employee temps",
               "SHIFTMANAGER", 3); 
         hrSystem.branchService.addNewEmployee(123456789, "emp20", "name20", 220, "emp20",
         20, 20, 202020, 35, 1000, localDate, "employee temps",
          "SHIFTMANAGER", 3);
        hrSystem.branchService.addNewEmployee(123456789, "emp22", "name22", 222, "emp22",
         22, 22, 2222222, 35, 1000, localDate, "employee temps",
          "SHIFTMANAGER", 4);
        hrSystem.branchService.addNewEmployee(123456789, "emp23", "name23", 223, "emp23",
         23, 23, 232323, 35, 1000, localDate, "employee temps",
          "SHIFTMANAGER", 5);
        hrSystem.branchService.addNewEmployee(123456789, "emp24", "name24", 224, "emp24",
         24, 24, 242424, 35, 1000, localDate, "employee temps",
          "SHIFTMANAGER", 5);

        hrSystem.branchService.addNewEmployee(123456789, "emp25", "name25", 225, "emp25",
         25, 25, 252525, 35, 1000, localDate, "employee temps",
          "STOREKEEPER", 1);
         hrSystem.branchService.addNewEmployee(123456789, "emp26", "name26", 226, "emp26",
         26, 26, 262626, 35, 1000, localDate, "employee temps",
          "STOREKEEPER", 1);
        hrSystem.branchService.addNewEmployee(123456789, "emp27", "name27", 227, "emp27",
         27, 27, 272727, 35, 1000, localDate, "employee temps",
          "STOREKEEPER", 1);
        hrSystem.branchService.addNewEmployee(123456789, "emp28", "name28", 228, "emp28",
         28, 28, 282828, 35, 1000, localDate, "employee temps",
          "STOREKEEPER", 2);
          hrSystem.branchService.addNewEmployee(123456789, "emp29", "name29", 229, "emp29",
           29, 29, 292929, 35, 1000, localDate, "employee temps",
            "STOREKEEPER", 3);
          hrSystem.branchService.addNewEmployee(123456789, "emp30", "name30", 230, "emp30",
           30, 30, 303030, 35, 1000, localDate, "employee temps",
            "STOREKEEPER", 3);
          hrSystem.branchService.addNewEmployee(123456789, "emp31", "name31", 231, "emp31",
           31, 31, 313131, 35, 1000, localDate, "employee temps",
            "STOREKEEPER", 3);
        hrSystem.branchService.addNewEmployee(123456789, "emp32", "name32", 232, "emp32",
         32, 32, 323232, 35, 1000, localDate, "employee temps",
          "STOREKEEPER", 4);
        hrSystem.branchService.addNewEmployee(123456789, "emp33", "name33", 233, "emp33",
         33, 33, 333333, 35, 1000, localDate, "employee temps",
          "STOREKEEPER", 4);
        hrSystem.branchService.addNewEmployee(123456789, "emp34", "name34", 234, "emp34",
         34, 34, 343434, 35, 1000, localDate, "employee temps",
          "STOREKEEPER", 4);
        hrSystem.branchService.addNewEmployee(123456789, "emp35", "name35", 235, "emp35",
         35, 35, 353535, 35, 1000, localDate, "employee temps",
          "STOREKEEPER", 5);
        hrSystem.branchService.addNewEmployee(123456789, "emp36", "name36", 236, "emp36",
         36, 36, 363636, 35, 1000, localDate, "employee temps",
          "STOREKEEPER", 5);
        hrSystem.branchService.addNewEmployee(123456789, "emp37", "name37", 237, "emp37",
         37, 37, 373737, 35, 1000, localDate, "employee temps",
          "STOREKEEPER", 5);

           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("10-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("11-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("12-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("13-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("14-06-2023", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("14-06-2023", formatter));
        // hrSystem.branchService.addNewEmployee(123456789,
        //////////////// "printAvailableShiftForEmployeeCheck", "Hatuli", 999, "123", 0,
        //////////////// 0, 0, 0, 0, localDate, "dsf", "cashier", 1);
        // System.out.println(hrSystem.employeeService.printAllEmployees(123456789));
        // System.out.println(hrSystem.branchService.printAllBranches(123456789));
        //System.out.println(hrSystem.employeeService.printAllDrivers(123456789));
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
        //  hrSystem.employeeService.logIn(207, "emp7");
        //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //  LocalDate localDate = LocalDate.parse("30-05-2023", formatter);
        //  System.out.println(memberSystem.branchService.printAvailableShiftForEmployee(207, localDate));
        // -----------------delete an employee-----------------
        // hrSystem.employeeService.logIn(123456789, "abc");
        // hrSystem.branchService.deleteEmployee(123456789, 999);
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("30-05-2023", formatter);
        // hrSystem.employeeService.logIn(345, "345");
        // hrSystem.employeeService.AddConstraintDriver(345, localDate);
        //////////////// My Beautifual Test Area/////////////////////

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