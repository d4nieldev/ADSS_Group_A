package PresentationLayer.GUI.DriverScreens;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import ServiceLayer.EmployeesLayer.ServiceFactory;

public class DriverScreen extends JFrame{
    private JButton addConstraintButton;
    private JButton removeConstraintButton;

    public DriverScreen(ServiceFactory serviceFactory) {
        setTitle("Driver Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        addConstraintButton = new JButton("Add Constraint For A Shift");
        removeConstraintButton = new JButton("Remove Constraint From A Shift");

        // Set button sizes
        Dimension buttonSize = new Dimension(250, 30);
        addConstraintButton.setPreferredSize(buttonSize);
        removeConstraintButton.setPreferredSize(buttonSize);

        // Set button focusability to false
        addConstraintButton.setFocusable(false);
        removeConstraintButton.setFocusable(false);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(addConstraintButton);
        add(removeConstraintButton);

        // Add action listeners to the buttons
        addConstraintButton.addActionListener((ActionEvent e) -> {
            dispose();
            new AddConstraintDriverScreen(serviceFactory);
        });

        removeConstraintButton.addActionListener((ActionEvent e) -> {
            dispose();
            new RemoveConstraintDriverScreen(serviceFactory);
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
