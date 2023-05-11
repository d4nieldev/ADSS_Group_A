package BusinessLayer.Suppliers;

import java.util.TreeMap;

import BusinessLayer.InveontorySuppliers.Discount;
import BusinessLayer.exceptions.SuppliersException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Supplier {

    private int id;
    private String name;
    private String bankAcc;
    private List<String> fields;
    private String paymentCondition;
    private TreeMap<Integer, Discount> amountToDiscount;
    private List<Contact> contacts;
    private Map<Integer, PeriodicReservation> branchToPeriodicReservations;

    public Supplier(int id, String name, String phone, String bankAcc, List<String> fields, String paymentCondition,
                    TreeMap<Integer, Discount> amountToDiscount, List<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.bankAcc = bankAcc;
        this.fields = fields;
        this.paymentCondition = paymentCondition;
        this.amountToDiscount = amountToDiscount;
        this.contacts = addOfficeContact(contacts, phone);
        this.branchToPeriodicReservations = new HashMap<>();
    }

    public abstract LocalDate getClosestDeliveryDate();

    public Map<Integer, PeriodicReservation> getBranchToPeriodicReservations() {
        return branchToPeriodicReservations;
    }

    public void setBranchToPeriodicReservations(Map<Integer, PeriodicReservation> branchToPeriodicReservations) {
        this.branchToPeriodicReservations = branchToPeriodicReservations;
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
    public TreeMap<Integer, Discount> getAmountToDiscount() {
        return amountToDiscount;
    }

    public void setAmountToDiscount(TreeMap<Integer, Discount> amountToDiscount) {
        this.amountToDiscount = amountToDiscount;
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

    public void setContacts(LinkedList<Contact> contacts) {
        this.contacts = contacts;
    }

    public PeriodicReservation getPeriodicReservationOfBranch(int branchId) {
        if (!branchToPeriodicReservations.containsKey(branchId))
            throw new SuppliersException(
                    "This supplier does not have a periodic reservation to the branch " + branchId);
        return branchToPeriodicReservations.get(branchId);
    }

    public void putPeriodicReservation(int branchId, PeriodicReservation reservation) {
        branchToPeriodicReservations.put(branchId, reservation);
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

    // Delete all contacts (office number is still existing)
    public void deleteAllContacts() {
        Contact office = new Contact(getOffice().getPhone(), "Office");
        this.contacts.clear();
        this.contacts.add(office);
    }

    // Add office contact for the begining of the list of contacts
    private List<Contact> addOfficeContact(List<Contact> contacts, String officePhone) {
        List<Contact> newContacts = new LinkedList<>();
        Contact office = new Contact(officePhone, "Office");
        newContacts.add(office);
        for (Contact c : contacts) {
            newContacts.add(c);
        }
        return newContacts;
    }

    public Discount getDiscount(int amount) {
        Discount discount = null;
        Integer key = amountToDiscount.floorKey(amount);
        if (key != null) {
            discount = amountToDiscount.get(key);
        }
        return discount;
    }

    @Override
    public String toString() {
        return "Supplier [id=" + id + ", name=" + name + ", phone=" + getPhone() + ", bankAcc=" + bankAcc + ", fields="
                + fields
                + ", paymentCondition=" + paymentCondition + ", amountToDiscount=" + amountToDiscount + "\ncontacts="
                + contacts + "]";

    }

}
