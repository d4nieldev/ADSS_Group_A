package PersentationLayer.GUI;

import PersentationLayer.GUI.Tasks.AddNewProductFrame;
import PersentationLayer.GUI.Tasks.FlawProductFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MangeStorageFrame implements ActionListener {
    JFrame frame;
    JLabel label;
    JButton backButton;
    JPanel tasksPanel;
    JButton button1 ;
    JButton button2 ;

    JButton button3 ;


    public MangeStorageFrame(){
        // Configure the frame
        frame = new JFrame();
        frame.setTitle("Manage storage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

// Configure the main label
        label = new JLabel();
        label.setText("Manage storage window");
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
        backButton.setFont(new Font(null, Font.PLAIN, 14));

// Configure tasks buttons
        button1 = new JButton("Report flaw product");
        button1.addActionListener(this);
        button2 = new JButton("Add new product");
        button2.addActionListener(this);
        button3 = new JButton("Task 3");
        button3.addActionListener(this);

// Configure panel for back button, label, and tasks panel
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(backButton);

        JPanel tasksPanel = new JPanel();
        tasksPanel.setPreferredSize(new Dimension(250, 250));
        tasksPanel.setLayout(new BorderLayout());
        tasksPanel.setBackground(Color.WHITE);
        tasksPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        tasksPanel.add(chooseActionLabel, BorderLayout.NORTH); // Add the label above the buttons

        JPanel buttonContainer = new JPanel(); // Container for the buttons
        buttonContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Align buttons in the center with spacing
        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.add(button1);
        buttonContainer.add(button2);
        buttonContainer.add(button3);

        tasksPanel.add(buttonContainer, BorderLayout.CENTER); // Add the button container to the center of the tasks panel

        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(label, BorderLayout.CENTER);
        contentPane.add(tasksPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.dispose();
            // Go back to StorekeeperFrame
            StorekeeperFrame  storekeeperFrame = new StorekeeperFrame();
        }

        if (e.getSource() == button1) {
            frame.dispose();
            FlawProductFrame flawProductFrame = new FlawProductFrame();
        }

        if (e.getSource() == button2) {
            frame.dispose();

            AddNewProductFrame addNewProductFrame = new AddNewProductFrame();
        }

    }


}
