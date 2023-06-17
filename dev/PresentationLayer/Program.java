package PresentationLayer;

//import PresentationLayer.CLI.CLI;
import PresentationLayer.GUI.GUI;
import ServiceLayer.EmployeesLayer.ServiceFactory;

public class Program {
    private static ServiceFactory serviceFactory = new ServiceFactory();
    public static void main(String[] args) {
        args = new String[2];
        args[0] = "GUI";
        args[1] = "Member";
        if (args.length != 2)
            throw new IllegalArgumentException("Expected 2 arguments: mode and role");

        String mode = args[0];
        String role = args[1];

        GUI.activate(role, serviceFactory);

        // if (mode.equals("CLI"))
        //     CLI.activate(role);
        // else
        //     GUI.activate(role);
    }
}
