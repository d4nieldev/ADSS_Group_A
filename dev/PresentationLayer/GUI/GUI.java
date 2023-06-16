package PresentationLayer.GUI;

public class GUI {
    public static void activate(String role) {
        if (role.equals("StoreManager"))
            new StoreManagerScreen();
        else if (role.equals("HRManager"))
            new HRManagerScreen();
        else
            throw new IllegalArgumentException("Unknown role: " + role);
    }
}
