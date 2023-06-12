package PersentationLayer.inventory;

import javax.swing.*;
import java.awt.*;

public class OrderSupplyFrame {
    JFrame frame = new JFrame();
    JLabel label;

    public OrderSupplyFrame() {
        label = new JLabel();
        label.setVisible(true);
        label.setText("Order Supply window");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.NORTH);
        label.setForeground(Color.BLACK);
        label.setFont(new Font(null, Font.PLAIN, 20));
        label.setVisible(true);

        frame = new JFrame();
        frame.setTitle("Order supply");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(420, 420);

        frame.setLayout(new BorderLayout()); // Set the layout manager for the frame

// Add label at the top center
        frame.add(label, BorderLayout.NORTH);
        frame.setVisible(true);
    }

}
