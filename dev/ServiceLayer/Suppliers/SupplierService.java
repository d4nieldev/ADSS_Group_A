package ServiceLayer.Suppliers;

import java.util.List;
import java.util.TreeMap;

import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.Suppliers.SupplierController;
import BusinessLayer.exceptions.SuppliersException;

public class SupplierService {
    private SupplierController supplierController;
    private ProductController productController;

    public SupplierService() {
        supplierController = SupplierController.getInstance();
        productController = ProductController.getInstance();
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
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, Double> amountToDiscount,
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
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, Double> amountToDiscount,
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
            List<String> supplierFields, String paymentCondition, TreeMap<Integer, Double> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, String address) {
        try {
            supplierController.addSelfPickupSupplierBaseAgreement(supplierName, supplierPhone, supplierBankAccount,
                    supplierFields,
                    paymentCondition, amountToDiscount, contactNames, contactPhones, address);
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

    // Update supplier fields
    public String setSupplierFields(int supplierId, List<String> supplierFields) {
        try {
            supplierController.setSupplierFields(supplierId, supplierFields);
            return "Supplier fields updated successfully";
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

    // Update supplier payment condition
    public String setSupplierPaymentCondition(int supplierId, String paymentCondition) {
        try {
            supplierController.setSupplierPaymentCondition(supplierId, paymentCondition);
            return "Supplier payment condition updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Update supplier discount
    public String setSupplierAmountToDiscount(int supplierId, TreeMap<Integer, Double> amountToDiscount) {
        try {
            supplierController.setSupplierAmountToDiscount(supplierId, amountToDiscount);
            return "Supplier field  updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Update supplier contacts names and phones
    public String setSupplierContactNamesAndPhones(int supplierId, List<String> contactPhones,
            List<String> contactNames) {
        try {
            supplierController.setSupplierContactNamesAndPhones(supplierId, contactPhones, contactNames);
            return "Supplier contacts updated successfully";
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

    // Delete all supplier contacts
    public String deleteAllSupplierContacts(int supplierId) {
        try {
            supplierController.deleteAllSupplierContacts(supplierId);
            return "All supplier contacts deleted successfully";
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
            double basePrice, TreeMap<Integer, Double> amountToDiscount) {
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
        } catch (SuppliersException e) {
            return e.getMessage();
        }
    }
}
