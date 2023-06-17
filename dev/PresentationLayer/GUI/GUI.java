package PresentationLayer.GUI;

import ServiceLayer.EmployeesLayer.ServiceFactory;

public class GUI {
    public static void activate(String role, ServiceFactory serviceFactory) {
        if (role.equals("StoreManager"))
            new StoreManagerScreen();
        else if (role.equals("HRManager"))
            new HRManagerScreen(serviceFactory);
        else
            throw new IllegalArgumentException("Unknown role: " + role);
    }
}
