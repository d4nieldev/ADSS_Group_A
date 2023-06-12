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

        JButton button = new JButton("back");

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
