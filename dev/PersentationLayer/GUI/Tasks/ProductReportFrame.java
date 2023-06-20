package PersentationLayer.GUI.Tasks;

import BusinessLayer.Inventory.BranchController;
import BusinessLayer.Inventory.ProductBranch;
import BusinessLayer.Inventory.ReportController;
import PersentationLayer.GUI.ReportsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductReportFrame implements ActionListener {
    JButton backButton;
     JFrame frame;
     JTextField productIdTextField;
     JTextField branchIdTextField;
     JButton importButton;
     JLabel invalidInputLabel;
    ReportController reportController = ReportController.getInstance();
    BranchController branchController = BranchController.getInstance();
    public ProductReportFrame(){
        frame = new JFrame();
        frame.setTitle("Import Selected Report");
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

        JLabel label = new JLabel("Import Report by Product Code");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        topPanel.add(label, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel chooseReportLabel = new JLabel("Choose Product ID");
        chooseReportLabel.setHorizontalAlignment(JLabel.CENTER);
        chooseReportLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(chooseReportLabel, gbc);

        JPanel productIdPanel = new JPanel(new BorderLayout());
        productIdPanel.add(new JLabel("Product ID"), BorderLayout.WEST);
        productIdTextField = new JTextField(20);
        productIdPanel.add(productIdTextField, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(productIdPanel, gbc);

        JLabel branchIdLabel = new JLabel("Branch ID");
        branchIdTextField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(branchIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(branchIdTextField, gbc);

        JPanel reportPanel = new JPanel(new GridLayout(1, 1, 10, 10));

        importButton = new JButton("Import Report");
        importButton.addActionListener(this);
        reportPanel.add(importButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(reportPanel, gbc);

        invalidInputLabel = new JLabel("Invalid input");
        invalidInputLabel.setForeground(Color.RED);
        invalidInputLabel.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(invalidInputLabel, gbc);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);
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

        if (e.getSource() == importButton) {
            String productIdText = productIdTextField.getText().trim();
            String branchIdText = branchIdTextField.getText().trim();
            int productId ;
            int branchId;
            try {
                productId = Integer.parseInt(productIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("product ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }
            try {
                branchId = Integer.parseInt(branchIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("branch ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }
            try {
                ProductBranch productBranch = branchController.getBranchById(branchId).getProductByCode(productId);
                ProductReportActual productReportActual = new ProductReportActual(productBranch);

            } catch (Exception ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("this Product doesn't exist");
                invalidInputLabel.setVisible(true);
                return;
            }
        }


    }
}
