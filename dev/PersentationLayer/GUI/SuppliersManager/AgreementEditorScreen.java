package PersentationLayer.GUI.SuppliersManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ServiceLayer.Suppliers.SupplierService;

public class AgreementEditorScreen extends JFrame {
    private int supplierId;
    private List<JPanel> productAgreementPanels;
    private JPanel containerPanel;
    private SupplierService supplierService;
    private final List<Map<String, Object>> init_productAgreements;

    public AgreementEditorScreen(int supplierId) {
        this.supplierId = supplierId;
        this.productAgreementPanels = new ArrayList<>();
        this.supplierService = SupplierService.create();
        this.init_productAgreements = supplierService.getSupplierProductAgreements(supplierId);

        if (init_productAgreements.size() > 0 && init_productAgreements.get(0).containsKey("error")) {
            JOptionPane.showMessageDialog(this, init_productAgreements.get(0).get("error").toString(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("Supplier " + supplierId + " Agreement Screen");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        List<Integer> productIds = init_productAgreements.stream().map((m) -> ((Integer) m.get("productId")))
                .collect(Collectors.toList());

        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        // Add the panels to the frame
        for (int productId : productIds) {
            JPanel panel = createProductAgreementPanel(productId);
            productAgreementPanels.add(panel);
            containerPanel.add(panel);
        }

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        add(scrollPane);

        add(createButtonsPanel());

        // Make the frame visible
        setVisible(true);
    }

    private Map<String, Object> getProductAgreementData(int productId) {
        for (Map<String, Object> productAgreement : init_productAgreements) {
            if (((Integer) productAgreement.get("productId")) == productId) {
                return productAgreement;
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("supplierId", supplierId);
        map.put("productSupplierId", "");
        map.put("basePrice", "");
        map.put("stockAmount", "");
        map.put("productId", productId);
        map.put("amountToDiscount", new TreeMap<String, String>());
        return map;
    }

    private JPanel createProductAgreementPanel(int productId) {
        Map<String, Object> data = getProductAgreementData(productId);
        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        // Add padding to the panel
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
        Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, paddingBorder);

        panel.setBorder(compoundBorder);
        panel.setPreferredSize(new Dimension(400, 250));
        panel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5); // Added padding between components

        // Product ID label and text field
        JLabel productIdLabel = new JLabel("Product ID:");
        JLabel productActualIdLabel = new JLabel("" + productId);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(productIdLabel, constraints);
        constraints.gridx = 1;
        panel.add(productActualIdLabel, constraints);

        // Base Price label and text field
        JLabel basePriceLabel = new JLabel("Base Price:");
        JTextField basePriceTextField = new JTextField(data.get("basePrice").toString(), 20);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(basePriceLabel, constraints);
        constraints.gridx = 1;
        panel.add(basePriceTextField, constraints);

        // Stock Amount label and text field
        JLabel stockAmountLabel = new JLabel("Stock Amount:");
        JTextField stockAmountTextField = new JTextField(data.get("stockAmount").toString(), 20);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(stockAmountLabel, constraints);
        constraints.gridx = 1;
        panel.add(stockAmountTextField, constraints);

        // Supplier Categorical Number label and text field
        JLabel supplierCategoricalNumberLabel = new JLabel("Supplier Categorical Number:");
        JTextField supplierCategoricalNumberTextField = new JTextField(data.get("productSupplierId").toString(), 20);
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(supplierCategoricalNumberLabel, constraints);
        constraints.gridx = 1;
        panel.add(supplierCategoricalNumberTextField, constraints);

        // Discount table
        JLabel discountLabel = new JLabel("Amount to Discount:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(discountLabel, constraints);

        constraints.gridx = 1;
        constraints.weighty = 1.0;
        String[] columnNames = { "Amount", "Discount" };

        TreeMap<String, String> amountToDiscount = (TreeMap<String, String>) data.get("amountToDiscount");
        List<String[]> amountToDiscountData = amountToDiscount.entrySet().stream()
                .map(entry -> new String[] { entry.getKey(), entry.getValue() }).collect(Collectors.toList());
        JPanel amountToDiscountTable = createTablePanel(columnNames, amountToDiscountData);
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(amountToDiscountTable, constraints);

        JButton deleteAgreementButton = new JButton("Delete Product Agreement");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(deleteAgreementButton);

        deleteAgreementButton.addActionListener((e) -> {
            productAgreementPanels.remove(panel);
            containerPanel.remove(panel);
            revalidate();
        });

        return panel;
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
        buttonsPanel.setBackground(Color.LIGHT_GRAY);

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

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        JButton addAgreementButton = new JButton("Add Product Agreement");
        JButton saveChangesButton = new JButton("Save Changes");
        JButton goBackButton = new JButton("Go Back");

        addAgreementButton.addActionListener((e) -> {
            String productId = showNumberInputDialog("Enter Product Id", "Product Id:");
            if (productId == null)
                return;
            JPanel panel = createProductAgreementPanel(Integer.parseInt(productId));
            productAgreementPanels.add(panel);
            containerPanel.add(panel);
            revalidate();
        });

        saveChangesButton.addActionListener((e) -> {
            // validate all panels
            for (JPanel panel : productAgreementPanels) {
                String basePrice = ((JTextField) panel.getComponent(3)).getText();
                String stockAmount = ((JTextField) panel.getComponent(5)).getText();
                String supplierProductId = ((JTextField) panel.getComponent(7)).getText();

                JPanel amountToDiscountPanel = (JPanel) panel.getComponent(9);
                List<List<String>> amountToDiscountList = getDataFromTablePanel(amountToDiscountPanel);
                List<String> amountsList = amountToDiscountList.get(0);
                List<String> discountsList = amountToDiscountList.get(1);

                if (!isValidData(basePrice, stockAmount, supplierProductId, amountsList, discountsList)) // TODO
                    return;
            }

            // all panels valid => delete the previous agreement
            supplierService.deleteSupplierAgreement(supplierId);

            // update to the new agreement
            for (JPanel panel : productAgreementPanels) {
                System.out.println(panel.getComponent(0));
                String productId = ((JLabel) panel.getComponent(1)).getText();
                String basePrice = ((JTextField) panel.getComponent(3)).getText();
                String stockAmount = ((JTextField) panel.getComponent(5)).getText();
                String supplierProductId = ((JTextField) panel.getComponent(7)).getText();

                JPanel amountToDiscountPanel = (JPanel) panel.getComponent(9);
                List<List<String>> amountToDiscountList = getDataFromTablePanel(amountToDiscountPanel);
                List<String> amountsList = amountToDiscountList.get(0);
                List<String> discountsList = amountToDiscountList.get(1);

                TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
                for (int i = 0; i < amountsList.size(); i++) {
                    Integer amount = Integer.parseInt(amountsList.get(i));
                    String discount = discountsList.get(i);
                    amountToDiscount.put(amount, discount);
                }

                String response = supplierService.addSupplierProductAgreement(supplierId, Integer.parseInt(productId),
                        Integer.parseInt(supplierProductId), Integer.parseInt(stockAmount),
                        Double.parseDouble(basePrice), amountToDiscount);

                if (!response.equals("Supplier product agreement added successfully")) {
                    JOptionPane.showMessageDialog(null, response + ". Reverting...", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    revertChanges();
                    return;
                }
            }

            JOptionPane.showMessageDialog(null, "Changed supplier agreement successfully");
        });

        goBackButton.addActionListener((e) -> {
            dispose(); // Close the current window
        });

        buttonsPanel.add(addAgreementButton);
        buttonsPanel.add(saveChangesButton);
        buttonsPanel.add(goBackButton);

        return buttonsPanel;
    }

    private void revertChanges() {
        supplierService.deleteSupplierAgreement(supplierId);

        for (Map<String, Object> agreement : init_productAgreements) {
            int productSupplierId = (Integer) agreement.get("productSupplierId");
            double basePrice = (Double) agreement.get("basePrice");
            int stockAmount = (Integer) agreement.get("stockAmount");
            int productId = (Integer) agreement.get("productId");
            TreeMap<String, String> amountToDiscount = (TreeMap<String, String>) agreement.get("amountToDiscount");
            TreeMap<Integer, String> myAmountToDiscount = new TreeMap<>();
            for (String amount : amountToDiscount.keySet())
                myAmountToDiscount.put(Integer.parseInt(amount), amountToDiscount.get(amount));

            String response = supplierService.addSupplierProductAgreement(supplierId, productId, productSupplierId,
                    stockAmount,
                    basePrice, myAmountToDiscount);

            if (!response.equals("Supplier product agreement added successfully")) {
                JOptionPane.showMessageDialog(null, response + ". Reverting...", "Error While Reverting Changes",
                        JOptionPane.ERROR_MESSAGE);
                revertChanges();
                return;
            }
        }
    }

    private boolean isValidData(String basePrice, String stockAmount, String supplierProductId,
            List<String> amountsList, List<String> discountsList) {
        if (!basePrice.matches("^[0-9]+(.[0-9]+)?$")) {
            JOptionPane.showMessageDialog(null,
                    "Base price must not be empty and a non-negative number", "Validation Warning", 2);
            return false;
        } else if (!stockAmount.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(null,
                    "Stock amount must not be empty and a non-negative number", "Validation Warning", 2);
            return false;
        } else if (!supplierProductId.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(null,
                    "Supplier categorical number must not be empty and a non-negative number", "Validation Warning", 2);
            return false;
        } else if (amountsList.size() != discountsList.size()) {
            JOptionPane.showMessageDialog(null,
                    "Amount and discount must be specified for each amount to discount pair", "Validation Warning", 2);
            return false;
        } else if (amountsList.stream().anyMatch((amount) -> !amount.matches("^[0-9]+$"))) {
            JOptionPane.showMessageDialog(null,
                    "Amount must not be empty and a non-negative integer", "Validation Warning", 2);
            return false;
        } else if (discountsList.stream()
                .anyMatch((discount) -> !discount.matches("^[0-9]{1,2}(\\.[0-9]+)?%|100%|[0-9]+(\\.[0-9]+)?$"))) {
            JOptionPane.showMessageDialog(null,
                    "Discount must not be empty and a non-negative precentage value with '%' at the end (max 100%), or a non-negative fixed number",
                    "Validation Warning", 2);
            return false;
        }

        return true;
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

    private String showNumberInputDialog(String message, String title) {
        JTextField textField = new JTextField();

        int result = JOptionPane.showConfirmDialog(null, textField, message, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String text = textField.getText();
            if (!text.matches("^[0-9]+$")) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return showNumberInputDialog(message, title); // Show the dialog again recursively
            } else if (productAgreementPanels.stream()
                    .anyMatch((panel) -> ((JLabel) panel.getComponent(1)).getText().equals(text))) {
                JOptionPane.showMessageDialog(null,
                        "Cannot add agreement for id " + text + " because it already exists",
                        "Product Agreement Already Exists",
                        JOptionPane.ERROR_MESSAGE);
                return showNumberInputDialog(message, title); // Show the dialog again recursively
            }
            return text;
        }

        return null; // User canceled or closed the dialog
    }
}
