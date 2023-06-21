package PersentationLayer;

import PersentationLayer.CLI.CLI;
import PersentationLayer.GUI.GUI;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException("Expected 2 arguments: mode and role");

        String mode = args[0];
        String role = args[1];

        if (mode.equals("CLI"))
            CLI.activate(role);

        else if (mode.equals("GUI"))
            GUI.activate(role);
    }
}
