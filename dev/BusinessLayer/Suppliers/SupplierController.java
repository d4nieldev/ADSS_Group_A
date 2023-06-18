package BusinessLayer.Suppliers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import BusinessLayer.InveontorySuppliers.Discount;
import BusinessLayer.InveontorySuppliers.DiscountFixed;
import BusinessLayer.InveontorySuppliers.DiscountPercentage;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.InveontorySuppliers.ReceiptItem;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DAOs.ContactDAO;
import DataAccessLayer.DAOs.DiscountDAO;
import DataAccessLayer.DAOs.FixedDaysSupplierDAO;
import DataAccessLayer.DAOs.OnOrderSuppliersDAO;
import DataAccessLayer.DAOs.ProductAgreementDAO;
import DataAccessLayer.DAOs.SelfPickUpSupplierDAO;
import DataAccessLayer.DAOs.SupplierDAO;
import DataAccessLayer.DAOs.SuppliersFieldsDAO;
import DataAccessLayer.DTOs.ContactDTO;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.FixedDaysSupplierDTO;
import DataAccessLayer.DTOs.OnOrderSuppliersDTO;
import DataAccessLayer.DTOs.PeriodicReservationDTO;
import DataAccessLayer.DTOs.ProductAgreementDTO;
import DataAccessLayer.DTOs.SelfPickUpSupplierDTO;
import DataAccessLayer.DTOs.SupplierDTO;
import DataAccessLayer.DTOs.SuppliersFieldsDTO;

public class SupplierController {
    private int nextSupplierIdInSystem;
    private int nextDiscountIdInSystem;
    private boolean initialized;
    private TreeMap<Integer, Supplier> idToSupplier;
    private static SupplierController instance = null;
    ProductAgreementDAO productAgreementDAO;

    private ContactDAO contactDAO;
    private FixedDaysSupplierDAO fixedDaysSupplierDAO;
    private OnOrderSuppliersDAO onOrderSuppliersDAO;
    private SelfPickUpSupplierDAO selfPickupSupplierDAO;
    private SupplierDAO supplierDAO;
    private SuppliersFieldsDAO suppliersFieldsDAO;
    private DiscountDAO discountDAO;

    // Constructor for SupplierController
    private SupplierController() {
        this.idToSupplier = new TreeMap<Integer, Supplier>();
        this.productAgreementDAO = ProductAgreementDAO.getInstance();
        contactDAO = ContactDAO.getInstance();
        fixedDaysSupplierDAO = FixedDaysSupplierDAO.getInstance();
        onOrderSuppliersDAO = OnOrderSuppliersDAO.getInstance();
        selfPickupSupplierDAO = SelfPickUpSupplierDAO.getInstance();
        supplierDAO = SupplierDAO.getInstance();
        suppliersFieldsDAO = SuppliersFieldsDAO.getInstance();
        discountDAO = DiscountDAO.getInstance();
        initialized = false;
    }

    /**
     * initializes the controller, fetchies relevant data from the database and
     * starts the periodic reservation thread
     * 
     * @throws SQLException if a database error occurs
     */
    public void init() throws SQLException {
        if (!initialized) {
            loadSupplierLastId();
            loadDiscountLastId();
            initialized = true;
        }
    }

    public static SupplierController getInstance() {
        if (instance == null)
            instance = new SupplierController();
        return instance;
    }

    private void loadSupplierLastId() throws SQLException {
        nextSupplierIdInSystem = supplierDAO.getLastId() + 1;
    }

    private void loadDiscountLastId() throws SQLException {
        nextDiscountIdInSystem = discountDAO.getLastId() + 1;
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
        if (!onOrderSuppliersDAO.deleteById(id)) {
            if (!selfPickupSupplierDAO.deleteById(id)) {
                if (!fixedDaysSupplierDAO.deleteById(id)) {
                    throw new SuppliersException("Supplier wasn't deleted from system");
                }

            }
        }

        // after successfuly deleted from data, we can delete in the presistence.
        ProductController.getInstance().deleteAllSupplierAgreements(supplierId);
        idToSupplier.remove(supplierId);
    }

    // Add 'Fixed days' supplier to the system
    private void addFixedDaysSupplierBaseAgreement(int supplierId, String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, String> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, List<Integer> days)
            throws SuppliersException, SQLException {
        // we add the office contact
        addOfficeContact(contactNames, contactPhones, supplierPhone);
        // first we try to make the supplier DTO
        SupplierDTO supDTO = new SupplierDTO(supplierId, supplierName, supplierBankAccount,
                paymentCondition,
                makeFieldsDTOList(supplierId, supplierFields),
                makeContactDTOList(makeContactList(contactPhones, contactNames, supplierId)),
                makeDiscountDTOMap(amountToDiscount), new HashMap<Integer, PeriodicReservationDTO>());
        // now we make fixedDaysSupplierDtosList
        List<FixedDaysSupplierDTO> dtos = new LinkedList<>();
        for (int day : days) {
            FixedDaysSupplierDTO dto = new FixedDaysSupplierDTO(supDTO, day);
            // now we insert to the proper DAO
            fixedDaysSupplierDAO.insert(dto);
            dtos.add(dto);
        }
        // now we insert to business layer
        FixedDaysSupplier fds = new FixedDaysSupplier(dtos);
        idToSupplier.put(supplierId, fds);
    }

    public void addFixedDaysSupplierBaseAgreement(String supplierName, String supplierPhone, String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, String> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, List<Integer> days)
            throws SuppliersException, SQLException {
        addFixedDaysSupplierBaseAgreement(nextSupplierIdInSystem++, supplierName, supplierPhone, supplierBankAccount,
                supplierFields, paymentCondition, amountToDiscount, contactNames, contactPhones, days);
    }

    // Add 'On Order' supplier to the system
    private void addOnOrderSupplierBaseAgreement(int supplierId, String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, String> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, int maxSupplyDays)
            throws SuppliersException, SQLException {
        // we add the office contact
        addOfficeContact(contactNames, contactPhones, supplierPhone);
        // first we try to make the DTO
        OnOrderSuppliersDTO dto = new OnOrderSuppliersDTO(
                new SupplierDTO(supplierId, supplierName, supplierBankAccount, paymentCondition,
                        makeFieldsDTOList(supplierId, supplierFields),
                        makeContactDTOList(makeContactList(contactPhones, contactNames, supplierId)),
                        makeDiscountDTOMap(amountToDiscount), new HashMap<Integer, PeriodicReservationDTO>()),
                maxSupplyDays);
        // now we insert to the proper DAO
        onOrderSuppliersDAO.insert(dto);
        // now we insert to business layer
        OnOrderSupplier oos = new OnOrderSupplier(dto);
        idToSupplier.put(supplierId, oos);
    }

    public void addOnOrderSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, String> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, int maxSupplyDays)
            throws SuppliersException, SQLException {
        addOnOrderSupplierBaseAgreement(nextDiscountIdInSystem++, supplierName, supplierPhone, supplierBankAccount,
                supplierFields, paymentCondition, amountToDiscount, contactNames, contactPhones, maxSupplyDays);
    }

    // Add 'Self Pickup' supplier to the system
    private void addSelfPickupSupplierBaseAgreement(int supplierId, String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, String> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, String address, int maxPreperationDays)
            throws SuppliersException {
        // we add the office contact
        addOfficeContact(contactNames, contactPhones, supplierPhone);
        // first we try to make the DTO
        SelfPickUpSupplierDTO dto = new SelfPickUpSupplierDTO(
                new SupplierDTO(supplierId, supplierName, supplierBankAccount, paymentCondition,
                        makeFieldsDTOList(supplierId, supplierFields),
                        makeContactDTOList(makeContactList(contactPhones, contactNames, supplierId)),
                        makeDiscountDTOMap(amountToDiscount), new HashMap<Integer, PeriodicReservationDTO>()),
                address, maxPreperationDays);
        try {
            // now we insert to the proper DAO
            selfPickupSupplierDAO.insert(dto);
            // now we insert to business layer
            SelfPickupSupplier sps = new SelfPickupSupplier(dto);
            idToSupplier.put(supplierId, sps);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addSelfPickupSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, String> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, String address, int maxPreperationDays)
            throws SuppliersException {
        addSelfPickupSupplierBaseAgreement(nextSupplierIdInSystem++, supplierName, supplierPhone, supplierBankAccount,
                supplierFields, paymentCondition, amountToDiscount, contactNames, contactPhones, address,
                maxPreperationDays);
    }

    // Add office contact for the begining of the list of contacts
    private void addOfficeContact(List<String> contactNames, List<String> contactPhones, String officePhone) {
        contactNames.add(0, "Office");
        contactPhones.add(0, officePhone);
    }

    // Update supplier name
    public void setSupplierName(int supplierId, String supplierName) {
        try {
            Supplier s = getSupplierById(supplierId);
            // try to set the dto and update in database
            SupplierDTO sDTO = s.getDTO();
            sDTO.setName(supplierName);
            supplierDAO.update(sDTO);
            // now update in presistence
            s.setName(supplierName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update supplier bank account
    public void setSupplierBankAccount(int supplierId, String supplierBankAccount) {
        try {
            Supplier s = getSupplierById(supplierId);
            // try to set the dto and update in database
            SupplierDTO sDTO = s.getDTO();
            sDTO.setBankAccount(supplierBankAccount);
            supplierDAO.update(sDTO);
            // now update in presistence
            s.setBankAcc(supplierBankAccount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update supplier payment condition
    public void setSupplierPaymentCondition(int supplierId, String paymentCondition) {
        try {
            Supplier s = getSupplierById(supplierId);
            // try to set the dto and update in database
            SupplierDTO sDTO = s.getDTO();
            sDTO.setPaymentCondition(paymentCondition);
            supplierDAO.update(sDTO);
            // now update in presistence
            s.setPaymentCondition(paymentCondition);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add supplier field
    public void addSupplierField(int supplierId, String field) {
        try {
            Supplier s = getSupplierById(supplierId);
            // try to make new dto and add to database;
            SuppliersFieldsDTO sfDTO = new SuppliersFieldsDTO(supplierId, field);
            suppliersFieldsDAO.insert(sfDTO);

            SupplierDTO sDTO = s.getDTO();
            sDTO.addField(sfDTO);

            // now update in presistence
            s.addField(field);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove supplier field
    public void removeSupplierField(int supplierId, String field) {
        try {
            Supplier s = getSupplierById(supplierId);
            if (!s.getFields().contains(field)) {
                throw new SuppliersException("Field " + field + " does not exist in supplier " + supplierId);
            }
            // try to delete from database first.
            SupplierDTO sDTO = s.getDTO();
            SuppliersFieldsDTO fieldDTO = sDTO.getFieldDTObyName(field);
            suppliersFieldsDAO.delete(fieldDTO);

            sDTO.removeField(fieldDTO);

            // now update in presistence
            s.removeField(field);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add supplier contact
    public void addSupplierContact(int supplierId, String contactPhone, String contactName) throws SuppliersException {
        try {
            Supplier s = getSupplierById(supplierId);
            // try to make new dto and add to database;
            ContactDTO cDTO = new ContactDTO(supplierId, contactPhone, contactName);
            contactDAO.insert(cDTO);

            SupplierDTO sDTO = s.getDTO();
            sDTO.addContact(cDTO);

            // now update in presistence
            Contact c = new Contact(cDTO);
            s.addContact(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete supplier contact
    public void deleteSupplierContact(int supplierId, String contactPhone, String contactName)
            throws SuppliersException {
        try {
            if (contactName.equals("Office")) {
                throw new SuppliersException("Cannot delete the Office contact");
            }
            Supplier s = getSupplierById(supplierId);
            Contact c = getContactByPhone(s, contactPhone);
            if (c == null) {
                throw new SuppliersException(
                        "Contact with phone " + contactPhone + " does not exist in supplier " + supplierId);
            }
            // try to delete from database first.
            ContactDTO cDTO = c.getContactDTO();
            contactDAO.delete(cDTO);
            // now update in presistence
            s.deleteContact(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Contact getContactByPhone(Supplier s, String contactPhone) {
        for (Contact c : s.getContacts()) {
            if (c.getPhone().equals(contactPhone)) {
                return c;
            }
        }
        return null;
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
            double basePrice, TreeMap<Integer, String> amountToDiscount) throws SuppliersException, SQLException {
        if (supplierId < 0) {
            throw new SuppliersException("Supplier id cannot be negative.");
        }
        // just to load if it doesn't exist in presistence.
        getSupplierById(supplierId);

        if (stockAmount < 0) {
            throw new SuppliersException("Stock amount cannot be negative.");
        }
        if (basePrice < 0) {
            throw new SuppliersException("Base price cannot be negative.");
        }

        Product product = ProductController.getInstance().getProductById(productShopId);
        // first we add to database
        TreeMap<Integer, DiscountDTO> discountDTOMap = makeDiscountDTOMap(amountToDiscount);
        ProductAgreementDTO dto = new ProductAgreementDTO(supplierId, product.getDTO(), stockAmount, basePrice,
                productSupplierId, discountDTOMap);
        productAgreementDAO.insert(dto);

        // now we add to presistence
        ProductAgreement pa = new ProductAgreement(dto);
        ProductController.getInstance().addProductAgreement(supplierId, productShopId, pa);

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
    public void updateSupplierProductAgreement(int supplierId, int productShopId, int stockAmount, int basePrice,
            TreeMap<Integer, String> amountToDiscount) throws SuppliersException {
        try {
            // just to make sure we load if it doesn't exist in presistence.
            getSupplierById(supplierId);

            // handles the data and presistence update.
            ProductController.getInstance().updateProductAgreement(supplierId, productShopId, stockAmount, basePrice,
                    makeDiscountMap(amountToDiscount));
        } catch (Exception e) {
            throw new SuppliersException(
                    "A error occurred while updating agreement to supplier with id: " + supplierId);
        }
    }

    private TreeMap<Integer, Discount> makeDiscountMap(TreeMap<Integer, String> amountToDiscount) {
        TreeMap<Integer, Discount> res = new TreeMap<>();
        for (Integer key : amountToDiscount.keySet()) {
            Discount dis = makeDiscountFromValue(amountToDiscount.get(key));
            res.put(key, dis);
        }
        return res;
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
        List<Contact> contactList = new ArrayList<>();
        for (int i = 0; i < contactPhones.size(); i++) {
            ContactDTO cDTO = new ContactDTO(supplierId, contactPhones.get(i), contactNames.get(i));
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

    private TreeMap<Integer, DiscountDTO> makeDiscountDTOMap(TreeMap<Integer, String> amountToDiscount) {
        TreeMap<Integer, DiscountDTO> res = new TreeMap<>();
        for (Integer key : amountToDiscount.keySet()) {
            Discount dis = makeDiscountFromValue(amountToDiscount.get(key));
            DiscountDTO disDTO = dis.getDto();
            res.put(key, disDTO);
        }
        return res;
    }

    private Discount makeDiscountFromValue(String value) throws SuppliersException {
        Discount dis = null;
        if (value.indexOf('%') != -1) {
            // means that the discount is of percentage type
            double disVal = tryParseDouble((value.substring(0, value.length() - 1)), Double.MIN_VALUE);
            if (disVal == Double.MIN_VALUE || disVal < 0 || disVal > 100) {
                throw new SuppliersException("Invalid precentage discount value: " + value);
            }
            DiscountDTO dto = new DiscountDTO(nextDiscountIdInSystem++, LocalDate.now(), null,
                    disVal, "Precentage");
            dis = new DiscountPercentage(dto);
            return dis;
        } else {
            // means that the discount is of fixed type
            double disVal = tryParseDouble((value.substring(0, value.length())), Double.MIN_VALUE);
            if (disVal == Double.MIN_VALUE || disVal < 0) {
                throw new SuppliersException("Invalid fixed discount value: " + value);
            }
            DiscountDTO dto = new DiscountDTO(nextDiscountIdInSystem++, LocalDate.now(), null,
                    disVal, "Fixed");
            dis = new DiscountFixed(dto);
            return dis;
        }
    }

    public static double tryParseDouble(String s, double defaultValue) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private List<SuppliersFieldsDTO> makeFieldsDTOList(int supplierId, List<String> fields) {
        List<SuppliersFieldsDTO> suppliersFieldsDTOs = new ArrayList<>();
        for (String field : fields) {
            // try to insert to database
            SuppliersFieldsDTO dto = new SuppliersFieldsDTO(supplierId, field);
            suppliersFieldsDTOs.add(dto);
        }
        return suppliersFieldsDTOs;
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

        Integer keyAmount = getSupplierById(supplierId).getAmountToDiscount().floorKey(amount);
        Discount discount = null;
        if (keyAmount != null)
            discount = getSupplierById(supplierId).getAmountToDiscount().get(keyAmount);

        for (ReceiptItem item : items) {
            double priceAfterDiscount = item.getPricePerUnitAfterDiscount();
            if (discount != null)
                priceAfterDiscount = discount.getPriceWithDiscount(priceAfterDiscount);

            item.setPricePerUnitAfterDiscount(priceAfterDiscount);
        }
    }

    /**
     * A map that contains the data about the supplier
     * 
     * @param supplierId the id of the supplier
     * @return information about the supplier
     * @throws SuppliersException
     */
    public Map<String, Object> getSupplierCard(int supplierId) throws SuppliersException {
        try {
            Supplier s = getSupplierById(supplierId);
            if (supplierId < 0) {
                throw new Exception("Supplier id cannot be negative.");
            }
            return s.getMap();
        } catch (Exception e) {
            throw new SuppliersException("Error occurred while getting supplier card with id: " + supplierId);
        }
    }

    public Contact getRandomContactOf(int supplierID) throws SuppliersException, SQLException {
        return getSupplierById(supplierID).getRandomContact();
    }

    public void clearData() {
        idToSupplier.clear();
        nextSupplierIdInSystem = 0;
    }

    public Contact getContactOfSupplier(int supplierId, String phone) {
        return getContactByPhone(getSupplierById(supplierId), phone);
    }

    public void editOnOrderSupplier(int supplierId, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition, TreeMap<Integer, String> amountToDiscount, List<String> contactNames,
            List<String> contactPhones, int maxSupplyDays) throws Exception {
        deleteSupplier(supplierId);
        addOnOrderSupplierBaseAgreement(supplierId, name, phone, bankAcc, fields, paymentCondition, amountToDiscount,
                contactNames,
                contactPhones, maxSupplyDays);
    }

    public void editFixedDaysSupplier(int supplierId, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition, TreeMap<Integer, String> amountToDiscount, List<String> contactNames,
            List<String> contactPhones, List<Integer> days) throws Exception {
        deleteSupplier(supplierId);
        addFixedDaysSupplierBaseAgreement(supplierId, name, phone, bankAcc, fields, paymentCondition, amountToDiscount,
                contactNames, contactPhones, days);

    }

    public void editSelfPickupSupplier(int supplierId, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition, TreeMap<Integer, String> amountToDiscount, List<String> contactNames,
            List<String> contactPhones, int maxPreperationDays, String address) throws Exception {
        deleteSupplier(supplierId);
        addSelfPickupSupplierBaseAgreement(supplierId, name, phone, bankAcc, fields, paymentCondition, amountToDiscount,
                contactNames, contactPhones, address, maxPreperationDays);
    }

    public List<String> getSomeSuppliersIds(int numOfSuppliers, List<Integer> alreadyHave) throws SQLException {
        List<SupplierDTO> suppliers = supplierDAO.selectAll();
        List<String> res = suppliers.stream().map(sup -> "" + sup.getId()).collect(Collectors.toList());
        res.add("done");
        return res;
    }

}
