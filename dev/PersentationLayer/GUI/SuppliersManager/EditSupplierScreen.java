package PersentationLayer.GUI.SuppliersManager;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import ServiceLayer.Suppliers.SupplierService;

public class EditSupplierScreen extends JFrame {
    public EditSupplierScreen() {
        setTitle("Edit Supplier");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField idField = new JTextField(20);
        JButton gotoEditScreenButton = new JButton("Edit");

        Dimension buttonSize = new Dimension(200, 30);
        gotoEditScreenButton.setPreferredSize(buttonSize);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(idField);
        add(gotoEditScreenButton);

        gotoEditScreenButton.addActionListener((ActionEvent e) -> {
            new SupplierEditorScreen(Integer.parseInt(idField.getText()));
        });

        // Set the frame size and make it visible
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
