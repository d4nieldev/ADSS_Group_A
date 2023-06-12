package PersentationLayer.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StorekeeperFrame implements ActionListener {
    JFrame frame=new JFrame();
    JTextField textField;
    JButton button1;
    JButton button2;
    JLabel label;
    JPanel panel;
//    String textLabel="<html><br><br><br>You Wrote: </html>";// ATENTION: THE TEXT BOX ACCEPT HTML TAGS
    public StorekeeperFrame(){
        label = new JLabel();
        label.setVisible(true);
        label.setText("Welcome StoreKeeper");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.NORTH);
        label.setForeground(Color.BLACK);
        label.setFont(new Font(null, Font.PLAIN, 20));
        label.setVisible(true);

        frame = new JFrame();
        frame.setTitle("JFrame title is here");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(420, 420);

        frame.setLayout(new BorderLayout()); // Set the layout manager for the frame

// Add label at the top center
        frame.add(label, BorderLayout.NORTH);

        button1 = new JButton("Manage storage");
        button1.addActionListener(this);

        button2 = new JButton("Order supply");
        button2.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(250, 250));
        panel.setBackground(Color.lightGray);
        panel.setLayout(new FlowLayout());

        panel.add(button1);
        panel.add(button2);
        frame.add(panel, BorderLayout.CENTER); // Add the panel to the center region

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1){
            frame.dispose();
            //open new ManageStorage Window
            MangeStorageFrame mangeStorageFrame = new MangeStorageFrame();
        }
        if (e.getSource() == button2){
            frame.dispose();
            //open new OrderSupply Window
            OrderSupplyFrame orderSupplyFrame = new OrderSupplyFrame();
        }
    }
}
