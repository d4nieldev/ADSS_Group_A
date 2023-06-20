package PersentationLayer.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderSupplyFrame implements ActionListener {
    JFrame frame = new JFrame();
    JLabel label;
    JButton backButton;

    public OrderSupplyFrame() {
        label = new JLabel();
        label.setVisible(true);
        label.setText("Order Supply window");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.NORTH);
        label.setForeground(Color.BLACK);
        label.setFont(new Font(null, Font.PLAIN, 20));
        label.setVisible(true);

        backButton = new JButton("back");
        backButton.addActionListener(this);
        backButton.setFont(new Font(null, Font.PLAIN, 14));

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(backButton);

        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(label, BorderLayout.CENTER);

        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.dispose();
            // Go back to StorekeeperFrame
//            storekeeperFrame.goBack();
//            storekeeperFrame.frame
        }
    }

}
