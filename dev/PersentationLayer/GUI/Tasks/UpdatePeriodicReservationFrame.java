package PersentationLayer.GUI.Tasks;

import PersentationLayer.GUI.OrderSupplyFrame;
import ServiceLayer.Suppliers.ReservationService;
import PersentationLayer.GUI.MangeStorageFrame;
import PersentationLayer.GUI.StorekeeperFrame;
import ServiceLayer.inventory.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.*;
import java.util.List;

public class UpdatePeriodicReservationFrame implements ActionListener {

    JFrame frame;
    JPanel productToAmountTabel;
    JLabel label;
    JButton button;
    StorekeeperFrame storekeeperFrame;
    JPanel tasksPanel;
    JButton button1;
    JButton button2;
    JButton button3;
    JTextField supplierIdTextField;
    JTextField branchIdTextField;
    JTextField dayTextField;
    JTextField descriptionTextField;
    JButton submitButton;
    JLabel invalidInputLabel;
    JButton backButton;
    private static ReservationService rs = ReservationService.create();
    ProductService productService = new ProductService();

    public UpdatePeriodicReservationFrame() {
        frame = new JFrame();
        frame.setTitle("Update Periodic Reservation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create the top panel with BorderLayout
        JPanel topPanel = new JPanel(new BorderLayout());

        // Create the back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);

        topPanel.add(backButton, BorderLayout.WEST);

        // Create the label
        JLabel label = new JLabel("Add Periodic Reservation");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16));
        topPanel.add(label, BorderLayout.CENTER);

        // Create the main panel for text boxes
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing around components
        gbc.weightx = 1.0; // Increase the width weight
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create the text boxes with labels
        JLabel supplierIdLabel = new JLabel("Supplier ID");
        supplierIdTextField = new JTextField(20);
        supplierIdTextField.setPreferredSize(new Dimension(250, 25)); // Increase preferred size

        JLabel branchIdLabel = new JLabel("Branch ID");
        branchIdTextField = new JTextField(20);
        branchIdTextField.setPreferredSize(new Dimension(250, 25)); // Increase preferred size

        JLabel dayLabel = new JLabel("Day");
        dayTextField = new JTextField(20);
        dayTextField.setPreferredSize(new Dimension(250, 25)); // Increase preferred size

        JLabel productToAmountLabel = new JLabel("Products and Amount");
        String[] columnNames = { "product", "amount" };
        List<String[]> data = new ArrayList<>();
        productToAmountTabel = createTablePanel(columnNames, data);

        // Add the labels and text fields to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(supplierIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(supplierIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(branchIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(branchIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(dayLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(dayTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        mainPanel.add(productToAmountLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(productToAmountTabel, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Create the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        submitButton.setFont(submitButton.getFont().deriveFont(Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(submitButton, gbc);

        // Create the invalid input label
        invalidInputLabel = new JLabel("Invalid input");
        invalidInputLabel.setForeground(Color.RED);
        invalidInputLabel.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3; // Adjusted gridwidth for invalidInputLabel
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(invalidInputLabel, gbc);

        // Adjust the text box sizes
        supplierIdTextField.setPreferredSize(new Dimension(250, 30));
        branchIdTextField.setPreferredSize(new Dimension(250, 30));
        dayTextField.setPreferredSize(new Dimension(250, 30));

        // Add the top panel and main panel to the frame's content pane
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.dispose();
            OrderSupplyFrame orderSupplyFrame = new OrderSupplyFrame();
        }
        // submit button operation
        if (e.getSource() == submitButton) {
            // Reset the invalid input label
            invalidInputLabel.setVisible(false);

            // Retrieve the values from the text fields
            int supplierId;
            int branchId;
            DayOfWeek day;
            Map<Integer, Integer> productToAmount;

            try {
                supplierId = Integer.parseInt(supplierIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Supplier ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }

            try {
                branchId = Integer.parseInt(branchIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Branch Id is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }

            try {
                day = intToDayOfWeek(Integer.parseInt(dayTextField.getText()));
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Day is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }

            try {
                productToAmount = convertListsToMap(getDataFromTablePanel(productToAmountTabel));
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Product ID or amount is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }

            // Check if description is empty
            if (productToAmount.isEmpty()) {
                invalidInputLabel.setText("The list of products to amount cannot be empty");
                invalidInputLabel.setVisible(true);
                return;
            }

            try {
                rs.updatePeriodicReservation(supplierId, branchId, day, productToAmount);
            } catch (Exception ex) {
                invalidInputLabel.setText(ex.getMessage());
                invalidInputLabel.setVisible(true);
            }
            invalidInputLabel.setText("operation succeed");
            invalidInputLabel.setForeground(Color.green);
            invalidInputLabel.setVisible(true);
            // clear the textBox data
            supplierIdTextField.setText("");
            branchIdTextField.setText("");
            dayTextField.setText("");
        }
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

        // Create the table model with column names
        DefaultTableModel tableModel = new DefaultTableModel(0, columnNames.length);
        tableModel.setColumnIdentifiers(columnNames);

        // Create the table with the model
        JTable contactsTable = new JTable(tableModel);
        JScrollPane contactsScrollPane = new JScrollPane(contactsTable);

        // Populate the table with data
        for (String[] rowData : data) {
            tableModel.addRow(rowData);
        }

        // Create the buttons for adding and deleting rows
        JButton addRowButton = new JButton("Add Row");
        JButton deleteRowButton = new JButton("Delete Row");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addRowButton);
        buttonsPanel.add(deleteRowButton);
        buttonsPanel.setBackground(Color.LIGHT_GRAY);

        // Add the table and buttons to the table panel
        tablePanel.add(contactsScrollPane, BorderLayout.CENTER);
        tablePanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners for the add and delete row buttons
        addRowButton.addActionListener((ActionEvent e) -> {
            tableModel.addRow(new Object[2]);
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
        JViewport vp = scrollPane.getViewport();
        JTable table = (JTable) vp.getView();

        TableModel tableModel = table.getModel();

        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            List<String> rowData = new ArrayList<>();
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                Object value = tableModel.getValueAt(i, j);
                rowData.add(value == null ? "" : value.toString());
            }
            data.add(rowData);
        }

        return data;
    }

    public static Map<Integer, Integer> convertListsToMap(List<List<String>> lists) {
        // if (lists.get(0).size() != lists.get(1).size()) {
        // throw new IllegalArgumentException("Lists must have the same size");
        // }

        Map<Integer, Integer> map = new HashMap<>();
        for (int j = 0; j < lists.size(); j++) {
            int productID = Integer.parseInt(lists.get(j).get(0));
            int amount = Integer.parseInt(lists.get(j).get(1));
            map.put(productID, amount);
        }

        return map;
    }

    private static DayOfWeek intToDayOfWeek(int day) {
        switch (day) {
            case 1:
                return DayOfWeek.SUNDAY;
            case 2:
                return DayOfWeek.MONDAY;
            case 3:
                return DayOfWeek.TUESDAY;
            case 4:
                return DayOfWeek.WEDNESDAY;
            case 5:
                return DayOfWeek.THURSDAY;
            case 6:
                return DayOfWeek.FRIDAY;
            case 7:
                return DayOfWeek.SATURDAY;
            default:
                return null;
        }
    }
}
