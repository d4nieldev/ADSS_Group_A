package BusinessLayer.Suppliers;

import java.util.Map;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Supplier {

    private int id;
    private String name;
    private String phone;
    private String bankAcc;
    private List<String> fields;
    private String paymentCondition;
    private Map<Integer, Double> amountToDiscount;
    private Queue<Integer> reservationHistory;
    private List<Contact> contacts;

    // Copy Constructor 
    public Supplier(int id, String name, String phone, String bankAcc, List<String> fields, String paymentCondition,
     Map<Integer, Double> amountToDiscount, Queue<Integer> reservationHistory, List<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.bankAcc = bankAcc;
        this.fields = fields;
        this.paymentCondition = paymentCondition;
        this.amountToDiscount = amountToDiscount;
        this.reservationHistory = reservationHistory;
        this.contacts = contacts;
     }

    // Constructor without contacts, reservation history and fields
    public Supplier(int id, String name, String phone, String bankAcc, String paymentCondition,
    Map<Integer, Double> amountToDiscount) {
       this.id = id;
       this.name = name;
       this.phone = phone;
       this.bankAcc = bankAcc;
       this.fields = new LinkedList<>();
       this.paymentCondition = paymentCondition;
       this.amountToDiscount = amountToDiscount;
       this.reservationHistory = new ArrayDeque<>();
       this.contacts = new LinkedList<>();
    }

    // Constructor without reservation history
    public Supplier(int id, String name, String phone, String bankAcc,List<String> fields, String paymentCondition,
    Map<Integer, Double> amountToDiscount, List<Contact> contacts) {
       this.id = id;
       this.name = name;
       this.phone = phone;
       this.bankAcc = bankAcc;
       this.fields = fields;
       this.paymentCondition = paymentCondition;
       this.amountToDiscount = amountToDiscount;
       this.reservationHistory = new ArrayDeque<>();
       this.contacts = contacts;
    }

    // Constructor without reservation history and contacts
    public Supplier(int id, String name, String phone, String bankAcc,List<String> fields, String paymentCondition,
    Map<Integer, Double> amountToDiscount) {
       this.id = id;
       this.name = name;
       this.phone = phone;
       this.bankAcc = bankAcc;
       this.fields = fields;
       this.paymentCondition = paymentCondition;
       this.amountToDiscount = amountToDiscount;
       this.reservationHistory = new ArrayDeque<>();
       this.contacts = new LinkedList<>();
    }

    // Getter and setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter and setter for bankAcc
    public String getBankAcc() {
        return bankAcc;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    // Getter and setter for fields
    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    // Getter and setter for paymentCondition
    public String getPaymentCondition() {
        return paymentCondition;
    }

    public void setPaymentCondition(String paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    // Getter and setter for amountToDiscount
    public Map<Integer, Double> getAmountToDiscount() {
        return amountToDiscount;
    }

    public void setAmountToDiscount(Map<Integer, Double> amountToDiscount) {
        this.amountToDiscount = amountToDiscount;
    }

    // Getter and setter for reservationHistory
    public Queue<Integer> getReservationHistory() {
        return reservationHistory;
    }

    public void setReservationHistory(Queue<Integer> reservationHistory) {
        this.reservationHistory = reservationHistory;
    }

    //Getter and setter for contacts
    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    //Add a new contact
    public void addContact(Contact contact) throws Exception {
        if(this.contacts.contains(contact)){
            throw new Exception("Contact already exists");
        }

        for (Contact c : contacts) {
            if(c.getPhone().equals(contact.getPhone())){
                throw new Exception("Contact with this phone number already exists");
            }
        }
        this.contacts.add(contact);
    }

    //Delete a contact
    public void deleteContact(Contact contact) throws Exception {
        if(!this.contacts.contains(contact)){
            throw new Exception("Contact not exists");
        }

        this.contacts.remove(contact);
    }

    //Delete all contacts
    public void deleteAllContacts() {
        this.contacts.clear();
    }

}
