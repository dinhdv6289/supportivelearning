/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Account;
import el.model.Batch;
import el.model.Course;
import el.model.Role;
import el.model.Semester;
import el.model.Staff;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class StaffDAO extends AbstractDAO<Staff> {

    @Override
    public int insert(Staff staff) throws Exception {

        return 0;
    }

    public int insertStaff(Staff staff, int accountId) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_Staff (?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, accountId);
            cstmt.setString(2, staff.getName());
            cstmt.setDate(3, (Date) staff.getBirthDay());
            cstmt.setBoolean(4, staff.getGender());
            cstmt.setString(5, staff.getPhone());
            cstmt.setString(6, staff.getEmail());
            cstmt.setString(7, staff.getAddress());

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
    public boolean update(Staff staff) throws Exception {
        Connection conn = null;
        int a = 0;
        //update khong doi accountId
        String sql = "{call Up_StaffById (?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, staff.getId());
            cstmt.setString(2, staff.getName());
            cstmt.setDate(3, (Date) staff.getBirthDay());
            cstmt.setBoolean(4, staff.getGender());
            cstmt.setString(5, staff.getPhone());
            cstmt.setString(6, staff.getEmail());
            cstmt.setString(7, staff.getAddress());

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
    public boolean delete(Staff staff) throws Exception {
        String sql = "{call Del_StaffById (?)}";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, staff.getId());
            a = stmt.executeUpdate();
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
    public ArrayList<Staff> list() throws Exception {
        ArrayList<Staff> staffs = new ArrayList<Staff>();
        Connection conn = null;
        String sql = "Sel_Staffs";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rsstudents = stmt.executeQuery(sql);
            while (rsstudents.next()) {
                RoleDAO roleDAO = new RoleDAO();
                Role roleSearch = new Role();
                roleSearch.setId(rsstudents.getInt("RoleId"));
                Role role = roleDAO.getObject(roleSearch);
                Staff staff = new Staff();
                staff.setId(rsstudents.getInt("StaffId"));
                staff.setName(rsstudents.getString("FullName"));
                staff.setUserName(rsstudents.getString("UserName"));
                staff.setPassword(rsstudents.getString("Password"));
                staff.setDateCreate(rsstudents.getDate("DateCreation"));
                staff.setRole(role);
                staff.setBirthDay(rsstudents.getDate("BirthDay"));
                staff.setGender(rsstudents.getBoolean("Gender"));
                staff.setPhone(rsstudents.getString("Phone"));
                staff.setEmail(rsstudents.getString("Email"));
                staff.setAddress(rsstudents.getString("Address"));
                staffs.add(staff);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return staffs;
    }

    @Override
    public Staff getObject(Staff staff) throws Exception {
        Staff s = new Staff();
        Connection conn = null;
        String sql = "{call Sel_StaffById (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, staff.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                RoleDAO roleDAO = new RoleDAO();
                Role roleSearch = new Role();
                roleSearch.setId(rs.getInt("RoleId"));
                Role role = roleDAO.getObject(roleSearch);
                s.setId(rs.getInt("StaffId"));
                s.setName(rs.getString("FullName"));
                s.setUserName(rs.getString("UserName"));
                s.setPassword(rs.getString("Password"));
                s.setDateCreate(rs.getDate("DateCreation"));
                s.setRole(role);
                s.setBirthDay(rs.getDate("BirthDay"));
                s.setGender(rs.getBoolean("Gender"));
                s.setPhone(rs.getString("Phone"));
                s.setEmail(rs.getString("Email"));
                s.setAddress(rs.getString("Address"));

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return s;
    }

    public Staff getStaffByAccountId(int accountId) throws Exception {
        Staff s = new Staff();
        Connection conn = null;
        String sql = "{call Sel_StaffByAccountId (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, accountId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                RoleDAO roleDAO = new RoleDAO();
                Role roleSearch = new Role();
                roleSearch.setId(rs.getInt("RoleId"));
                Role role = roleDAO.getObject(roleSearch);
                s.setId(rs.getInt("StaffId"));
                s.setName(rs.getString("FullName"));
                s.setUserName(rs.getString("UserName"));
                s.setPassword(rs.getString("Password"));
                s.setDateCreate(rs.getDate("DateCreation"));
                s.setRole(role);
                s.setBirthDay(rs.getDate("BirthDay"));
                s.setGender(rs.getBoolean("Gender"));
                s.setPhone(rs.getString("Phone"));
                s.setEmail(rs.getString("Email"));
                s.setAddress(rs.getString("Address"));

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return s;
    }

    //Batch.BatchName, Batch.StartDate, Staff.AccountId,
    //StaffAndBatch.StaffAndBatchId, StaffAndBatch.StaffId, StaffAndBatch.BatchId

    public ArrayList<Batch> getListBatchsByStaffId(int staffId) throws Exception {
        ArrayList<Batch> listBatchs = new ArrayList<Batch>();
        Batch batch = new Batch();
        Staff staff = new Staff();
        Connection conn = null;
        String sql = "{call Sel_BatchsOfStaff (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, staffId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                batch.setId(rs.getInt("BatchId"));
                batch.setName(rs.getString("BatchName"));
                batch.setStartDate(rs.getDate("StartDate"));
                staff.setId(rs.getInt("StaffId"));
                listBatchs.add(batch);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return listBatchs;
    }

    public boolean deleteBatchByStaff(Staff s, Batch b) throws Exception{
        String sql = "{call Del_BatchByStaff (?, ?)}";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, s.getId());
            stmt.setInt(2, b.getId());
            a = stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return a == 1 ? true : false;
    }

    public int insertStaffAndBatch(Staff staff, Batch batch) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_StaffAndBatch (?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, staff.getId());
            cstmt.setInt(2, batch.getId());

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
}
