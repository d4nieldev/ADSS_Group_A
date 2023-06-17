package PresentationLayer.GUI.StoreManagerScreens;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import PresentationLayer.GUI.HRManagerScreens.HRManagerScreen;
import PresentationLayer.GUI.MemberScreens.MemberScreen;
import ServiceLayer.EmployeesLayer.ServiceFactory;

public class StoreManagerScreen extends JFrame {
    private JButton HRManagerButton;
    private JButton MemberButton;

    public StoreManagerScreen(ServiceFactory serviceFactory) {
        setTitle("StoreManager Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        HRManagerButton = new JButton("HR Manager Screen");
        MemberButton = new JButton("Member Screen");

        // Set button sizes
        Dimension buttonSize = new Dimension(250, 30);
        HRManagerButton.setPreferredSize(buttonSize);
        MemberButton.setPreferredSize(buttonSize);

        // Set button focusability to false
        HRManagerButton.setFocusable(false);
        MemberButton.setFocusable(false);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(HRManagerButton);
        add(MemberButton);

        // Add action listeners to the buttons
        HRManagerButton.addActionListener((ActionEvent e) -> {
            dispose();
            new HRManagerScreen(serviceFactory);
        });

        MemberButton.addActionListener((ActionEvent e) -> {
            dispose();
            new MemberScreen(serviceFactory);
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
