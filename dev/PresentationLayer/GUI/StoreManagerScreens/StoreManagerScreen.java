package PresentationLayer.GUI.StoreManagerScreens;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import PresentationLayer.GUI.DriverScreens.DriverScreen;
import PresentationLayer.GUI.HRManagerScreens.HRManagerScreen;
import PresentationLayer.GUI.MemberScreens.MemberScreen;
import ServiceLayer.EmployeesLayer.ServiceFactory;

public class StoreManagerScreen extends JFrame {
    private JButton hRManagerButton;
    private JButton memberButton;
    private JButton driverButton;

    public StoreManagerScreen(ServiceFactory serviceFactory) {
        setTitle("StoreManager Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        hRManagerButton = new JButton("HR Manager Screen");
        memberButton = new JButton("Member Screen");
        driverButton = new JButton("Driver Screen");

        // Set button sizes
        Dimension buttonSize = new Dimension(250, 30);
        hRManagerButton.setPreferredSize(buttonSize);
        memberButton.setPreferredSize(buttonSize);
        driverButton.setPreferredSize(buttonSize);

        // Set button focusability to false
        hRManagerButton.setFocusable(false);
        memberButton.setFocusable(false);
        driverButton.setFocusable(false);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(hRManagerButton);
        add(memberButton);
        add(driverButton);

        // Add action listeners to the buttons
        hRManagerButton.addActionListener((ActionEvent e) -> {
            dispose();
            new HRManagerScreen(serviceFactory);
        });

        memberButton.addActionListener((ActionEvent e) -> {
            dispose();
            new MemberScreen(serviceFactory);
        });

        driverButton.addActionListener((ActionEvent e) -> {
            dispose();
            new DriverScreen(serviceFactory);
        });


        // Set the frame size and make it visible
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon image = new ImageIcon("dev\\PresentationLayer\\GUI\\super li.png");
        setIconImage(image.getImage());

        // JLabel myLabel = new JLabel(image);
        // myLabel.setSize(400, 200);
        // add(myLabel);

        setVisible(true);
    }
}
