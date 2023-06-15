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
    private JFrame currentFrame;
    private static int y = 0;
    private SupplierService supplierService;

    public AddSupplierScreen() {
        supplierService = SupplierService.create();
        setTitle("Add Supplier");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
                openOnOrderSupplierWindow();
            }
        });

        fixedDaysSupplierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Fixed Days Supplier button action
                openFixedDaysSupplierWindow();
            }
        });

        selfPickupSupplierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Self Pickup Supplier button action
                openSelfPickupSupplierWindow();
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

    private void openOnOrderSupplierWindow() {
        JFrame onOrderSupplierFrame = new JFrame("On Order Supplier");
        this.currentFrame = onOrderSupplierFrame;
        onOrderSupplierFrame.setPreferredSize(new Dimension(800, 800));
        onOrderSupplierFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        onOrderSupplierFrame.setLayout(new BorderLayout());

        JPanel fieldsPanel = createFieldsPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = y++;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(5, 5, 5, 5);

        fieldsPanel.add(new JLabel("Max Supply Days:"), constraints);

        constraints.gridx = 1;
        JTextField maxSupplyDaysField = new JTextField(10);
        fieldsPanel.add(maxSupplyDaysField, constraints);

        JPanel buttonsPanel = createButtonsPanel(onOrderSupplierFrame);

        onOrderSupplierFrame.add(fieldsPanel, BorderLayout.CENTER);
        onOrderSupplierFrame.add(buttonsPanel, BorderLayout.SOUTH);
        onOrderSupplierFrame.pack();
        onOrderSupplierFrame.setLocationRelativeTo(null);
        onOrderSupplierFrame.setVisible(true);
    }

    private void openFixedDaysSupplierWindow() {
        JFrame fixedDaysSupplierFrame = new JFrame("Fixed Days Supplier");
        this.currentFrame = fixedDaysSupplierFrame;
        fixedDaysSupplierFrame.setPreferredSize(new Dimension(800, 800));
        fixedDaysSupplierFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fixedDaysSupplierFrame.setLayout(new BorderLayout());

        JPanel fieldsPanel = createFieldsPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = y++;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(5, 5, 5, 5);

        fieldsPanel.add(new JLabel("Supply Days:"), constraints);

        JCheckBox sundayCheckBox = new JCheckBox("Sunday");
        JCheckBox mondayCheckBox = new JCheckBox("Monday");
        JCheckBox tuesdayCheckBox = new JCheckBox("Tuesday");
        JCheckBox wednesdayCheckBox = new JCheckBox("Wednesday");
        JCheckBox thursdayCheckBox = new JCheckBox("Thursday");
        JCheckBox fridayCheckBox = new JCheckBox("Friday");
        JCheckBox saturdayCheckBox = new JCheckBox("Saturday");

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = y++;
        fieldsPanel.add(sundayCheckBox, constraints);
        constraints.gridy = y++;
        fieldsPanel.add(mondayCheckBox, constraints);
        constraints.gridy = y++;
        fieldsPanel.add(tuesdayCheckBox, constraints);
        constraints.gridy = y++;
        fieldsPanel.add(wednesdayCheckBox, constraints);
        constraints.gridy = y++;
        fieldsPanel.add(thursdayCheckBox, constraints);
        constraints.gridy = y++;
        fieldsPanel.add(fridayCheckBox, constraints);
        constraints.gridy = y++;
        fieldsPanel.add(saturdayCheckBox, constraints);
        constraints.gridy = y++;

        JPanel buttonsPanel = createButtonsPanel(fixedDaysSupplierFrame);

        fixedDaysSupplierFrame.add(fieldsPanel, BorderLayout.CENTER);
        fixedDaysSupplierFrame.add(buttonsPanel, BorderLayout.SOUTH);
        fixedDaysSupplierFrame.pack();
        fixedDaysSupplierFrame.setLocationRelativeTo(null);
        fixedDaysSupplierFrame.setVisible(true);
    }

    private void openSelfPickupSupplierWindow() {
        JFrame selfPickupSupplierFrame = new JFrame("Self Pickup Supplier");
        this.currentFrame = selfPickupSupplierFrame;
        selfPickupSupplierFrame.setPreferredSize(new Dimension(800, 800));
        selfPickupSupplierFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selfPickupSupplierFrame.setLayout(new BorderLayout());

        JPanel fieldsPanel = createFieldsPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = y++;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(5, 5, 5, 5);

        fieldsPanel.add(new JLabel("Address:"), constraints);

        constraints.gridx = 1;
        JTextField addressField = new JTextField(20);
        fieldsPanel.add(addressField, constraints);

        constraints.gridy = y++;
        constraints.gridx = 0;
        fieldsPanel.add(new JLabel("Max Preparation Days:"), constraints);

        constraints.gridx = 1;
        JTextField maxPreparationDaysField = new JTextField(10);
        fieldsPanel.add(maxPreparationDaysField, constraints);

        JPanel buttonsPanel = createButtonsPanel(selfPickupSupplierFrame);

        selfPickupSupplierFrame.add(fieldsPanel, BorderLayout.CENTER);
        selfPickupSupplierFrame.add(buttonsPanel, BorderLayout.SOUTH);
        selfPickupSupplierFrame.pack();
        selfPickupSupplierFrame.setLocationRelativeTo(null);
        selfPickupSupplierFrame.setVisible(true);
    }

    private JPanel createFieldsPanel() {
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

    private List<List<String>> getDataFromTablePanel(JPanel tablePanel) {
        JScrollPane scrollPane = (JScrollPane) tablePanel.getComponent(0);
        JViewport vp = (JViewport) scrollPane.getComponent(0);
        TableModel tableModel = ((JTable) vp.getComponent(0)).getModel();

        List<List<String>> data = new ArrayList<>();
        for (int j = 0; j < tableModel.getColumnCount(); j++) {
            List<String> colList = new ArrayList<>();
            for (int i = 0; i < tableModel.getRowCount(); i++)
                colList.add(tableModel.getValueAt(i, j).toString());
            data.add(colList);
        }

        return data;
    }

    private JPanel createButtonsPanel(JFrame frame) {
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add Supplier");
        JButton goBackButton = new JButton("Go Back");

        addButton.addActionListener((ActionEvent e) -> {
            String supplierType = currentFrame.getTitle();

            JPanel fieldsPanel = (JPanel) currentFrame.getContentPane().getComponent(0);
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

            System.out.println(supplierType);
            System.out.println(supplierName);
            System.out.println(supplierPhone);
            System.out.println(bankAccount);
            System.out.println(paymentCondition);
            System.out.println(contactsPhones);
            System.out.println(contactsNames);
            System.out.println(amountToDiscount);

            String response = null;
            switch (supplierType) {
                case "On Order Supplier":
                    Integer maxSupplyDays = Integer.parseInt(((JTextField) fieldsPanel.getComponent(15)).getText());
                    response = supplierService.addOnOrderSupplierBaseAgreement(supplierName, supplierPhone, bankAccount,
                            fields, paymentCondition, amountToDiscount, contactsNames, contactsPhones, maxSupplyDays);

                    break;
                case "Fixed Days Supplier":
                    boolean[] isDay = new boolean[] {
                            ((JCheckBox) fieldsPanel.getComponent(15)).isSelected(),
                            ((JCheckBox) fieldsPanel.getComponent(16)).isSelected(),
                            ((JCheckBox) fieldsPanel.getComponent(17)).isSelected(),
                            ((JCheckBox) fieldsPanel.getComponent(18)).isSelected(),
                            ((JCheckBox) fieldsPanel.getComponent(19)).isSelected(),
                            ((JCheckBox) fieldsPanel.getComponent(20)).isSelected(),
                            ((JCheckBox) fieldsPanel.getComponent(21)).isSelected()
                    };
                    List<Integer> days = new ArrayList<>();
                    for (int i = 0; i < isDay.length; i++)
                        if (isDay[i])
                            days.add(i + 1);
                    response = supplierService.addFixedDaysSupplierBaseAgreement(supplierName, supplierPhone,
                            bankAccount, fields, paymentCondition, amountToDiscount, contactsNames, contactsPhones,
                            days);
                    break;
                case "Self Pickup Supplier":
                    String address = ((JTextField) fieldsPanel.getComponent(15)).getText();
                    Integer maxPreperationDays = Integer
                            .parseInt(((JTextField) fieldsPanel.getComponent(17)).getText());
                    response = supplierService.addSelfPickupSupplierBaseAgreement(supplierName, supplierPhone,
                            bankAccount, fields, paymentCondition, amountToDiscount, contactsNames, contactsPhones,
                            address, maxPreperationDays);
                    break;
                default:
                    response = "Unknown supplier type";
                    break;
            }

            // Add Supplier button action
            // Implement the desired functionality here
            JOptionPane.showMessageDialog(frame, response);
            frame.dispose(); // Close the current window
        });

        goBackButton.addActionListener((ActionEvent e) -> {
            // Go Back button action
            // Implement the desired functionality here
            frame.dispose(); // Close the current window
        });

        buttonsPanel.add(addButton);
        buttonsPanel.add(goBackButton);

        return buttonsPanel;
    }
}