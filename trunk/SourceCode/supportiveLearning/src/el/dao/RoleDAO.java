/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Role;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TuyenPV
 */
public class RoleDAO extends AbstractDAO<Role> {

    @Override
    public int insert(Role t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_Roles (?, ?)}";
        CallableStatement cstmt = null;

        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, t.getName());
            cstmt.setString(2, t.getDescription());

            a = cstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return a;
    }

    @Override
    public boolean update(Role t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Udp_RolesById (?, ?, ?)}";
        CallableStatement cstmt = null;

        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());
            cstmt.setString(2, t.getName());
            cstmt.setString(3, t.getDescription());

            a = cstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return a == 1 ? true : false;
    }

    @Override
    public boolean delete(Role role) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Role> list() throws Exception {
        ArrayList<Role> roles = new ArrayList<Role>();
        Connection conn = null;
        String sql = "Sel_AllRoles";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("RoleId"));
                role.setName(rs.getString("RoleName"));
                role.setDescription(rs.getString("Description"));
                roles.add(role);

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return roles;
    }

    @Override
    public Role getObject(Role role) throws Exception {
        Connection conn = null;
        Role roleObject = null;
        String sql = "{call Sel_RolesById (?)}";
        CallableStatement cstmt = null;

        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);

            cstmt.setInt(1, role.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                roleObject = new Role();
                role.setId(rs.getInt("RoleId"));
                role.setName(rs.getString("RoleName"));
                role.setDescription(rs.getString("Description"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return roleObject;
    }
}
