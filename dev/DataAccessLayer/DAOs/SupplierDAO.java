package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import DataAccessLayer.Repository;
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
    private Repository repo;
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
        repo = Repository.getInstance();
        discountDAO = DiscountDAO.getInstance();
    }

    public static SupplierDAO getInstance() {
        if (instance == null)
            instance = new SupplierDAO();
        return instance;
    }

    @Override
    public SupplierDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int id = rs.getInt("id");
        String name = rs.getString("name");
        String bankAccount = rs.getString("bankAccount");
        String paymentCondition = rs.getString("paymentCondition");
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
        for (SuppliersFieldsDTO field : dataObject.getFields()) {
            suppliersFieldsDAO.update(field);
        }
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
        String query = "SELECT * FROM Suppliers WHERE supplierId= ?;";
        ResultSet rs = repo.executeQuery(query, supplierId);
        SupplierDTO dto = makeDTO(rs);
        rs.close();
        return dto;
    }

    public int getLastId() throws SQLException {
        String query = "SELECT * FROM Suppliers WHERE id = (SELECT Max(id) FROM Suppliers);";
        ResultSet rs = repo.executeQuery(query);
        SupplierDTO dto = makeDTO(rs);
        if (dto == null) {
            return -1;
        }
        rs.close();
        return dto.getId();
    }

}