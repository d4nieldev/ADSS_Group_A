package ServiceLayer.Suppliers;

import java.util.List;
import java.util.Map;

import BusinessLayer.Suppliers.ProductAgreementController;
import BusinessLayer.Suppliers.SupplierController;

public class SupplierService {
    private SupplierController supplierController;
    private ProductAgreementController productAgreementController;

    public String addFixedDaysSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, Map<Integer, Double> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, List<String> days) {
        return null;
    }

    public String addOnOrderSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, Map<Integer, Double> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, int maxSupplyDays) {
        return null;
    }

    public String addSelfPickupSupplierBaseAgreement(String supplierName, String supplierPhone,
            String supplierBankAccount,
            List<String> supplierFields, String paymentCondition, Map<Integer, Double> amountToDiscount,
            List<String> contactNames, List<String> contactPhones, String address) {
        return null;
    }

    public String setSupplierName(int supplierId, String supplierName) {
        return null;
    }

    public String setSupplierPhone(int supplierId, String supplierPhone) {
        return null;
    }

    public String setSupplierBankAccount(int supplierId, String supplierBankAccount) {
        return null;
    }

    public String setSupplierFields(int supplierId, List<String> supplierFields) {
        return null;
    }

    public String setBaseAgreementPaymentCondition(int supplierId, String paymentCondition) {
        return null;
    }

    public String setBaseAgreementAmountToDiscount(int supplierId, Map<Integer, Double> amountToDiscount) {
        return null;
    }

    public String setSupplierContactNamesAndPhones(int supplierId, List<String> contactNames,
            List<String> contactPhones) {
        return null;
    }

    public String addSupplierProductAgreement(int supplierId, int productShopId, int productSupplierId, int stockAmount,
            double basePrice, Map<Integer, Double> amountToDiscount) {
        return null;
    }

    public String setProductAgreementStockAmount(int supplierId, int productShopId, int stockAmount) {
        return null;
    }

    public String setProductAgreementBasePrice(int supplierId, int productShopId, double basePrice) {
        return null;
    }

    public String setProductAgreementAmountToDiscount(int supplierId, int productShopId,
            Map<Integer, Double> amountToDiscount) {
        return null;
    }

    public String getSupplierCard(int supplierId) {
        return null;
    }

    public String getProductAgreement(int supplierId, int productId) {
        return null;
    }
}
