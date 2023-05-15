package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import DataAccessLayer.DTOs.ContactDTO;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.PeriodicReservationDTO;
import DataAccessLayer.DTOs.SupplierAmountToDiscountDTO;
import DataAccessLayer.DTOs.SupplierDTO;
import DataAccessLayer.DTOs.SuppliersFieldsDTO;

public class SupplierDAO extends DAO<SupplierDTO> {
    private SuppliersFieldsDAO suppliersFieldsDAO;
    private ContactDAO contactDAO;
    private SupplierAmountToDiscountDAO supplierAmountToDiscountDAO;
    private PeriodicReservationDAO periodicReservationDAO;
    private static SupplierDAO instance = null;
    private DiscountDAO discountDAO;

    // public abstract List<FieldDTO> getFieldsBySupplierId(int supplierId);
    // public abstract void addFieldToSupplier(int supplierId, int fieldId);
    // public abstract void removeFieldFromSupplier(int supplierId, int fieldId);
    private SupplierDAO() {
        super("Suppliers");
        suppliersFieldsDAO = new SuppliersFieldsDAO();
        contactDAO = ContactDAO.getInstance();
        supplierAmountToDiscountDAO = SupplierAmountToDiscountDAO.getInstance();
        periodicReservationDAO = PeriodicReservationDAO.getInstance();
        discountDAO = DiscountDAO.getInstance();
    }

    public static SupplierDAO getInstance() {
        if (instance == null)
            instance = new SupplierDAO();
        return instance;
    }

    @Override
    public SupplierDTO makeDTO(Map<String, Object> row) throws SQLException {
        int id = (int) row.get("id");
        String name = (String) row.get("name");
        String bankAccount = (String) row.get("bankAccount");
        String paymentCondition = (String) row.get("paymentCondition");
        List<SuppliersFieldsDTO> fields = suppliersFieldsDAO.getFieldsOfSupplier(id);
        List<ContactDTO> contacts = contactDAO.getSupplierContacts(id);
        TreeMap<Integer, DiscountDTO> amountToDiscount = supplierAmountToDiscountDAO.getSupplierAmountToDiscount(id);
        Map<Integer, PeriodicReservationDTO> periodToReservation = periodicReservationDAO
                .getSupplierPeriodicReservations(id);

        return new SupplierDTO(id, name, bankAccount, paymentCondition, fields, contacts, amountToDiscount,
                periodToReservation);
    }

    @Override
    public void insert(SupplierDTO dataObject) throws SQLException {
        super.insert(dataObject);
        // insert all fields of the supplier
        for (SuppliersFieldsDTO field : dataObject.getFields()) {
            suppliersFieldsDAO.insert(field);
        }
        // insert all contacts of the supplier
        for (ContactDTO contact : dataObject.getContacts()) {
            contactDAO.insert(contact);
        }
        // insert all amountToDiscount of the supplier
        for (Integer amount : dataObject.getAmountToDiscount().keySet()) {
            DiscountDTO disDTO = dataObject.getAmountToDiscount().get(amount);
            discountDAO.insert(disDTO);
            SupplierAmountToDiscountDTO supDisDTO = new SupplierAmountToDiscountDTO(dataObject.getId(), amount, disDTO);
            supplierAmountToDiscountDAO.insert(supDisDTO);
        }
    }

    @Override
    public void update(SupplierDTO dataObject) throws SQLException {
        super.update(dataObject);
        // update all fields of the supplier
        // TODO: verify with vladi - this creates a problem because we update the key!
        // for (SuppliersFieldsDTO field : dataObject.getFields()) {
        // suppliersFieldsDAO.update(field);
        // }
        // update all contacts of the supplier
        for (ContactDTO contact : dataObject.getContacts()) {
            contactDAO.update(contact);
        }
        // update all amountToDiscount of the supplier
        for (Integer amount : dataObject.getAmountToDiscount().keySet()) {
            DiscountDTO disDTO = dataObject.getAmountToDiscount().get(amount);
            SupplierAmountToDiscountDTO supDisDTO = new SupplierAmountToDiscountDTO(dataObject.getId(), amount, disDTO);
            supplierAmountToDiscountDAO.update(supDisDTO);
        }
    }

    public SupplierDTO getById(int supplierId) throws SQLException {
        String query = "SELECT * FROM Suppliers WHERE id= ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));
        return null;
    }

    public int getLastId() throws SQLException {
        String query = "SELECT * FROM Suppliers WHERE id = (SELECT Max(id) FROM Suppliers);";
        List<Map<String, Object>> rows = repo.executeQuery(query);
        if (rows.size() > 0)
            return makeDTO(rows.get(0)).getId();

        return -1;
    }

}