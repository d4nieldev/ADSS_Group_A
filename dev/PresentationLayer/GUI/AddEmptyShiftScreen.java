package PresentationLayer.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import Misc.ShiftTime;
import PresentationLayer.GUI.Fields.DateField;
import PresentationLayer.GUI.Filters.CharFilter;
import PresentationLayer.GUI.Filters.IntFilter;
import ServiceLayer.EmployeesLayer.BranchService;
import ServiceLayer.EmployeesLayer.EmployeeService;
import ServiceLayer.EmployeesLayer.ServiceFactory;
import ServiceLayer.EmployeesLayer.ShiftService;

public class AddEmptyShiftScreen extends JFrame {
    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    JPanel panel;
    JTextField branchIdField;
    DateField shiftDateField;
    JTextField startHourField;
    JTextField endHourField;
    JTextField shiftTimeField;
    JTextField branchManagerField;
    JTextField shiftManagerField;
    JTextField cashierField;
    JTextField storekeeperField;
    JTextField generralField;
    JTextField cleanerField;
    JTextField securityField;
    JLabel branchIdLabel;
    JLabel shiftDateLabel;
    JLabel startHourLabel;
    JLabel endHourLabel;
    JLabel shiftTimeLabel;
    JLabel branchManagerLabel;
    JLabel shiftManagerLabel;
    JLabel cashierLabel;
    JLabel storekeeperLabel;
    JLabel generralLabel;
    JLabel cleanerLabel;
    JLabel securityLabel;
    JButton submitButton;
    JButton backButton;

    public AddEmptyShiftScreen(ServiceFactory serviceFactory) {
        // Set the services
        employeeService = serviceFactory.getEmployeeService();
        shiftService = serviceFactory.getShiftService();
        branchService = serviceFactory.getBranchService();

        // Defualt frame settings
        setTitle("Add Empty Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create a panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(14, 1));

        // Create buttoms
        submitButton = new JButton("Submit");
        submitButton.setEnabled(false);
        backButton = new JButton("Back");

        // Set button focusability to false
        submitButton.setFocusable(false);
        backButton.setFocusable(false);

        ImageIcon image = new ImageIcon("dev\\PresentationLayer\\GUI\\super li.png");
        setIconImage(image.getImage());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Create the fields
        branchIdField = new JTextField();
        shiftDateField = new DateField(formatter);
        startHourField = new JTextField();
        endHourField = new JTextField();
        shiftTimeField = new JTextField();
        branchManagerField = new JTextField();
        shiftManagerField = new JTextField();
        cashierField = new JTextField();
        storekeeperField = new JTextField();
        generralField = new JTextField();
        cleanerField = new JTextField();
        securityField = new JTextField();

        shiftDateField.setValue(LocalDate.now(ZoneId.systemDefault()));

        Dimension fieldSize = new Dimension(150, 30);

        // Set fields sizes
        branchIdField.setPreferredSize(fieldSize);
        shiftDateField.setPreferredSize(fieldSize);
        startHourField.setPreferredSize(fieldSize);
        endHourField.setPreferredSize(fieldSize);
        shiftTimeField.setPreferredSize(fieldSize);
        branchManagerField.setPreferredSize(fieldSize);
        shiftManagerField.setPreferredSize(fieldSize);
        cashierField.setPreferredSize(fieldSize);
        storekeeperField.setPreferredSize(fieldSize);
        generralField.setPreferredSize(fieldSize);
        cleanerField.setPreferredSize(fieldSize);
        securityField.setPreferredSize(fieldSize);

        // Add key listener to the fields
        branchIdField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        shiftDateField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        startHourField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        endHourField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        shiftTimeField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        branchManagerField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        shiftManagerField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        cashierField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        storekeeperField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        generralField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        cleanerField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        securityField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        // Set fields filters
        ((AbstractDocument) branchIdField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) startHourField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) endHourField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) shiftTimeField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument) branchManagerField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) shiftManagerField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) cashierField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) storekeeperField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) generralField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) cleanerField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) securityField.getDocument()).setDocumentFilter(new IntFilter());

        // Create labels
        branchIdLabel = new JLabel("Branch ID");
        shiftDateLabel = new JLabel("Shift Date");
        startHourLabel = new JLabel("Start Hour");
        endHourLabel = new JLabel("End Hour");
        shiftTimeLabel = new JLabel("Shift Time (Morning/Evening)");
        branchManagerLabel = new JLabel("Branch Manager");
        shiftManagerLabel = new JLabel("Shift Manager");
        cashierLabel = new JLabel("Cashier");
        storekeeperLabel = new JLabel("Storekeeper");
        generralLabel = new JLabel("General");
        cleanerLabel = new JLabel("Cleaner");
        securityLabel = new JLabel("Security");

        // Add the fields an the labels to the panel
        panel.add(branchIdLabel);
        panel.add(branchIdField);
        panel.add(shiftDateLabel);
        panel.add(shiftDateField);
        panel.add(startHourLabel);
        panel.add(startHourField);
        panel.add(endHourLabel);
        panel.add(endHourField);
        panel.add(shiftTimeLabel);
        panel.add(shiftTimeField);
        panel.add(branchManagerLabel);
        panel.add(branchManagerField);
        panel.add(shiftManagerLabel);
        panel.add(shiftManagerField);
        panel.add(cashierLabel);
        panel.add(cashierField);
        panel.add(storekeeperLabel);
        panel.add(storekeeperField);
        panel.add(generralLabel);
        panel.add(generralField);
        panel.add(cleanerLabel);
        panel.add(cleanerField);
        panel.add(securityLabel);
        panel.add(securityField);

        // Add action listener to the button
        submitButton.addActionListener((ActionEvent e) -> {
            int branchId = Integer.parseInt(branchIdField.getText());
            LocalDate shiftDate = LocalDate.parse(shiftDateField.getText(), formatter);
            int startHour = Integer.parseInt(startHourField.getText());
            int endHour = Integer.parseInt(endHourField.getText());

            ShiftTime shiftTime = null;
            try {
                shiftTime = ShiftTime.valueOf(shiftTimeField.getText().toUpperCase());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Shift time must be 'Morning' or 'Evening'", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                shiftTimeField.setText("");
                submitButton.setEnabled(false);
            }

            int branchManager = Integer.parseInt(branchManagerField.getText());
            int shiftManager = Integer.parseInt(shiftManagerField.getText());
            int cashier = Integer.parseInt(cashierField.getText());
            int storekeeper = Integer.parseInt(storekeeperField.getText());
            int generral = Integer.parseInt(generralField.getText());
            int cleaner = Integer.parseInt(cleanerField.getText());
            int security = Integer.parseInt(securityField.getText());

            HashMap<Integer, Integer> numEmployeesForRole = new HashMap<>();
            numEmployeesForRole.put(4, branchManager);
            numEmployeesForRole.put(5, shiftManager);
            numEmployeesForRole.put(6, cashier);
            numEmployeesForRole.put(7, storekeeper);
            numEmployeesForRole.put(9, generral);
            numEmployeesForRole.put(10, cleaner);
            numEmployeesForRole.put(11, security);

            if (shiftTime != null) {
                try {
                    branchService.addShift(123456789, branchId, shiftDate, startHour, endHour, shiftTime,
                            numEmployeesForRole);
                    JOptionPane.showMessageDialog(null, "Shift added successfully", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Error ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener((ActionEvent e) -> {
            dispose();
            HRManagerScreen previousWindow = new HRManagerScreen(serviceFactory);
            previousWindow.setVisible(true);
        });

        // Add the buttons to the panel
        panel.add(submitButton);
        panel.add(backButton);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public void checkButton() { // watch for key strokes
        if (branchIdField.getText().length() == 0 || shiftDateField.getText().length() == 0
                || startHourField.getText().length() == 0 || endHourField.getText().length() == 0
                || shiftTimeField.getText().length() == 0 || branchManagerField.getText().length() == 0
                || shiftManagerField.getText().length() == 0 || cashierField.getText().length() == 0
                || storekeeperField.getText().length() == 0 || generralField.getText().length() == 0
                || cleanerField.getText().length() == 0 || securityField.getText().length() == 0)
            submitButton.setEnabled(false);
        else {
            submitButton.setEnabled(true);
        }
    }
}
