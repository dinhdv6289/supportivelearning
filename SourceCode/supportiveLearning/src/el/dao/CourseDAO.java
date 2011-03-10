/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.Course;
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
public class CourseDAO extends AbstractDAO<Course>{

    @Override
    public boolean insert(Course t) throws Exception {
        String sql = "Ins_Course ?, ?, ?, ?";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, t.getName());
            ps.setDate(2, (Date) t.getDateStart());
            ps.setDate(3, (Date) t.getDateEnd());
            ps.setString(4, t.getBatch());

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
    public boolean update(Course t) throws Exception {
        String sql = "Udp_CourseById ?, ?, ?, ?, ?";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, t.getId());
            ps.setString(2, t.getName());
            ps.setDate(3, (Date) t.getDateStart());
            ps.setDate(4, (Date) t.getDateEnd());
            ps.setString(5, t.getBatch());

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
                courses.add(new Course(
                        rs.getInt("CourseId")
                        ,rs.getString("CourseName")
                        ,rs.getDate("DateStart")
                        ,rs.getDate("DateEnd")
                        ,rs.getString("Batch")));

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return courses;
    }

    public Course getCourseById(int courseId) throws Exception {
        Connection conn = null;
        Course course = null;
        String sql = "Sel_CourseById ?";
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                course = new Course(rs.getInt("CourseId")
                        , rs.getString("CourseName")
                        ,rs.getDate("DateStart")
                        ,rs.getDate("DateEnd")
                        ,rs.getString("Batch"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return course;
    }
}
