package PersentationLayer.GUI.Tasks;

import BusinessLayer.Inventory.DeficientReport;
import BusinessLayer.Inventory.ProductBranch;
import BusinessLayer.Inventory.ReportController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class DeficiencyReportFrame {

    private JFrame frame;
    private JTable table;
    ReportController reportController = ReportController.getInstance();


    public DeficiencyReportFrame(int branchId) {
        frame = new JFrame();
        frame.setTitle("Deficiency Report");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new BorderLayout());

        // Create the table model with column names
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("NO.");
        model.addColumn("Product Name");
        model.addColumn("Code");
        model.addColumn("Missing Amount");

        try {
            // Retrieve the deficiency report data
            DeficientReport deficientReport = reportController.importDeficientReport(branchId);
            Map<Integer, ProductBranch> products = deficientReport.getProducts();
            Map<Integer, Integer> idToMissingAmount = deficientReport.getIdToMissingAmount();
            int index = 1;

            // Add the data to the table model
            for (Integer productCode : products.keySet()) {
                Object[] row = new Object[4];
                row[0] = index;
                row[1] = products.get(productCode).getName();
                row[2] = productCode;
                row[3] = idToMissingAmount.get(productCode);
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
