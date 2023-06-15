package PersentationLayer.GUI.SuppliersManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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

    private final String actionType;

    public SupplierEditorScreen(int supplierId) {
        actionType = "Edit";
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

    public SupplierEditorScreen(String supplierType) {
        actionType = "Add";
        supplierService = SupplierService.create();
        init_supplierCard = new HashMap<>();
        init_name = "";
        init_bankAcc = "";
        init_fields = new ArrayList<>();
        init_phone = "";
        init_paymentCondition = "";
        init_amountToDiscount = new HashMap<>();
        init_contacts = new HashMap<>();

        if (supplierType.equals("On Order")) {
            init_supplierCard.put("maxSupplyDays", null);
            openOnOrderSupplierWindow();
        } else if (supplierType.equals("Fixed Days")) {
            init_supplierCard.put("days", new ArrayList<>());
            openFixedDaysSupplierWindow();
        } else if (supplierType.equals("Self Pickup")) {
            init_supplierCard.put("address", "");
            init_supplierCard.put("maxPreperationDays", null);
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
        String maxSupplyDaysInitialString = init_maxSupplyDays == null ? "" : init_maxSupplyDays.toString();
        JTextField maxSupplyDaysField = new JTextField(maxSupplyDaysInitialString, 20);
        fieldsPanel.add(maxSupplyDaysField, constraints);

        JPanel buttonsPanel = createButtonsPanel();

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

        JPanel buttonsPanel = createButtonsPanel();

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
        String maxPreperationDaysInitialString = init_maxPreperationDays == null ? ""
                : init_maxPreperationDays.toString();
        JTextField maxPreparationDaysField = new JTextField(maxPreperationDaysInitialString, 20);
        fieldsPanel.add(maxPreparationDaysField, constraints);

        JPanel buttonsPanel = createButtonsPanel();

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
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Object value = tableModel.getValueAt(i, j);
                colList.add(value == null ? "" : value.toString());
            }
            data.add(colList);
        }

        return data;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        JButton commitButton = new JButton("Commit Changes");
        JButton goBackButton = new JButton("Go Back");

        commitButton.addActionListener((ActionEvent e) -> {
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
            List<String> contactsNames = contacts.get(0);
            List<String> contactsPhones = contacts.get(1);

            JPanel amountToDiscountPanel = (JPanel) fieldsPanel.getComponent(13);
            List<List<String>> amountToDiscountList = getDataFromTablePanel(amountToDiscountPanel);
            List<String> amountsList = amountToDiscountList.get(0);
            List<String> discountsList = amountToDiscountList.get(1);

            TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
            if (!isValidInput(supplierName, supplierPhone, bankAccount, fields, contactsPhones, contactsNames,
                    amountsList, discountsList))
                return;

            for (int i = 0; i < amountsList.size(); i++) {
                Integer amount = Integer.parseInt(amountsList.get(i));
                String discount = discountsList.get(i);
                amountToDiscount.put(amount, discount);
            }

            String response = null;
            switch (supplierType) {
                case "On Order Supplier":
                    String maxSupplyDaysRaw = ((JTextField) fieldsPanel.getComponent(15)).getText();
                    if (!isValidInputOnOrder(maxSupplyDaysRaw))
                        return;

                    int maxSupplyDays = Integer.parseInt(maxSupplyDaysRaw);
                    if (actionType.equals("Add"))
                        response = supplierService.addOnOrderSupplierBaseAgreement(supplierName, supplierPhone,
                                bankAccount, fields, paymentCondition, amountToDiscount, contactsNames, contactsPhones,
                                maxSupplyDays);
                    else if (actionType.equals("Edit")) {

                        // handle edit on order supplier
                    }

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

                    if (actionType.equals("Add"))
                        response = supplierService.addFixedDaysSupplierBaseAgreement(supplierName, supplierPhone,
                                bankAccount, fields, paymentCondition, amountToDiscount, contactsNames, contactsPhones,
                                days);
                    else if (actionType.equals("Edit")) {
                        // handle edit fixed days supplier
                    }
                    break;
                case "Self Pickup Supplier":
                    String maxPreperationDaysRaw = ((JTextField) fieldsPanel.getComponent(17)).getText();
                    String address = ((JTextField) fieldsPanel.getComponent(15)).getText();

                    if (!isValidInputSelfPickup(maxPreperationDaysRaw, address))
                        return;

                    Integer maxPreperationDays = Integer.parseInt(maxPreperationDaysRaw);
                    if (actionType.equals("Add"))
                        response = supplierService.addSelfPickupSupplierBaseAgreement(supplierName, supplierPhone,
                                bankAccount, fields, paymentCondition, amountToDiscount, contactsNames, contactsPhones,
                                address, maxPreperationDays);
                    else if (actionType.equals("Edit")) {
                        // handle edit fixed days supplier
                    }
                    break;
                default:
                    response = "Unknown supplier type";
                    break;
            }

            // Add Supplier button action
            // Implement the desired functionality here
            JOptionPane.showMessageDialog(currentFrame, response);
            currentFrame.dispose(); // Close the current window
        });

        goBackButton.addActionListener((ActionEvent e) -> {
            // Go Back button action
            // Implement the desired functionality here
            currentFrame.dispose(); // Close the current window
        });

        buttonsPanel.add(commitButton);
        buttonsPanel.add(goBackButton);

        return buttonsPanel;
    }

    private boolean isValidInput(String supplierName, String supplierPhone, String bankAccount,
            List<String> fields, List<String> contactPhones, List<String> contactNames, List<String> amountsList,
            List<String> discountsList) {
        if (!supplierName.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(currentFrame, "Name must not be empty and contain only letters");
            return false;
        } else if (!supplierPhone.matches("^05[0-9]{8}$")) {
            JOptionPane.showMessageDialog(currentFrame,
                    "Phone must not be empty and in the format 05... and 8 digits following");
            return false;
        } else if (!bankAccount.matches("^[0-9]{9}$")) {
            JOptionPane.showMessageDialog(currentFrame, "Bank account must not be empty and of 9 digits");
            return false;
        } else if (fields.stream().anyMatch((field) -> field.isBlank())) {
            JOptionPane.showMessageDialog(currentFrame, "All the fields must not be empty");
            return false;
        } else if (contactPhones.size() != contactNames.size()) {
            JOptionPane.showMessageDialog(currentFrame, "Name and phone must be specified for each contact");
            return false;
        } else if (contactNames.stream().anyMatch((contactName) -> contactName.isBlank())) {
            JOptionPane.showMessageDialog(currentFrame, "Contact name must not be empty");
            return false;
        } else if (contactPhones.stream().anyMatch((contactPhone) -> !contactPhone.matches("^05[0-9]{8}$"))) {
            JOptionPane.showMessageDialog(currentFrame,
                    "Contact phone must not be empty and in the format 05... and 8 digits following");
            return false;
        } else if (contactPhones.stream()
                .anyMatch((phoneNumber) -> Collections.frequency(contactPhones, phoneNumber) > 1)) {
            JOptionPane.showMessageDialog(currentFrame, "All contacts phones must be unique");
            return false;
        } else if (amountsList.size() != discountsList.size()) {
            JOptionPane.showMessageDialog(currentFrame,
                    "Amount and discount must be specified for each amount to discount pair");
            return false;
        } else if (amountsList.stream().anyMatch((amount) -> !amount.matches("^[0-9]+$"))) {
            JOptionPane.showMessageDialog(currentFrame,
                    "Amount must not be empty and a non-negative integer");
            return false;
        } else if (discountsList.stream()
                .anyMatch((discount) -> !discount.matches("^[0-9]{1,2}(\\.[0-9]+)?%|100%|[0-9]+(\\.[0-9]+)?$"))) {
            JOptionPane.showMessageDialog(currentFrame,
                    "Discount must not be empty and a non-negative precentage value with '%' at the end (max 100%), or a non-negative fixed number");
            return false;
        }

        return true;
    }

    private boolean isValidInputOnOrder(String maxSupplyDays) {
        if (!maxSupplyDays.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(currentFrame,
                    "Max supply days must be a non-negative number");
            return false;
        }
        return true;
    }

    private boolean isValidInputSelfPickup(String maxPreperationDays, String address) {
        if (!maxPreperationDays.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(currentFrame,
                    "Max preperation days must be a non-negative number");
            return false;
        } else if (address.isBlank()) {
            JOptionPane.showMessageDialog(currentFrame,
                    "Address must not be blank");
            return false;
        }
        return true;
    }
}
