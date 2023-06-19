package PresentationLayer.GUITransport;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import ServiceLayer.EmployeesLayer.ServiceFactory;

public class TransportManagerScreen extends JFrame {
    private JButton createTransportsButton;
    private JButton runTransportsButton;
    private ServiceFactory serviceFactory;

    public TransportManagerScreen(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        setTitle("Welcome Transport Manager!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        createTransportsButton = new JButton("Create Transports");
        runTransportsButton = new JButton("Run Transports");

        // Set button sizes
        int buttonWidth = 200; // Adjust the width as needed
        int buttonHeight = 25;
        createTransportsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        runTransportsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        // Create empty components for centering the buttons
        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();

        // Set the layout manager for the empty panels
        emptyPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        emptyPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Set the layout manager for the main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(emptyPanel1, constraints);
        constraints.gridy = 2;
        mainPanel.add(emptyPanel2, constraints);
        constraints.gridy = 1;
        mainPanel.add(createTransportsButton, constraints);
        constraints.gridy = 3;
        mainPanel.add(runTransportsButton, constraints);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Add action listeners to the buttons
        createTransportsButton.addActionListener(e -> {
            dispose(); // Close the current screen
            new CreateTransportScreen(serviceFactory); // Open a new screen
        });

        runTransportsButton.addActionListener(e -> {
            dispose(); // Close the current screen
            new RunTransportScreen(serviceFactory); // Open a new screen
        });

        // Set the frame size and make it visible
        setSize(500, 300); // Adjust the size as needed
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
