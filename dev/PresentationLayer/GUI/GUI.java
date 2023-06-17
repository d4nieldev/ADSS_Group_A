package PresentationLayer.GUI;

import PresentationLayer.GUI.HRManagerScreens.HRManagerScreen;
import PresentationLayer.GUI.MemberScreens.MemberScreen;
import ServiceLayer.EmployeesLayer.ServiceFactory;

public class GUI {
    public static void activate(String role, ServiceFactory serviceFactory) {
        if (role.equals("StoreManager"))
            new StoreManagerScreen();
        else if (role.equals("HRManager")) {
            serviceFactory.getEmployeeService().logIn(123456789, "HRmanager");
            new HRManagerScreen(serviceFactory);
        }           
        else if (role.equals("Member"))
            new MemberScreen(serviceFactory);
        else
            throw new IllegalArgumentException("Unknown role: " + role);
    }
}
