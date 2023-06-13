package PersentationLayer.GUI.Tasks;

import PersentationLayer.GUI.StorekeeperFrame;
import ServiceLayer.inventory.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewProductFrame implements ActionListener {
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
    JTextField DiscountValueTextField;
    JTextField DiscountStartTextField;
    JTextField DiscountEndTextField;
    JTextField PriceTextField;
    JTextField MinQuantityTextField;
    JTextField idealQuantityTextField;
    JButton submitButton;
    JLabel invalidInputLabel;
    JButton backButton;
    ProductService productService = new ProductService();

    public AddNewProductFrame(){
        frame = new JFrame();
        frame.setTitle("Add new Product");
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
        JLabel label = new JLabel("Add new Product");
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

        JLabel DiscountValueLabel = new JLabel("discount value");
        DiscountValueTextField = new JTextField(20);

        JLabel DiscountStartLabel = new JLabel("discount start date");
        DiscountStartTextField = new JTextField(20);

        JLabel DiscountEndLabel = new JLabel("discount end date");
        DiscountEndTextField = new JTextField(20);

        JLabel PriceLabel = new JLabel("price");
        PriceTextField = new JTextField(20);

        JLabel MinQuantityLabel = new JLabel("Min Quantity");
        MinQuantityTextField = new JTextField(20);

        JLabel idealQuantityLabel = new JLabel("ideal Quantity");
        idealQuantityTextField = new JTextField(20);



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
        mainPanel.add(DiscountValueLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(DiscountValueTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(DiscountStartLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(DiscountStartTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(DiscountEndLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(DiscountEndTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(PriceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(PriceTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(MinQuantityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        mainPanel.add(MinQuantityTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(idealQuantityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        mainPanel.add(idealQuantityTextField, gbc);

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

    }
}
