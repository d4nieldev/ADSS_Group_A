package DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.Repository;

public abstract class DAO<T> {
    protected String tableName;

    public abstract int insert(T Ob);

    public abstract int update(T updatedOb);

    public List<T> getAll() {
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

    public abstract T makeDTO(ResultSet RS);

    public int delete(String colName,String value)
    {
        String DELETE_SQL=String.format("Delete From %s WHERE %s=\"%s\"",tableName,colName,value);
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        try {
            Statement stmt=con.createStatement();
            rowsAffected=stmt.executeUpdate(DELETE_SQL);
        } catch (SQLException e) {
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    protected String InsertStatement(String Values) {
        return String.format("INSERT INTO %s \n" +
                "VALUES %s;", tableName, Values);
    }
}
