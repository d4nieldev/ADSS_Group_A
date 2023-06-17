package PresentationLayer.GUI.MemberScreens;

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

public class ShowShiftsScreen extends JFrame {
    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    JPanel panel;
    JTextField memberIdField;
    DateField dateField;
    JLabel memberIdLabel;
    JLabel dateLabel;
    JButton showAllButton;
    JButton backButton;

    public ShowShiftsScreen(ServiceFactory serviceFactory) {
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
        panel.setLayout(new GridLayout(3, 1));

        // Create buttoms
        showAllButton = new JButton("Show Shifts");
        backButton = new JButton("Back");

        // Set button focusability to false
        showAllButton.setFocusable(false);
        backButton.setFocusable(false);

        ImageIcon image = new ImageIcon("dev\\PresentationLayer\\GUI\\super li.png");
        setIconImage(image.getImage());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Create the fields
        memberIdField = new JTextField();
        dateField = new DateField(formatter);

        Dimension fieldSize = new Dimension(150, 30);

        // Set fields sizes
        memberIdField.setPreferredSize(fieldSize);
        dateField.setPreferredSize(fieldSize);

        // Add values to the fields
        dateField.setValue(LocalDate.now(ZoneId.systemDefault()));

        // Add key listener to the fields
        memberIdField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        dateField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        // Set fields filters
        ((AbstractDocument) memberIdField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) dateField.getDocument()).setDocumentFilter(new CharFilter());

        // Create labels
        memberIdLabel = new JLabel("Your ID");
        dateLabel = new JLabel("Date");

        // Add the fields an the labels to the panel
        panel.add(memberIdLabel);
        panel.add(memberIdField);
        panel.add(dateLabel);
        panel.add(dateField);

        // Add action listener to the button
        showAllButton.addActionListener((ActionEvent e) -> {
            int memberId = Integer.parseInt(memberIdField.getText());
            LocalDate date = LocalDate.parse(dateField.getText(), formatter);
            try {
                String str = branchService.printAvailableShiftForEmployee(memberId, date);
                JOptionPane.showMessageDialog(null, str, "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Error ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        });

        backButton.addActionListener((ActionEvent e) -> {
            dispose();
            MemberScreen previousWindow = new MemberScreen(serviceFactory);
            previousWindow.setVisible(true);
        });

        // Add the buttons to the panel
        panel.add(showAllButton);
        panel.add(backButton);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public void checkButton() { // watch for key strokes
        if (memberIdLabel.getText().length() == 0 || dateField.getText().length() == 0)
            showAllButton.setEnabled(false);
        else {
            showAllButton.setEnabled(true);
        }
    }
}
