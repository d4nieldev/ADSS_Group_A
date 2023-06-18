package PersentationLayer.GUI.SuppliersManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditAgreementScreen extends JFrame {
    public EditAgreementScreen() {
        setTitle("Supplier & Product Screen");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 40)); // Added padding between cells

        // Supplier ID label and text field
        JLabel supplierIdLabel = new JLabel("Supplier ID:");
        JTextField supplierIdTextField = new JTextField();
        panel.add(supplierIdLabel);
        panel.add(supplierIdTextField);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener((e) -> {
            // Perform action on submit button click
            String supplierId = supplierIdTextField.getText();
            if (!supplierId.matches("^[0-9]+$")) {
                JOptionPane.showMessageDialog(this, "Supplier id must not be empty and a non-negative number", null, 2);
                return;
            }

            new AgreementEditorScreen(Integer.parseInt(supplierId));
        });
        panel.add(submitButton);

        // Go Back button
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener((e) -> {
            dispose();
        });
        panel.add(goBackButton);

        // Add padding to the panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Added padding around the panel

        // Add the panel to the frame
        add(panel);

        // Make the frame visible
        setVisible(true);
    }
}
