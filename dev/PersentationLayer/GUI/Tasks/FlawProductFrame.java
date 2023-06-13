package PersentationLayer.GUI.Tasks;

import PersentationLayer.GUI.MangeStorageFrame;
import PersentationLayer.GUI.StorekeeperFrame;
import ServiceLayer.inventory.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlawProductFrame implements ActionListener {
    JFrame frame;
    JLabel label;
    JButton button;
    StorekeeperFrame storekeeperFrame ;
    JPanel tasksPanel;
    JButton button1 ;
    JButton button2 ;
    JButton button3 ;
    JTextField branchIdTextField;
    JTextField productCodeTextField;
    JTextField specificIdTextField;
    JTextField descriptionTextField;
    JButton submitButton;
    JLabel invalidInputLabel;
    JButton backButton;
    ProductService productService = new ProductService();



    public FlawProductFrame(){
        frame = new JFrame();
        frame.setTitle("Report flaw product");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 300);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create the top panel with BorderLayout
        JPanel topPanel = new JPanel(new BorderLayout());

        // Create the back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);

        topPanel.add(backButton, BorderLayout.WEST);

        // Create the label
        JLabel label = new JLabel("Report flaw product");
        label.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(label, BorderLayout.CENTER);

        // Create the main panel for text boxes
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing around components

        // Create the text boxes with labels
        JLabel branchIdLabel = new JLabel("Branch ID");
        branchIdTextField = new JTextField(20);

        JLabel productCodeLabel = new JLabel("Product Code");
        productCodeTextField = new JTextField(20);

        JLabel specificIdLabel = new JLabel("Specific ID");
        specificIdTextField = new JTextField(20);

        JLabel descriptionLabel = new JLabel("Description");
        descriptionTextField = new JTextField(20);

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
        mainPanel.add(specificIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(specificIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(descriptionTextField, gbc);

        // Create the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST; // Align the button to the right
        mainPanel.add(submitButton, gbc);

        // Create the invalid input label
        invalidInputLabel = new JLabel("Invalid input");
        invalidInputLabel.setForeground(Color.RED);
        invalidInputLabel.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
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
        if (e.getSource() == backButton) {
            frame.dispose();
            MangeStorageFrame mangeStorageFrame = new MangeStorageFrame();
        }
        //submit button operation
        if (e.getSource() == submitButton) {
            // Reset the invalid input label
            invalidInputLabel.setVisible(false);

            // Retrieve the values from the text fields
            int branchId;
            int productCode;
            int specificId;
            String description;

            try {
                branchId = Integer.parseInt(branchIdTextField.getText());
            } catch (NumberFormatException ex) {
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

            try {
                specificId = Integer.parseInt(specificIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("Specific ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }

            description = descriptionTextField.getText();

            // Check if description is empty
            if (description.isEmpty()) {
                invalidInputLabel.setText("Description cannot be empty");
                invalidInputLabel.setVisible(true);
                return;
            }

            try {
                productService.reportFlawProduct(branchId,productCode,specificId,description);
            } catch (Exception ex) {
                invalidInputLabel.setText(ex.getMessage());
                invalidInputLabel.setVisible(true);
            }
            invalidInputLabel.setText("operation succeed");
            invalidInputLabel.setForeground(Color.green);
            invalidInputLabel.setVisible(true);
            //clear the textBox data
            branchIdTextField.setText("");
            productCodeTextField.setText("");
            specificIdTextField.setText("");
            descriptionTextField.setText("");
        }
    }
}
