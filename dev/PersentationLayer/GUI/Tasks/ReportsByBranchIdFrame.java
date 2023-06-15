package PersentationLayer.GUI.Tasks;

import PersentationLayer.GUI.StoreMangerFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsByBranchIdFrame implements ActionListener {
    JButton backButton;
    JFrame frame;
    JTextField branchIdTextField;
    JButton inventoryButton;
    JButton expiredButton;
    JButton deficiencyButton;
    JLabel invalidInputLabel;



    public ReportsByBranchIdFrame(){
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

        JLabel label = new JLabel("Import Report by Branch");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        topPanel.add(label, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel chooseReportLabel = new JLabel("Choose Desired Report");
        chooseReportLabel.setHorizontalAlignment(JLabel.CENTER);
        chooseReportLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(chooseReportLabel, gbc);

        JPanel branchIdPanel = new JPanel(new BorderLayout());
        branchIdPanel.add(new JLabel("Branch ID"), BorderLayout.WEST);
        branchIdTextField = new JTextField(20);
        branchIdPanel.add(branchIdTextField, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(branchIdPanel, gbc);

        JPanel reportPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        inventoryButton = new JButton("Inventory Report");
        inventoryButton.addActionListener(this);
        reportPanel.add(inventoryButton);

        expiredButton = new JButton("Expired and Flaws Report");
        expiredButton.addActionListener(this);
        reportPanel.add(expiredButton);

        deficiencyButton = new JButton("Deficiency Report");
        deficiencyButton.addActionListener(this);
        reportPanel.add(deficiencyButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(reportPanel, gbc);

        invalidInputLabel = new JLabel("Invalid input");
        invalidInputLabel.setForeground(Color.RED);
        invalidInputLabel.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
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
            StoreMangerFrame storeMangerFrame = new StoreMangerFrame();
        }

        if (e.getSource() == inventoryButton) {
            String branchIdText = branchIdTextField.getText().trim();
            int branchId ;
            try {
                branchId = Integer.parseInt(branchIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("Branch ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }
            InventoryReportFrame inventoryReportActualFrame = new InventoryReportFrame(branchId);
        }

        if (e.getSource() == expiredButton) {
            int branchId ;
            try {
                branchId = Integer.parseInt(branchIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("Branch ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }
            ExpiredAndFlawsReportFrame expiredAndFlawsReportFrame = new ExpiredAndFlawsReportFrame(branchId);
        }
        if (e.getSource() == deficiencyButton) {
            int branchId ;
            try {
                branchId = Integer.parseInt(branchIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("Branch ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }
            DeficiencyReportFrame deficiencyReport = new DeficiencyReportFrame(branchId);
        }


    }
}
