package PresentationLayer.GUI.HRManagerScreens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import PresentationLayer.GUI.Filters.IntFilter;
import ServiceLayer.EmployeesLayer.BranchService;
import ServiceLayer.EmployeesLayer.EmployeeService;
import ServiceLayer.EmployeesLayer.ServiceFactory;
import ServiceLayer.EmployeesLayer.ShiftService;

public class SubmitShiftScreen extends JFrame {
    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    HashMap<Integer, Integer> employees;

    JPanel panel;
    JTextField shiftIdField;
    JTextField branchIdField;
    JLabel shiftIdLabel;
    JLabel branchIdLabel;
    JButton addEmployeeButton;
    JButton submitAllButton;
    JButton backButton;

    public SubmitShiftScreen(ServiceFactory serviceFactory, int shiftId, int branchId,
            HashMap<Integer, Integer> employees) {
        // Set the services
        employeeService = serviceFactory.getEmployeeService();
        shiftService = serviceFactory.getShiftService();
        branchService = serviceFactory.getBranchService();

        this.employees = employees;

        // Defualt frame settings
        setTitle("Add Empty Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create a panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        // Create buttoms
        submitAllButton = new JButton("Submit");
        if (employees.size() == 0) {
            submitAllButton.setEnabled(false);
        } else {
            submitAllButton.setEnabled(true);
        }
        backButton = new JButton("Back");
        addEmployeeButton = new JButton("Add Employee");

        // Set button focusability to false
        submitAllButton.setFocusable(false);
        backButton.setFocusable(false);
        addEmployeeButton.setFocusable(false);

        ImageIcon image = new ImageIcon("dev\\PresentationLayer\\GUI\\super li.png");
        setIconImage(image.getImage());

        // Create the fields
        shiftIdField = new JTextField();
        branchIdField = new JTextField();

        Dimension fieldSize = new Dimension(150, 30);

        // Set fields sizes
        shiftIdField.setPreferredSize(fieldSize);
        branchIdField.setPreferredSize(fieldSize);

        // Add values to the fields
        shiftIdField.setText(Integer.toString(shiftId));
        branchIdField.setText(Integer.toString(branchId));

        // Add key listener to the fields
        shiftIdField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        branchIdField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        // Set fields filters
        ((AbstractDocument) shiftIdField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) branchIdField.getDocument()).setDocumentFilter(new IntFilter());

        // Create labels
        shiftIdLabel = new JLabel("Shift ID");
        branchIdLabel = new JLabel("Branch ID");

        // Add the fields an the labels to the panel
        panel.add(shiftIdLabel);
        panel.add(shiftIdField);
        panel.add(branchIdLabel);
        panel.add(branchIdField);

        // Add action listener to the button
        submitAllButton.addActionListener((ActionEvent e) -> {
            int newShiftId = Integer.parseInt(shiftIdField.getText());
            int newBranchId = Integer.parseInt(branchIdField.getText());

            try {
                branchService.approveFinalShift(123456789, newShiftId, newBranchId, this.employees);
                JOptionPane.showMessageDialog(null, "Shift was submitted successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        });

        backButton.addActionListener((ActionEvent e) -> {
            dispose();
            HRManagerScreen previousWindow = new HRManagerScreen(serviceFactory);
            previousWindow.setVisible(true);
        });

        addEmployeeButton.addActionListener((ActionEvent e) -> {
            dispose();
            if (shiftIdField.getText().length() == 0 || branchIdField.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields before adding employees", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            AddEmployeeToShiftScreen addEmployeeToShift = new AddEmployeeToShiftScreen(serviceFactory,
                    Integer.parseInt(shiftIdField.getText()), Integer.parseInt(branchIdField.getText()), employees);
            addEmployeeToShift.setVisible(true);
        });

        // Add the buttons to the panel
        panel.add(addEmployeeButton);
        panel.add(submitAllButton);
        panel.add(backButton);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public void checkButton() { // watch for key strokes
        if (shiftIdField.getText().length() == 0 || branchIdField.getText().length() == 0
                || this.employees.size() == 0)
            submitAllButton.setEnabled(false);
        else {
            submitAllButton.setEnabled(true);
        }
    }
}
