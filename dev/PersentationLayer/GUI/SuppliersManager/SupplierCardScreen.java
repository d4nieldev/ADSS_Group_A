package PersentationLayer.GUI.SuppliersManager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ServiceLayer.Suppliers.SupplierService;

public class SupplierCardScreen extends JFrame{

    private int y = 0;
    private SupplierService service;
    private JFrame previousFrame;
    public SupplierCardScreen(JFrame previousFrame, boolean isBeforeDeletion, int supplierId){
        setTitle("Suppleir View");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.previousFrame = previousFrame;
        this.service = SupplierService.create();
        add(createSupplierPanel(service.getSupplierCard(supplierId)));
        // Set the frame size and make it visible
        setSize(400, 200);
        setLocationRelativeTo(previousFrame);
        setVisible(true);
    }

    private void errorOccured(String errorMsg){
        JOptionPane.showMessageDialog(this, errorMsg);
        dispose();
        previousFrame.setVisible(true);
    }
     /**
     * a map that contains: name, bankAcc, fields, paymentCondition, phone,
     * amountToDiscount (map), contacts (map) for each supplier.
     * 
     * if the supplier is of type on order: the map also contains maxSupplyDays
     * (integer)
     * 
     * if the supplier is of type fixed days: the map also contains days (list of
     * integers - monday = 1, sunday = 7)
     * 
     * if the supplier is of type self pickup: the map also contains address and
     * maxPreperationDays (integer)
     **/
    private JPanel createSupplierPanel(Map<String, Object> dataMap) {
        if(dataMap.get("error") != null){
            errorOccured((String)(dataMap.get("error")));
        }
        String supplierName = (String) (dataMap.get("name"));
        String supplierPhone = (String) (dataMap.get("phone"));
        String supplierBankAcc = (String) (dataMap.get("bankAcc"));
        List<String> fields = (List<String>) (dataMap.get("fields"));
        String paymentCondition = (String) (dataMap.get("paymentCondition"));
        Map<Integer,String> amountToDiscount = (Map<Integer, String>) (dataMap.get("amountToDiscount"));
        Map<String,String> contacts = (Map<String, String>) (dataMap.get("contacts"));
        if(dataMap.containsKey("maxSupplyDays")){
            //The supplier is of type onOrderSupplier
            Integer maxSupplyDays = (Integer) (dataMap.get("maxSupplyDays"));
            
        }else if(dataMap.containsKey("days")){
            //The supplier is of type fixedDaysSupplier
            List<Integer> days = (List<Integer>) (dataMap.get("days"));
        }else{
            //The supplier is of type selfPickupSupplier
            String address = (String) (dataMap.get("address"));
            Integer maxPreperationDays = (Integer) (dataMap.get("maxPreperationDays"));
        }

    }
}
