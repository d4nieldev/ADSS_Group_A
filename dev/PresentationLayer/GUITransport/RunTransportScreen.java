package PresentationLayer.GUITransport;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ServiceLayer.EmployeesLayer.ServiceFactory;

public class RunTransportScreen extends JFrame {
    private JTextField dateTextField;
    private JButton runTransportButton;
    private JButton backButton;
    private ServiceFactory serviceFactory;

    public RunTransportScreen(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        setTitle("run Transport");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel titleLabel = new JLabel("Date of Transport (YYYY-MM-DD)");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11));
        dateTextField = new JTextField(15);
        runTransportButton = new JButton("run");
        backButton = new JButton("Back");

        // Set layout manager
        setLayout(new BorderLayout());

        // Create panel for the form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Add title label and date text field to the form panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        formPanel.add(titleLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(dateTextField, constraints);

        // Create panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(runTransportButton);
        buttonPanel.add(backButton);

        // Set the preferred size of the text field and buttons
        Dimension buttonSize = new Dimension(150, 30);
        dateTextField.setPreferredSize(buttonSize);
        runTransportButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        // Add form panel and button panel to the frame
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        runTransportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = dateTextField.getText();
                // TODO: Perform further processing with the entered date

                dispose(); // Close the current screen
                new RunTransportByDateScreen(serviceFactory,date); // Open the createTransportByDateScreen
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current screen
                new TransportManagerScreen(serviceFactory); // Open the TransportManagerScreen
            }
        });

        // Set the frame size and make it visible
        setSize(400, 200); // Adjust the size as needed
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
