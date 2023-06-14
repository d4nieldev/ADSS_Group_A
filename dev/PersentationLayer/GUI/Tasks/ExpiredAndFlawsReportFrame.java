package PersentationLayer.GUI.Tasks;

import BusinessLayer.Inventory.ExpiredAndFlawReport;
import BusinessLayer.Inventory.ProductBranch;
import BusinessLayer.Inventory.ReportController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class ExpiredAndFlawsReportFrame {

    private JFrame frame;
    private JTable table;
    ReportController reportController = ReportController.getInstance();
    public ExpiredAndFlawsReportFrame(int branchId){
        frame = new JFrame();
        frame.setTitle("Expired and Flaws Report");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new BorderLayout());

        // Create the table model with column names
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("NO.");
        model.addColumn("NAME");
        model.addColumn("Category Name");
        model.addColumn("Specific ID");
        model.addColumn("Flaw Description");
        model.addColumn("Expired Date");

        try {
            // Retrieve the expired and flaws report data
            ExpiredAndFlawReport expiredAndFlawReport = reportController.importExpiredAndFlawReport(branchId);
            Map<Integer, ProductBranch> products = expiredAndFlawReport.getProducts();
            Map<Integer, Map<Integer, LocalDate>> idToExpiredSpecificIdAndDate = expiredAndFlawReport.getIdToExpiredSpecificIdAndDate();
            Map<Integer, Map<Integer, String>> codeToSpecificDescription = expiredAndFlawReport.getCodeToSpecificDescription();

            int index = 1;

            // Iterate through each general product and add its flow products to the table model
            for (Integer productCode : products.keySet()) {
                Map<Integer, String> flowProducts = codeToSpecificDescription.get(productCode);
                if (flowProducts != null) {
                    for (int id : flowProducts.keySet()) {
                        Object[] row = new Object[6];
                        row[0] = index;
                        row[1] = products.get(productCode).getName();
                        row[2] = products.get(productCode).getCategoryName();
                        row[3] = id;
                        row[4] = flowProducts.get(id);
                        row[5] = "X";
                        model.addRow(row);
                        index++;
                    }
                }
            }

            // Iterate through all expired products and add them to the table model
            for (Integer productCode : products.keySet()) {
                Map<Integer, LocalDate> expiredDate = idToExpiredSpecificIdAndDate.get(productCode);
                if (expiredDate != null) {
                    for (int id : expiredDate.keySet()) {
                        Object[] row = new Object[6];
                        row[0] = index;
                        row[1] = products.get(productCode).getName();
                        row[2] = products.get(productCode).getCategoryName();
                        row[3] = id;
                        row[4] = "X";
                        row[5] = "Expired at: " + expiredDate.get(id);
                        model.addRow(row);
                        index++;
                    }
                }
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
        // Create the "Save Report" button
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
