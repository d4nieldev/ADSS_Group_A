package PersentationLayer.GUI;

import PersentationLayer.GUI.Tasks.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OrderSupplyFrame1 implements ActionListener {
    JFrame frame;
    JLabel label;
    JButton backButton;
    JPanel tasksPanel;
    JButton button1 ;
    JButton button2 ;
//    JButton button3 ;
//    JButton button4 ;
//    JButton button5 ;
//    JButton button6 ;

    public OrderSupplyFrame1(){
        // Configure the frame
        frame = new JFrame();
        frame.setTitle("Order supply");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 420);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Configure the main label
        label = new JLabel();
        label.setText("Order supply window");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setForeground(Color.BLACK);
        label.setFont(new Font(null, Font.PLAIN, 20));

        // Configure the "Choose your action" label
        JLabel chooseActionLabel = new JLabel("Choose your action");
        chooseActionLabel.setHorizontalAlignment(JLabel.CENTER);
        chooseActionLabel.setFont(new Font(null, Font.BOLD, 16));

        // Configure back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));

        // Configure tasks buttons
        button1 = new JButton("Add periodic reservation");
        button1.addActionListener(this);
        button2 = new JButton("Update periodic reservation");
        button2.addActionListener(this);
//        button3 = new JButton("Set Discount on Categories");
//        button3.addActionListener(this);
//        button4 = new JButton("Set Discount on Products");
//        button4.addActionListener(this);
//        button5 = new JButton("Button 5");
//        button5.addActionListener(this);
//        button6 = new JButton("Button 6");
//        button6.addActionListener(this);

        // Configure panel for back button, label, and tasks panel
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(backButton);

        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new GridBagLayout());
        tasksPanel.setBackground(Color.WHITE);
        tasksPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 20, 0); // Adjust the bottom inset value to reduce the gap
        tasksPanel.add(chooseActionLabel, gbc);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(2, 3, 10, 10));
        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.add(button1);
        buttonContainer.add(button2);
//        buttonContainer.add(button3);
//        buttonContainer.add(button4);
//        buttonContainer.add(button5);
//        buttonContainer.add(button6);

        // Adjust button size dynamically based on the preferred size of the button text
        Font buttonFont = button1.getFont();
        FontMetrics fontMetrics = button1.getFontMetrics(buttonFont);
        int maxButtonWidth = Math.max(fontMetrics.stringWidth(button1.getText()), fontMetrics.stringWidth(button2.getText()));

        Dimension buttonSize = new Dimension(maxButtonWidth + 20, 50); // Add padding to the button width
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
//        button3.setPreferredSize(buttonSize);
//        button4.setPreferredSize(buttonSize);
//        button5.setPreferredSize(buttonSize);
//        button6.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0); // Reset the bottom inset value
        tasksPanel.add(buttonContainer, gbc);

        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(label, BorderLayout.CENTER);
        contentPane.add(tasksPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.dispose();
            // Go back to StorekeeperFrame
            StoreManagerFrame storeManagerFrame = new StoreManagerFrame();
        }

        if (e.getSource() == button1) {
            frame.dispose();
            AddPeriodicReservationFrame addPeriodicReservationFrame = new AddPeriodicReservationFrame();
        }

        if (e.getSource() == button2) {
            frame.dispose();
            UpdatePeriodicReservationFrame updatePeriodicReservationFrame = new UpdatePeriodicReservationFrame();
        }



    }
}
