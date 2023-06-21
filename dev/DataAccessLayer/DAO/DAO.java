package DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.Repository;

public abstract class DAO<T> {
    protected String tableName;

    public abstract int insert(T Ob);

    public abstract int update(T updatedOb);

    public  List<T> getAll() {
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
            System.out.println(e.getMessage());
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    // get all by localDate
    public List<T> getAll(String colName,LocalDate date) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE \"%s\"=\"%s\"", tableName, colName, date);
        Connection conn = Repository.getInstance().connect();
        ResultSet RS = null;
        List<T> output = new ArrayList<T>();
        try {
            Statement S = conn.createStatement();
            RS = S.executeQuery(SELECT_SQL);
            while (RS.next())
                output.add(makeDTO(RS));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    // get by Int
    public ResultSet get(String nameOfTable, String colName, Integer value, Connection con) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE \"%s\"=\"%d\"", nameOfTable, colName, value);
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_SQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rs;
    }

    // get by String
    public ResultSet get(String nameOfTable, String colName, String value, Connection con) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE \"%s\"=\"%s\"", nameOfTable, colName, value);
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_SQL);
        } catch (SQLException e) {
        }

        return rs;
    }

    // get by LocalDate
    public ResultSet get(String nameOfTable, String colName, LocalDate value, Connection con) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE \"%s\"=\"%s\"", nameOfTable, colName, value);
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_SQL);
        } catch (SQLException e) {
        }

        return rs;
    }

    // get by String and 3 WHERE
    public ResultSet get(String nameOfTable, String colName1, Integer value1, String colName2, Integer value2,
                         String colName3, String value3, Connection con) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE \"%s\"=\"%s\" AND \"%d\"=\"%d\" AND \"%s\"=\"%s\"",
         nameOfTable, colName1, value1, colName2, value2, colName3, value3);
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_SQL);
        } catch (SQLException e) {
        }

        return rs;
    }

    // get by String and 2 WHERE
    public ResultSet get(String nameOfTable, String colName1, Integer value1, String colName2, String value2
                         , Connection con) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE \"%s\"=\"%s\" AND \"%s\"=\"%s\"",
         nameOfTable, colName1, value1, colName2, value2);
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_SQL);
        } catch (SQLException e) {
        }

        return rs;
    }

    public abstract T makeDTO(ResultSet RS);

    public int delete(String colName,Integer value)
    {
        String DELETE_SQL=String.format("Delete From %s WHERE %s=\"%d\"",tableName,colName,value);
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
