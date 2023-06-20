package PersentationLayer.GUI;

import PersentationLayer.GUI.Tasks.AddPeriodicReservationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderSupplyFrame implements ActionListener {
    JFrame frame;
    JTextField textField;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton backButton;
    JLabel label;
    JPanel panel;

    public OrderSupplyFrame(){
        frame = new JFrame();
        frame.setTitle("JFrame title is here");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create the top panel with BorderLayout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(59, 89, 152)); // Blue color
        // Configure back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(new Color(0, 122, 255)); // Blue color
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false); // Remove button border
        backButton.setFocusPainted(false); // Remove button focus border
        backButton.setVisible(false);

        // Configure panel for back button and label
        topPanel.add(backButton, BorderLayout.WEST);

        // Create the label
        JLabel label = new JLabel("Welcome Store keeper");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.NORTH);
        label.setForeground(Color.WHITE);
        label.setFont(new Font(null, Font.PLAIN, 20));
        label.setVisible(true);
        topPanel.add(label, BorderLayout.CENTER);

        // Create the main panel for buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0)); // Add space above the components

        // Create the "Choose your action" label
        JLabel chooseActionLabel = new JLabel("Choose your action");
        chooseActionLabel.setHorizontalAlignment(JLabel.CENTER);
        chooseActionLabel.setFont(new Font(null, Font.PLAIN, 18));

        // Create a sub-panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        // Create the buttons
        button1 = new JButton("Manage storage");
        button1.addActionListener(this);
        button1.setPreferredSize(new Dimension(200, 50)); // Set button size

        button2 = new JButton("Manage suppliers order");
        button2.addActionListener(this);
        button2.setPreferredSize(new Dimension(200, 50)); // Set button size



        // Add the buttons to the button panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        // Add the "Choose your action" label and button panel to the main panel
        mainPanel.add(chooseActionLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the top panel and main panel to the frame's content pane
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1){
            frame.dispose();
            //open new ManageStorage Window
            AddPeriodicReservationFrame AddPeriodicReservationFrame = new AddPeriodicReservationFrame();
        }
        if (e.getSource() == button2){
            frame.dispose();
            //open new OrderSupply Window
            AddPeriodicReservationFrame AddPeriodicReservationFrame = new AddPeriodicReservationFrame();
        }
        if (e.getSource() == backButton){
            frame.dispose();
            //open new OrderSupply Window
            StoreManagerFrame storeManagerFrame = new StoreManagerFrame();

        }

    }
}
