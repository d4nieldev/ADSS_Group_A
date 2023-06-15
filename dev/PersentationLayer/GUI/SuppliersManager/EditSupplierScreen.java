package PersentationLayer.GUI.SuppliersManager;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ServiceLayer.Suppliers.SupplierService;

public class EditSupplierScreen extends JFrame {
    public EditSupplierScreen() {
        setTitle("Edit Supplier");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField idField = new JTextField(20);
        JButton gotoEditScreenButton = new JButton("Edit");
        JButton goBackButton = new JButton("Go back");

        Dimension buttonSize = new Dimension(200, 30);
        gotoEditScreenButton.setPreferredSize(buttonSize);
        goBackButton.setPreferredSize(buttonSize);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(idField);
        add(gotoEditScreenButton);
        add(goBackButton);

        gotoEditScreenButton.addActionListener((ActionEvent e) -> {
            String idString = idField.getText();
            if (!idString.matches("^[0-9]+$")) {
                JOptionPane.showMessageDialog(this, "id must not be empty and a non-negative number", null, 0);
                return;
            }
            new SupplierEditorScreen(Integer.parseInt(idField.getText()), "Edit", this);
        });

        goBackButton.addActionListener((ActionEvent e) -> {
            dispose(); // Close the current window
        });

        // Set the frame size and make it visible
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
