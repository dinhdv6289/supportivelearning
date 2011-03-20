/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Semester;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TuyenPV
 */
public class SemesterDAO extends AbstractDAO<Semester> {

    @Override
    public int insert(Semester t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_Semester (?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, t.getSemesterName());
            cstmt.setInt(2, t.getSemesterTime());

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
    public boolean update(Semester t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Udp_SemesterById (?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());
            cstmt.setString(2, t.getSemesterName());
            cstmt.setInt(3, t.getSemesterTime());

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
    public boolean delete(Semester t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Semester> list() throws Exception {
        ArrayList<Semester> semesters = new ArrayList<Semester>();
        Connection conn = null;
        String sql = "Sel_AllSemester";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                semesters.add(new Semester(
                        rs.getInt("SemesterId"), rs.getString("SemesterName"), rs.getInt("SemesterTime")));

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return semesters;
    }

    public Semester getSemesterById(int semesterId) throws Exception {
        Connection conn = null;
        Semester semester = null;
        String sql = "{call Sel_SemesterById (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, semesterId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                semester = new Semester(rs.getInt("SemesterId"), rs.getString("SemesterName"), rs.getInt("SemesterTime"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return semester;
    }
    @Override
    public Semester getObject(Semester t) throws Exception {
        Semester semester = new Semester();
        Connection conn = null;
        String sql = "{call Sel_CourseById (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();            
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                semester.setId(rs.getInt("SemesterId"));
                semester.setSemesterName(rs.getString("SemesterName"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return semester;
    }

}
