import java.util.Scanner;

import ServiceLayer.EmployeesMoudle.EmployeeService;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello world!");

        // System.out.print("Please enter your request to the system according to the PDF file: ");
        // Scanner sc = new Scanner(System.in);

        EmployeeService service = new EmployeeService();
        service.logIn(12345, "12345");
    }
}