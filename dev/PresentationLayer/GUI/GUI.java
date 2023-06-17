package PresentationLayer.GUI;

import PresentationLayer.GUI.HRManagerScreens.HRManagerScreen;
import PresentationLayer.GUI.MemberScreens.MemberScreen;
import PresentationLayer.GUI.StoreManagerScreens.StoreManagerScreen;
import ServiceLayer.EmployeesLayer.ServiceFactory;

public class GUI {
    public static void activate(String role, ServiceFactory serviceFactory) {
        if (role.equals("StoreManager")){
            serviceFactory.getEmployeeService().logIn(123456789, "HRmanager");
            new StoreManagerScreen(serviceFactory);
        }
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
