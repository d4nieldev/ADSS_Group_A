package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DataAccessLayer.DTOs.FieldDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public abstract class SupplierDAO <T extends SupplierDTO> extends DAO<T> {
    
    //public abstract List<FieldDTO> getFieldsBySupplierId(int supplierId);
    //public abstract void addFieldToSupplier(int supplierId, int fieldId);
    //public abstract void removeFieldFromSupplier(int supplierId, int fieldId);
    
    
    
}
