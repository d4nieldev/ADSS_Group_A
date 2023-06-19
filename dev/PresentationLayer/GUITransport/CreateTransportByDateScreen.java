package PresentationLayer.GUITransport;

import javax.swing.*;
import BussinessLayer.EmployeesLayer.Driver;
import BussinessLayer.TransPortLayer.Delivery;
import BussinessLayer.TransPortLayer.Truck;
import BussinessLayer.TransPortLayer.Status;
import BussinessLayer.TransPortLayer.Destination;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import ServiceLayer.EmployeesLayer.ServiceFactory;
import ServiceLayer.TransportLayer.TransportService;

public class CreateTransportByDateScreen extends JFrame {
    private ServiceFactory serviceFactory;
    private String date;
    private List<DeliveryCheckBox> deliveryCheckboxes;
    private JList<Truck> truckList;
    private JList<String> driverList;
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
        JButton createButton = new JButton("Create Transport");

        // Create lists
        List<Delivery> deliveries = transportService.getDeliveries(date);
        deliveryCheckboxes = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            deliveryCheckboxes.add(new DeliveryCheckBox(delivery));
        }
        JPanel deliveryPanel = new JPanel(new GridLayout(deliveryCheckboxes.size(), 1));
        for (DeliveryCheckBox checkbox : deliveryCheckboxes) {
            deliveryPanel.add(checkbox);
        }
        JScrollPane deliveryScrollPane = new JScrollPane(deliveryPanel);

        List<Truck> trucks = transportService.getTrucks(date);
        DefaultListModel<Truck> truckListModel = new DefaultListModel<>();
        for (Truck truck : trucks) {
            truckListModel.addElement(truck);
        }
        truckList = new JList<>(truckListModel);
        JScrollPane truckScrollPane = new JScrollPane(truckList);

        List<Driver> drivers = transportService.getDrivers(date);
        DefaultListModel<String> driverListModel = new DefaultListModel<>();
        for (Driver driver : drivers) {
            String driverInfo = "ID: " + driver.getId() + ", Full Name: " + driver.getFirstName()+" "+driver.getLastName() + ", License: " + driver.getDriverLicense();
            driverListModel.addElement(driverInfo);
        }
        driverList = new JList<>(driverListModel);
        JScrollPane driverScrollPane = new JScrollPane(driverList);

        // Set layout manager
        setLayout(new BorderLayout());

        // Create panel for the lists
        JPanel listPanel = new JPanel(new GridLayout(1, 3));
        listPanel.add(deliveryScrollPane);
        listPanel.add(truckScrollPane);
        listPanel.add(driverScrollPane);

        // Create panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createButton);
        buttonPanel.add(backButton);

        // Add list panel and button panel to the frame
        add(listPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current screen
                new CreateTransportScreen(serviceFactory); // Open the CreateTransportScreen
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (truckList.isSelectionEmpty() || driverList.isSelectionEmpty() || getSelectedDeliveries().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a truck, driver, and at least one delivery.");
                } else {
                    Truck selectedTruck = truckList.getSelectedValue();
                    String selectedDriverInfo = driverList.getSelectedValue();
                    int driverId = Integer.parseInt(selectedDriverInfo.split(",")[0].split(":")[1].trim());

                    Driver selectedDriver = getDriver(driverId,drivers);

                    if (selectedDriver.getDriverLicense().name().equals(selectedTruck.getModel())) {
                        // Call the transportService.createTransportByDate method with the selected driver, truck, and deliveries
                        transportService.createTransportByDate(date, selectedDriver, selectedTruck, getSelectedDeliveries());

                        dispose(); // Close the current screen
                        // Show a success message or open a new screen
                        JOptionPane.showMessageDialog(null, "Transport created successfully!");
                        new CreateTransportScreen(serviceFactory); // Open the CreateTransportScreen
                    } else {
                        JOptionPane.showMessageDialog(null, "Driver's license does not match the selected truck's model. Please select a different driver or truck.");
                    }
                }
            }

            private Driver getDriver(int driverId, List<Driver> drivers) {
                for (Driver driver : drivers) {
                    if (driver.getId() == driverId) {
                        return driver;
                    }
                }
                return null; // Return null if no driver with the matching ID is found
            }

            private List<Delivery> getSelectedDeliveries() {
                List<Delivery> selectedDeliveries = new ArrayList<>();
                for (DeliveryCheckBox checkbox : deliveryCheckboxes) {
                    if (checkbox.isSelected()) {
                        selectedDeliveries.add(checkbox.getDelivery());
                    }
                }
                return selectedDeliveries;
            }
        });

        // Set the frame size and make it visible
        setSize(1000, 400); // Adjust the size as needed
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    // Custom class for the delivery checkbox
    private class DeliveryCheckBox extends JCheckBox {
        private Delivery delivery;

        public DeliveryCheckBox(Delivery delivery) {
            super(getDeliveryText(delivery));
            this.delivery = delivery;
        }

        public Delivery getDelivery() {
            return delivery;
        }

        private static String getDeliveryText(Delivery delivery) {
            StringBuilder builder = new StringBuilder();
            builder.append("ID: ").append(delivery.getId()).append(", ");
            builder.append("Source: ").append(delivery.getSource().getAddress()).append(", ");
            builder.append("Destination: ").append(delivery.getDest().getAddress()).append(", ");
            builder.append("Status: ").append(delivery.getStatus().name());
            return builder.toString();
        }

        @Override
        public String toString() {
            return getDeliveryText(delivery);
        }
    }
}
