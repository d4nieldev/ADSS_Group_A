package PresentationLayer.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import ServiceLayer.EmployeesLayer.ServiceFactory;

public class HRManagerScreen extends JFrame {
    private JButton addEmployeeButton;
    private JButton addDriverButton;
    private JButton addEmptyShift;
    private JButton submitShift;
    private JButton editEmployee;

    public HRManagerScreen(ServiceFactory serviceFactory) {
        setTitle("HR Manager Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        addEmployeeButton = new JButton("Add Employee");
        addDriverButton = new JButton("Add Driver");
        addEmptyShift = new JButton("Add Empty Shift");
        submitShift = new JButton("Submit A Shift");
        editEmployee = new JButton("Edit An Employee");

        // Set button sizes
        Dimension buttonSize = new Dimension(150, 30);
        addEmployeeButton.setPreferredSize(buttonSize);
        addDriverButton.setPreferredSize(buttonSize);
        addEmptyShift.setPreferredSize(buttonSize);
        submitShift.setPreferredSize(buttonSize);
        editEmployee.setPreferredSize(buttonSize);

        // Set button focusability to false
        addEmployeeButton.setFocusable(false);
        addDriverButton.setFocusable(false);
        addEmptyShift.setFocusable(false);
        submitShift.setFocusable(false);
        editEmployee.setFocusable(false);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(addEmployeeButton);
        add(addDriverButton);
        add(addEmptyShift);
        add(submitShift);
        add(editEmployee);

        // Add action listeners to the buttons
        addEmployeeButton.addActionListener((ActionEvent e) -> {
            dispose();
            new AddEmployeeScreen(serviceFactory);
        });

        addDriverButton.addActionListener((ActionEvent e) -> {
            dispose();
            new AddDriverScreen(serviceFactory);
        });

        addEmptyShift.addActionListener((ActionEvent e) -> {
            dispose();
            new AddEmptyShiftScreen(serviceFactory);
        });

        submitShift.addActionListener((ActionEvent e) -> {
            dispose();
            new SubmitShiftScreen(serviceFactory, 0, 0, new java.util.HashMap<Integer, Integer>());
        });

        editEmployee.addActionListener((ActionEvent e) -> {
            dispose();
            new EditEmployeeScreen(serviceFactory);
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
