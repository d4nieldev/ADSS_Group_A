package PersentationLayer.GUI.Tasks;

import BusinessLayer.Inventory.InventoryReport;
import BusinessLayer.Inventory.ProductBranch;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class ProductReportActual {
    ProductBranch productBranch;
    JFrame frame;
    JTable table;
    public ProductReportActual(ProductBranch productBranch) {
        frame = new JFrame();
        frame.setTitle("ProductBranch Report");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("ProductBranch Report");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table data and column names
        Object[][] data = {
                {"1", productBranch.getName(), productBranch.getCode(), productBranch.getManufacturer(),
                        productBranch.getOnShelfProduct().size(),
                        productBranch.getTotalAmount() - productBranch.getOnShelfProduct().size()}
        };
        String[] columnNames = {"NO.", "Product Name", "Code", "Manufacturer", "Shop Amount", "Storage Amount"};

        // Create the table model
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Create the table using the table model
        table = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create the "Save Report" button
        JButton saveButton = new JButton("Save Report");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveReportToCSV(tableModel);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Handle exception if necessary
                }
            }
        });

        // Add the "Save Report" button to the panel
        mainPanel.add(saveButton, BorderLayout.SOUTH);

        // Add the panel to the frame's content pane

        frame.add(mainPanel);
        frame.setVisible(true);
    }
    private void saveReportToCSV(DefaultTableModel model) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            try (FileWriter writer = new FileWriter(filePath)) {
                // Write the column headers
                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.write(model.getColumnName(i) + ",");
                }
                writer.write("\n");

                // Write the data rows
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        writer.write(model.getValueAt(i, j) + ",");
                    }
                    writer.write("\n");
                }

                writer.flush();
                writer.close();
                JOptionPane.showMessageDialog(frame, "Report saved successfully.");
            }
        }
    }
}
