package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;

public class EmployeesDAO extends DAO<EmployeeDTO> {

    public EmployeesDAO() {
        this.tableName = "Employees";
    }

    public int insert(EmployeeDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    public int update(EmployeeDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
