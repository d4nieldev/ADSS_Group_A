package PresentationLayer.GUITransport;

import javax.swing.*;

public class mainTransportGUI {
    public static void main(String[] args) {
        JFrame fTransport = new JFrame("Make Transport");
        fTransport.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton bNewTransport = new JButton("new transport");
        bNewTransport.setBounds(50,100,95,30);
        fTransport.add(bNewTransport);
        fTransport.setSize(400, 400);

        JButton bChangeTransport = new JButton("change transport");
        bChangeTransport.setBounds(50,100,95,30);
        fTransport.add(bChangeTransport);

        JButton bChangeTruck = new JButton("change truck");
        bChangeTruck.setBounds(50,100,95,30);
        fTransport.add(bChangeTruck);

        JButton bChangeDriver = new JButton("change driver");
        bChangeDriver.setBounds(50,100,95,30);
        fTransport.add(bChangeDriver);
        

        fTransport.setLayout(null);
        fTransport.setVisible(true);


    }
}
