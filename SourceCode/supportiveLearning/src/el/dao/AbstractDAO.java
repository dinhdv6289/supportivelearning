/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DINHDV
 */
public abstract class AbstractDAO<T> {

    public abstract int insert(T t) throws Exception;

    public abstract boolean update(T t) throws Exception;

    public abstract boolean delete(T t) throws Exception;

    public abstract ArrayList<T> list() throws Exception;

    public abstract T getObject(T t) throws Exception;

    private static String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String connString = "jdbc:sqlserver://localhost:1433;databaseName=SupportiveLearning;user=sa;password=adminadmin";


    protected Connection getConnection() throws Exception {
        Class.forName(jdbcDriver);
        return DriverManager.getConnection(connString);
    }

    protected Date sql2date(java.sql.Date date) {
        if (date == null) {
            return null;
        }
        return new java.util.Date(date.getTime());
    }

    protected java.sql.Date date2sql(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }
}
