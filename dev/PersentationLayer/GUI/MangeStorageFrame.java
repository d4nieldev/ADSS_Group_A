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

public class MangeStorageFrame implements ActionListener {
    JFrame frame;
    JLabel label;
    JButton backButton;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JButton button5;
    JButton button6;
    JButton button7;

    public MangeStorageFrame() {
        // Configure the frame
        frame = new JFrame();
        frame.setTitle("Manage Storage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 420);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Configure the main label
        label = new JLabel();
        label.setText("Manage Storage Window");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 20));

        // Configure the "Choose your action" label
        JLabel chooseActionLabel = new JLabel("Choose Your Action");
        chooseActionLabel.setHorizontalAlignment(JLabel.CENTER);
        chooseActionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Configure back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(new Color(0, 122, 255)); // Blue color
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false); // Remove button border
        backButton.setFocusPainted(false); // Remove button focus border

        // Configure tasks buttons
        button1 = new JButton("Report Flaw Product");
        button1.addActionListener(this);
        button2 = new JButton("Add New Product Branch");
        button2.addActionListener(this);
        button3 = new JButton("Set Discount on Categories");
        button3.addActionListener(this);
        button4 = new JButton("Set Discount on Products");
        button4.addActionListener(this);
        button5 = new JButton("Add New Category");
        button5.addActionListener(this);
        button6 = new JButton("Add New Specific Product");
        button6.addActionListener(this);
        button7 = new JButton("Add New General Product");
        button7.addActionListener(this);

        // Configure panel for back button and label
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(59, 89, 152)); // Blue color
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(label, BorderLayout.CENTER);

        // Configure panel for tasks buttons
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new GridBagLayout());
        tasksPanel.setBackground(Color.WHITE);
        tasksPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 10, 0); // Adjust the bottom inset value to reduce the gap
        tasksPanel.add(chooseActionLabel, gbc);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(2, 3, 10, 10));
        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.add(button1);
        buttonContainer.add(button2);
        buttonContainer.add(button3);
        buttonContainer.add(button4);
        buttonContainer.add(button5);
        buttonContainer.add(button6);
        buttonContainer.add(button7);

        // Adjust button size dynamically based on the preferred size of the button text
        Font buttonFont = button1.getFont();
        FontMetrics fontMetrics = button1.getFontMetrics(buttonFont);
        int maxButtonWidth = Math.max(
                Math.max(fontMetrics.stringWidth(button1.getText()), fontMetrics.stringWidth(button2.getText())),
                Math.max(fontMetrics.stringWidth(button3.getText()), fontMetrics.stringWidth(button4.getText())));
        Dimension buttonSize = new Dimension(maxButtonWidth + 40, 50); // Add padding to the button width
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        button4.setPreferredSize(buttonSize);
        button5.setPreferredSize(buttonSize);
        button6.setPreferredSize(buttonSize);
        button7.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0); // Reset the bottom inset value
        tasksPanel.add(buttonContainer, gbc);

        // Configure the content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(tasksPanel, BorderLayout.CENTER);

        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.dispose();
            // Go back to StorekeeperFrame
            StorekeeperFrame storekeeperFrame = new StorekeeperFrame();
        }

        if (e.getSource() == button1) {
            frame.dispose();
            FlawProductFrame flawProductFrame = new FlawProductFrame();
        }

        if (e.getSource() == button2) {
            frame.dispose();
            AddNewProductBranchFrame addNewProductFrame = new AddNewProductBranchFrame();
        }
        if (e.getSource() == button3) {
            frame.dispose();
            SetDiscountOnCategoriesFrame setDiscountOnCategoriesFrame = new SetDiscountOnCategoriesFrame();
        }
        if (e.getSource() == button4) {
            frame.dispose();
            SetDiscountOnProductsFrame setDiscountOnProductsFrame = new SetDiscountOnProductsFrame();
        }
        if (e.getSource() == button5) {
            frame.dispose();
            AddCategoryFrame addCategoryFrame = new AddCategoryFrame();
        }
        if (e.getSource() == button6) {
            frame.dispose();
            new AddSpecificProductFrame();
        }
        if (e.getSource() == button7) {
            frame.dispose();
            new AddGeneralProductFrame();
        }

    }

}
