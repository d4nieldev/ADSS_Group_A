package DataAccessLayer.DAO.TransportLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.TransportLayer.TransportDTO;
import DataAccessLayer.Repository;

public class TransportDAO extends DAO<TransportDTO> {

    public TransportDAO() {
        this.tableName = "Transports";
    }

    @Override
    public int insert(TransportDTO Ob) {
        int ans = 0;
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsert = Ob.fieldsToString();
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsert));
            ans = 1;
        } catch (Exception e) {
            ans = 0;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return ans;
    }

    @Override
    public int update(TransportDTO updatedOb) {
        Connection conn = Repository.getInstance().connect();
        if (updatedOb == null) return 0;
        String updateString = String.format("UPDATE %s" +
                        " SET \"Date\"= \"%s\", " +
                        "\"LeavingTime\"= \"%s\", " +
                        "\"TruckNumber\"= \"%s\", " +
                        "\"DriverName\"= \"%s\", " +
                        "\"DriverID\"= \"%s\", " +
                        "\"Source\"= \"%s\", " +
                        "\"TruckWeightNeto\"= \"%s\", " +
                        "\"TruckWeightMax\"= \"%s\", " +
                        "\"CurrentWeight\"= \"%s\" " +
                        "WHERE \"ID\" == \"%s\";",
                tableName, updatedOb.getDate(), updatedOb.getLeavingTime(), updatedOb.getTruckNumber(),
                updatedOb.getDriverName(), updatedOb.getDriverId(), updatedOb.getSource(),
                updatedOb.getTruckWeightNeto(), updatedOb.getTruckWeightMax(), updatedOb.getCurrentWeight(),
                updatedOb.getId());
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


    public int delete(TransportDTO Ob) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }



    @Override
    public TransportDTO makeDTO(ResultSet RS) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeDTO'");
    }
}
