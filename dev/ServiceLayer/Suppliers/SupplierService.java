package ServiceLayer.Suppliers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.management.RuntimeErrorException;

import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.Suppliers.ProductAgreement;
import BusinessLayer.Suppliers.SupplierController;

public class SupplierService {
    private SupplierController supplierController;
    private ProductController productController;

    private SupplierService() {
        supplierController = SupplierController.getInstance();
        productController = ProductController.getInstance();
    }

    public static SupplierService create() {
        try {
            SupplierService service = new SupplierService();
            service.supplierController.init();
            return service;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Add 'Fixed days' supplier
     * 
     * @param supplierName
     * @param supplierPhone
     * @param supplierBankAccount
     * @param supplierFields
     * @param paymentCondition
     * @param amountToDiscount
     * @param contactNames
     * @param contactPhones
     * @param days
     * @return success/error message
     */
    public String addFixedDaysSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, String> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, List<Integer> days) {
        try {
            supplierController.addFixedDaysSupplierBaseAgreement(supplierName, supplierPhone, supplierBankAccount,
                    supplierFields,
                    paymentCondition, amountToDiscount, contactNames, contactPhones, days);
            return "Successfully added supplier " + supplierName + " of type 'Fixed Days'.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Add 'On Order' supplier
     * 
     * @param supplierName
     * @param supplierPhone
     * @param supplierBankAccount
     * @param supplierFields
     * @param paymentCondition
     * @param amountToDiscount
     * @param contactNames
     * @param contactPhones
     * @param maxSupplyDays
     * @return success/error message
     */
    public String addOnOrderSupplierBaseAgreement(String supplierName, String supplierPhone, String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, String> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, int maxSupplyDays) {
        try {
            supplierController.addOnOrderSupplierBaseAgreement(supplierName, supplierPhone, supplierBankAccount,
                    supplierFields, paymentCondition, amountToDiscount, contactNames, contactPhones, maxSupplyDays);
            return "Successfully added supplier " + supplierName + " of type 'On Order'.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Add 'Self Pickup' supplier
     * 
     * @param supplierName
     * @param supplierPhone
     * @param supplierBankAccount
     * @param supplierFields
     * @param paymentCondition
     * @param amountToDiscount
     * @param contactNames
     * @param contactPhones
     * @param address
     * @return success/error message
     */
    public String addSelfPickupSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, String> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, String address, Integer maxPreparationDays) {
        try {
            supplierController.addSelfPickupSupplierBaseAgreement(supplierName, supplierPhone, supplierBankAccount,
                    supplierFields,
                    paymentCondition, amountToDiscount, contactNames, contactPhones, address, maxPreparationDays);
            return "Successfully added supplier " + supplierName + " of type 'Self Pickup'.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Deletes a supplier from the system and all its products agreements
     * 
     * @param supplierId the id of the supplier to be deleted
     * @return success/error message
     */
    public String deleteSupplierBaseAgreement(int supplierId) {
        try {
            supplierController.deleteSupplier(supplierId);
            return "Supplier with id: " + supplierId + " deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Update supplier name
    public String setSupplierName(int supplierId, String supplierName) {
        try {
            supplierController.setSupplierName(supplierId, supplierName);
            return "Supplier name updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Update supplier phone
    public String setSupplierPhone(int supplierId, String supplierPhone) {
        try {
            supplierController.setSupplierName(supplierId, supplierPhone);
            return "Supplier phone updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Update supplier bank account
    public String setSupplierBankAccount(int supplierId, String supplierBankAccount) {
        try {
            supplierController.setSupplierBankAccount(supplierId, supplierBankAccount);
            return "Supplier back account updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Add supplier field
    public String addSupplierField(int supplierId, String field) {
        try {
            supplierController.addSupplierField(supplierId, field);
            return "Supplier field named: " + field + " added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Remove supplier field
    public String removeSupplierField(int supplierId, String field) {
        try {
            supplierController.removeSupplierField(supplierId, field);
            return "Supplier field named: " + field + " removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Update supplier payment condition
    public String setSupplierPaymentCondition(int supplierId, String paymentCondition) {
        try {
            supplierController.setSupplierPaymentCondition(supplierId, paymentCondition);
            return "Supplier payment condition updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Add supplier contact
    public String addSupplierContact(int supplierId, String contactPhone, String contactName) {
        try {
            supplierController.addSupplierContact(supplierId, contactPhone, contactName);
            return "Supplier contact added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Delete supplier contact
    public String deleteSupplierContact(int supplierId, String contactPhone, String contactName) {
        try {
            supplierController.deleteSupplierContact(supplierId, contactPhone, contactName);
            return "Supplier contact deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Addes a new supplier agreement about a specific product
     * 
     * @param supplierId
     * @param productShopId
     * @param productSupplierId
     * @param stockAmount
     * @param basePrice
     * @param amountToDiscount
     * @return success/error message
     */
    public String addSupplierProductAgreement(int supplierId, int productShopId, int productSupplierId, int stockAmount,
            double basePrice, TreeMap<Integer, String> amountToDiscount) {
        try {
            supplierController.addSupplierProductAgreement(supplierId, productShopId, productSupplierId, stockAmount,
                    basePrice, amountToDiscount);
            return "Supplier product agreement added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
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
     * 
     * @param supplierId
     * @return infromation about the supplier
     */
    public Map<String, Object> getSupplierCard(int supplierId) {
        try {
            return supplierController.getSupplierCard(supplierId);
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", e.getMessage());
            return map;
        }
    }

    public List<Map<String, Object>> getSupplierProductAgreements(int supplierId) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            for (ProductAgreement pa : productController.getProductAgreementsOfSupplier(supplierId))
                list.add(pa.getMap());

            return list;
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", e.getMessage());
            list.add(map);
            return list;
        }
    }

    public String deleteSupplierAgreement(int supplierId) {
        try {
            productController.deleteAllSupplierAgreements(supplierId);
            return "Supplier agreement deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String editOnOrderSupplier(int supplierId, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition, TreeMap<Integer, String> amountToDiscount, List<String> contactNames,
            List<String> contactPhones, int maxSupplyDays) {
        try {
            supplierController.editOnOrderSupplier(supplierId, name, phone, bankAcc, fields, paymentCondition,
                    amountToDiscount, contactNames, contactPhones, maxSupplyDays);
            return "Edited supplier " + name + " (id: " + supplierId + ") successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String editFixedDaysSupplier(int supplierId, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition, TreeMap<Integer, String> amountToDiscount, List<String> contactNames,
            List<String> contactPhones, List<Integer> days) {
        try {
            supplierController.editFixedDaysSupplier(supplierId, name, phone, bankAcc, fields, paymentCondition,
                    amountToDiscount, contactNames, contactPhones, days);
            return "Edited supplier " + name + " (id: " + supplierId + ") successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String editSelfPickupSupplier(int supplierId, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition, TreeMap<Integer, String> amountToDiscount, List<String> contactNames,
            List<String> contactPhones, int maxPreperationDays, String address) {
        try {
            supplierController.editSelfPickupSupplier(supplierId, name, phone, bankAcc, fields, paymentCondition,
                    amountToDiscount, contactNames, contactPhones, maxPreperationDays, address);
            return "Edited supplier " + name + " (id: " + supplierId + ") successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
