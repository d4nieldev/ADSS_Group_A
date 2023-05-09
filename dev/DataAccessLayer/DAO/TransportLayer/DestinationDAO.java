package DataAccessLayer.DAO.TransportLayer;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.TransportLayer.DestinationDTO;
import DataAccessLayer.Repository;

public class DestinationDAO extends DAO<DestinationDTO> {

    public DestinationDAO() {
        this.tableName = "Destinations";
    }

    @Override
    public int insert(DestinationDTO Ob) {
        int ans = 0;
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsertDestination = Ob.fieldsToString();
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertDestination));
            ans = 1;
        } catch (Exception e) {
            ans = 0;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return ans;
    }


    @Override
    public int update(DestinationDTO updatedOb) {
        Connection conn = Repository.getInstance().connect();
        if (updatedOb == null) return 0;
        String updateString = String.format("UPDATE %s" +
                        " SET \"Address\"= \"%s\", \"PhoneNumber\"= \"%s\", \"ContactName\"= \"%s\", \"Location\"= %f, \"DestinationType\"= \"%s\"" +
                        " WHERE \"Address\" = %s;",
                tableName, updatedOb.getAddress(), updatedOb.getPhoneNumber(), updatedOb.getContactName(),
                updatedOb.getLocation(),
                updatedOb.getDestinationType(), updatedOb.getAddress());
        Statement s;
        try {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        } catch (Exception e) {
            return 0;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
    }


    @Override
    public DestinationDTO makeDTO(ResultSet RS) {
        // TODO: Implement makeDTO method
        return null;
    }

}
