package PersentationLayer.inventory;

import javax.swing.*;
import java.awt.*;

public class test {
    JFrame frame=new JFrame();
    JTextField textField;
    JButton button;
    JLabel label;
    private JLabel kjbsadklvbksdbvkas;

    public test(){
        label=new JLabel();
        label.setVisible(true);
        label.setText("Welcome StoreKeeper");
        label.setHorizontalAlignment(JLabel.CENTER);//Set label horizontal position (LEFT,CENTER,RIGHT)
        label.setVerticalAlignment(JLabel.CENTER);//Set label vertical position (TOP,CENTER,BOTTOM)
        label.setForeground(new Color(0x00FF00));//set text-color
        label.setFont(new Font(null,Font.PLAIN,20));//Set the font

        frame=new JFrame();
        frame.setTitle("JFrame title is here");//Set the name of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Set what the CloseButton does
        frame.setResizable(false);//Set if the window can be resized or not
        frame.setSize(420,420);//Sets the X-dimension, and Y-dimension
        frame.add(label);
        frame.setVisible(true);//Make frame visible
    }

}
