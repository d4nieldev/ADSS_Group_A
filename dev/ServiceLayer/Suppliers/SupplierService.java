package ServiceLayer.Suppliers;

import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

import javax.management.RuntimeErrorException;

import BusinessLayer.InveontorySuppliers.ProductController;
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
            // service.productController.init();
            return service;
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing SupplierService");
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
            return "Supplier of type: 'Fixed days' added successfully";
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
                    supplierFields,
                    paymentCondition, amountToDiscount, contactNames, contactPhones, maxSupplyDays);
            return "Supplier of type: 'On Order' added successfully";
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
            return "Supplier of type: 'Self Pickup' added successfully";
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
     * 
     * @param supplierId
     * @return infromation about the supplier
     */
    public String getSupplierCard(int supplierId) {
        try {
            return supplierController.getSupplierCard(supplierId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getSupplierProductAgreements(int supplierId) {
        try {
            return productController.getProductAgreementsOfSupplier(supplierId).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
