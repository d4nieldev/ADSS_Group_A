package BusinessLayer.Suppliers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import BusinessLayer.Inventory.ProductStatus;
import BusinessLayer.InveontorySuppliers.*;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DAOs.*;
import DataAccessLayer.DTOs.ContactDTO;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.FixedDaysSupplierDTO;
import DataAccessLayer.DTOs.OnOrderSuppliersDTO;
import DataAccessLayer.DTOs.PeriodicReservationDTO;
import DataAccessLayer.DTOs.SelfPickUpSupplierDTO;
import DataAccessLayer.DTOs.SupplierDTO;
import DataAccessLayer.DTOs.ProductAgreementDTO;

import java.util.List;
import java.util.Map;

public class SupplierController {

    private int nextSupplierIdInSystem;
    private TreeMap<Integer, Supplier> idToSupplier;
    private static SupplierController instance = null;
    private ReservationController rc;
    ProductAgreementDAO productAgreementDAO;
    Thread periodicReservationsCareTaker;
    private ContactDAO contactDAO;
    private FixedDaysSupplierDAO fixedDaysSupplierDAO;
    private OnOrderSuppliersDAO onOrderSuppliersDAO;
    private SelfPickUpSupplierDAO selfPickupSupplierDAO;

    // Constructor for SupplierController
    private SupplierController() {
        this.idToSupplier = new TreeMap<Integer, Supplier>();
        this.rc = ReservationController.getInstance();
        this.productAgreementDAO = ProductAgreementDAO.getInstance();
        periodicReservationsCareTaker = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    makePeriodicalReservations();
                    Thread.sleep(86400000); // sleep for a day
                }
            } catch (InterruptedException ignored) {
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        periodicReservationsCareTaker.start();
        contactDAO = ContactDAO.getInstance();
        fixedDaysSupplierDAO = FixedDaysSupplierDAO.getInstance();
        onOrderSuppliersDAO = OnOrderSuppliersDAO.getInstance();
        selfPickupSupplierDAO = SelfPickUpSupplierDAO.getInstance();
        // TODO: Add load of next supplier id from database
        this.nextSupplierIdInSystem = 0;
    }

    public static SupplierController getInstance() {
        if (instance == null)
            instance = new SupplierController();
        return instance;
    }

    // getSupplierFromData
    private Supplier LoadSupplierFromData(int supplierId) throws SQLException {
        List<FixedDaysSupplierDTO> res = fixedDaysSupplierDAO.getById(supplierId);
        if (res != null) {
            return new FixedDaysSupplier(res);
        } else {
            OnOrderSuppliersDTO res2 = onOrderSuppliersDAO.getById(supplierId);
            if (res2 != null) {
                return new OnOrderSupplier(res2);
            } else {
                SelfPickUpSupplierDTO res3 = selfPickupSupplierDAO.getById(supplierId);
                if (res3 != null) {
                    return new SelfPickupSupplier(res3);
                }
            }
        }
        return null;
    }

    // Getter for Supplier by id
    public Supplier getSupplierById(int supplierId) throws SuppliersException {
        if (supplierId < 0)
            throw new SuppliersException("Supplier with negative id is illegal in the system.");
        if (idToSupplier.containsKey(supplierId)) {
            return idToSupplier.get(supplierId);
        } else {
            Supplier s;
            try {
                s = LoadSupplierFromData(supplierId);
            } catch (SQLException e) {
                throw new SuppliersException("A database error occurred while loading supplier " + supplierId);
            }
            if (s != null) {
                return s;
            } else {
                throw new SuppliersException("There is no supplier with id " + supplierId + " in the system.");
            }
        }

    }

    // Delete Supplier by id
    public void deleteSupplier(int supplierId) throws SuppliersException, SQLException {
        // will throw exception if not exists.
        Supplier s = getSupplierById(supplierId);
        // if exists, it is not null
        int id = s.getId();
        onOrderSuppliersDAO.deleteById(id);
        selfPickupSupplierDAO.deleteById(id);
        fixedDaysSupplierDAO.deleteById(id);
        // after successfuly deleted from data, we can delete in the presistence.
        // TODO: maybe we need to delete all his periodic reservations.
        ProductController.getInstance().deleteAllSupplierAgreements(supplierId);
        idToSupplier.remove(supplierId);
    }

    // Add 'Fixed days' supplier to the system
    public void addFixedDaysSupplierBaseAgreement(String supplierName, String supplierPhone, String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, Discount> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, List<Integer> days)
            throws SuppliersException, SQLException {
        // we add the office contact
        addOfficeContact(contactNames, contactPhones, supplierPhone);
        // first we try to make the supplier DTO
        SupplierDTO supDTO = new SupplierDTO(nextSupplierIdInSystem, supplierName, supplierBankAccount,
                paymentCondition,
                supplierFields,
                makeContactDTOList(makeContactList(contactPhones, contactNames, nextSupplierIdInSystem)),
                makeDiscountDTOMap(amountToDiscount), new HashMap<Integer, PeriodicReservationDTO>());
        // now we make fixedDaysSupplierDtosList
        List<FixedDaysSupplierDTO> dtos = new LinkedList<>();
        for (int day : days) {
            FixedDaysSupplierDTO dto = new FixedDaysSupplierDTO(supDTO, day);
            // now we insert to the proper DAO
            fixedDaysSupplierDAO.insert(dto);
        }
        // now we insert to business layer
        FixedDaysSupplier fds = new FixedDaysSupplier(dtos);
        idToSupplier.put(nextSupplierIdInSystem, fds);
        nextSupplierIdInSystem++;

    }

    // Add 'On Order' supplier to the system
    public void addOnOrderSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, Discount> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, int maxSupplyDays)
            throws SuppliersException, SQLException {
        // we add the office contact
        addOfficeContact(contactNames, contactPhones, supplierPhone);
        // first we try to make the DTO
        OnOrderSuppliersDTO dto = new OnOrderSuppliersDTO(
                new SupplierDTO(nextSupplierIdInSystem, supplierName, supplierBankAccount, paymentCondition,
                        supplierFields,
                        makeContactDTOList(makeContactList(contactPhones, contactNames, nextSupplierIdInSystem)),
                        makeDiscountDTOMap(amountToDiscount), new HashMap<Integer, PeriodicReservationDTO>()),
                maxSupplyDays);
        // now we insert to the proper DAO
        onOrderSuppliersDAO.insert(dto);
        // now we insert to business layer
        OnOrderSupplier oos = new OnOrderSupplier(dto);
        idToSupplier.put(nextSupplierIdInSystem, oos);
        nextSupplierIdInSystem++;
    }

    // Add 'Self Pickup' supplier to the system
    public void addSelfPickupSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, Discount> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, String address, int maxPreperationDays)
            throws SuppliersException, SQLException {
        // we add the office contact
        addOfficeContact(contactNames, contactPhones, supplierPhone);
        // first we try to make the DTO
        SelfPickUpSupplierDTO dto = new SelfPickUpSupplierDTO(
                new SupplierDTO(nextSupplierIdInSystem, supplierName, supplierBankAccount, paymentCondition,
                        supplierFields,
                        makeContactDTOList(makeContactList(contactPhones, contactNames, nextSupplierIdInSystem)),
                        makeDiscountDTOMap(amountToDiscount), new HashMap<Integer, PeriodicReservationDTO>()),
                address, maxPreperationDays);
        // now we insert to the proper DAO
        selfPickupSupplierDAO.insert(dto);
        // now we insert to business layer
        SelfPickupSupplier sps = new SelfPickupSupplier(dto);
        idToSupplier.put(nextSupplierIdInSystem, sps);
        nextSupplierIdInSystem++;

    }

    // Add office contact for the begining of the list of contacts
    private void addOfficeContact(List<String> contactNames, List<String> contactPhones, String officePhone) {
        contactNames.add(0, "Office");
        contactPhones.add(0, officePhone);
    }

    // -----------------------------------------------------------------------------------------

    // Update supplier name
    public void setSupplierName(int supplierId, String supplierName) throws SuppliersException {
        try {
            getSupplierById(supplierId).setName(supplierName);
        } catch (Exception e) {
            throw e;
        }
    }

    // Update supplier phone
    public void setSupplierPhone(int supplierId, String supplierPhone) throws SuppliersException {
        try {
            getSupplierById(supplierId).setPhone(supplierPhone);
        } catch (Exception e) {
            throw e;
        }
    }

    // Update supplier bank account
    public void setSupplierBankAccount(int supplierId, String supplierBankAccount) throws SuppliersException {
        try {
            getSupplierById(supplierId).setBankAcc(supplierBankAccount);
        } catch (Exception e) {
            throw e;
        }
    }

    // Update supplier fields
    public void setSupplierFields(int supplierId, List<String> supplierFields) throws SuppliersException {
        try {
            getSupplierById(supplierId).setFields(supplierFields);
        } catch (Exception e) {
            throw e;
        }
    }

    // Add supplier field
    public void addSupplierField(int supplierId, String field) throws SuppliersException {
        try {
            getSupplierById(supplierId).getFields().add(field);
        } catch (Exception e) {
            throw e;
        }
    }

    // Update supplier payment condition
    public void setSupplierPaymentCondition(int supplierId, String paymentCondition) throws SuppliersException {
        try {
            getSupplierById(supplierId).setPaymentCondition(paymentCondition);
        } catch (Exception e) {
            throw e;
        }
    }

    // Update supplier discount
    public void setSupplierAmountToDiscount(int supplierId, TreeMap<Integer, Discount> amountToDiscount)
            throws SuppliersException {
        try {
            getSupplierById(supplierId).setAmountToDiscount(amountToDiscount);
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
            Contact c = new Contact(contactPhone, contactName);
            s.addContact(c);
        } catch (Exception e) {
            throw e;
        }
    }

    // Delete supplier contact
    public void deleteSupplierContact(int supplierId, String contactPhone, String contactName) throws Exception {
        try {
            Supplier s = getSupplierById(supplierId);
            Contact c = new Contact(contactPhone, contactName);
            s.deleteContact(c);
        } catch (Exception e) {
            throw e;
        }
    }

    // Delete all supplier contacts
    public void deleteAllSupplierContacts(int supplierId) throws SuppliersException {
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
            double basePrice, TreeMap<Integer, DiscountDTO> amountToDiscount) throws SuppliersException, SQLException {
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

            Product product = ProductController.getInstance().getProductById(productShopId);
            ProductAgreementDTO dto = new ProductAgreementDTO(supplierId, product.getDTO(), stockAmount, basePrice,
                    productSupplierId, amountToDiscount);
            productAgreementDAO.insert(dto);
            ProductAgreement pa = new ProductAgreement(dto);
            ProductController.getInstance().addProductAgreement(supplierId, productShopId, pa);
        } catch (SuppliersException e) {
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
            TreeMap<Integer, Discount> amountToDiscount) throws Exception {
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
    private List<Contact> makeContactList(List<String> contactPhones, List<String> contactNames, int supplierId)
            throws SQLException {
        List<Contact> contactList = new LinkedList<Contact>();
        for (int i = 0; i < contactPhones.size(); i++) {
            // try to insert to database
            ContactDTO cDTO = new ContactDTO(supplierId, contactPhones.get(i), contactNames.get(i));
            contactDAO.insert(cDTO);
            // add to presistence
            Contact c = new Contact(cDTO);
            contactList.add(c);
        }
        return contactList;
    }

    private List<ContactDTO> makeContactDTOList(List<Contact> contacts) {
        List<ContactDTO> contactDTOs = new ArrayList<ContactDTO>();
        for (Contact c : contacts) {
            contactDTOs.add(c.getContactDTO());
        }
        return contactDTOs;
    }

    private TreeMap<Integer, DiscountDTO> makeDiscountDTOMap(TreeMap<Integer, Discount> amountToDiscount) {
        TreeMap<Integer, DiscountDTO> res = new TreeMap<>();
        for (Integer key : amountToDiscount.keySet()) {
            Discount dis = amountToDiscount.get(key);
            DiscountDTO disDTO = dis.getDto();
            res.put(key, disDTO);
        }
        return res;
    }

    /**
     * Sets a new price of items in receipt, after calculating the discount per
     * total order amount.
     * 
     * @param supplierId the id of the supplier
     * @param items      the items of the reservation that their amount sets the
     *                   discount.
     */
    public void calculateSupplierDiscount(int supplierId, List<ReceiptItem> items) {
        int amount = 0;

        for (ReceiptItem item : items)
            amount += item.getAmount();

        Integer keyAmount = idToSupplier.get(supplierId).getAmountToDiscount().floorKey(amount);
        Discount discount = null;
        if (keyAmount != null)
            discount = idToSupplier.get(supplierId).getAmountToDiscount().get(keyAmount);

        for (ReceiptItem item : items) {
            double priceAfterDiscount = item.getPricePerUnitAfterDiscount();
            if (discount != null)
                priceAfterDiscount = discount.getPriceWithDiscount(priceAfterDiscount);

            item.setPricePerUnitAfterDiscount(priceAfterDiscount);
        }
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

    public Contact getRandomContactOf(int supplierID) throws SuppliersException, SQLException {
        return getSupplierById(supplierID).getRandomContact();
    }

    private void makePeriodicalReservations() throws SQLException {
        for (Supplier s : idToSupplier.values()) {
            Map<Integer, PeriodicReservation> branchToPeriodicReservations = s.getBranchToPeriodicReservations();
            for (int branchId : branchToPeriodicReservations.keySet()) {
                PeriodicReservation pr = branchToPeriodicReservations.get(branchId);
                Map<Integer, Map<Integer, Integer>> supplierToProductToAmount = new HashMap<>();
                supplierToProductToAmount.put(s.getId(), pr.getProductsToAmounts());
                rc.makeManualReservation(supplierToProductToAmount, pr.getBranchId());
            }
        }
    }

    public void clearData() {
        idToSupplier.clear();
        nextSupplierIdInSystem = 0;
    }

    public PeriodicReservation addNewPeriodicReservation(int supplierId, int branchId, ProductStatus day) {
        // TODO : create new PeriodicReservation and return the object.
        List<PeriodicReservationItemDAO> lst = new ArrayList<>();
        PeriodicReservationDTO periodicReservationDTO = new PeriodicReservationDTO(supplierId, branchId, day,lst);
        PeriodicReservation periodicReservation = new PeriodicReservation(periodicReservationDTO);
        // add it to the needed Hashmaps.
        return null;
    }

    public Contact getContactOfSupplier(int supplierId, String phone) {
        // TODO: implement this method
        throw new IllegalStateException("Not implemented");
    }

}
