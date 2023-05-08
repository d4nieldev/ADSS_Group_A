package DataAccessLayer.DAO.EmployeesLayer;
import java.sql.ResultSet;

import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;

public class DriversDAO extends DAO<DriverDTO> {
    
    public DriversDAO() {
        this.tableName = "Drivers";
    }

    @Override
    public int insert(DriverDTO Ob) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public int update(DriverDTO updatedOb) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public DriverDTO makeDTO(ResultSet RS) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeDTO'");
    }
}
