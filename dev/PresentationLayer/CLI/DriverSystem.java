package PresentationLayer.CLI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import ServiceLayer.EmployeesLayer.BranchService;
import ServiceLayer.EmployeesLayer.EmployeeService;
import ServiceLayer.EmployeesLayer.ShiftService;
import ServiceLayer.EmployeesLayer.ServiceFactory;

public class DriverSystem {

    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    public DriverSystem(ServiceFactory serviceFactory) {
        employeeService = serviceFactory.getEmployeeService();
        shiftService = serviceFactory.getShiftService();
        branchService = serviceFactory.getBranchService();
    }

    public void run(int loginId) {
        Scanner sc = new Scanner(System.in);
        // System.out.println("1 - Show all shifts.\n2 - Add constraint for a shift.\n3
        // - Add constraint for a shift for drivers.\n4 - Go back.");
        System.out.println(getMenu() + "\n");
        String option = sc.nextLine();

        while (!option.equals("0")) {
            try {

                if (option.equals("1")) { // 4 Add constraint for a shift for drivers
                    System.out.println("You choose to add constraint for a driver to a shift.");
                    System.out.println("please enter the following information:");
                    System.out.println("");

                    System.out.println("Please enster the date in that format Date: 05-06-2003\n");
                    System.out.print("Date: ");
                    String date = sc.nextLine();
                    System.out.println("");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate localDate = LocalDate.parse(date, formatter);

                    employeeService.AddConstraintDriver(loginId, localDate);
                }

                else if (option.equals("2")) { // 5 Remove constraint for a shift for drivers
                    System.out.println("You choose to remove constraint for a driver to a shift.");
                    System.out.println("please enter the following information:");
                    System.out.println("");

                    System.out.println("Please enster the date in that format Date: 05-06-2003\n");
                    System.out.print("Date: ");
                    String date = sc.nextLine();
                    System.out.println("");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate localDate = LocalDate.parse(date, formatter);

                    employeeService.RemoveConstraintDriver(loginId, localDate);
                }

                System.out.println("");
                // System.out.println("1 - Show all shifts.\n2 - Add constraint for a shift.\n3
                // - Add constraint for a shift for drivers.\n4 - Go back.");
                System.out.println(getMenu() + "\n");
                option = sc.nextLine();
            } catch (Error e) {
                System.out.println(e.toString());
                System.out.print("Please choose again: \n");
                System.out.println(getMenu() + "\n");
                option = sc.nextLine();
            } 
        }

        employeeService.logOut(loginId);
    }

    public static String getMenu() {
        String horizontalLine = "+-----------------------------------------------+";
        String option0 = "| 0 - Go back.                                    |";
        String option1 = "| 1 - Add constraint for a shift.                 |";
        String option2 = "| 2 - Remove constraint for a shift.              |";
        String bottomLine = "+---------------------------------------------------+";

        StringBuilder sb = new StringBuilder();
        sb.append(horizontalLine).append("\n")
                .append(option0).append("\n")
                .append(option1).append("\n")
                .append(option2).append("\n")
                .append(bottomLine);
        return sb.toString();
    }
}
