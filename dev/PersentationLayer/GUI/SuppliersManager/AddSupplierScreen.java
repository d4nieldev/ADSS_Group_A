package PersentationLayer.GUI.SuppliersManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ServiceLayer.Suppliers.SupplierService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

public class AddSupplierScreen extends JFrame {
    private SupplierEditorScreen currentFrame;
    private int y = 0;
    private SupplierService supplierService;

    public AddSupplierScreen() {
        supplierService = SupplierService.create();
        setTitle("Add Supplier");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton onOrderSupplierButton = new JButton("On Order Supplier");
        JButton fixedDaysSupplierButton = new JButton("Fixed Days Supplier");
        JButton selfPickupSupplierButton = new JButton("Self Pickup Supplier");
        JButton goBackButton = new JButton("Go Back");

        // Set button sizes
        Dimension buttonSize = new Dimension(200, 30);
        onOrderSupplierButton.setPreferredSize(buttonSize);
        fixedDaysSupplierButton.setPreferredSize(buttonSize);
        selfPickupSupplierButton.setPreferredSize(buttonSize);
        goBackButton.setPreferredSize(buttonSize);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(onOrderSupplierButton);
        add(fixedDaysSupplierButton);
        add(selfPickupSupplierButton);
        add(goBackButton);

        // Add action listeners to the buttons
        onOrderSupplierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // On Order Supplier button action
                new SupplierEditorScreen("On Order");
            }
        });

        fixedDaysSupplierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Fixed Days Supplier button action
                new SupplierEditorScreen("Fixed Days");
            }
        });

        selfPickupSupplierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Self Pickup Supplier button action
                new SupplierEditorScreen("Self Pickup");
            }
        });

        goBackButton.addActionListener((ActionEvent e) -> {
            // Go Back button action
            // Implement the desired functionality here
            dispose(); // Close the current window
        });

        // Set the frame size and make it visible
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}