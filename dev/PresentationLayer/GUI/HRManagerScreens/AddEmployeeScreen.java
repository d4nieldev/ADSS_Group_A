package PresentationLayer.GUI.HRManagerScreens;

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

import PresentationLayer.GUI.Fields.DateField;
import PresentationLayer.GUI.Filters.CharFilter;
import PresentationLayer.GUI.Filters.IntFilter;
import ServiceLayer.EmployeesLayer.BranchService;
import ServiceLayer.EmployeesLayer.EmployeeService;
import ServiceLayer.EmployeesLayer.ServiceFactory;
import ServiceLayer.EmployeesLayer.ShiftService;

public class AddEmployeeScreen extends JFrame {
    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    JPanel panel;
    JTextField firstNameField;
    JTextField lastNameField;
    JTextField idField;
    JTextField passwordField;
    JTextField bankNumberField;
    JTextField bankBranchField;
    JTextField bankAccountField;
    JTextField salaryField;
    DateField startDateField;
    JTextField bounsField;
    JTextField termsField;
    JTextField roleField;
    JTextField superBranchField;
    JLabel firstNameLabel;
    JLabel lastNameLabel;
    JLabel idLabel;
    JLabel passwordLabel;
    JLabel bankNumberLabel;
    JLabel bankBranchLabel;
    JLabel bankAccountLabel;
    JLabel salaryLabel;
    JLabel startDateLabel;
    JLabel bounsLabel;
    JLabel termsLabel;
    JLabel roleLabel;
    JLabel superBranchLabel;
    JButton submitButton;
    JButton backButton;

    public AddEmployeeScreen(ServiceFactory serviceFactory) {
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
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        idField = new JTextField();
        passwordField = new JTextField();
        bankNumberField = new JTextField();
        bankBranchField = new JTextField();
        bankAccountField = new JTextField();
        salaryField = new JTextField();
        startDateField = new DateField(formatter);
        bounsField = new JTextField();
        termsField = new JTextField();
        roleField = new JTextField();
        superBranchField = new JTextField();

        startDateField.setValue(LocalDate.now(ZoneId.systemDefault()));

        Dimension fieldSize = new Dimension(150, 30);

        // Set fields sizes
        firstNameField.setPreferredSize(fieldSize);
        lastNameField.setPreferredSize(fieldSize);
        idField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        bankNumberField.setPreferredSize(fieldSize);
        bankBranchField.setPreferredSize(fieldSize);
        bankAccountField.setPreferredSize(fieldSize);
        salaryField.setPreferredSize(fieldSize);
        startDateField.setPreferredSize(fieldSize);
        bounsField.setPreferredSize(fieldSize);
        termsField.setPreferredSize(fieldSize);
        roleField.setPreferredSize(fieldSize);
        superBranchField.setPreferredSize(fieldSize);

        // Add key listener to the fields
        firstNameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        lastNameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        idField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        bankNumberField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        bankBranchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        bankAccountField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        salaryField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        startDateField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        bounsField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        termsField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        roleField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        superBranchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        // Set fields filters
        ((AbstractDocument) firstNameField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument) lastNameField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument) idField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) bankNumberField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) bankBranchField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) bankAccountField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) salaryField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) bounsField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) termsField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument) roleField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument) superBranchField.getDocument()).setDocumentFilter(new IntFilter());

        // Create labels
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        idLabel = new JLabel("ID");
        passwordLabel = new JLabel("Password");
        bankNumberLabel = new JLabel("Bank Number");
        bankBranchLabel = new JLabel("Bank Branch Number");
        bankAccountLabel = new JLabel("Bank Account Number");
        salaryLabel = new JLabel("Salary");
        startDateLabel = new JLabel("Start Date");
        bounsLabel = new JLabel("Bouns");
        termsLabel = new JLabel("Terms");
        roleLabel = new JLabel("Role");
        superBranchLabel = new JLabel("Super Branch");

        // Add the fields an the labels to the panel
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(bankNumberLabel);
        panel.add(bankNumberField);
        panel.add(bankBranchLabel);
        panel.add(bankBranchField);
        panel.add(bankAccountLabel);
        panel.add(bankAccountField);
        panel.add(salaryLabel);
        panel.add(salaryField);
        panel.add(startDateLabel);
        panel.add(startDateField);
        panel.add(bounsLabel);
        panel.add(bounsField);
        panel.add(termsLabel);
        panel.add(termsField);
        panel.add(roleLabel);
        panel.add(roleField);
        panel.add(superBranchLabel);
        panel.add(superBranchField);

        // Add action listener to the button
        submitButton.addActionListener((ActionEvent e) -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int id = Integer.parseInt(idField.getText());
            String password = passwordField.getText();
            int bankNumber = Integer.parseInt(bankNumberField.getText());
            int bankBranch = Integer.parseInt(bankBranchField.getText());
            int bankAccount = Integer.parseInt(bankAccountField.getText());
            int salary = Integer.parseInt(salaryField.getText());
            LocalDate startDate = LocalDate.parse(startDateField.getText(), formatter);
            int bouns = Integer.parseInt(bounsField.getText());
            String terms = termsField.getText();
            String role = roleField.getText();
            int superBranch = Integer.parseInt(superBranchField.getText());
            
            try {
                branchService.addNewEmployee(123456789, firstName, lastName, id, password, bankNumber,
                            bankBranch, bankAccount, salary, bouns, startDate, terms, role,
                            superBranch);
                JOptionPane.showMessageDialog(null, "Employee added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
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
        if (firstNameField.getText().length() == 0 || lastNameField.getText().length() == 0
                || idField.getText().length() == 0 || passwordField.getText().length() == 0
                || bankNumberField.getText().length() == 0 || bankBranchField.getText().length() == 0
                || bankAccountField.getText().length() == 0 || salaryField.getText().length() == 0
                || bounsField.getText().length() == 0 || startDateField.getText().length() == 0 
                || termsField.getText().length() == 0 || roleField.getText().length() == 0 
                || superBranchField.getText().length() == 0)
            submitButton.setEnabled(false);
        else {
            submitButton.setEnabled(true);
        }
    }

}
