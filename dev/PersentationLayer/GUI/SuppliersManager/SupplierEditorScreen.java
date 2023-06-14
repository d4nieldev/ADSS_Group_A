package PersentationLayer.GUI.SuppliersManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.stream.Collectors;

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

public class SupplierEditorScreen extends JFrame {
    private SupplierService supplierService;
    private JFrame currentFrame;
    private int y = 0;

    // INITIAL VALUES
    private final Map<String, Object> init_supplierCard;
    private final String init_name;
    private final String init_bankAcc;
    private final List<String> init_fields;
    private final String init_phone;
    private final String init_paymentCondition;
    private final Map<String, String> init_amountToDiscount;
    private final Map<String, String> init_contacts;

    public SupplierEditorScreen(int supplierId) {
        supplierService = SupplierService.create();
        init_supplierCard = supplierService.getSupplierCard(supplierId);
        init_name = (String) init_supplierCard.get("name");
        init_bankAcc = (String) init_supplierCard.get("bankAcc");
        init_fields = (List<String>) init_supplierCard.get("fields");
        init_phone = (String) init_supplierCard.get("phone");
        init_paymentCondition = (String) init_supplierCard.get("paymentCondition");
        init_amountToDiscount = (Map<String, String>) init_supplierCard.get("amountToDiscount");
        init_contacts = (Map<String, String>) init_supplierCard.get("contacts");

        if (init_supplierCard.containsKey("maxSupplyDays")) {
            openOnOrderSupplierWindow();
        } else if (init_supplierCard.containsKey("days")) {
            openFixedDaysSupplierWindow();
        } else if (init_supplierCard.containsKey("address") && init_supplierCard.containsKey("maxPreperationDays")) {
            openSelfPickupSupplierWindow();
        } else {
            // handle invalid
        }
    }

    private void openOnOrderSupplierWindow() {
        final Integer init_maxSupplyDays = (Integer) init_supplierCard.get("maxSupplyDays");
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
        JTextField maxSupplyDaysField = new JTextField(init_maxSupplyDays.toString(), 20);
        fieldsPanel.add(maxSupplyDaysField, constraints);

        JPanel buttonsPanel = createButtonsPanel(onOrderSupplierFrame);

        onOrderSupplierFrame.add(fieldsPanel, BorderLayout.CENTER);
        onOrderSupplierFrame.add(buttonsPanel, BorderLayout.SOUTH);
        onOrderSupplierFrame.pack();
        onOrderSupplierFrame.setLocationRelativeTo(null);
        onOrderSupplierFrame.setVisible(true);
    }

    private void openFixedDaysSupplierWindow() {
        final List<Integer> init_days = (List<Integer>) init_supplierCard.get("days");
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

        JCheckBox[] daysOfWeekCheckBoxes = new JCheckBox[] {
                new JCheckBox("Sunday"),
                new JCheckBox("Monday"),
                new JCheckBox("Tuesday"),
                new JCheckBox("Wednesday"),
                new JCheckBox("Thursday"),
                new JCheckBox("Friday"),
                new JCheckBox("Saturday"),
        };

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < daysOfWeekCheckBoxes.length; i++) {
            JCheckBox dayCheckBox = daysOfWeekCheckBoxes[i];
            if (init_days.contains(1 + (i + 6) % 7))
                dayCheckBox.setSelected(true);
            constraints.gridy = y++;
            fieldsPanel.add(dayCheckBox, constraints);
        }

        JPanel buttonsPanel = createButtonsPanel(fixedDaysSupplierFrame);

        fixedDaysSupplierFrame.add(fieldsPanel, BorderLayout.CENTER);
        fixedDaysSupplierFrame.add(buttonsPanel, BorderLayout.SOUTH);
        fixedDaysSupplierFrame.pack();
        fixedDaysSupplierFrame.setLocationRelativeTo(null);
        fixedDaysSupplierFrame.setVisible(true);
    }

    private void openSelfPickupSupplierWindow() {
        final String init_address = (String) init_supplierCard.get("address");
        final Integer init_maxPreperationDays = (Integer) init_supplierCard.get("maxPreperationDays");

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
        JTextField addressField = new JTextField(init_address, 20);
        fieldsPanel.add(addressField, constraints);

        constraints.gridy = y++;
        constraints.gridx = 0;
        fieldsPanel.add(new JLabel("Max Preparation Days:"), constraints);

        constraints.gridx = 1;
        JTextField maxPreparationDaysField = new JTextField(init_maxPreperationDays.toString(), 20);
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
        JTextField supplierNameField = new JTextField(init_name, 20);
        fieldsPanel.add(supplierNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Supplier Phone:"), constraints);

        constraints.gridx = 1;
        JTextField supplierPhoneField = new JTextField(init_phone, 20);
        fieldsPanel.add(supplierPhoneField, constraints);

        constraints.gridx = 0;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Bank Account:"), constraints);

        constraints.gridx = 1;
        JTextField bankAccountField = new JTextField(init_bankAcc, 20);
        fieldsPanel.add(bankAccountField, constraints);

        constraints.gridx = 0;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Payment Condition:"), constraints);

        constraints.gridx = 1;
        String[] paymentConditions = { "net 30 EOM", "net 60 EOM" };
        JComboBox<String> paymentConditionComboBox = new JComboBox<>(paymentConditions);
        paymentConditionComboBox.setSelectedItem(init_paymentCondition);
        fieldsPanel.add(paymentConditionComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Fields:"), constraints);

        constraints.weighty = 1;

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        List<String[]> fieldsTableData = init_fields.stream().map((field) -> new String[] { field })
                .collect(Collectors.toList());
        JPanel fieldsField = createTablePanel(new String[] { "Field" }, fieldsTableData);
        fieldsPanel.add(fieldsField, constraints);

        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Contacts:"), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        List<String[]> fieldsContactData = init_contacts.keySet().stream()
                .map((phone) -> new String[] { init_contacts.get(phone), phone }).collect(Collectors.toList());
        JPanel contactsTablePanel = createTablePanel(new String[] { "Name", "Phone" }, fieldsContactData);
        fieldsPanel.add(contactsTablePanel, constraints);

        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = y++;
        fieldsPanel.add(new JLabel("Amount To Discount:"), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        List<String[]> amountToDiscountTableData = init_amountToDiscount.keySet().stream()
                .map((amount) -> new String[] { amount, init_amountToDiscount.get(amount) })
                .collect(Collectors.toList());
        JPanel amountToDiscountTablePanel = createTablePanel(new String[] { "Amount", "Discount" },
                amountToDiscountTableData);
        fieldsPanel.add(amountToDiscountTablePanel, constraints);

        return fieldsPanel;

    }

    /**
     * 
     * @param columnNames column names
     * @param data        List of lists that every inner list represents a row in
     *                    the table
     * @return
     */
    private JPanel createTablePanel(String[] columnNames, List<String[]> data) {
        JPanel tablePanel = new JPanel(new BorderLayout());
        // tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the table model with column names
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (String[] rowData : data)
            tableModel.addRow(rowData);

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
