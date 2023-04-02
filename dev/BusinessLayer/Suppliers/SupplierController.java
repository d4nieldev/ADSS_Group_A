package BusinessLayer.Suppliers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import BusinessLayer.Suppliers.exceptions.SuppliersException;

import java.util.List;

public class SupplierController {
    private int nextSupplierIdInSystem;
    private Map<Integer, Supplier> idToSupplier;
    private static SupplierController instance = null;

    // Constructor for SupplierController
    private SupplierController() {
        this.idToSupplier = new HashMap<Integer, Supplier>();
        this.nextSupplierIdInSystem = 0;
    }

    public static SupplierController getInstance() {
        if (instance == null)
            instance = new SupplierController();
        return instance;
    }

    // Getter for Supplier by id
    public Supplier getSupplierById(int supplierId) throws SuppliersException {
        if (idToSupplier.containsKey(supplierId)) {
            return idToSupplier.get(supplierId);
        } else {
            throw new SuppliersException("There is no supplier with id " + supplierId + " in the system.");
        }
    }

    // Delete Supplier by id
    public void deleteSupplier(int supplierId) throws Exception {
        if (idToSupplier.containsKey(supplierId)) {
            ProductController.getInstance().deleteAllSupplierAgreements(supplierId);
            idToSupplier.remove(supplierId);
        } else {
            throw new SuppliersException("There is no supplier with id " + supplierId + " in the system.");
        }
    }

    // Add 'Fixed days' supplier to the system
    public void addFixedDaysSupplierBaseAgreement(String supplierName, String supplierPhone, String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, Map<Integer, Double> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, List<Integer> days) {
        try {
            FixedDaysSupplier fds = new FixedDaysSupplier(nextSupplierIdInSystem, supplierName, supplierPhone,
                    supplierBankAccount,
                    supplierFields, paymentCondition, amountToDiscount,
                    makeContactList(contactPhones, contactNames, nextSupplierIdInSystem), days);
            idToSupplier.put(nextSupplierIdInSystem, fds);
            nextSupplierIdInSystem++;
        } catch (Exception e) {
            throw e;
        }
    }

    // Add 'On Order' supplier to the system
    public void addOnOrderSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, Map<Integer, Double> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, int maxSupplyDays) {
        try {
            OnOrderSupplier oos = new OnOrderSupplier(nextSupplierIdInSystem, supplierName, supplierPhone,
                    supplierBankAccount,
                    supplierFields, paymentCondition, amountToDiscount,
                    makeContactList(contactPhones, contactNames, nextSupplierIdInSystem), maxSupplyDays);
            idToSupplier.put(nextSupplierIdInSystem, oos);
            nextSupplierIdInSystem++;
        } catch (Exception e) {
            throw e;
        }
    }

    // Add 'Self Pickup' supplier to the system
    public void addSelfPickupSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, Map<Integer, Double> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, String address) {
        try {
            SelfPickupSupplier spus = new SelfPickupSupplier(nextSupplierIdInSystem, supplierName, supplierPhone,
                    supplierBankAccount,
                    supplierFields, paymentCondition, amountToDiscount,
                    makeContactList(contactPhones, contactNames, nextSupplierIdInSystem), address);
            idToSupplier.put(nextSupplierIdInSystem, spus);
            nextSupplierIdInSystem++;
        } catch (Exception e) {
            throw e;
        }

    }

    // Update supplier name
    public void setSupplierName(int supplierId, String supplierName) throws Exception {
        try {
            getSupplierById(supplierId).setName(supplierName);
        } catch (Exception e) {
            throw e;
        }
    }

    // Update supplier phone
    public void setSupplierPhone(int supplierId, String supplierPhone) throws Exception {
        try {
            getSupplierById(supplierId).setPhone(supplierPhone);
        } catch (Exception e) {
            throw e;
        }
    }

    // Update supplier bank account
    public void setSupplierBankAccount(int supplierId, String supplierBankAccount) throws Exception {
        try {
            getSupplierById(supplierId).setBankAcc(supplierBankAccount);
        } catch (Exception e) {
            throw e;
        }
    }

    // Update supplier fields
    public void setSupplierFields(int supplierId, List<String> supplierFields) throws Exception {
        try {
            getSupplierById(supplierId).setFields(supplierFields);
        } catch (Exception e) {
            throw e;
        }
    }

    // Add supplier field
    public void addSupplierField(int supplierId, String field) throws Exception {
        try {
            getSupplierById(supplierId).getFields().add(field);
        } catch (Exception e) {
            throw e;
        }
    }

    // Update supplier payment condition
    public void setSupplierPaymentCondition(int supplierId, String paymentCondition) throws Exception {
        try {
            getSupplierById(supplierId).setPaymentCondition(paymentCondition);
        } catch (Exception e) {
            throw e;
        }
    }

    // Update supplier discount
    public void setSupplierAmountToDiscount(int supplierId, Map<Integer, Double> amountToDiscount) throws Exception {
        try {
            getSupplierById(supplierId).setAmountToPrice(amountToDiscount);
            ;
        } catch (Exception e) {
            throw e;
        }
    }

    // Add supplier contacts names and phones
    public void setSupplierContactNamesAndPhones(int supplierId, List<String> contactPhones, List<String> contactNames)
            throws Exception {
        try {
            if (contactNames.size() != contactPhones.size()) {
                throw new Exception("Number of contact names and phones does not match");
            }

            for (int i = 0; i < contactNames.size(); i++) {
                addSupplierContact(supplierId, contactPhones.get(i), contactNames.get(i));
            }

        } catch (Exception e) {
            throw e;
        }
    }

    // Add supplier contact
    public void addSupplierContact(int supplierId, String contactPhone, String contactName) throws Exception {
        try {
            Supplier s = getSupplierById(supplierId);
            Contact c = new Contact(contactPhone, supplierId, contactName);
            s.addContact(c);
        } catch (Exception e) {
            throw e;
        }
    }

    // Delete supplier contact
    public void deleteSupplierContact(int supplierId, String contactPhone, String contactName) throws Exception {
        try {
            Supplier s = getSupplierById(supplierId);
            Contact c = new Contact(contactPhone, supplierId, contactName);
            s.deleteContact(c);
        } catch (Exception e) {
            throw e;
        }
    }

    // Delete all supplier contacts
    public void deleteAllSupplierContacts(int supplierId) throws Exception {
        try {
            Supplier s = getSupplierById(supplierId);
            s.deleteAllContacts();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Adds an agreement with the supplier about a specific product.
     * 
     * @param supplierId        the id of the supplier
     * @param productShopId     the product's id in the shop
     * @param productSupplierId the product's id in the supplier's system
     * @param stockAmout        the amount that the supplier can supply in a single
     *                          reservation
     * @param basePrice         the base price of the product (without discounts)
     * @param amountToDiscount  maps between the amount of the product and the
     *                          discount per unit that the supplier offers (in
     *                          percentage)
     * @param manufacturer      the manufacturer of the product
     * @throws SuppliersException if the information is not valid
     * 
     **/
    public void addSupplierProductAgreement(int supplierId, int productShopId, int productSupplierId, int stockAmount,
            double basePrice, TreeMap<Integer, Double> amountToDiscount) throws Exception {
        try {
            if (supplierId < 0) {
                throw new SuppliersException("Supplier id cannot be negative.");
            }
            if (!idToSupplier.containsKey(supplierId)) {
                throw new SuppliersException("There is no supplier with id " + supplierId + " in the system.");
            }
            if (stockAmount < 0) {
                throw new SuppliersException("Stock amount cannot be negative.");
            }
            if (basePrice < 0) {
                throw new SuppliersException("Base price cannot be negative.");
            }

            // TODO: make it integrate with delivery service
            Product product = ProductController.getInstance().getProductById(productShopId);
            ProductAgreement pa = new ProductAgreement(supplierId, product, productSupplierId, stockAmount,
                    amountToDiscount);
            ProductController.getInstance().addProductAgreement(supplierId, productShopId, pa);
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Updates an agreement with the supplier about a specific product.
     * 
     * @param supplierId        the id of the supplier
     * @param productShopId     the product's id in the shop
     * @param productSupplierId the product's id in the supplier's system
     * @param stockAmout        the amount that the supplier can supply in a single
     *                          reservation
     * @param basePrice         the base price of the product (without discounts)
     * @param amountToDiscount  maps between the amount of the product and the
     *                          discount per unit that the supplier offers (in
     *                          percentage)
     * @param manufacturer      the manufacturer of the product
     * @throws SuppliersException if the information is not valid
     * 
     **/
    public void updateSupplierProductAgreement(int supplierId, int productShopId, int stockAmount,
            TreeMap<Integer, Double> amountToDiscount) throws Exception {
        ProductController.getInstance().updateProductAgreement(supplierId, productShopId, stockAmount,
                amountToDiscount);
    }

    /**
     *
     * @param contactPhones phone numbers of the contacts
     * @param contactNames  names of contacts
     * @param supplierId    the supplier that the contacts belong to
     * @return list of Contact objects built from a list of phone numbers and list
     *         of names
     */
    private List<Contact> makeContactList(List<String> contactPhones, List<String> contactNames, int supplierId) {
        List<Contact> contactList = new LinkedList<Contact>();
        for (int i = 0; i < contactPhones.size(); i++) {
            contactList.add(new Contact(contactPhones.get(i), supplierId, contactNames.get(i)));
        }
        return contactList;
    }

    public void calculateSupplierDiscount(int supplierId, List<ReceiptItem> items) {
        int amount = 0;

        for (ReceiptItem item : items)
            amount += item.getAmount();

        double discount_coefficient = idToSupplier.get(supplierId).getAmountToPrice().get(amount);

        for (ReceiptItem item : items)
            item.setPricePerUnitAfterDiscount(discount_coefficient * item.getPricePerUnitAfterDiscount());
    }

    /**
     * 
     * @param supplierId the id of the supplier
     * @return information about the supplier
     * @throws Exception
     */
    public String getSupplierCard(int supplierId) throws Exception {
        try {
            if (!idToSupplier.containsKey(supplierId)) {
                throw new Exception("There is no supplier with id " + supplierId + " in the system.");
            }
            if (supplierId < 0) {
                throw new Exception("Supplier id cannot be negative.");
            }
            return idToSupplier.get(supplierId).toString();
        } catch (Exception e) {
            throw e;
        }
    }

    public Contact getRandomContactOf(int supplierID) throws SuppliersException {
        return getSupplierById(supplierID).getRandomContact();
    }

}
