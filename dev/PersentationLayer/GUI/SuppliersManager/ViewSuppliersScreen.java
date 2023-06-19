package PersentationLayer.GUI.SuppliersManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ServiceLayer.Suppliers.SupplierService;

public class ViewSuppliersScreen extends JFrame {
    private final int NUM_OF_SUPPLIERS_SHOWN_EACH_TIME = 1;
    private JPanel containerPanel;
    private SupplierService service;
    private List<Integer> shownSuppliers;

    public ViewSuppliersScreen(JFrame previousFrame) {
        service = SupplierService.create();
        this.shownSuppliers = new ArrayList<>();
        setTitle("View Suppliers");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton showMoreBtn = new JButton("Show More");
        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        showSuppliers(showMoreBtn, new ArrayList<Integer>());

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(showMoreBtn);
        JButton back = new JButton("Go Back");
        back.addActionListener((ActionEvent e) -> {
            dispose();
            previousFrame.setVisible(true);
        });
        buttonPanel.add(back);

        add(buttonPanel, BorderLayout.SOUTH);

        pack(); // Pack the components to their preferred sizes
        setVisible(true);
        setLocationRelativeTo(previousFrame);
        setSize(800, 800);

        showMoreBtn.addActionListener((ActionEvent e) -> {
            showSuppliers(showMoreBtn, shownSuppliers);
        });

    }

    private JSeparator createDivider() {
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
        return separator;
    }

    private void showSuppliers(JButton showMoreBtn, List<Integer> alreadyShown) {
        List<String> data = service.getSomeSuppliersIds(NUM_OF_SUPPLIERS_SHOWN_EACH_TIME, alreadyShown);
        if (data.size() != NUM_OF_SUPPLIERS_SHOWN_EACH_TIME || data.get(data.size() - 1).equals("done")) {
            showMoreBtn.setEnabled(false);
            showMoreBtn.setText("All suppliers shown");
        }
        int i = 0;
        while (NUM_OF_SUPPLIERS_SHOWN_EACH_TIME > i && !data.get(i).equals("done")) {
            int id = Integer.parseInt(data.get(i));
            SupplierEditorScreen newSuppToShow = new SupplierEditorScreen(id, "View", this);
            JFrame child = newSuppToShow.getChild();
            child.setVisible(false);
            containerPanel.add((JPanel) child.getContentPane().getComponent(0));
            containerPanel.add(createDivider()); // Add divider between panels
            shownSuppliers.add(id);
            i++;
        }
    }
}
