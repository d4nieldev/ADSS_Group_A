package PersentationLayer.GUI.SuppliersManager;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.NumberFormatter;

public class DeleteSupplierScreen extends JFrame {

    public DeleteSupplierScreen(JFrame previousFrame) {
        setTitle("Delete Supplier");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FlowLayout layout = new FlowLayout();
        setLayout(layout);

        JLabel insertIdLabel = new JLabel("Please insert the id of supplier to delete:");
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

        JButton deleteButton = new JButton("Delete");
        add(deleteButton);

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

        deleteButton.addActionListener((ActionEvent e) -> {
            String idString = idInsertField.getText();
            if (!idString.matches("^[0-9]+$")) {
                JOptionPane.showMessageDialog(this, "id must not be empty and a non-negative number", null, 0);
                return;
            }
            dispose();
            new SupplierEditorScreen(Integer.parseInt(idInsertField.getText()), "Delete", this);
        });
    }

}
