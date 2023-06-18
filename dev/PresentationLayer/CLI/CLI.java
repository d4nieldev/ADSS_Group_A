package PresentationLayer.CLI;

import ServiceLayer.EmployeesLayer.ServiceFactory;

public class CLI {
    public static void activate(String role, ServiceFactory serviceFactory) {
        if (role.equals("StoreManager")) {
            new MainV2(serviceFactory).run("BRANCHMANAGER");
        }
        else if (role.equals("HRManager")) {
            new MainV2(serviceFactory).run("HRMANAGER");
        }           
        else if (role.equals("Member")) {
            new MainV2(serviceFactory).run("MEMBER");
        }
        else if (role.equals("Driver")) {
            new MainV2(serviceFactory).run("DRIVER");
        }
        else
            throw new IllegalArgumentException("Unknown role: " + role);
    }
}
