package PresentationLayer.GUITransport;

import BussinessLayer.TransPortLayer.Transport;
import ServiceLayer.TransportLayer.TransportService;
import ServiceLayer.EmployeesLayer.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RunTransportByDateScreen extends JFrame {
    private ServiceFactory serviceFactory;
    private String date;
    private JList<Transport> transportList;

    public RunTransportByDateScreen(ServiceFactory serviceFactory, String date) {
        this.serviceFactory = serviceFactory;
        this.date = date;
        TransportService transportService = serviceFactory.getTransportService();
        setTitle("Run Transport by Date");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel titleLabel = new JLabel("Select a transport to run:");
        JButton backButton = new JButton("Back");
        JButton runButton = new JButton("Run Transport");

        // Get list of transports for the selected date
        List<Transport> transports = transportService.getTransports(date);

        // Create the transport list
        DefaultListModel<Transport> transportListModel = new DefaultListModel<>();
        for (Transport transport : transports) {
            transportListModel.addElement(transport);
        }
        transportList = new JList<>(transportListModel);
        transportList.setCellRenderer(new TransportListRenderer());
        JScrollPane transportScrollPane = new JScrollPane(transportList);

        // Set layout manager
        setLayout(new BorderLayout());

        // Create panel for the components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create panel for the title and transport list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(titleLabel, BorderLayout.NORTH);
        listPanel.add(transportScrollPane, BorderLayout.CENTER);

        // Create panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(runButton);
        buttonPanel.add(backButton);

        // Add list panel and button panel to the main panel
        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to the frame
        add(mainPanel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current screen
                new RunTransportScreen(serviceFactory); // Open the RunTransportScreen
            }
        });

            runButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!transportList.isSelectionEmpty()) {
                        // Get the selected transport
                        Transport selectedTransport = transportList.getSelectedValue();
                        // Perform any necessary operations with the selected transport
                        // transportService.runTransport(selectedTransport)

                        // Open a new screen or perform any other action
                        dispose(); // Close the current screen
                        new TransportRunSuccessScreen(serviceFactory, selectedTransport); // Open the TransportRunSuccessScreen
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a transport to run.");
                    }
                }
            });


        // Set the frame size and make it visible
        setSize(600, 400); // Adjust the size as needed
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    // Custom renderer for the transport list
    private class TransportListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Transport) {
                Transport transport = (Transport) value;
                String transportDetails = String.format("ID: %d, Source: %s, Driver: %s, Neto Weight: %d, Max Weight: %d",
                        transport.getId(), transport.getSource(), transport.getDriverName(),
                        200, transport.getWeightMax());
                setText(transportDetails);
            }

            return this;
        }
    }
}
