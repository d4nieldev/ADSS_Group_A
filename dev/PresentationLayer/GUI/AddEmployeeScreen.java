package PresentationLayer.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import PresentationLayer.GUI.Fields.DateField;
import PresentationLayer.GUI.Filters.CharFilter;
import PresentationLayer.GUI.Filters.IntFilter;

public class AddEmployeeScreen extends JFrame {

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

    public AddEmployeeScreen() {
        // Defualt frame settings
        setTitle("Add Employee Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(1,13));
        // setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create a panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(14, 1));

        // Create a buttom
        submitButton = new JButton("Submit");

        ImageIcon image = new ImageIcon("dev\\PresentationLayer\\GUI\\MainLogo.png");
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

        // Set fields filters
        ((AbstractDocument)firstNameField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument)lastNameField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument)idField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument)bankNumberField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument)bankBranchField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument)bankAccountField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument)salaryField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument)bounsField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument)termsField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument)roleField.getDocument()).setDocumentFilter(new CharFilter());
        ((AbstractDocument)superBranchField.getDocument()).setDocumentFilter(new IntFilter());


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
            String id = idField.getText();
            String password = passwordField.getText();
            String bankNumber = bankNumberField.getText();
            String bankBranch = bankBranchField.getText();
            String bankAccount = bankAccountField.getText();
            String salary = salaryField.getText();
            String startDate = startDateField.getText();
            String bouns = bounsField.getText();
            String terms = termsField.getText();
            String role = roleField.getText();
            String superBranch = superBranchField.getText();
        });

        // Add the button to the panel
        panel.add(submitButton);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);
        
        pack();
        setVisible(true);
    }

}
