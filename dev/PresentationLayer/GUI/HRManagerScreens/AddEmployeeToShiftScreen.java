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

public class AddEmployeeToShiftScreen extends JFrame{
    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    HashMap<Integer, Integer> employees;

    JPanel panel;
    JTextField employeeIdField;
    JTextField employeeRoleField;
    JLabel employeeIdLabel;
    JLabel employeeRoleLabel;
    JButton addEmployeeButton;
    JButton backButton;

    public AddEmployeeToShiftScreen(ServiceFactory serviceFactory, int shiftId, int branchId, HashMap<Integer, Integer> employees) {
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
        addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.setEnabled(false);
        backButton = new JButton("Back");

        // Set button focusability to false
        addEmployeeButton.setFocusable(false);
        backButton.setFocusable(false);
        
        ImageIcon image = new ImageIcon("dev\\PresentationLayer\\GUI\\super li.png");
        setIconImage(image.getImage());

        // Create the fields
        employeeIdField = new JTextField();
        employeeRoleField = new JTextField();

        Dimension fieldSize = new Dimension(150, 30);

        // Set fields sizes
        employeeIdField.setPreferredSize(fieldSize);
        employeeRoleField.setPreferredSize(fieldSize);

        // Add key listener to the fields
        employeeIdField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        employeeRoleField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        // Set fields filters
        ((AbstractDocument) employeeIdField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) employeeRoleField.getDocument()).setDocumentFilter(new IntFilter());

        // Create labels
        employeeIdLabel = new JLabel("Employee ID");
        employeeRoleLabel = new JLabel("Employee Role");

        // Add the fields an the labels to the panel
        panel.add(employeeIdLabel);
        panel.add(employeeIdField);
        panel.add(employeeRoleLabel);
        panel.add(employeeRoleField);

        // Add action listener to the button
        addEmployeeButton.addActionListener((ActionEvent e) -> {
            int employeeId = Integer.parseInt(employeeIdField.getText());
            int role = Integer.parseInt(employeeRoleField.getText());

            try{
                employees.put(employeeId, role);
                JOptionPane.showMessageDialog(null, "Employee added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Employee already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        backButton.addActionListener((ActionEvent e) -> {
            dispose();
            SubmitShiftScreen previousWindow = new SubmitShiftScreen(serviceFactory, shiftId, branchId, employees);
            previousWindow.setVisible(true);
        });

        // Add the buttons to the panel
        panel.add(addEmployeeButton);
        panel.add(backButton);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public void checkButton() { // watch for key strokes
        if (employeeIdField.getText().length() == 0 || employeeRoleField.getText().length() == 0)
            addEmployeeButton.setEnabled(false);
        else {
            addEmployeeButton.setEnabled(true);
        }
    }
}
