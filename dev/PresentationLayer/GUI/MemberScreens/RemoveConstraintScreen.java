package PresentationLayer.GUI.MemberScreens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

public class RemoveConstraintScreen extends JFrame {
    EmployeeService employeeService;
    ShiftService shiftService;
    BranchService branchService;

    JPanel panel;
    JTextField memberIdField;
    JTextField branchIdField;
    JTextField shiftIdField;
    JLabel memberIdLabel;
    JLabel branchIdLabel;
    JLabel shiftIdLabel;
    JButton removeConstraintButton;
    JButton backButton;

    public RemoveConstraintScreen(ServiceFactory serviceFactory) {
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
        panel.setLayout(new GridLayout(4, 1));

        // Create buttoms
        removeConstraintButton = new JButton("Remove Constraint");
        backButton = new JButton("Back");

        // Set buttoms to disabled
        removeConstraintButton.setEnabled(false);

        // Set button focusability to false
        removeConstraintButton.setFocusable(false);
        backButton.setFocusable(false);

        ImageIcon image = new ImageIcon("dev\\PresentationLayer\\GUI\\super li.png");
        setIconImage(image.getImage());

        // Create the fields
        memberIdField = new JTextField();
        branchIdField = new JTextField();
        shiftIdField = new JTextField();

        Dimension fieldSize = new Dimension(150, 30);

        // Set fields sizes
        memberIdField.setPreferredSize(fieldSize);
        branchIdField.setPreferredSize(fieldSize);
        shiftIdField.setPreferredSize(fieldSize);

        // Add key listener to the fields
        memberIdField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        branchIdField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        shiftIdField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButton();
            }
        });

        // Set fields filters
        ((AbstractDocument) memberIdField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) branchIdField.getDocument()).setDocumentFilter(new IntFilter());
        ((AbstractDocument) shiftIdField.getDocument()).setDocumentFilter(new IntFilter());

        // Create labels
        memberIdLabel = new JLabel("Your ID");
        branchIdLabel = new JLabel("Branch ID");
        shiftIdLabel = new JLabel("Shift ID");

        // Add the fields an the labels to the panel
        panel.add(memberIdLabel);
        panel.add(memberIdField);
        panel.add(branchIdLabel);
        panel.add(branchIdField);
        panel.add(shiftIdLabel);
        panel.add(shiftIdField);

        // Add action listener to the button
        removeConstraintButton.addActionListener((ActionEvent e) -> {
            int memberId = Integer.parseInt(memberIdField.getText());
            int branchId = Integer.parseInt(branchIdField.getText());
            int shiftId = Integer.parseInt(shiftIdField.getText());
            try {
                branchService.removeConstraint(branchId, memberId, shiftId);
                JOptionPane.showMessageDialog(null, "Constraint removed successfully", "Success",
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
        panel.add(removeConstraintButton);
        panel.add(backButton);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public void checkButton() { // watch for key strokes
        if (memberIdLabel.getText().length() == 0 || shiftIdField.getText().length() == 0 || branchIdField.getText().length() == 0)
            removeConstraintButton.setEnabled(false);
        else {
            removeConstraintButton.setEnabled(true);
        }
    }
}
