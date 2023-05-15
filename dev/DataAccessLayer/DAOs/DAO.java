package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.DTO;

public abstract class DAO<T extends DTO> {
    protected String tableName;
    protected Repository repo;

    protected DAO(String tableName) {
        this.tableName = tableName;
        this.repo = Repository.getInstance();
    }

    /**
     * Makes a DTO object from a result set.
     * 
     * @param rs Result set that points to a specific row in the table.
     * @return A DTO object.
     */
    public abstract T makeDTO(ResultSet rs) throws SQLException;

    private Map<String, String> getColumnNameToType() throws SQLException {
        Connection conn = repo.connect();

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
        repo.closeConnection(conn);

        return nameToType;
    }

    private List<String> getPKColumns() throws SQLException {
        Connection conn = repo.connect();

        DatabaseMetaData meta = conn.getMetaData();

        ResultSet pk = meta.getPrimaryKeys(null, null, tableName);

        List<String> pks = new ArrayList<>();
        while (pk.next()) {
            String columnName = pk.getString("COLUMN_NAME");
            pks.add(columnName);
        }

        pk.close();
        repo.closeConnection(conn);

        return pks;
    }

    private void setValInStatement(PreparedStatement statement, String val, String type, int idx) throws SQLException {
        switch (type) {
            case "INTEGER":
                if (val == null)
                    statement.setNull(idx, Types.INTEGER);
                else
                    statement.setInt(idx, Integer.parseInt(val));
            case "REAL":
                if (val == null)
                    statement.setNull(idx, Types.REAL);
                else
                    statement.setDouble(idx, Double.parseDouble(val));
            case "TEXT":
                if (val == null)
                    statement.setNull(idx, Types.VARCHAR);
                else
                    statement.setString(idx, val);
        }
    }

    public void insert(T dataObject) throws SQLException {
        Connection con = repo.connect();

        Map<String, String> nameToType = getColumnNameToType();
        StringBuilder query = new StringBuilder("INSERT INTO " + this.tableName);
        query.append("(" + String.join(", ", nameToType.keySet()) + ")\n");
        query.append(
                " VALUES (" + String.join(", ", Collections.nCopies(nameToType.size(), "?")) + ");");
        PreparedStatement statement = con.prepareStatement(query.toString());

        Map<String, String> nameToVal = dataObject.getNameToVal();
        int i = 1;
        for (String colName : nameToType.keySet()) {
            String val = nameToVal.get(colName);
            String type = nameToType.get(colName);
            setValInStatement(statement, val, type, i++);
        }

        statement.executeUpdate();
        statement.close();
        repo.closeConnection(con);
    }

    public void update(T newDataObject) throws SQLException {
        Connection con = repo.connect();
        Map<String, String> nameToVal = newDataObject.getNameToVal();

        StringBuilder query = new StringBuilder("UPDATE " + this.tableName + " SET ");

        List<String> pks = getPKColumns();
        List<String> assignments = new ArrayList<>();
        for (String colName : nameToVal.keySet())
            if (!pks.contains(colName))
                assignments.add(colName + " = ?");
        query.append(String.join(", ", assignments));

        query.append(" WHERE ");
        List<String> searchConditions = new ArrayList<>();
        for (String colName : pks)
            searchConditions.add(colName + " = ?");
        query.append(String.join(" AND ", searchConditions) + ";");

        PreparedStatement statement = con.prepareStatement(query.toString());

        // fill fields parameters
        Map<String, String> nameToType = getColumnNameToType();
        int i = 1;
        for (String colName : nameToVal.keySet()) {
            String val = nameToVal.get(colName);
            String type = nameToType.get(colName);
            if (!pks.contains(colName))
                setValInStatement(statement, val, type, i++);
        }
        // fill primary key parameters
        for (String colName : pks) {
            String val = nameToVal.get(colName);
            String type = nameToType.get(colName);
            setValInStatement(statement, val, type, i++);
        }

        statement.executeUpdate();
        statement.close();
        repo.closeConnection(con);
    }

    /**
     * Generic deletion method for the the rows that meet the criteria
     * 
     * @param colName the column name
     * @param value   the value that the row should meet to be deleted
     * @return the number of the deleted rows.
     */
    public void delete(T dataObject) throws SQLException {
        Connection con = repo.connect();

        StringBuilder query = new StringBuilder("DELETE FROM " + tableName + " WHERE ");

        Map<String, String> nameToVal = dataObject.getNameToVal();
        List<String> pks = getPKColumns();
        List<String> searchConditions = new ArrayList<>();
        for (String colName : pks)
            searchConditions.add(colName + " = ?");
        query.append(String.join(" AND ", searchConditions) + ";");

        Map<String, String> nameToType = getColumnNameToType();
        PreparedStatement statement = con.prepareStatement(query.toString());
        int i = 1;
        for (String colName : pks) {
            String val = nameToVal.get(colName);
            String type = nameToType.get(colName);
            setValInStatement(statement, val, type, i++);
        }

        statement.executeUpdate();

        statement.close();
        repo.closeConnection(con);
    }

    /**
     * Selects all the rows of a table.
     * 
     * @return a list that contains all the DTO objects that represent all the rows
     *         of a table.
     */
    public List<T> selectAll() throws SQLException {
        Connection conn = repo.connect();

        String query = "SELECT * FROM " + tableName + ";";

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);

        List<T> output = new ArrayList<>();
        while (rs.next())
            output.add(makeDTO(rs));

        repo.closeConnection(conn);
        return output;
    }

}
