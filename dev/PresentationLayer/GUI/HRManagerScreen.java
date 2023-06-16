package PresentationLayer.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class HRManagerScreen extends JFrame{
    private JButton addEmployeeButton;
    private JButton addDriverButton;
    private JButton addEmptyShift;
    private JButton submitShift;
    private JButton deleteEmployee;

    String horizontalLine = "+-------------------------------------------------------+";
    String option1 = "| 0 - Go back                                                  |";
    String option2 = "| 1 - Add employee (not driver).                               |";
    String option3 = "| 2 - Add driver.                                              |";
    String option4 = "| 3 - Print all branches.                                      |";
    String option5 = "| 4 - Print all employees (drivers not included).              |";
    String option6 = "| 5 - Print all drivers.                                       |";
    String option7 = "| 6 - Add empty shift.                                         |";
    String option8 = "| 7 - Submit a shift.                                          |";
    String option9 = "| 8 - Add constraint for some Employee to Shift.               |";
    String option10 = "| 9 - Edit employee.                                          |";
    String option11 = "| 10 - Delete an employee.                                    |";
    String bottomLine = "+-----------------------------------------------------------+";

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
            //new AddSupplierScreen();
        });

        addDriverButton.addActionListener((ActionEvent e) -> {
            //new EditSupplierScreen();
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
        setVisible(true);
    }
}
