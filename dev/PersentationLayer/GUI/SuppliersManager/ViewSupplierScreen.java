package PersentationLayer.GUI.SuppliersManager;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ViewSupplierScreen extends JFrame {
    public ViewSupplierScreen(JFrame previousFrame) {
        setTitle("View Supplier");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FlowLayout layout = new FlowLayout();
        setLayout(layout);

        JLabel insertIdLabel = new JLabel("Please insert the id of supplier to view:");
        add(insertIdLabel);

        // Format to accpet only integers in field
        // NumberFormat numberFormat = NumberFormat.getNumberInstance();
        // NumberFormatter formatter = new NumberFormatter(numberFormat);
        // formatter.setMinimum(0); // Minimum id is 0
        // formatter.setAllowsInvalid(false);
        // JFormattedTextField idInsertField = new JFormattedTextField(formatter);
        JTextField idInsertField = new JTextField();
        idInsertField.setColumns(10);
        add(idInsertField);

        JButton viewButton = new JButton("View");
        add(viewButton);

        JButton back = new JButton("Go Back");
        add(back);

        // Set the frame size and make it visible
        setSize(400, 150);
        setVisible(true);
        setLocationRelativeTo(previousFrame);

        back.addActionListener((ActionEvent e) -> {
            dispose();
            previousFrame.setVisible(true);
        });

        viewButton.addActionListener((ActionEvent e) -> {
            String idString = idInsertField.getText();
            if (!idString.matches("^[0-9]+$")) {
                JOptionPane.showMessageDialog(this, "id must not be empty and a non-negative number", null, 0);
                return;
            }
            dispose();
            new SupplierEditorScreen(Integer.parseInt(idInsertField.getText()), "View", this);
        });
    }
}
