package PersentationLayer.GUI.Tasks;

import BusinessLayer.Inventory.BranchController;
import PersentationLayer.GUI.StorekeeperFrame;
import ServiceLayer.InventorySuppliers.BranchService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class AddSpecificProductFrame implements ActionListener {
    private JFrame frame;
    private JTextField branchIdTextField;
    private JTextField productCodeTextField;
    private JTextField buyPriceTextField;
    private JTextField expirationDateTextField;
    JButton submitButton;
    JLabel invalidInputLabel;

    public AddSpecificProductFrame() {
        frame = new JFrame();
        frame.setTitle("Add Specific Product");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 300);
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(this);

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.setBackground(new Color(59, 89, 152)); // Blue color

        JLabel label = new JLabel("Add Specific Product");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        topPanel.add(label, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel branchIdLabel = new JLabel("Branch ID");
        branchIdTextField = new JTextField(20);

        JLabel productCodeLabel = new JLabel("Product Code");
        productCodeTextField = new JTextField(20);

        JLabel buyPriceLabel = new JLabel("Buy Price");
        buyPriceTextField = new JTextField(20);

        JLabel expirationDateLabel = new JLabel("Expiration Date");
        expirationDateTextField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(branchIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(branchIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(productCodeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(productCodeTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(buyPriceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(buyPriceTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(expirationDateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(expirationDateTextField, gbc);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(submitButton, gbc);

        invalidInputLabel = new JLabel("Invalid input");
        invalidInputLabel.setForeground(Color.RED);
        invalidInputLabel.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(invalidInputLabel, gbc);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitButton) {
                // Get data from text fields
                String branchIdText = branchIdTextField.getText();
                String productCodeText = productCodeTextField.getText();
                String buyPriceText = buyPriceTextField.getText();
                String expirationDateText = expirationDateTextField.getText();

                int branchId;
                int productCode;
                double buyPrice;
                LocalDate discountEndDate;

                // Validate product code
                try {
                     branchId = Integer.parseInt(branchIdText);
                    // TODO: Perform further validation or processing
                } catch (NumberFormatException ex) {
                    // Set error label for invalid product code
                    invalidInputLabel.setText("branchId  is not valid");
                    invalidInputLabel.setForeground(Color.RED);
                    invalidInputLabel.setVisible(true);
                    return; // Exit the method
                }
                try {
                     productCode = Integer.parseInt(productCodeText);
                    // TODO: Perform further validation or processing
                } catch (NumberFormatException ex) {
                    // Set error label for invalid product code
                    invalidInputLabel.setText("Product code is not valid");
                    invalidInputLabel.setForeground(Color.RED);
                    invalidInputLabel.setVisible(true);
                    return; // Exit the method
                }

                // Validate buy price
                try {
                     buyPrice = Double.parseDouble(buyPriceText);
                    // TODO: Perform further validation or processing
                } catch (NumberFormatException ex) {
                    // Set error label for invalid buy price
                    invalidInputLabel.setText("Buy price is not valid");
                    invalidInputLabel.setForeground(Color.RED);
                    invalidInputLabel.setVisible(true);
                    return; // Exit the method
                }

                // Validate expiration date
                try {
                     discountEndDate = LocalDate.parse(expirationDateText, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (DateTimeParseException ex) {
                    invalidInputLabel.setText("Invalid Discount End Date - The Format Is : YYYY-MM-DD");
                    invalidInputLabel.setVisible(true);
                    return;
                }


                // If all data is valid, hide the error label
                try {
                    BranchController.getInstance().receiveSupply(productCode,1,buyPrice,discountEndDate,branchId);
                }
                catch (Exception ex)
                {
                    invalidInputLabel.setText(ex.getMessage());
                    invalidInputLabel.setVisible(true);
                    return;
                }
                invalidInputLabel.setText("success");
                invalidInputLabel.setForeground(Color.green);
                invalidInputLabel.setVisible(true);

               branchIdTextField.setText("");
               productCodeTextField.setText("");
               buyPriceTextField.setText("");
               expirationDateTextField.setText("");
            }

    }
}


