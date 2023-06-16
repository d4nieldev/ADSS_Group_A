package PresentationLayer.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class HRManagerScreen extends JFrame {
    private JButton addEmployeeButton;
    private JButton addDriverButton;
    private JButton addEmptyShift;
    private JButton submitShift;
    private JButton deleteEmployee;

    public HRManagerScreen() {
        setTitle("HR Manager Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        addEmployeeButton = new JButton("Add Employee");
        addDriverButton = new JButton("Add Driver");
        addEmptyShift = new JButton("Add Empty Shift");
        submitShift = new JButton("Submit A Shift");
        deleteEmployee = new JButton("Delete An Employee");

        // Set button sizes
        Dimension buttonSize = new Dimension(150, 30);
        addEmployeeButton.setPreferredSize(buttonSize);
        addDriverButton.setPreferredSize(buttonSize);
        addEmptyShift.setPreferredSize(buttonSize);
        submitShift.setPreferredSize(buttonSize);
        deleteEmployee.setPreferredSize(buttonSize);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(addEmployeeButton);
        add(addDriverButton);
        add(addEmptyShift);
        add(submitShift);
        add(deleteEmployee);

        // Add action listeners to the buttons
        addEmployeeButton.addActionListener((ActionEvent e) -> {
            dispose();
            new AddEmployeeScreen();
        });

        addDriverButton.addActionListener((ActionEvent e) -> {
            // new EditSupplierScreen();
        });

        addEmptyShift.addActionListener((ActionEvent e) -> {
            // Delete supplier button action
            // Implement the desired functionality here
            JOptionPane.showMessageDialog(null, "Delete Supplier button clicked!");
        });

        submitShift.addActionListener((ActionEvent e) -> {
            // View suppliers button action
            // Implement the desired functionality here
            JOptionPane.showMessageDialog(null, "View Suppliers button clicked!");
        });

        deleteEmployee.addActionListener((ActionEvent e) -> {
            // View supplier button action
            // Implement the desired functionality here
            JOptionPane.showMessageDialog(null, "View Supplier button clicked!");
        });

        // Set the frame size and make it visible
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon image = new ImageIcon("dev\\PresentationLayer\\GUI\\MainLogo.png");
        setIconImage(image.getImage());

        // JLabel myLabel = new JLabel(image);
        // myLabel.setSize(400, 200);
        // add(myLabel);

        setVisible(true);
    }
}
