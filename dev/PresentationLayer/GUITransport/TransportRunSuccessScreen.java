package PresentationLayer.GUITransport;

import BussinessLayer.TransPortLayer.Transport;
import ServiceLayer.EmployeesLayer.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransportRunSuccessScreen extends JFrame {
    private ServiceFactory serviceFactory;
    private Transport transport;
    private int currentWeight;
    private int maxWeight;
    private JLabel currentWeightLabel;
    private JLabel maxWeightLabel;
    private JTextField weightInput;

    public TransportRunSuccessScreen(ServiceFactory serviceFactory, Transport transport) {
        this.serviceFactory = serviceFactory;
        this.transport = transport;
        this.currentWeight = transport.getWeightNeto();
        this.maxWeight = (int) transport.getWeightMax();
        setTitle("Transport Run Success");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel titleLabel = new JLabel("Transport Run Success");
        currentWeightLabel = new JLabel("Current Weight: " + currentWeight);
        maxWeightLabel = new JLabel("Max Weight: " + maxWeight);
        JLabel weightInputLabel = new JLabel("Enter Weight:");
        weightInput = new JTextField(10);
        JButton addButton = new JButton("Add");
        JButton finishButton = new JButton("Finish");

        // Set layout manager
        setLayout(new BorderLayout());

        // Create panel for the components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create panel for the title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);

        // Create panel for the weight labels
        JPanel weightPanel = new JPanel(new GridLayout(2, 1));
        weightPanel.add(currentWeightLabel);
        weightPanel.add(maxWeightLabel);

        // Create panel for weight input and buttons
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(weightInputLabel);
        inputPanel.add(weightInput);
        inputPanel.add(addButton);
        inputPanel.add(finishButton);

        // Add title panel, weight panel, and input panel to the main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(weightPanel, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Add main panel to the frame
        add(mainPanel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String weightText = weightInput.getText();
                try {
                    int weightToAdd = Integer.parseInt(weightText);
                    currentWeight += weightToAdd;
                    currentWeightLabel.setText("Current Weight: " + currentWeight);
                    weightInput.setText("");
                    if (currentWeight > maxWeight) {
                        handleOverweight();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid weight input. Please enter a valid number.");
                }
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current screen
                JOptionPane.showMessageDialog(null, "Transport ran successfully!");
            }
        });

        // Set the frame size and make it visible
        setSize(400, 200); // Adjust the size as needed
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void handleOverweight() {
        String[] options = {"Change Truck", "Drop Items", "Change Destination"};
        int choice = JOptionPane.showOptionDialog(null, "Error: Current weight exceeds maximum weight.", "Overweight",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                openChangeTruckScreen();
                break;
            case 1:
                openDropItemsScreen();
                break;
            case 2:
                openChangeDestinationScreen();
                break;
        }
    }

    private void openChangeTruckScreen() {
        dispose(); // Close the current screen
        // Open the ChangeTruckScreen with the updated maximum weight (200 higher)
        new TransportRunSuccessScreen(serviceFactory, transport);
    }

    private void openDropItemsScreen() {
        dispose(); // Close the current screen
        // Open the DropItemsScreen
        new TransportRunSuccessScreen(serviceFactory, transport);
    }

    private void openChangeDestinationScreen() {
        dispose(); // Close the current screen
        // Open the ChangeDestinationScreen
        new TransportRunSuccessScreen(serviceFactory, transport);
    }
}
