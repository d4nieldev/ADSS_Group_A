package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SupplierDTO implements DTO {
    protected int id;
    protected String name;
    protected String bankAccount;
    protected String paymentCondition;
    protected List<SuppliersFieldsDTO> fields;
    protected List<ContactDTO> contacts;
    protected TreeMap<Integer, DiscountDTO> amountToDiscount;

    public SupplierDTO(int id, String name, String bankAccount, String paymentCondition,
            List<SuppliersFieldsDTO> fields, List<ContactDTO> contacts, TreeMap<Integer, DiscountDTO> amountToDiscount,
            Map<Integer, PeriodicReservationDTO> branchToPeriodicReservations) {
        this.id = id;
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentCondition = paymentCondition;
        this.fields = fields;
        this.contacts = contacts;
        this.amountToDiscount = amountToDiscount;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("id", "" + id);
        nameToVal.put("name", name);
        nameToVal.put("bankAccount", bankAccount);
        nameToVal.put("paymentCondition", paymentCondition);
        return nameToVal;
    }

    public int getSupplierId() {
        return this.id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getPaymentCondition() {
        return paymentCondition;
    }

    public List<SuppliersFieldsDTO> getFields() {
        return fields;
    }

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public TreeMap<Integer, DiscountDTO> getAmountToDiscount() {
        return amountToDiscount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setPaymentCondition(String paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    public void addField(SuppliersFieldsDTO field) {
        fields.add(field);
    }

    public void removeField(SuppliersFieldsDTO field) {
        fields.remove(field);
    }

    public SuppliersFieldsDTO getFieldDTObyName(String fieldName) {
        for (SuppliersFieldsDTO field : fields) {
            if (field.getFieldName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    public void addContact(ContactDTO contact) {
        contacts.add(contact);
    }

    public void removeContact(ContactDTO contact) {
        contacts.remove(contact);
    }
}
