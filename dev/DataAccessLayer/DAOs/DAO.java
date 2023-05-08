package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DataAccessLayer.Repository;

public abstract class DAO<T> {

    protected String tableName;
    protected Map<Integer,T> identityMap;
    protected List<String> columnNames;

    public abstract boolean insert (T dataObject) throws SQLException;
    public abstract boolean update (T newDataObject) throws SQLException;

    /**
     * Generic deletion method for the the rows that meet the criteria
     * @param colName the column name
     * @param value the value that the row should meet to be deleted
     * @return the number of the deleted rows.
     */
    public int delete(String colName,String value) throws SQLException
    {
        String statement=String.format("Delete From %s WHERE %s=\"%s\"",tableName,colName,value);
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        try {
            Statement stmt=con.createStatement();
            rowsAffected=stmt.executeUpdate(statement);
        } catch (SQLException e) {
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }
    
    /**
     * Selects all the rows of a table.
     * @return a list that contains all the DTO objects that represent all the rows of a table.
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
     * @return a DTO object representing the row.
     */
    public T selectRow(int id) throws SQLException {
        String statement = "SELECT * FROM " + tableName + " Where id= "+id+";";
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
     * @param RS Result set that points to a specific row in the table.
     * @return A DTO object.
     */
    public abstract T makeDTO(ResultSet RS) throws SQLException;


    //TODO: Do we need this??
    public String InsertStatement(String Values) throws SQLException {
        return String.format("INSERT INTO %s \n" +
                "VALUES %s;", tableName, Values);
    }

    public T getIfExists(){
        if(identityMap.containsKey(identityMap))
    }

    public String columnsString(List<String> columnNames){
        String res="";
        for(String column : columnNames){
            res+=column+", ";
        }
        return res.substring(0, res.length()-2);
    }


    
}
