package PersentationLayer.GUI;

import PersentationLayer.GUI.SuppliersManager.SuppliersManagerScreen;

public class GUI {
    public static void activate(String role) {
        if (role.equals("StoreManager"))
            new StoreManagerFrame();
        else if (role.equals("StoreKeeper"))
            new StorekeeperFrame();
        else if (role.equals("SuppliersManager"))
            new SuppliersManagerScreen();
        else
            throw new IllegalArgumentException("Unknown role: " + role);
    }
}
