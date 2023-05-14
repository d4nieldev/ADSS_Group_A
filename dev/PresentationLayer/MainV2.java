package PresentationLayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import BussinessLayer.TransPortLayer.Delivery;
import BussinessLayer.TransPortLayer.Destination;
import BussinessLayer.TransPortLayer.DestinationType;
import BussinessLayer.TransPortLayer.Location;
import Misc.ShiftTime;
import ServiceLayer.TransportLayer.TransportService;
import ServiceLayer.TransportLayer.TruckService;

import ServiceLayer.EmployeesLayer.*;

class MainV2 {

    private static serviceFactory serviceFactory = new serviceFactory();
    private static HRSystem hrSystem;
    private static MemberSystem memberSystem;
    private static TransportSystem transportSystem;

    public static void main(String[] args) {

        hrSystem = new HRSystem(serviceFactory);
        memberSystem = new MemberSystem(serviceFactory);
        transportSystem = new TransportSystem(serviceFactory);

        ////////////////My Beautifual Test Area/////////////////////
        // hrSystem.employeeService.logIn(123456789, "abc");
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("05-06-2003", formatter);
        // hrSystem.branchService.addNewEmployee(123456789, "printAvailableShiftForEmployeeCheck", "Hatuli", 999, "123", 0, 0, 0, 0, 0, localDate, "dsf", "cashier", 1);
        // System.out.println(hrSystem.employeeService.printAllEmployees(123456789));
        // System.out.println(hrSystem.branchService.printAllBranches(123456789));
        // System.out.println(hrSystem.employeeService.printAllDrivers(123456789));       
        // System.out.println("check");
        //-----------------add new shift-----------------
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("31-05-2023", formatter);
        // HashMap<Integer, Integer> numEmployeesForRole = new HashMap<>();
        // numEmployeesForRole.put(5, 1);
        // numEmployeesForRole.put(6, 1);
        // hrSystem.branchService.addShift(123456789, 1, localDate, 9, 17, ShiftTime.MORNING, numEmployeesForRole);
        //-----------------submit final shift-----------------
        // HashMap<Integer, Integer> shiftAssign = new HashMap<>();
        // shiftAssign.put(999, 5);
        // hrSystem.branchService.approveFinalShift(123456789, 5, 1, shiftAssign);
        //-----------------add constraint-----------------      
        // memberSystem.branchService.addConstraint(1, 999, 4);
        //-----------------show all available shift for employee-----------------
        // hrSystem.employeeService.logIn(44, "4");
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("30-05-2023", formatter);
        // System.out.println(memberSystem.branchService.printAvailableShiftForEmployee(44, localDate));
        //-----------------delete an employee-----------------
        // hrSystem.employeeService.logIn(123456789, "abc");
        // hrSystem.branchService.deleteEmployee(123456789, 999);
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("30-05-2023", formatter);
        // hrSystem.employeeService.logIn(345, "345");
        // hrSystem.employeeService.AddConstraintDriver(345, localDate);
        ////////////////My Beautifual Test Area/////////////////////

        Scanner sc = new Scanner(System.in);

        while (true) {

            //System.out.println("1 - Exit the system\n2 - Login to the system\n");
            System.out.println(getMenu() + "\n");
            int option = Integer.parseInt(sc.nextLine());
            if (option == 2) {
                System.out.print("Hello there, in order to login to the system please enter your Id: ");
                int loginId = Integer.parseInt(sc.nextLine());
                System.out.println("");

                System.out.print("Great, now enter your password: ");
                String loginPassword = sc.nextLine();
                System.out.println("");

                while (true){
                    try {
                        hrSystem.employeeService.logIn(loginId, loginPassword);
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
                
                String managerRole = hrSystem.employeeService.getManagerType(loginId);

                switch (managerRole){
                    case("HRMANAGER") : {
                        hrSystem.run(loginId);
                        break;
                    }                
        
                    case("TRANSPORTMANAGER") : {
                        transportSystem.run(loginId);
                        break;
                    }
                    
                    default : {
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
        String option1 = "| 1 - Exit the system                  |";
        String option2 = "| 2 - Login to the system              |";
        String bottomLine = "+---------------------------------------+";
    
        StringBuilder sb = new StringBuilder();
        sb.append(horizontalLine).append("\n")
          .append(option1).append("\n")
          .append(option2).append("\n")
          .append(bottomLine);
        return sb.toString();
    }
}