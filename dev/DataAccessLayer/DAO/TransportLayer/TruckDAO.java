package DataAccessLayer.DAO.TransportLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.TransportLayer.TruckDTO;
import DataAccessLayer.Repository;

public class TruckDAO extends DAO<TruckDTO> {

    public TruckDAO() {
        this.tableName = "Trucks";
    }

    @Override
    public int insert(TruckDTO Ob) {
        int ans = 0;
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsertTruck = Ob.fieldsToString();
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertTruck));
            ans = 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ans = 0;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return ans;
    }

    @Override
    public int update(TruckDTO updatedOb) {
        Connection conn = Repository.getInstance().connect();
        if (updatedOb == null) return 0;
        String updateString = String.format("UPDATE %s" +
                        " SET \"Model\"= \"%s\", \"WeightNeto\"= %d, \"WeightMax\"= %d, \"IsAvailable\"= %d" +
                        " WHERE \"PlateNumber\" == \"%s\";",
                tableName, updatedOb.getModel(), updatedOb.getWeightNeto(), updatedOb.getWeightMax(),
                updatedOb.isAvailable() ? 1 : 0, updatedOb.getPlateNumber());
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
    public TruckDTO makeDTO(ResultSet RS) {
        TruckDTO output = null;
        try {
            output = new TruckDTO(RS.getString(1), RS.getString(2), RS.getInt(3), RS.getInt(4), RS.getBoolean(5));
        } catch (Exception e) {
            output = null;
        }
        return output;
    }

    public List<TruckDTO> getAvailableTrucks() {
        String statement = "SELECT * FROM " + tableName + " WHERE \"IsAvailable\" = 1;";
        Connection conn = Repository.getInstance().connect();
        ResultSet RS = null;
        List<TruckDTO> output = new ArrayList<>();
        try {
            Statement S = conn.createStatement();
            RS = S.executeQuery(statement);
            while (RS.next())
                output.add(makeDTO(RS));
        } catch (Exception e) {
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }
}
