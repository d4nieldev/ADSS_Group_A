package PersentationLayer.GUI.Tasks;

import BusinessLayer.InveontorySuppliers.Branch;
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

public class AddNewProductFrame implements ActionListener {
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
    JTextField productCodeTextField;
    JTextField discountValueTextField;
    JTextField discountStartTextField;
    JTextField discountEndTextField;
    JTextField priceTextField;
    JTextField minQuantityTextField;
    JTextField idealQuantityTextField;
    JLabel invalidInputLabel;
    JButton backButton;
    BranchService branchService = new BranchService();
    JComboBox<String> discountTypeComboBox; // Added ComboBox


    public AddNewProductFrame() {
        frame = new JFrame();
        frame.setTitle("Add new Product");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 550);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        invalidInputLabel = new JLabel();
        invalidInputLabel.setVisible(false);

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
        JLabel label = new JLabel("Add new Product");
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

        JLabel productCodeLabel = new JLabel("Product Code");
        productCodeTextField = new JTextField(20);

        JLabel discountValueLabel = new JLabel("Discount Value");
        discountValueTextField = new JTextField(20);

        JLabel discountStartLabel = new JLabel("Discount Start Date");
        discountStartTextField = new JTextField(20);
        discountStartTextField.setText("2024-11-11");

        JLabel discountEndLabel = new JLabel("Discount End Date");
        discountEndTextField = new JTextField(20);
        discountEndTextField.setText("2024-11-11");

        JLabel priceLabel = new JLabel("Price");
        priceTextField = new JTextField(20);

        JLabel minQuantityLabel = new JLabel("Min Quantity");
        minQuantityTextField = new JTextField(20);

        JLabel idealQuantityLabel = new JLabel("Ideal Quantity");
        idealQuantityTextField = new JTextField(20);

        // Create the discount type combo box
        JLabel discountTypeLabel = new JLabel("Discount Type");
        discountTypeComboBox = new JComboBox<>(new String[]{"Discount Percentage", "Discount Fix"});

        // Add the labels and text fields to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(branchIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(branchIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(productCodeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(productCodeTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(discountValueLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(discountValueTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(discountStartLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(discountStartTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(discountEndLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(discountEndTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(priceTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(minQuantityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        mainPanel.add(minQuantityTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(idealQuantityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        mainPanel.add(idealQuantityTextField, gbc);

        // Add the discount type label and combo box to the main panel
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(discountTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        mainPanel.add(discountTypeComboBox, gbc);


        // Create the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        ////
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST; // Align the button to the right
        mainPanel.add(submitButton, gbc);

        // Create the invalid input label
        invalidInputLabel = new JLabel("Invalid input");
        invalidInputLabel.setForeground(Color.RED);
        invalidInputLabel.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(invalidInputLabel, gbc);

        // Add the top panel and main panel to the frame's content pane
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        //back to tasks view window
        if (e.getSource() == backButton) {
            frame.dispose();
            MangeStorageFrame mangeStorageFrame = new MangeStorageFrame();
        }

        if (e.getSource() == submitButton) {
            String branchIdText = branchIdTextField.getText().trim();
            String productCodeText = productCodeTextField.getText().trim();
            String discountValueText = discountValueTextField.getText().trim();
            String discountStart = discountStartTextField.getText().trim();
            String discountEnd = discountEndTextField.getText().trim();
            String priceText = priceTextField.getText().trim();
            String minQuantityText = minQuantityTextField.getText().trim();
            String idealQuantityText = idealQuantityTextField.getText().trim();

            boolean isValid = true;
            StringBuilder errorMessage = new StringBuilder("Invalid input:");

            int branchId ;
            int productCode;
            double discountValue ;
            double price ;
            int minQuantity ;
            int idealQuantity ;

            try {
                branchId = Integer.parseInt(branchIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("Branch ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }

            try {
                productCode = Integer.parseInt(productCodeTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Product Code is invalid");
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

            //Validate discount start date
            try {
                LocalDate discountStartDate = LocalDate.parse(discountStart, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException ex) {
                invalidInputLabel.setText("Invalid Discount Start Date - The Format Is : YYYY-MM-DD");
                invalidInputLabel.setVisible(true);
                return;
            }
            //Validate discount End date
            try {
                LocalDate discountEndDate = LocalDate.parse(discountEnd, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException ex) {
                invalidInputLabel.setText("Invalid Discount End Date - The Format Is : YYYY-MM-DD");
                invalidInputLabel.setVisible(true);
                return;
            }


            try {
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Invalid Price");
                invalidInputLabel.setVisible(true);
                return;
            }

            try {
                minQuantity = Integer.parseInt(minQuantityText);
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Invalid Min Quantity");
                invalidInputLabel.setVisible(true);
                return;
            }

            try {
                idealQuantity = Integer.parseInt(minQuantityText);
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Invalid ideal Quantity");
                invalidInputLabel.setVisible(true);
                return;
            }

            boolean isDiscountPercentage = discountTypeComboBox.getSelectedItem().equals("DiscountPercentage");
            String res = "";
            try{
                 res =  branchService.addProductBranch(productCode,branchId, LocalDate.parse(discountStart),LocalDate.parse(discountEnd),discountValue,isDiscountPercentage,price,minQuantity,idealQuantity);
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

            if(res!="Success")
                {
                    invalidInputLabel.setText(res);
                    invalidInputLabel.setVisible(true);
                    return;
                }
            invalidInputLabel.setText("operation succeed");
            invalidInputLabel.setForeground(Color.green);
            invalidInputLabel.setVisible(true);
            //clear the textBox data
            branchIdTextField.setText("");
            productCodeTextField.setText("");
            discountValueTextField.setText("");
            discountStartTextField.setText("");
            discountEndTextField.setText("");
            priceTextField.setText("");
            minQuantityTextField.setText("");
            idealQuantityTextField.setText("");




        }


        }
    }


