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
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class AccountDAO extends AbstractDAO<Account>{

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


}
