package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import DataAccessLayer.DTOs.SelfPickUpSupplierDTO;

public class SelfPickUpSupplierDAO extends SupplierDAO {

    public SelfPickUpSupplierDAO(){
        this.tableName = "SelfPickUpSupplier";
        this.identityMap = new HashMap<Integer, SelfPickUpSupplierDTO>(); 
    }

    @Override
    public boolean insert(SelfPickUpSupplierDTO dataObject) {
        Connection con=Repository.getInstance().connect();
        PreparedStatement statement = con.prepareStatement("INSERT INTO "+this.tableName+"(col1, col2....) Values (?, ?)");
        statement.setString(1, dataObject.getId());
        statement.setString(2, dataObject.getName());
        //.....
        statement.executeUpdate();
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public boolean update(SelfPickUpSupplierDTO newDataObject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public SelfPickUpSupplierDTO makeDTO(ResultSet RS) {
       
    }
    
}
