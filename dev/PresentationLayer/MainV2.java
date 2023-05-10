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
        hrSystem.employeeService.logIn(123456789, "abc");
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("05-06-2003", formatter);
        // hrSystem.branchService.addNewEmployee(123456789, "Tuli", "Hatuli", 1111, "123", 0, 0, 0, 0, 0, localDate, "dsf", "cashier", 0);
        // System.out.println(hrSystem.employeeService.printAllEmployees(123456789));
        // System.out.println(hrSystem.branchService.printAllBranches(123456789));
        // System.out.println(hrSystem.employeeService.printAllDrivers(123456789));       
        // System.out.println("check");
        //-----------------add new branch-----------------
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("30-05-2023", formatter);
        // HashMap<Integer, Integer> numEmployeesForRole = new HashMap<>();
        // numEmployeesForRole.put(5, 1);
        // numEmployeesForRole.put(6, 1);
        // hrSystem.branchService.addShift(123456789, 1, localDate, 9, 17, ShiftTime.MORNING, numEmployeesForRole);
        //-----------------submit final shift-----------------
        System.out.println("You choose to submit a final shift.");
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

        hrSystem.branchService.approveFinalShift(123456789, 1, 1,shiftAssign);
        ////////////////My Beautifual Test Area/////////////////////

        // Scanner sc = new Scanner(System.in);

        // System.out.print("Hello there, in order to login to the system please enter your Id: ");
        // int loginId = Integer.parseInt(sc.nextLine());
        // System.out.println("");

        // System.out.print("Great, now enter your password: ");
        // String loginPassword = sc.nextLine();
        // System.out.println("");

        // while (true){
        //     try {
        //         hrSystem.employeeService.logIn(loginId, loginPassword);
        //         System.out.println("");
        //         break;
        //     }
        //     catch (Error e) {
        //         System.out.println(e.toString());
        //         System.out.println();
        //         System.out.print("Enter your Id again: ");
        //         loginId = Integer.parseInt(sc.nextLine());
        //         System.out.println("");

        //         System.out.print("Enter your password again: ");
        //         loginPassword = sc.nextLine();
        //         System.out.println("");
        //     }
        // }

        // System.out.println("Wellcome to the system\n");
        
        // String managerRole = hrSystem.employeeService.getManagerType(loginId);

        // switch (managerRole){
        //     case("HRMANAGER") : {

        //         hrSystem.run(loginId);
                
        //         break;
        //     }                

        //     case("TRANSPORTMANAGER") : {
        //         transportSystem.run(loginId);
        //         break;
        //     }
            
        //     default : {
        //         memberSystem.run(loginId);
        //         break;
        //     }
        // }

        // sc.close();
    }



    


    /**
     * Makes default drivers in facade or scan this from user
     *
     * @return
     */

}