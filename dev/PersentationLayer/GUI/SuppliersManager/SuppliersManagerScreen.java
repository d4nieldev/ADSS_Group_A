package PersentationLayer.GUI.SuppliersManager;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SuppliersManagerScreen extends JFrame {
    public SuppliersManagerScreen() {
        setTitle("Suppliers Manager Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton addSupplierButton = new JButton("Add Supplier");
        JButton editSupplierButton = new JButton("Edit Supplier");
        JButton deleteSupplierButton = new JButton("Delete Supplier");
        JButton viewSuppliersButton = new JButton("View Suppliers");
        JButton viewSupplierButton = new JButton("View Supplier");
        JButton editAgreementButton = new JButton("Edit Agreement");

        // Set button sizes
        Dimension buttonSize = new Dimension(150, 30);
        addSupplierButton.setPreferredSize(buttonSize);
        editSupplierButton.setPreferredSize(buttonSize);
        deleteSupplierButton.setPreferredSize(buttonSize);
        viewSuppliersButton.setPreferredSize(buttonSize);
        viewSupplierButton.setPreferredSize(buttonSize);
        editAgreementButton.setPreferredSize(buttonSize);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(addSupplierButton);
        add(editSupplierButton);
        add(deleteSupplierButton);
        add(viewSuppliersButton);
        add(viewSupplierButton);
        add(editAgreementButton);

        // Add action listeners to the buttons
        addSupplierButton.addActionListener((ActionEvent e) -> {
            dispose();
            new AddSupplierScreen(this);
        });

        editSupplierButton.addActionListener((ActionEvent e) -> {
            dispose();
            new EditSupplierScreen(this);
        });

        deleteSupplierButton.addActionListener((ActionEvent e) -> {
            dispose();
            new DeleteSupplierScreen(this);
        });

        viewSuppliersButton.addActionListener((e) -> {
            dispose();
            new ViewSuppliersScreen(this);
            
        });

        viewSupplierButton.addActionListener((e) -> {
            dispose();
            new ViewSupplierScreen(this);
        });

        editAgreementButton.addActionListener((e) -> {
            dispose();
            new EditAgreementScreen();
        });

        // Set the frame size and make it visible
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
