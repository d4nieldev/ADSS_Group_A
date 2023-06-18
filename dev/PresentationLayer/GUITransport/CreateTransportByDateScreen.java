package PresentationLayer.GUITransport;

import javax.swing.*;
import BussinessLayer.EmployeesLayer.Driver;
import BussinessLayer.TransPortLayer.Delivery;
import BussinessLayer.TransPortLayer.Truck;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import ServiceLayer.EmployeesLayer.ServiceFactory;
import ServiceLayer.TransportLayer.TransportService;

public class CreateTransportByDateScreen extends JFrame {
    private ServiceFactory serviceFactory;
    private String date;
    private JList<Delivery> deliveryList;
    private JList<Truck> truckList;
    private JList<Driver> driverList;
    private TransportService transportService;

    public CreateTransportByDateScreen(ServiceFactory serviceFactory, String date) {
        this.serviceFactory = serviceFactory;
        this.date = date;
        this.transportService = serviceFactory.getTransportService();
        setTitle("Create Transport by Date");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel deliveryLabel = new JLabel("Delivery List:");
        JLabel truckLabel = new JLabel("Truck List:");
        JLabel driverLabel = new JLabel("Driver List:");
        JButton backButton = new JButton("Back");

        // Create lists
        List<Delivery> deliveries = transportService.getDeliveries(date);
        DefaultListModel<Delivery> deliveryListModel = new DefaultListModel<>();
        for (Delivery delivery : deliveries) {
            deliveryListModel.addElement(delivery);
        }
        deliveryList = new JList<>(deliveryListModel);
        JScrollPane deliveryScrollPane = new JScrollPane(deliveryList);

        List<Truck> trucks = transportService.getTrucks(date);
        DefaultListModel<Truck> truckListModel = new DefaultListModel<>();
        for (Truck truck : trucks) {
            truckListModel.addElement(truck);
        }
        truckList = new JList<>(truckListModel);
        JScrollPane truckScrollPane = new JScrollPane(truckList);

        List<Driver> drivers = transportService.getDrivers(date);
        DefaultListModel<Driver> driverListModel = new DefaultListModel<>();
        for (Driver driver : drivers) {
            driverListModel.addElement(driver);
        }
        driverList = new JList<>(driverListModel);
        JScrollPane driverScrollPane = new JScrollPane(driverList);

        // Set layout manager
        setLayout(new GridLayout(1, 4));

        // Create panel for the lists
        JPanel listPanel = new JPanel(new GridLayout(3, 1));
        listPanel.add(deliveryLabel);
        listPanel.add(deliveryScrollPane);
        listPanel.add(truckLabel);
        listPanel.add(truckScrollPane);
        listPanel.add(driverLabel);
        listPanel.add(driverScrollPane);

        // Create panel for the back button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);

        // Add list panel and button panel to the frame
        add(listPanel);
        add(buttonPanel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current screen
                new CreateTransportScreen(serviceFactory); // Open the CreateTransportScreen
            }
        });

        // Set the frame size and make it visible
        setSize(800, 400); // Adjust the size as needed
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
