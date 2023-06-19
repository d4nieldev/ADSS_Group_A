package PersentationLayer.GUI.Tasks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import PersentationLayer.GUI.MangeStorageFrame;
import PersentationLayer.GUI.StorekeeperFrame;
import ServiceLayer.inventory.ProductService;

public class AddGeneralProductFrame implements ActionListener {
    JFrame frame;
    JLabel label;
    JButton button;
    StorekeeperFrame storekeeperFrame;
    JPanel tasksPanel;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton submitButton;
    JTextField manufacturerTextField;
    JTextField productCodeTextField;
    JTextField productNameTextField;
    JTextField catergoryTextField;
    JLabel invalidInputLabel;
    JButton backButton;
    ProductService productService = new ProductService();
    JComboBox<String> discountTypeComboBox; // Added ComboBox
    JCheckBox discountCheckbox;
    JLabel discountValueLabel;
    JLabel discountStartLabel;
    JLabel discountEndLabel;
    JLabel discountTypeLabel;

    public AddGeneralProductFrame() {
        frame = new JFrame();
        frame.setTitle("Add New General Product");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
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
        JLabel label = new JLabel("Add New General Product");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        topPanel.add(label, BorderLayout.CENTER);

        // Create the main panel for text boxes
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Reduce spacing around components

        // Create the text boxes with labels
        JLabel productCodeLabel = new JLabel("Product Code");
        productCodeTextField = new JTextField(20);

        // Add the labels and text fields to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        // gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(productCodeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        // gbc.anchor = GridBagConstraints.WEST;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(productCodeTextField, gbc);

        // Repeat the same pattern for other labels and text fields...

        JLabel nameLabel = new JLabel("Name");
        productNameTextField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(productNameTextField, gbc);

        JLabel manufacturerLabel = new JLabel("Manufacturer");
        manufacturerTextField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(manufacturerLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(manufacturerTextField, gbc);

        JLabel catergoryLabel = new JLabel("Category Id");
        catergoryTextField = new JTextField(20);
        // discountStartTextField.setText("2024-11-11");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(catergoryLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(catergoryTextField, gbc);

        // Create the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 10;
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
        // back to tasks view window
        if (e.getSource() == backButton) {
            frame.dispose();
            new MangeStorageFrame();
        }

        if (e.getSource() == submitButton) {
            String productCodeText = productCodeTextField.getText().trim();
            String productNameText = productNameTextField.getText().trim();
            String manufacturerText = manufacturerTextField.getText().trim();
            String catergoryText = catergoryTextField.getText().trim();

            int productCode;
            int category;

            try {
                category = Integer.parseInt(catergoryText);
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("Branch ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }

            try {
                productCode = Integer.parseInt(productCodeText);
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Product Code is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }
            String res = null;
            try {
                productService.addNewProduct(productCode, productNameText, manufacturerText, category);
                res = "Success";
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

            if (res != "Success") {
                invalidInputLabel.setText(res);
                invalidInputLabel.setVisible(true);
                return;
            }
            invalidInputLabel.setText("operation succeed");
            invalidInputLabel.setForeground(Color.green);
            invalidInputLabel.setVisible(true);
            // clear the textBox data

            productCodeTextField.setText("");
            productNameTextField.setText("");
            manufacturerTextField.setText("");
            catergoryTextField.setText("");

        }

    }
}
