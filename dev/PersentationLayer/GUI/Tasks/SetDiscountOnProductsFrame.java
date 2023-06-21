package PersentationLayer.GUI.Tasks;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.Inventory.Global;
import BusinessLayer.InveontorySuppliers.DiscountFixed;
import BusinessLayer.InveontorySuppliers.DiscountPercentage;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import DataAccessLayer.DTOs.DiscountDTO;
import PersentationLayer.GUI.MangeStorageFrame;
import PersentationLayer.GUI.StorekeeperFrame;
import ServiceLayer.InventorySuppliers.BranchService;
import ServiceLayer.inventory.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class SetDiscountOnProductsFrame implements ActionListener {
    JFrame frame;
    JLabel label;
    JButton button;
    StorekeeperFrame storekeeperFrame;
    JPanel tasksPanel;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton submitButton;
    JTextField branchIdTextField;
    JTextField discountValueTextField;
    JTextField discountStartTextField;
    JTextField discountEndTextField;
    JCheckBox checkBox;

    JLabel invalidInputLabel;
    JButton backButton;
    BranchService branchService = new BranchService();
    JComboBox<String> discountTypeComboBox; // Added ComboBox
    List<Product> productList;
    List<JCheckBox> checkBoxList;
    ProductService productService = new ProductService();

    public SetDiscountOnProductsFrame() {
        frame = new JFrame();
        frame.setTitle("Set Discount by Products");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 550);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background color

        // Create the top panel with BorderLayout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(59, 89, 152)); // Blue color

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(0, 122, 255)); // Blue color
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false); // Remove button border
        backButton.setFocusPainted(false); // Remove button focus border
        topPanel.add(backButton, BorderLayout.WEST);

        // Create the label
        JLabel label = new JLabel("Set Discount by Products");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        topPanel.add(label, BorderLayout.CENTER);

        // Create the main panel for text boxes
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing around components

        // Create the text boxes with labels
        JLabel branchIdLabel = new JLabel("Branch ID");
        branchIdTextField = new JTextField(20);

        JLabel discountValueLabel = new JLabel("Discount Value");
        discountValueTextField = new JTextField(20);

        JLabel discountStartLabel = new JLabel("Discount Start Date");
        discountStartTextField = new JTextField(20);
        discountStartTextField.setText("2024-11-11");

        JLabel discountEndLabel = new JLabel("Discount End Date");
        discountEndTextField = new JTextField(20);
        discountEndTextField.setText("2024-11-11");

        // Create the discount type combo box
        JLabel discountTypeLabel = new JLabel("Discount Type");
        discountTypeComboBox = new JComboBox<>(new String[] { "Discount Percentage", "Discount Fixed" });

        try {
            productList = ProductController.getInstance().getAllProducts();
        } catch (SQLException exception) {
            invalidInputLabel.setText(exception.getMessage());
            invalidInputLabel.setVisible(true);
        }

        // Create the desired categories label
        JLabel desiredCategoriesLabel = new JLabel("Desired Products");

        // Create a panel to hold the checkbox list
        JPanel categoriesPanel = new JPanel(new GridLayout(0, 1)); // Use GridLayout for vertical layout

        // Create checkboxes for each category
        checkBoxList = new ArrayList<>(); // Store checkboxes in a list
        for (Product product : productList) {
            String checkboxText = product.getId() + " - " + product.getName();
            JCheckBox checkBox = new JCheckBox(checkboxText);
            checkBoxList.add(checkBox); // Add the checkbox to the list
            categoriesPanel.add(checkBox);
        }

        // Add the labels and text fields to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(branchIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(branchIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(discountValueLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(discountValueTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(discountStartLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(discountStartTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(discountEndLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(discountEndTextField, gbc);

        // Add the desired categories label and checkbox list panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(desiredCategoriesLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPanel.add(new JScrollPane(categoriesPanel), gbc);

        // Add the discount type label and combo box to the main panel
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(discountTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(discountTypeComboBox, gbc);

        // Create the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size for smaller button
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_END; // Align the button to the right
        gbc.insets = new Insets(10, 0, 0, 10); // Adjust insets for spacing
        mainPanel.add(submitButton, gbc);

        // Create the invalid input label
        invalidInputLabel = new JLabel("Invalid input");
        invalidInputLabel.setForeground(Color.RED);
        invalidInputLabel.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(invalidInputLabel, gbc);

        // Add the top panel and main panel to the frame's content pane
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // back to tasks view window
        if (e.getSource() == backButton) {
            frame.dispose();
            MangeStorageFrame mangeStorageFrame = new MangeStorageFrame();
        }

        if (e.getSource() == submitButton) {
            String branchIdText = branchIdTextField.getText().trim();
            String discountValueText = discountValueTextField.getText().trim();
            String discountStart = discountStartTextField.getText().trim();
            String discountEnd = discountEndTextField.getText().trim();
            String selectedDiscountType = (String) discountTypeComboBox.getSelectedItem();

            List<Integer> selectedProductsIds = new ArrayList<>();

            // Iterate over the checkbox list
            for (JCheckBox checkBox : checkBoxList) {
                if (checkBox.isSelected()) {
                    // Extract the category ID from the checkbox label
                    String checkboxText = checkBox.getText();
                    int categoryId = Integer.parseInt(checkboxText.split(" - ")[0]); // Extract the ID portion
                    selectedProductsIds.add(categoryId);
                }
            }
            try {
                List<Category> categories = CategoryController.getInstance().getCategoriesByIds(selectedProductsIds);
            } catch (Exception exception) {
                invalidInputLabel.setText(exception.getMessage());
                invalidInputLabel.setVisible(true);
            }

            boolean isValid = true;
            StringBuilder errorMessage = new StringBuilder("Invalid input:");

            int branchId;
            int productCode;
            double discountValue;

            try {
                branchId = Integer.parseInt(branchIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("Branch ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }

            // Validate discountValue

            try {
                discountValue = Double.parseDouble(discountValueText);
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Invalid Discount Value");
                invalidInputLabel.setVisible(true);
                return;
            }

            // Validate discount start date
            try {
                LocalDate discountStartDate = LocalDate.parse(discountStart, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException ex) {
                invalidInputLabel.setText("Invalid Discount Start Date - The Format Is : YYYY-MM-DD");
                invalidInputLabel.setVisible(true);
                return;
            }
            // Validate discount End date
            try {
                LocalDate discountEndDate = LocalDate.parse(discountEnd, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException ex) {
                invalidInputLabel.setText("Invalid Discount End Date - The Format Is : YYYY-MM-DD");
                invalidInputLabel.setVisible(true);
                return;
            }

            // create discount object
            if (selectedDiscountType == "Discount Percentage") {
                try {
                    DiscountPercentage discountPercentage = new DiscountPercentage(
                            new DiscountDTO(Global.getNewDiscountId(), LocalDate.parse(discountStart),
                                    LocalDate.parse(discountEnd), discountValue, "Precentage"));
                    productService.setDiscountByProducts(branchId, selectedProductsIds, discountPercentage);
                } catch (Exception ex) {
                    String keyword = "this";
                    int startIndex = ex.getMessage().toLowerCase().indexOf(keyword.toLowerCase());
                    if (startIndex != -1) {
                        String trimmedString = ex.getMessage().substring(startIndex, ex.getMessage().length());
                        invalidInputLabel.setText(trimmedString);
                        invalidInputLabel.setVisible(true);
                        return;
                    }
                }
            } else {
                try {
                    DiscountFixed discountFixed = new DiscountFixed(new DiscountDTO(Global.getNewDiscountId(),
                            LocalDate.parse(discountStart), LocalDate.parse(discountEnd), discountValue, "Fixed"));
                    productService.setDiscountByProducts(branchId, selectedProductsIds, discountFixed);
                } catch (Exception ex) {
                    String keyword = "this";
                    int startIndex = ex.getMessage().toLowerCase().indexOf(keyword.toLowerCase());
                    if (startIndex != -1) {
                        String trimmedString = ex.getMessage().substring(startIndex, ex.getMessage().length());
                        invalidInputLabel.setText(trimmedString);
                        invalidInputLabel.setVisible(true);
                        return;
                    }
                }
            }

            //
            // if (res != "Success") {
            // invalidInputLabel.setText(res);
            // invalidInputLabel.setVisible(true);
            // return;
            // }
            invalidInputLabel.setText("operation succeed");
            invalidInputLabel.setForeground(Color.green);
            invalidInputLabel.setVisible(true);
            // clear the textBox data
            branchIdTextField.setText("");
            discountValueTextField.setText("");
            discountStartTextField.setText("");
            discountEndTextField.setText("");
            // Clear the selections of checkboxes
            for (JCheckBox checkBox : checkBoxList) {
                checkBox.setSelected(false);
            }

        }
    }
}
