package PersentationLayer.GUI.SuppliersManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ServiceLayer.Suppliers.SupplierService;

public class EditSupplierScreen extends JFrame {
    private SupplierService supplierService;
    private JFrame editor;

    public EditSupplierScreen() {
        supplierService = SupplierService.create();
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
