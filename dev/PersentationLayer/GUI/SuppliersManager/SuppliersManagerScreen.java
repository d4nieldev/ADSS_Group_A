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
    private JButton addSupplierButton;
    private JButton editSupplierButton;
    private JButton deleteSupplierButton;
    private JButton viewSuppliersButton;
    private JButton viewSupplierButton;

    public SuppliersManagerScreen() {
        setTitle("Suppliers Manager Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        addSupplierButton = new JButton("Add Supplier");
        editSupplierButton = new JButton("Edit Supplier");
        deleteSupplierButton = new JButton("Delete Supplier");
        viewSuppliersButton = new JButton("View Suppliers");
        viewSupplierButton = new JButton("View Supplier");

        // Set button sizes
        Dimension buttonSize = new Dimension(150, 30);
        addSupplierButton.setPreferredSize(buttonSize);
        editSupplierButton.setPreferredSize(buttonSize);
        deleteSupplierButton.setPreferredSize(buttonSize);
        viewSuppliersButton.setPreferredSize(buttonSize);
        viewSupplierButton.setPreferredSize(buttonSize);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(addSupplierButton);
        add(editSupplierButton);
        add(deleteSupplierButton);
        add(viewSuppliersButton);
        add(viewSupplierButton);

        // Add action listeners to the buttons
        addSupplierButton.addActionListener((ActionEvent e) -> {
            dispose();
            new AddSupplierScreen();
        });

        editSupplierButton.addActionListener((ActionEvent e) -> {
            new EditSupplierScreen();
        });

        deleteSupplierButton.addActionListener((ActionEvent e) -> {
            dispose();
            new DeleteSupplierScreen(this);
        });

        viewSuppliersButton.addActionListener((ActionEvent e) -> {
            // View suppliers button action
            // Implement the desired functionality here
            JOptionPane.showMessageDialog(null, "View Suppliers button clicked!");
        });

        viewSupplierButton.addActionListener((ActionEvent e) -> {
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
