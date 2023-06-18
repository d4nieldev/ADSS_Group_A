package PersentationLayer.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;

public class StoreManagerScreen extends JFrame {
    public static void activate() {
        JFrame f = new JFrame();
        JButton b = new JButton("Click Me!");
        b.setBounds(130, 100, 100, 40);
        f.add(b);
        f.setSize(400, 500);
        f.setLayout(null);
        f.setVisible(true);
    }
}
