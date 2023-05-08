package PresentationLayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
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
        //     }
            
        //     default : {
        //         memberSystem.run(loginId);
        //     }
        // }

        // sc.close();

        hrSystem.employeeService.logIn(123456789, "abc");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("05-06-2003", formatter);
        hrSystem.branchService.addNewEmployee(123456789, "Tuli", "Hatuli", 0, "123", 0, 0, 0, 0, 0, localDate, "dsf", "cashier", 0);
    }

    
}