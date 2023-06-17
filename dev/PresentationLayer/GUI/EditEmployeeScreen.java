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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import Misc.License;
import PresentationLayer.GUI.Fields.DateField;
import PresentationLayer.GUI.Filters.CharFilter;
import PresentationLayer.GUI.Filters.IntFilter;
import ServiceLayer.EmployeesLayer.BranchService;
import ServiceLayer.EmployeesLayer.EmployeeService;
import ServiceLayer.EmployeesLayer.ServiceFactory;
import ServiceLayer.EmployeesLayer.ShiftService;

public class EditEmployeeScreen extends JFrame {
    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    JPanel panel;
    JTextField employeeIdField;
    JTextField firstNameField;
    JTextField lastNameField;
    JTextField passwordField;
    JTextField bankNumberField;
    JTextField bankBranchField;
    JTextField bankAccountField;
    JTextField salaryField;
    DateField startDateField;
    JTextField licenseField;
    JLabel employeeIdLabel;
    JLabel firstNameLabel;
    JLabel lastNameLabel;
    JLabel passwordLabel;
    JLabel bankNumberLabel;
    JLabel bankBranchLabel;
    JLabel bankAccountLabel;
    JLabel salaryLabel;
    JLabel startDateLabel;
    JLabel licenseLabel;
    JButton firstNameButton;
    JButton lastNameButton;
    JButton passwordButton;
    JButton bankNumberButton;
    JButton bankBranchButton;
    JButton bankAccountButton;
    JButton salaryButton;
    JButton startDateButton;
    JButton licenseButton;
    JButton backButton;

    public EditEmployeeScreen(ServiceFactory serviceFactory) {
        // Set the services
        employeeService = serviceFactory.getEmployeeService();
        shiftService = serviceFactory.getShiftService();
        branchService = serviceFactory.getBranchService();

        // Defualt frame settings
        setTitle("Add Employee Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create a panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(11, 2));

        // Create buttoms
        firstNameButton = new JButton("Change First Name");
        lastNameButton = new JButton("Change Last Name");
        passwordButton = new JButton("Change Password");
        bankNumberButton = new JButton("Change Bank Number");
        bankBranchButton = new JButton("Change Bank Branch");
        bankAccountButton = new JButton("Change Bank Account");
        salaryButton = new JButton("Change Salary");
        startDateButton = new JButton("Change Start Date");
        licenseButton = new JButton("Change License");
        backButton = new JButton("Back");

        // Set buttoms to disabled
        firstNameButton.setEnabled(false);
        lastNameButton.setEnabled(false);
        passwordButton.setEnabled(false);
        bankNumberButton.setEnabled(false);
        bankBranchButton.setEnabled(false);
        bankAccountButton.setEnabled(false);
        salaryButton.setEnabled(false);
        startDateButton.setEnabled(false);
        licenseButton.setEnabled(false);

        // Set button focusability to false
        firstNameButton.setFocusable(false);
        lastNameButton.setFocusable(false);
        passwordButton.setFocusable(false);
        bankNumberButton.setFocusable(false);
        bankBranchButton.setFocusable(false);
        bankAccountButton.setFocusable(false);
        salaryButton.setFocusable(false);
        startDateButton.setFocusable(false);
        licenseButton.setFocusable(false);
        backButton.setFocusable(false);

        ImageIcon image = new ImageIcon("dev\\PresentationLayer\\GUI\\super li.png");
        setIconImage(image.getImage());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Create the fields
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        passwordField = new JTextField();
        bankNumberField = new JTextField();
        bankBranchField = new JTextField();
        bankAccountField = new JTextField();
        salaryField = new JTextField();
        startDateField = new DateField(formatter);
        licenseField = new JTextField();
        employeeIdField = new JTextField();

        startDateField.setValue(LocalDate.now(ZoneId.systemDefault()));

        Dimension fieldSize = new Dimension(150, 30);

        // Set fields sizes
        firstNameField.setPreferredSize(fieldSize);
        lastNameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        bankNumberField.setPreferredSize(fieldSize);
        bankBranchField.setPreferredSize(fieldSize);
        bankAccountField.setPreferredSize(fieldSize);
        salaryField.setPreferredSize(fieldSize);
        startDateField.setPreferredSize(fieldSize);
        licenseField.setPreferredSize(fieldSize);
        employeeIdField.setPreferredSize(fieldSize);

        // Add key listener to the fields
        firstNameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton(firstNameField, firstNameButton);
            }
        });

        lastNameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton(lastNameField, lastNameButton);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton(passwordField, passwordButton);
            }
        });

        bankNumberField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton(bankNumberField, bankNumberButton);
            }
        });

        bankBranchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton(bankBranchField, bankBranchButton);
            }
        });

        bankAccountField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton(bankAccountField, bankAccountButton);
            }
        });

        salaryField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton(salaryField, salaryButton);
            }
        });

        startDateField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton(startDateField, startDateButton);
            }
        });

        licenseField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton(licenseField, licenseButton);
            }
        });

        employeeIdField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton(firstNameField, firstNameButton);
                checkButton(lastNameField, lastNameButton);
                checkButton(passwordField, passwordButton);
                checkButton(bankNumberField, bankNumberButton);
                checkButton(bankBranchField, bankBranchButton);
                checkButton(bankAccountField, bankAccountButton);
                checkButton(salaryField, salaryButton);
                checkButton(startDateField, startDateButton);
                checkButton(licenseField, licenseButton);
            }
        });

        // Set fields filters
        ((AbstractDocument) employeeIdField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) firstNameField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument) lastNameField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument) bankNumberField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) bankBranchField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) bankAccountField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) salaryField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) licenseField.getDocument()).setDocumentFilter(new CharFilter());

        // Create labels
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        passwordLabel = new JLabel("Password");
        bankNumberLabel = new JLabel("Bank Number");
        bankBranchLabel = new JLabel("Bank Branch Number");
        bankAccountLabel = new JLabel("Bank Account Number");
        salaryLabel = new JLabel("Salary");
        startDateLabel = new JLabel("Start Date");
        licenseLabel = new JLabel("License");
        employeeIdLabel = new JLabel("Employee ID To Edit");

        // Add the fields, labels and buttons to the panel
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(firstNameButton);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(lastNameButton);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(passwordButton);
        panel.add(bankNumberLabel);
        panel.add(bankNumberField);
        panel.add(bankNumberButton);
        panel.add(bankBranchLabel);
        panel.add(bankBranchField);
        panel.add(bankBranchButton);
        panel.add(bankAccountLabel);
        panel.add(bankAccountField);
        panel.add(bankAccountButton);
        panel.add(salaryLabel);
        panel.add(salaryField);
        panel.add(salaryButton);
        panel.add(startDateLabel);
        panel.add(startDateField);
        panel.add(startDateButton);
        panel.add(licenseLabel);
        panel.add(licenseField);
        panel.add(licenseButton);
        panel.add(employeeIdLabel);
        panel.add(employeeIdField);

        // Add action listener to the button
        firstNameButton.addActionListener((ActionEvent e) -> {
            int idToEdit = Integer.parseInt(employeeIdField.getText());
            String firstName = firstNameField.getText();
            try {
                employeeService.changeFirstName(123456789, idToEdit, firstName);
                JOptionPane.showMessageDialog(null, "First name changed successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        lastNameButton.addActionListener((ActionEvent e) -> {
            int idToEdit = Integer.parseInt(employeeIdField.getText());
            String lastName = lastNameField.getText();
            try {
                employeeService.changeLastName(123456789, idToEdit, lastName);
                JOptionPane.showMessageDialog(null, "Last name changed successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        passwordButton.addActionListener((ActionEvent e) -> {
            int idToEdit = Integer.parseInt(employeeIdField.getText());
            String password = passwordField.getText();
            try {
                employeeService.changePassword(123456789, idToEdit, password);
                JOptionPane.showMessageDialog(null, "Password changed successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        bankNumberButton.addActionListener((ActionEvent e) -> {
            int idToEdit = Integer.parseInt(employeeIdField.getText());
            int bankNumber = Integer.parseInt(bankNumberField.getText());
            try {
                employeeService.changeBankNum(123456789, idToEdit, bankNumber);
                JOptionPane.showMessageDialog(null, "Bank number changed successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        bankBranchButton.addActionListener((ActionEvent e) -> {
            int idToEdit = Integer.parseInt(employeeIdField.getText());
            int bankBranch = Integer.parseInt(bankBranchField.getText());
            try {
                employeeService.changeBankBranch(123456789, idToEdit, bankBranch);
                JOptionPane.showMessageDialog(null, "Bank branch changed successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        bankAccountButton.addActionListener((ActionEvent e) -> {
            int idToEdit = Integer.parseInt(employeeIdField.getText());
            int bankAccount = Integer.parseInt(bankAccountField.getText());
            try {
                employeeService.changeBankAccount(123456789, idToEdit, bankAccount);
                JOptionPane.showMessageDialog(null, "Bank account changed successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        salaryButton.addActionListener((ActionEvent e) -> {
            int idToEdit = Integer.parseInt(employeeIdField.getText());
            int salary = Integer.parseInt(salaryField.getText());
            try {
                employeeService.changeSalary(123456789, idToEdit, salary);
                JOptionPane.showMessageDialog(null, "Salary changed successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        startDateButton.addActionListener((ActionEvent e) -> {
            int idToEdit = Integer.parseInt(employeeIdField.getText());
            LocalDate startDate = LocalDate.parse(startDateField.getText(), formatter);
            try {
                employeeService.changeStartDate(123456789, idToEdit, startDate);
                JOptionPane.showMessageDialog(null, "Start date changed successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        licenseButton.addActionListener((ActionEvent e) -> {
            int idToEdit = Integer.parseInt(employeeIdField.getText());
            License driverLicense = null;
            try {
                driverLicense = License.valueOf(licenseField.getText().toUpperCase());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid license - It musy be B,C OR NULL", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                licenseField.setText("");
                licenseButton.setEnabled(false);
            }
            if (driverLicense != null) {
                try {
                    employeeService.changeDriverLicence(123456789, idToEdit, driverLicense);
                    JOptionPane.showMessageDialog(null, "License changed successfully", "Success",
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

        // Add back button to the panel
        panel.add(backButton);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public void checkButton(JTextField field, JButton button) { // watch for key strokes
        if (field.getText().length() == 0 || employeeIdField.getText().length() == 0)
            button.setEnabled(false);
        else {
            button.setEnabled(true);
        }
    }
}
