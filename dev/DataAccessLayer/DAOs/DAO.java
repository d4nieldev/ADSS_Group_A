package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.DTO;

public abstract class DAO {

    protected String tableName;
    protected Map<Integer, DTO> identityMap;
    protected List<String> columnNames;

    private Map<String, String> getColumnNameToType() throws SQLException {
        Connection conn = Repository.getInstance().connect();

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

        ResultSetMetaData meta = rs.getMetaData();

        Map<String, String> nameToType = new HashMap<>();
        int numColumns = meta.getColumnCount();
        for (int i = 1; i <= numColumns; i++) {
            String columnName = meta.getColumnName(i);
            String columnType = meta.getColumnTypeName(i);
            nameToType.put(columnName, columnType);
        }

        rs.close();
        stmt.close();
        conn.close();

        return nameToType;
    }

    public boolean insert(DTO dataObject) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        con = Repository.getInstance().connect();

        Map<String, String> nameToType = getColumnNameToType();
        StringBuilder query = new StringBuilder("INSERT INTO " + this.tableName);
        query.append("(" + String.join(", ", nameToType.keySet()) + ")");
        query.append(
                "VALUES (" + String.join(", ", Collections.nCopies(nameToType.size(), "?")) + ")");
        statement = con.prepareStatement(query.toString());

        List<String> values = dataObject.getColumnValues();
        int i = 0;
        for (String colName : nameToType.keySet()) {
            String genericVal = values.get(i);
            switch (nameToType.get(colName)) {
                case "INTEGER":
                    statement.setInt(++i, Integer.parseInt(genericVal));
                case "REAL":
                    statement.setDouble(++i, Double.parseDouble(genericVal));
                case "TEXT":
                    statement.setString(++i, genericVal);

            }
        }

        statement.executeUpdate();
        statement.close();
        con.close();

        return true;
    }

    public abstract boolean update(DTO newDataObject) throws SQLException;

    /**
     * Generic deletion method for the the rows that meet the criteria
     * 
     * @param colName the column name
     * @param value   the value that the row should meet to be deleted
     * @return the number of the deleted rows.
     */
    public int delete(String colName, String value) throws SQLException {
        String statement = String.format("Delete From %s WHERE %s=\"%s\"", tableName, colName, value);
        int rowsAffected = -1;
        Connection con = Repository.getInstance().connect();
        try {
            Statement stmt = con.createStatement();
            rowsAffected = stmt.executeUpdate(statement);
        } catch (SQLException e) {
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    /**
     * Selects all the rows of a table.
     * 
     * @return a list that contains all the DTO objects that represent all the rows
     *         of a table.
     */
    public List<T> selectAll() throws SQLException {
        String statement = "SELECT * FROM " + tableName + ";";
        Connection conn = Repository.getInstance().connect();
        ResultSet RS = null;
        List<T> output = new ArrayList<T>();
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

    /**
     * Selects one row from the table.
     * 
     * @return a DTO object representing the row.
     */
    public T selectRow(int id) throws SQLException {
        String statement = "SELECT * FROM " + tableName + " Where id= " + id + ";";
        Connection conn = Repository.getInstance().connect();
        ResultSet RS = null;
        T output = null;
        try {
            Statement S = conn.createStatement();
            RS = S.executeQuery(statement);
            RS.next();
            output = makeDTO(RS);
        } catch (Exception e) {
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    /**
     * Makes a DTO object from a result set.
     * 
     * @param RS Result set that points to a specific row in the table.
     * @return A DTO object.
     */
    public abstract T makeDTO(ResultSet RS) throws SQLException;

    // TODO: Do we need this??
    public String InsertStatement(String Values) throws SQLException {
        return String.format("INSERT INTO %s \n" +
                "VALUES %s;", tableName, Values);
    }

    public T getIfExists(){
        if(identityMap.containsKey(identityMap))
    }

    public String columnsString(List<String> columnNames) {
        return String.join(", ", columnNames);
    }

}
