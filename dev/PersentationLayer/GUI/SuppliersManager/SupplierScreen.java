package PersentationLayer.GUI.SuppliersManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import javax.swing.table.DefaultTableModel;

import ServiceLayer.Suppliers.SupplierService;

public class SupplierScreen extends JFrame {
    private int y = 0;
    private SupplierService service;
    private JFrame previousFrame;
    public SupplierScreen(String supplierType, JFrame previousFrame, int supplierId){ // if supplier id is -1 then no need to pull data.
        JFrame SupplierFrame = new JFrame(supplierType);
        this.service = SupplierService.create();
        this.previousFrame = previousFrame;
        SupplierFrame.setPreferredSize(new Dimension(800, 800));
        SupplierFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        SupplierFrame.setLayout(new BorderLayout());
        JPanel fieldsPanel = createFieldsPanel(supplierType);
        if(supplierId > 0){
            Map<String, Object> dataMap = service.getSupplierCard(supplierId);
            loadData(fieldsPanel, dataMap);
        }
        
        

        //JPanel buttonsPanel = createButtonsPanel(onOrderSupplierFrame);

        SupplierFrame.add(fieldsPanel, BorderLayout.CENTER);
        //onOrderSupplierFrame.add(buttonsPanel, BorderLayout.SOUTH);
        SupplierFrame.pack();
        SupplierFrame.setLocationRelativeTo(null);
        SupplierFrame.setVisible(true);
    }

    private void errorOccured(String errorMsg){
        JOptionPane.showMessageDialog(this, errorMsg);
        dispose();
        previousFrame.setVisible(true);
    }

    private void loadData(JPanel fieldsPanel, Map<String, Object> dataMap){
        //getAllDataFirst
        if(dataMap.get("error") != null){
            errorOccured((String)(dataMap.get("error")));
        }
        String supplierName = (String) (dataMap.get("name"));
        String supplierPhone = (String) (dataMap.get("phone"));
        String supplierBankAcc = (String) (dataMap.get("bankAcc"));
        List<String> fields = (List<String>) (dataMap.get("fields"));
        String paymentCondition = (String) (dataMap.get("paymentCondition"));
        Map<Integer,String> amountToDiscount = (Map<Integer, String>) (dataMap.get("amountToDiscount"));
        Map<String,String> contacts = (Map<String, String>) (dataMap.get("contacts"));
        if(dataMap.containsKey("maxSupplyDays")){
            //The supplier is of type onOrderSupplier
            Integer maxSupplyDays = (Integer) (dataMap.get("maxSupplyDays"));
            
        }else if(dataMap.containsKey("days")){
            //The supplier is of type fixedDaysSupplier
            List<Integer> days = (List<Integer>) (dataMap.get("days"));
        }else{
            //The supplier is of type selfPickupSupplier
            String address = (String) (dataMap.get("address"));
            Integer maxPreperationDays = (Integer) (dataMap.get("maxPreperationDays"));
        }
        //Now imply the data into the components of the panel.
        String supplierName = ((JTextField) fieldsPanel.getComponent(1)).getText();
        String supplierPhone = ((JTextField) fieldsPanel.getComponent(3)).getText();
        String bankAccount = ((JTextField) fieldsPanel.getComponent(5)).getText();
        String paymentCondition = ((JComboBox<String>) fieldsPanel.getComponent(7)).getSelectedItem().toString();

        JPanel fieldsJPanel = (JPanel) fieldsPanel.getComponent(9);
        List<String> fields = getDataFromTablePanel(fieldsJPanel).get(0);

        JPanel contactsPanel = (JPanel) fieldsPanel.getComponent(11);
        List<List<String>> contacts = getDataFromTablePanel(contactsPanel);
        List<String> contactsPhones = contacts.get(0);
        List<String> contactsNames = contacts.get(1);

        JPanel amountToDiscountPanel = (JPanel) fieldsPanel.getComponent(13);
        List<List<String>> amountToDiscountList = getDataFromTablePanel(amountToDiscountPanel);
        List<String> amountsList = amountToDiscountList.get(0);
        List<String> discountsList = amountToDiscountList.get(1);

        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        for (int i = 0; i < amountsList.size(); i++) {
            Integer amount = Integer.parseInt(amountsList.get(i));
            String discount = discountsList.get(i);
            amountToDiscount.put(amount, discount);
        }
    } 
    private JPanel createFieldsPanel(String supplierType) {
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = y++;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(5, 5, 5, 5);

        fieldsPanel.add(new JLabel("Supplier Name:"), constraints);

        constraints.gridx = 1;
        JTextField supplierNameField = new JTextField(20);
        fieldsPanel.add(supplierNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Supplier Phone:"), constraints);

        constraints.gridx = 1;
        JTextField supplierPhoneField = new JTextField(20);
        fieldsPanel.add(supplierPhoneField, constraints);

        constraints.gridx = 0;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Bank Account:"), constraints);

        constraints.gridx = 1;
        JTextField bankAccountField = new JTextField(20);
        fieldsPanel.add(bankAccountField, constraints);

        constraints.gridx = 0;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Payment Condition:"), constraints);

        constraints.gridx = 1;
        String[] paymentConditions = { "net 30 EOM", "net 60 EOM" };
        JComboBox<String> paymentConditionComboBox = new JComboBox<>(paymentConditions);
        fieldsPanel.add(paymentConditionComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Fields:"), constraints);

        constraints.weighty = 1;

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        JPanel fieldsField = createTablePanel(new String[] { "Field" });
        fieldsPanel.add(fieldsField, constraints);

        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Contacts:"), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        JPanel contactsTablePanel = createTablePanel(new String[] { "Name", "Phone" });
        fieldsPanel.add(contactsTablePanel, constraints);

        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Amount To Discount:"), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        JPanel amountToDiscountTablePanel = createTablePanel(new String[] { "Amount", "Discount" });
        fieldsPanel.add(amountToDiscountTablePanel, constraints);
        switch (supplierType) {
            case "On Order Supplier":
                break;
            case "Fixed Days Supplier":
                break;
            case "Self Pickup Supplier":
        }
        return fieldsPanel;
    }
        
    

    private JPanel createTablePanel(String[] columnNames) {
        JPanel tablePanel = new JPanel(new BorderLayout());
        // tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the table model with column names
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Create the table with the model
        JTable contactsTable = new JTable(tableModel);
        JScrollPane contactsScrollPane = new JScrollPane(contactsTable);

        // Create the buttons for adding and deleting rows
        JButton addRowButton = new JButton("Add Row");
        JButton deleteRowButton = new JButton("Delete Row");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addRowButton);
        buttonsPanel.add(deleteRowButton);

        // Add the table and buttons to the fields panel
        tablePanel.add(contactsScrollPane, BorderLayout.CENTER);
        tablePanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners for the add and delete row buttons
        addRowButton.addActionListener((ActionEvent e) -> {
            tableModel.addRow(new Vector<>());
        });

        deleteRowButton.addActionListener((ActionEvent e) -> {
            int selectedRow = contactsTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            }
        });

        return tablePanel;
    }
}

