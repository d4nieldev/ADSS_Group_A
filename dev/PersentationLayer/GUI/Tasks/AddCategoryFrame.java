package PersentationLayer.GUI.Tasks;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import PersentationLayer.GUI.MangeStorageFrame;
import PersentationLayer.GUI.StorekeeperFrame;
import ServiceLayer.InventorySuppliers.BranchService;
import ServiceLayer.inventory.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddCategoryFrame implements ActionListener {
    JFrame frame;

    JButton submitButton;
    JTextField categoryNameTextField;

     JComboBox<Category> categoryComboBox;
    JLabel invalidInputLabel;
    JButton backButton;
    java.util.List<Category> categoryList;
    ProductService productService = new ProductService();

    public AddCategoryFrame(){
        frame = new JFrame();
        frame.setTitle("Add Category");
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
        JLabel label = new JLabel("Add category");
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
        JLabel categoryNameLabel = new JLabel("Category Name");
        categoryNameTextField = new JTextField(20);

        try {
            categoryList = CategoryController.getInstance().getAllCategories();
        } catch (SQLException exception) {
            invalidInputLabel.setText(exception.getMessage());
            invalidInputLabel.setVisible(true);
        }
        // Create a new ArrayList to include the empty option
        List<Category> categoryListWithEmpty = new ArrayList<>(categoryList);
        categoryListWithEmpty.add(0, null);

        categoryComboBox = new JComboBox<>(categoryListWithEmpty.toArray(new Category[0]));

        // Override the toString() method of Category to display id - category name
        categoryComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) {
                    setText("Choose None");
                } else if (value instanceof Category) {
                    Category category = (Category) value;
                    setText(category.getId() + " - " + category.getName());
                }
                return this;
            }
        });

        // Create the desired categories label
        JLabel desiredCategoriesLabel = new JLabel("parent category");


        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(categoryNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(categoryNameTextField, gbc);

        // Add the discount type label and combo box to the main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(desiredCategoriesLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(categoryComboBox, gbc);

        // Create the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        ////
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST; // Align the button to the right
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
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //back to tasks view window
        if (e.getSource() == backButton) {
            frame.dispose();
            MangeStorageFrame mangeStorageFrame = new MangeStorageFrame();
        }

        if (e.getSource() == submitButton) {
            int parentId = -1;
            String categoryName = categoryNameTextField.getText().trim();
            boolean isValid = true;
            StringBuilder errorMessage = new StringBuilder("Invalid input:");

            if(categoryName.isBlank())
            {
                invalidInputLabel.setText("Category name is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }

            Object selectedCategory = categoryComboBox.getSelectedItem();
            if (selectedCategory == null) {
                // None selected
            } else if (selectedCategory instanceof Category) {
                Category category = (Category) selectedCategory;
                int categoryId = category.getId();
                parentId = categoryId;
            }

            try {
                productService.addNewCategory(categoryName, parentId );
            } catch (Exception ex) {
                invalidInputLabel.setText(ex.getMessage());
                invalidInputLabel.setVisible(true);
            }

            invalidInputLabel.setText("operation succeed");
            invalidInputLabel.setForeground(Color.green);
            invalidInputLabel.setVisible(true);
            //clear the textBox data
            categoryNameTextField.setText("");
            categoryComboBox.setSelectedIndex(0);

        }


    }
}
