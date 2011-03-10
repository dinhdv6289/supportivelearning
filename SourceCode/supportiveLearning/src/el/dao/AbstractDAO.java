/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public abstract class AbstractDAO<T> {


    public abstract boolean insert(T t) throws Exception;

    public abstract boolean update(T t)  throws Exception;

    public abstract boolean delete(T t)  throws Exception;

    public abstract ArrayList<T> list()  throws Exception;


    public final static String JDBC_DRIVER = "jdbc.driver";
    public final static String CONN_STRING = "conn.string";

    private static String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String connString = "jdbc:sqlserver://localhost:1433;databaseName=SupportiveLearning;user=sa;password=adminadmin";
    protected Connection getConnection() throws Exception {
        Class.forName(jdbcDriver);
        return DriverManager.getConnection(connString);
    }
}
