package BusinessLayer.Suppliers;

import java.util.Map;
import java.util.LinkedList;
import java.util.List;

public abstract class Supplier {
    private int id;
    private String name;
    private String bankAcc;
    private List<String> fields;
    private String paymentCondition;
    private Map<Integer, Double> amountToPrice;
    private List<Contact> contacts;

    // Copy Constructor
    public Supplier(int id, String name, String phone, String bankAcc, List<String> fields, String paymentCondition,
            Map<Integer, Double> amountToDiscount, List<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.bankAcc = bankAcc;
        this.fields = fields;
        this.paymentCondition = paymentCondition;
        this.amountToPrice = amountToDiscount;
        this.contacts = contacts;

        Contact office = new Contact(phone, "Office");
        contacts.add(office);
    }

    // Constructor without contacts, reservation history and fields
    public Supplier(int id, String name, String phone, String bankAcc, String paymentCondition,
            Map<Integer, Double> amountToDiscount) {
        this.id = id;
        this.name = name;
        this.bankAcc = bankAcc;
        this.fields = new LinkedList<>();
        this.paymentCondition = paymentCondition;
        this.amountToPrice = amountToDiscount;
        this.contacts = new LinkedList<>();
    }

    // Constructor without reservation history and contacts
    public Supplier(int id, String name, String phone, String bankAcc, List<String> fields, String paymentCondition,
            Map<Integer, Double> amountToDiscount) {
        this.id = id;
        this.name = name;
        this.bankAcc = bankAcc;
        this.fields = fields;
        this.paymentCondition = paymentCondition;
        this.amountToPrice = amountToDiscount;
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
        return getOffice().getPhone();
    }

    public void setPhone(String phone) {
        getOffice().setPhone(phone);
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
    public Map<Integer, Double> getAmountToPrice() {
        return amountToPrice;
    }

    public void setAmountToPrice(Map<Integer, Double> amountToDiscount) {
        this.amountToPrice = amountToDiscount;
    }

    // Getter and setter for contacts
    public List<Contact> getContacts() {
        return contacts;
    }

    public Contact getRandomContact() {
        return contacts.get((int) (Math.random() * contacts.size()));
    }

    public Contact getOffice() {
        return contacts.get(0);
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    // Add a new contact
    public void addContact(Contact contact) throws Exception {
        if (this.contacts.contains(contact)) {
            throw new Exception("Contact already exists");
        }

        for (Contact c : contacts) {
            if (c.getPhone().equals(contact.getPhone())) {
                throw new Exception("Contact with this phone number already exists");
            }
        }
        this.contacts.add(contact);
    }

    // Delete a contact
    public void deleteContact(Contact contact) throws Exception {
        if (!this.contacts.contains(contact)) {
            throw new Exception("Contact not exists");
        }

        this.contacts.remove(contact);
    }

    // Delete all contacts
    public void deleteAllContacts() {
        this.contacts.clear();
    }

    @Override
    public String toString() {
        return "Supplier [id=" + id + ", name=" + name + ", phone=" + getPhone() + ", bankAcc=" + bankAcc + ", fields="
                + fields
                + ", paymentCondition=" + paymentCondition + ", amountToDiscount=" + amountToPrice + "\ncontacts="
                + contacts + "]";

    }

}
