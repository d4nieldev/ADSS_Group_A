package PresentationLayer.GUI.MemberScreens;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import ServiceLayer.EmployeesLayer.ServiceFactory;

public class MemberScreen extends JFrame {
    private JButton showShiftButton;
    private JButton addConstraintButton;
    private JButton removeConstraintButton;

    public MemberScreen(ServiceFactory serviceFactory) {
        setTitle("Member Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        showShiftButton = new JButton("Show All Shifts");
        addConstraintButton = new JButton("Add Constraint For A Shift");
        removeConstraintButton = new JButton("Remove Constraint From A Shift");

        // Set button sizes
        Dimension buttonSize = new Dimension(250, 30);
        showShiftButton.setPreferredSize(buttonSize);
        addConstraintButton.setPreferredSize(buttonSize);
        removeConstraintButton.setPreferredSize(buttonSize);

        // Set button focusability to false
        showShiftButton.setFocusable(false);
        addConstraintButton.setFocusable(false);
        removeConstraintButton.setFocusable(false);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(showShiftButton);
        add(addConstraintButton);
        add(removeConstraintButton);

        // Add action listeners to the buttons
        showShiftButton.addActionListener((ActionEvent e) -> {
            dispose();
            new ShowShiftsScreen(serviceFactory);
        });

        addConstraintButton.addActionListener((ActionEvent e) -> {
            dispose();
            new AddConstraintScreen(serviceFactory);
        });

        removeConstraintButton.addActionListener((ActionEvent e) -> {
            dispose();
            new RemoveConstraintScreen(serviceFactory);
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