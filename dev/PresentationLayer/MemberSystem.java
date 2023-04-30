package PresentationLayer;

import java.util.Scanner;

import ServiceLayer.EmployeesLayer.BranchService;
import ServiceLayer.EmployeesLayer.EmployeeService;
import ServiceLayer.EmployeesLayer.ShiftService;
import ServiceLayer.EmployeesLayer.serviceFactory;

public class MemberSystem {
    
    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    public MemberSystem(serviceFactory serviceFactory) {
        employeeService = serviceFactory.getEmployeeService();
        shiftService = serviceFactory.getShiftService();
        branchService = serviceFactory.getBranchService();
    }

    public void run(int loginId) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Show all shifts.\n 2 - Add constraint for a shift.\n3 - Go back.");
        String option = sc.nextLine();

        while(!option.equals("0")){
            try{
                if (option.equals("1")){ // 1 enter new employee
                    
                }

                else if(option.equals("2")){ // 2 Add constraint for a shift
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

                System.out.println("");
                System.out.println("1 - Show all shifts.\n 2 - Add constraint for a shift.\n3 - Go back.");
            }
            catch(Error e) {
                System.out.println(e.toString());
                System.out.print("Please enter AGAIN your request to the system according to the PDF file: ");
                option = sc.nextLine();
            }
        }

        employeeService.logOut(loginId);

        sc.close();
    }
}
