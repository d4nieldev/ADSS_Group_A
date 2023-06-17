package PersentationLayer.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagerFrame implements ActionListener
{
    JFrame frame;
    JTextField textField;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JLabel label;
    JPanel panel;
    public StoreManagerFrame() {
        frame = new JFrame();
        frame.setTitle("store manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create the top panel with BorderLayout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(59, 89, 152)); // Blue color

        // Create the label
        JLabel label = new JLabel("Welcome Store Manager-main");
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

        button2 = new JButton("Manage suppliers");
        button2.addActionListener(this);
        button2.setPreferredSize(new Dimension(200, 50)); // Set button size

        button3 = new JButton("Reports");
        button3.addActionListener(this);
        button3.setPreferredSize(new Dimension(200, 50)); // Set button size


        // Add the buttons to the button panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

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
            StorekeeperFrame storekeeperFrame = new StorekeeperFrame();
            storekeeperFrame.backButton.setVisible(true);
        }
        //manageSuppliers frame
        if (e.getSource() == button2){
            frame.dispose();
            //open new OrderSupply Window
//            OrderSupplyFrame orderSupplyFrame = new OrderSupplyFrame(this);
        }

        if (e.getSource() == button3){
            frame.dispose();
            //open new Reports Window
            ReportsFrame reportsFrame = new ReportsFrame();
        }
    }

}
