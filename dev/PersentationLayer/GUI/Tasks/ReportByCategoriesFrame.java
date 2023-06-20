package PersentationLayer.GUI.Tasks;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import PersentationLayer.GUI.ReportsFrame;
import ServiceLayer.inventory.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportByCategoriesFrame implements ActionListener {

    JButton backButton;
    JButton submitButton;
    JFrame frame;
    JTextField branchIdTextField;
    JLabel invalidInputLabel;

    java.util.List<Category> categoryList;
    List<JCheckBox> checkBoxList;
    ProductService productService = new ProductService();


    public ReportByCategoriesFrame() {

        frame = new JFrame();
        frame.setTitle("Add new Product");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 550);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(59, 89, 152));

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(0, 122, 255));
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel label = new JLabel("Import Inventory Report");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        topPanel.add(label, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel branchIdLabel = new JLabel("Branch ID");
        branchIdTextField = new JTextField(20);

        try {
            categoryList = CategoryController.getInstance().getAllCategories();
        } catch (SQLException exception) {
            invalidInputLabel.setText(exception.getMessage());
            invalidInputLabel.setVisible(true);
        }

        // Create the desired categories label
        JLabel desiredCategoriesLabel = new JLabel("Desired Categories");

        // Create a panel to hold the checkbox list
        JPanel categoriesPanel = new JPanel(new GridLayout(0, 1)); // Use GridLayout for vertical layout

        // Create checkboxes for each category
        checkBoxList = new ArrayList<>(); // Store checkboxes in a list
        for (Category category : categoryList) {
            String checkboxText = category.getId() + " - " + category.getName();
            JCheckBox checkBox = new JCheckBox(checkboxText);
            checkBoxList.add(checkBox); // Add the checkbox to the list
            categoriesPanel.add(checkBox);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(branchIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(branchIdTextField, gbc);

        // Add the desired categories label and checkbox list panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(desiredCategoriesLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPanel.add(new JScrollPane(categoriesPanel), gbc);

        // Create the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        Dimension buttonSize = new Dimension(80, 25);
        submitButton.setMaximumSize(buttonSize); // Set the maximum size for the button
        submitButton.setPreferredSize(buttonSize); // Set the preferred size for the button
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END; // Align the button to the right
        gbc.fill = GridBagConstraints.NONE; // Prevent the button from expanding
        gbc.weightx = 0.0; // Reset the horizontal weight
        mainPanel.add(submitButton, gbc);


        // Create the invalid input label
        invalidInputLabel = new JLabel("Invalid input");
        invalidInputLabel.setForeground(Color.RED);
        invalidInputLabel.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
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
            ReportsFrame storeMangerFrame = new ReportsFrame();
        }

        if (e.getSource() == submitButton) {
            String branchIdText = branchIdTextField.getText().trim();
            List<Integer> selectedCategoryIds = new ArrayList<>();

            // Iterate over the checkbox list
            for (JCheckBox checkBox : checkBoxList) {
                if (checkBox.isSelected()) {
                    // Extract the category ID from the checkbox label
                    String checkboxText = checkBox.getText();
                    int categoryId = Integer.parseInt(checkboxText.split(" - ")[0]); // Extract the ID portion
                    selectedCategoryIds.add(categoryId);
                }
            }
            List<Category> categories = new ArrayList<>();
            try {
                categories = CategoryController.getInstance().getCategoriesByIds(selectedCategoryIds);
            } catch (Exception exception) {
                invalidInputLabel.setText(exception.getMessage());
                invalidInputLabel.setVisible(true);
            }
            int branchId;

            try {
                branchId = Integer.parseInt(branchIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("Branch ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }
            List<Integer> categoriesIds = new ArrayList<>();
            for (Category category : categories){
                categoriesIds.add(category.getId());
            }
            ActualReportByCategories actualReportByCategories = new ActualReportByCategories(branchId,categoriesIds);

        }
    }
}

