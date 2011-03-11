/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Clazz;
import el.model.Course;
import el.model.Semester;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TuyenPV
 */
public class ClazzDAO extends AbstractDAO<Clazz> {

    @Override
    public boolean insert(Clazz t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_Clazz (?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall (sql);
            cstmt.setInt(1, t.getCourse().getId());
            cstmt.setInt(2, t.getSemester().getId());
            cstmt.setString(3, t.getName());
            cstmt.setDate(4, (Date) t.getStartDate());

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
    public boolean update(Clazz t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Udp_ClazzById (?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall (sql);
            cstmt.setInt(1, t.getId());
            cstmt.setInt(2, t.getCourse().getId());
            cstmt.setInt(3, t.getSemester().getId());
            cstmt.setString(4, t.getName());
            cstmt.setDate(5, (Date) t.getStartDate());

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
    public boolean delete(Clazz t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Clazz> list() throws Exception {
        Connection conn = null;
        ArrayList<Clazz> clazzs = new ArrayList<Clazz>();
        String sql = "Sel_AllClazz";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CourseDAO courseDAO = new CourseDAO();
                Course course = courseDAO.getCourseById(rs.getInt("CourseId"));
                SemesterDAO semesterDAO = new SemesterDAO();
                Semester semester = semesterDAO.getSemesterById(rs.getInt("SemesterId"));
                clazzs.add(new Clazz(rs.getInt("ClazzId"), rs.getString("ClazzName"), course, semester, rs.getDate("StartDate")));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return clazzs;
    }

    public Clazz getClazzById(int clazzId) throws Exception {
        Connection conn = null;
        Clazz clazz = null;

        String sql = "{call Sel_ClazzById (?)}";
        CallableStatement cstmt = null;

        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);

            cstmt.setInt(1, clazzId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                CourseDAO courseDAO = new CourseDAO();
                Course course = courseDAO.getCourseById(rs.getInt("CourseId"));
                SemesterDAO semesterDAO = new SemesterDAO();
                Semester semester = semesterDAO.getSemesterById(rs.getInt("SemesterId"));
                clazz = new Clazz(rs.getInt("ClazzId"), rs.getString("ClazzName"), course, semester, rs.getDate("StartDate"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return clazz;
    }
}
