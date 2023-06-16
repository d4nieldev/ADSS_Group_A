package PresentationLayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import ServiceLayer.EmployeesLayer.BranchService;
import ServiceLayer.EmployeesLayer.EmployeeService;
import ServiceLayer.EmployeesLayer.ShiftService;
import ServiceLayer.EmployeesLayer.ServiceFactory;

public class MemberSystem {

    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    public MemberSystem(ServiceFactory serviceFactory) {
        employeeService = serviceFactory.getEmployeeService();
        shiftService = serviceFactory.getShiftService();
        branchService = serviceFactory.getBranchService();
    }

    public void run(int loginId) {
        Scanner sc = new Scanner(System.in);
        //System.out.println("1 - Show all shifts.\n2 - Add constraint for a shift.\n3 - Add constraint for a shift for drivers.\n4 - Go back.");
        System.out.println(getMenu() + "\n");
        String option = sc.nextLine();

        while (!option.equals("0")) {
            try {
                if (option.equals("1")) { // 1 Show all shifts
                    System.out.println("Please enter the date that you would like to work in.\n");
                    System.out.println("Please enster the date in that format Date: 05-06-2003\n");
                    String date = sc.nextLine();
                    System.out.println("");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate localDate = LocalDate.parse(date, formatter);

                    System.out.println(branchService.printAvailableShiftForEmployee(loginId, localDate));    
                }

                else if (option.equals("2")) { // 2 Add constraint for a shift
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

                else if (option.equals("3")) { // 3 Remove constraint for a shift
                    System.out.println("You choose to remove constraint for an employee to a shift.");
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

                    branchService.removeConstraint(branch, idEmployee, shift);
                }

                // else if (option.equals("4")) { // 4 Add constraint for a shift for drivers
                //     System.out.println("You choose to add constraint for a driver to a shift.");
                //     System.out.println("please enter the following information:");
                //     System.out.println("");

                //     System.out.println("Please enster the date in that format Date: 05-06-2003\n");
                //     System.out.print("Date: ");
                //     String date = sc.nextLine();
                //     System.out.println("");

                //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                //     LocalDate localDate = LocalDate.parse(date, formatter);

                //     employeeService.AddConstraintDriver(loginId, localDate);
                // }

                // else if (option.equals("5")) { // 5 Remove constraint for a shift for drivers
                //     System.out.println("You choose to remove constraint for a driver to a shift.");
                //     System.out.println("please enter the following information:");
                //     System.out.println("");

                //     System.out.println("Please enster the date in that format Date: 05-06-2003\n");
                //     System.out.print("Date: ");
                //     String date = sc.nextLine();
                //     System.out.println("");

                //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                //     LocalDate localDate = LocalDate.parse(date, formatter);

                //     employeeService.RemoveConstraintDriver(loginId, localDate);
                // }

                System.out.println("");
                //System.out.println("1 - Show all shifts.\n2 - Add constraint for a shift.\n3 - Add constraint for a shift for drivers.\n4 - Go back.");
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
        String option1 = "| 1 - Show all shifts.                            |";
        String option2 = "| 2 - Add constraint for a shift.                 |";
        String option3 = "| 3 - Remove constraint for a shift.              |";
        String bottomLine = "+---------------------------------------------------+";
    
        StringBuilder sb = new StringBuilder();
        sb.append(horizontalLine).append("\n")
          .append(option0).append("\n")
          .append(option1).append("\n")
          .append(option2).append("\n")
          .append(option3).append("\n")
          .append(bottomLine);
        return sb.toString();
    }

}
