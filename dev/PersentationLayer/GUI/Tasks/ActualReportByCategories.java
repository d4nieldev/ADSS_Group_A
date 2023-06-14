package PersentationLayer.GUI.Tasks;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.Inventory.InventoryReport;
import BusinessLayer.Inventory.ReportController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActualReportByCategories {
    private JFrame frame;
    private JTable table;
    ReportController reportController = ReportController.getInstance();
    CategoryController categoryController = CategoryController.getInstance();
    public ActualReportByCategories(int branchId, List<Integer> categoriesId) {
        frame = new JFrame();
        frame.setTitle("Inventory Report by Categories");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new BorderLayout());

        // Create the table model with column names
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("NO.");
        model.addColumn("Product Name");
        model.addColumn("Code");
        model.addColumn("Shop Amount");
        model.addColumn("Storage Amount");

        try {
            // Retrieve the inventory report data by categories
            List<Category> allCategories = categoryController.getListAllSubCategoriesByIds(categoriesId);
            InventoryReport inventoryReport = reportController.importInventoryReport(branchId, allCategories);
            Set<Integer> productsCode = inventoryReport.getIdToName().keySet();
            Map<Integer, String> codeToName = inventoryReport.getIdToName();
            Map<Integer, Integer> shelfAmount = inventoryReport.getIdToShelfAmount();
            Map<Integer, Integer> storageAmount = inventoryReport.getIdToStorageAmount();
            int index = 1;

            // Add the data to the table model
            for (Integer productCode : productsCode) {
                Object[] row = new Object[5];
                row[0] = index;
                row[1] = codeToName.get(productCode);
                row[2] = productCode;
                row[3] = shelfAmount.get(productCode);
                row[4] = storageAmount.get(productCode);
                model.addRow(row);
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception if necessary
        }

        // Create the table and set the table model
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the table to the panel
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save Report");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveReportToCSV(model);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Handle exception if necessary
                }
            }
        });

        // Add the "Save Report" button to the panel
        panel.add(saveButton, BorderLayout.SOUTH);

        // Add the panel to the frame's content pane
        frame.getContentPane().add(panel);
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
