package PresentationLayer.GUI.MemberScreens;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import ServiceLayer.EmployeesLayer.ServiceFactory;

public class MemberScreen extends JFrame {
    private JButton addEmployeeButton;
    private JButton addDriverButton;
    private JButton addEmptyShift;

    public MemberScreen(ServiceFactory serviceFactory) {
        setTitle("Member Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        addEmployeeButton = new JButton("Show All Shifts");
        addDriverButton = new JButton("Add Constraint For A Shift");
        addEmptyShift = new JButton("Remove Constraint From A Shift");

        // Set button sizes
        Dimension buttonSize = new Dimension(250, 30);
        addEmployeeButton.setPreferredSize(buttonSize);
        addDriverButton.setPreferredSize(buttonSize);
        addEmptyShift.setPreferredSize(buttonSize);

        // Set button focusability to false
        addEmployeeButton.setFocusable(false);
        addDriverButton.setFocusable(false);
        addEmptyShift.setFocusable(false);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(addEmployeeButton);
        add(addDriverButton);
        add(addEmptyShift);

        // Add action listeners to the buttons
        addEmployeeButton.addActionListener((ActionEvent e) -> {
            dispose();
            new ShowShiftsScreen(serviceFactory);
        });

        // addDriverButton.addActionListener((ActionEvent e) -> {
        //     dispose();
        //     new AddConstraintScreen(serviceFactory);
        // });

        // addEmptyShift.addActionListener((ActionEvent e) -> {
        //     dispose();
        //     new RemoveConstraintScreen(serviceFactory);
        // });

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