package BusinessLayer.Suppliers;

import java.util.TreeMap;

import BusinessLayer.InveontorySuppliers.Discount;
import BusinessLayer.InveontorySuppliers.DiscountFixed;
import BusinessLayer.InveontorySuppliers.DiscountPercentage;
import BusinessLayer.InveontorySuppliers.PeriodicReservation;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DTOs.ContactDTO;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.PeriodicReservationDTO;
import DataAccessLayer.DTOs.SupplierDTO;
import DataAccessLayer.DTOs.SuppliersFieldsDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
    private SupplierDTO supplierDTO;

    // public Supplier(int id, String name, String phone, String bankAcc,
    // List<String> fields, String paymentCondition,
    // TreeMap<Integer, Discount> amountToDiscount, List<Contact> contacts) {
    // this.id = id;
    // this.name = name;
    // this.bankAcc = bankAcc;
    // this.fields = fields;
    // this.paymentCondition = paymentCondition;
    // this.amountToDiscount = amountToDiscount;
    // this.contacts = addOfficeContact(contacts, phone);
    // this.branchToPeriodicReservations = new HashMap<>();
    // //this.supplierDTO = new SupplierDTO(id, name, bankAcc, paymentCondition,
    // fields, makeContactDTOList(contacts), );
    // }

    public Supplier(SupplierDTO supplierDTO) {
        this.supplierDTO = supplierDTO;
        this.id = supplierDTO.getId();
        this.name = supplierDTO.getName();
        this.bankAcc = supplierDTO.getBankAccount();
        this.fields = makeFieldsList(supplierDTO.getFields());
        this.paymentCondition = supplierDTO.getPaymentCondition();
        TreeMap<Integer, DiscountDTO> amountToDiscountDTO = supplierDTO.getAmountToDiscount();
        this.amountToDiscount = makeDiscountMap(amountToDiscountDTO);
        this.contacts = makeContactList(supplierDTO.getContacts());
        Map<Integer, PeriodicReservationDTO> periodicReservationDTO = supplierDTO.getBranchToPeriodicReservations();
        this.branchToPeriodicReservations = makePeriodicalReservations(periodicReservationDTO);
    }

    private Map<Integer, PeriodicReservation> makePeriodicalReservations(
            Map<Integer, PeriodicReservationDTO> periodicReservationDTO) {
        Map<Integer, PeriodicReservation> res = new HashMap<>();
        for (Integer key : periodicReservationDTO.keySet()) {
            PeriodicReservationDTO dto = periodicReservationDTO.get(key);
            // TODO: what going on here? why we make pr and leave it like this?
            PeriodicReservation pr = new PeriodicReservation(dto);
        }
        return res;
    }

    private List<Contact> makeContactList(List<ContactDTO> contactDTOs) {
        List<Contact> contacts = new ArrayList<Contact>();
        for (ContactDTO c : contactDTOs) {
            contacts.add(new Contact(c));
        }
        return contacts;
    }

    private TreeMap<Integer, Discount> makeDiscountMap(TreeMap<Integer, DiscountDTO> amountToDiscountDTO) {
        TreeMap<Integer, Discount> res = new TreeMap<>();
        for (Integer key : amountToDiscountDTO.keySet()) {
            // we need to add
            DiscountDTO dto = amountToDiscountDTO.get(key);
            if (dto.getdType().equals("Fixed")) {
                DiscountFixed df = new DiscountFixed(dto);
                res.put(key, df);
            } else {
                DiscountPercentage dp = new DiscountPercentage(dto);
                res.put(key, dp);
            }
        }
        return res;
    }

    private List<String> makeFieldsList(List<SuppliersFieldsDTO> fieldsDTOs) {
        List<String> fields = new ArrayList<>();
        for (SuppliersFieldsDTO f : fieldsDTOs) {
            fields.add(f.getFieldName());
        }
        return fields;
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

    // Getter and setter for name
    public String getName() {
        return name;
    }

    // Getter and setter for phone
    public String getPhone() {
        return getOffice().getPhone();
    }

    // Getter and setter for bankAcc
    public String getBankAcc() {
        return bankAcc;
    }

    // Getter and setter for fields
    public List<String> getFields() {
        return fields;
    }

    // Getter and setter for paymentCondition
    public String getPaymentCondition() {
        return paymentCondition;
    }

    // Getter and setter for amountToDiscount
    public TreeMap<Integer, Discount> getAmountToDiscount() {
        return amountToDiscount;
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

    public Discount getDiscount(int amount) {
        Discount discount = null;
        Integer key = amountToDiscount.floorKey(amount);
        if (key != null) {
            discount = amountToDiscount.get(key);
        }
        return discount;
    }

    public SupplierDTO getDTO() {
        return this.supplierDTO;
    }

    @Override
    public String toString() {
        return "Supplier [id=" + id + ", name=" + name + ", phone=" + getPhone() + ", bankAcc=" + bankAcc + ", fields="
                + fields
                + ", paymentCondition=" + paymentCondition + ", amountToDiscount=" + amountToDiscount + "\ncontacts="
                + contacts + "]";

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    public void setPaymentCondition(String paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    public void addField(String field) {
        this.fields.add(field);
    }

    public void removeField(String field) {
        this.fields.remove(field);
    }

}
