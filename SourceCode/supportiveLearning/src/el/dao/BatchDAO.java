/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Batch;
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
public class BatchDAO extends AbstractDAO<Batch> {

    @Override
    public boolean insert(Batch t) throws Exception {
        String sql = "Ins_Clazz ?, ?, ?, ?";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, t.getCourse().getId());
            ps.setInt(2, t.getSemester().getId());
            ps.setString(3, t.getName());
            ps.setDate(4, (Date) t.getStartDate());

            a = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return true ? a == 1 : false;
    }

    @Override
    public boolean update(Batch t) throws Exception {
        String sql = "Udp_ClazzById ?, ?, ?, ?, ?";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, t.getId());
            ps.setInt(2, t.getCourse().getId());
            ps.setInt(3, t.getSemester().getId());
            ps.setString(4, t.getName());
            ps.setDate(5, (Date) t.getStartDate());

            a = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return true ? a == 1 : false;
    }

    @Override
    public boolean delete(Batch t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Batch> list() throws Exception {
        Connection conn = null;
        ArrayList<Batch> clazzs = new ArrayList<Batch>();
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
                clazzs.add(new Batch(rs.getInt("ClazzId"), rs.getString("ClazzName"), course, semester, rs.getDate("StartDate")));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return clazzs;
    }

    public Batch getClazzById(Batch batch) throws Exception {
        Connection conn = null;
        Batch clazz = null;

        String sql = "{call Sel_ClazzById (?)}";
        CallableStatement cstmt = null;

        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);

            cstmt.setInt(1, batch.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                CourseDAO courseDAO = new CourseDAO();
                Course course = courseDAO.getCourseById(rs.getInt("CourseId"));
                SemesterDAO semesterDAO = new SemesterDAO();
                Semester semester = semesterDAO.getSemesterById(rs.getInt("SemesterId"));
                clazz = new Batch(rs.getInt("ClazzId"), rs.getString("ClazzName"), course, semester, rs.getDate("StartDate"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return clazz;
    }

    @Override
    public Batch getObject(Batch t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
