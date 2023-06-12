package PersentationLayer.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MangeStorageFrame {
    JFrame frame=new JFrame();
    JLabel label;
    JButton button;

    public MangeStorageFrame(){
        JLabel label = new JLabel();
        label.setText("Manage storage window");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setForeground(Color.BLACK);
        label.setFont(new Font(null, Font.PLAIN, 20));

        JButton button = new JButton("back");

        JFrame frame = new JFrame();
        frame.setTitle("Manage storage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(button);

        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(label, BorderLayout.CENTER);

        frame.setContentPane(contentPane);
        frame.setVisible(true);

    }
}
