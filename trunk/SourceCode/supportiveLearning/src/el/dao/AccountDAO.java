/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Account;
import el.model.Role;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DINHDV
 */
public class AccountDAO extends AbstractDAO<Account> {

    @Override
    public int insert(Account account) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Account account) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Account account) throws Exception {
        return false;
    }

    @Override
    public ArrayList<Account> list() throws Exception {
        return null;
    }

    @Override
    public Account getObject(Account account) throws Exception {
        Connection conn = null;
        Account account1 = new Account();
        String sql = "{call Sel_AccountByUserNameAndPass (?, ?)}";
        try {
            CallableStatement cstmt = null;
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, account.getUserName());
            cstmt.setString(2, account.getPassword());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                account1.setAddress(rs.getString("Address"));
                account1.setBirthDay(rs.getDate("BirthDay"));
                account1.setDateCreate(rs.getDate("DateCreation"));
                account1.setEmail(rs.getString("Email"));
                account1.setGender(rs.getBoolean("Gender"));
                account1.setId(rs.getInt("AccountId"));
                account1.setName(rs.getString("FullName"));
                account1.setPassword(rs.getString("PassWord"));
                account1.setPhone(rs.getString("Phone"));
                Role role = new Role();
                role.setId(rs.getInt("RoleId"));
                RoleDAO roleDAO = new RoleDAO();
                role = roleDAO.getObject(role);
                account1.setRole(role);
                account1.setUserName(rs.getString("UserName"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return account1;
    }

    public Account getAccountById(int accountId) throws Exception {
        Connection conn = null;
        Account account1 = new Account();
        String sql = "{call Sel_AccountById (?)}";
        try {
            CallableStatement cstmt = null;
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, accountId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                account1.setAddress(rs.getString("Address"));
                account1.setBirthDay(rs.getDate("BirthDay"));
                account1.setDateCreate(rs.getDate("DateCreation"));
                account1.setEmail(rs.getString("Email"));
                account1.setGender(rs.getBoolean("Gender"));
                account1.setId(rs.getInt("AccountId"));
                account1.setName(rs.getString("FullName"));
                account1.setPassword(rs.getString("PassWord"));
                account1.setPhone(rs.getString("Phone"));
                Role role = new Role();
                role.setId(rs.getInt("RoleId"));
                RoleDAO roleDAO = new RoleDAO();
                role = roleDAO.getObject(role);
                account1.setRole(role);
                account1.setUserName(rs.getString("UserName"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return account1;
    }

    public Account getLatestAccount() throws Exception {
        Connection conn = null;
        Account account = new Account();
        String sql = "SelectLatestAccount";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                account.setAddress(rs.getString("Address"));
                account.setBirthDay(rs.getDate("BirthDay"));
                account.setDateCreate(rs.getDate("DateCreation"));
                account.setEmail(rs.getString("Email"));
                account.setGender(rs.getBoolean("Gender"));
                account.setId(rs.getInt("AccountId"));
                account.setName(rs.getString("FullName"));
                account.setPassword(rs.getString("PassWord"));
                account.setPhone(rs.getString("Phone"));
                Role role = new Role();
                role.setId(rs.getInt("RoleId"));
                RoleDAO roleDAO = new RoleDAO();
                role = roleDAO.getObject(role);
                account.setRole(role);
                account.setUserName(rs.getString("UserName"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return account;
    }

    public ArrayList<Account> getObjectIsOnline() throws Exception {
        ArrayList<Account> accounts = new ArrayList<Account>();
        Connection conn = null;

        String sql = "Sel_AccountIsOnline";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Account account = new Account();
                account.setAddress(rs.getString("Address"));
                account.setBirthDay(rs.getDate("BirthDay"));
                account.setDateCreate(rs.getDate("DateCreation"));
                account.setEmail(rs.getString("Email"));
                account.setGender(rs.getBoolean("Gender"));
                account.setId(rs.getInt("AccountId"));
                account.setName(rs.getString("FullName"));
                account.setPassword(rs.getString("PassWord"));
                account.setPhone(rs.getString("Phone"));
                Role role = new Role();
                role.setId(rs.getInt("RoleId"));
                RoleDAO roleDAO = new RoleDAO();
                role = roleDAO.getObject(role);
                account.setRole(role);
                account.setUserName(rs.getString("UserName"));
                accounts.add(account);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return accounts;
    }

    public boolean setAccountOnline(int accountId, boolean isLogin) throws Exception {
        String sql = "{call SetAccountOnline (?, ?)}";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            CallableStatement ps = conn.prepareCall(sql);
            ps.setInt(1, accountId);
            ps.setBoolean(2, isLogin);

            a = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return a == 1 ? true : false;
    }
}
