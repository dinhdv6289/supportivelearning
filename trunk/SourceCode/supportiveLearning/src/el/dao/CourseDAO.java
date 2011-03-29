/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Course;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TuyenPV
 */
public class CourseDAO extends AbstractDAO<Course> {

    @Override
    public int insert(Course t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_Course (?, ?, ?, ?)}";
        CallableStatement cstmt = null;

        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, t.getName());
            cstmt.setDate(2, (Date) t.getDateStart());
            cstmt.setDate(3, (Date) t.getDateEnd());
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
    public boolean update(Course t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Udp_CourseById (?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;

        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());
            cstmt.setString(2, t.getName());
            cstmt.setDate(3, (Date) t.getDateStart());
            cstmt.setDate(4, (Date) t.getDateEnd());
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
    public boolean delete(Course t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Course> list() throws Exception {
        ArrayList<Course> courses = new ArrayList<Course>();
        Connection conn = null;
        String sql = "Sel_AllCourse";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("CourseId"));
                course.setName(rs.getString("CourseName"));
                course.setDateStart(sql2date(rs.getDate("DateStart")));
                course.setDateEnd(sql2date(rs.getDate("DateEnd")));
                courses.add(course);

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return courses;
    }

    @Override
    public Course getObject(Course t) throws Exception {
        Course c = new Course();
        Connection conn = null;
        String sql = "{call Sel_CourseById (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();         
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                c.setId(rs.getInt("CourseId"));
                c.setName(rs.getString("CourseName"));
                c.setDateEnd(rs.getDate("DateEnd"));
                c.setDateStart(rs.getDate("DateStart"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return c;
    }
}
