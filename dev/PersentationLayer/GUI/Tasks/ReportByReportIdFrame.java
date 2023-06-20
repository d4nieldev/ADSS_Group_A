package PersentationLayer.GUI.Tasks;

import BusinessLayer.Inventory.DeficientReport;
import BusinessLayer.Inventory.ExpiredAndFlawReport;
import BusinessLayer.Inventory.InventoryReport;
import BusinessLayer.Inventory.ReportController;
import BusinessLayer.exceptions.InventoryException;
import PersentationLayer.GUI.ReportsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ReportByReportIdFrame implements ActionListener {
    JButton backButton;
    JFrame frame;
    JTextField reportIdTextField;
    JButton inventoryButton;
    JLabel invalidInputLabel;
    ReportController reportController = ReportController.getInstance();

    public ReportByReportIdFrame() {
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

        JLabel label = new JLabel("Import Report by Branch Id");
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

        JPanel reportIdPanel = new JPanel(new BorderLayout());
        reportIdPanel.add(new JLabel("report ID"), BorderLayout.WEST);
        reportIdTextField = new JTextField(20);
        reportIdPanel.add(reportIdTextField, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(reportIdPanel, gbc);

        JPanel reportPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        inventoryButton = new JButton("import report");
        inventoryButton.addActionListener(this);
        reportPanel.add(inventoryButton);


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
            ReportsFrame reportsFrame = new ReportsFrame();
        }

        if (e.getSource() == inventoryButton) {

            String reportIdText = reportIdTextField.getText().trim();
            int reportId;
            try {
                reportId = Integer.parseInt(reportIdTextField.getText());
            } catch (NumberFormatException ex) {
                invalidInputLabel.setText("");
                invalidInputLabel.setText("Report ID is invalid");
                invalidInputLabel.setVisible(true);
                return;
            }


//            if (!(reportController.getAllReports().containsKey(reportId))) {

//            } else
            if (reportController.getAllReports().get(reportId) instanceof InventoryReport) {
                try {
                    InventoryReport inventoryReport = reportController.getInventoryReport(reportId);
                    InventoryReportFrame inventoryReportFrame = new InventoryReportFrame(inventoryReport);
                } catch (InventoryException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

//            } else if (reportController.getAllReports().get(reportId) instanceof ExpiredAndFlawReport) {
//                importExpiredAndFlawsReportByReportId(reportId);
//            } else if (reportController.getAllReports().get(reportId) instanceof DeficientReport) {
//                importDeficientReportByReportId(reportId);
//            }

//                InventoryReportFrame inventoryReportActualFrame = new InventoryReportFrame(branchId);

            }

        }
    }
}
