/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Batch;
import el.model.Course;
import el.model.Semester;
import el.model.Staff;
import el.utility.Utility;
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
public class BatchDAO extends AbstractDAO<Batch> {

    @Override
    public int insert(Batch t) throws Exception {
        String sql = "{call Ins_Batch (?, ?, ?, ?)}";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            CallableStatement ps = conn.prepareCall(sql);
            ps.setInt(1, t.getCourse().getId());
            ps.setInt(2, t.getSemester().getId());
            ps.setString(3, t.getName());
            ps.setDate(4, Utility.date2sql(t.getStartDate()));

            a = ps.executeUpdate();
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
    public boolean update(Batch t) throws Exception {
        String sql = "{call Udp_BatchById (?, ?, ?, ?, ?)}";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            CallableStatement ps = conn.prepareCall(sql);
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
        ArrayList<Batch> batchs = new ArrayList<Batch>();
        String sql = "Sel_AllBatch";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CourseDAO courseDAO = new CourseDAO();
                Course course = new Course();
                course.setId(rs.getInt("CourseId"));
                course =       courseDAO.getObject(course);
                SemesterDAO semesterDAO = new SemesterDAO();
                Semester semester = new Semester();
                semester.setId(rs.getInt("SemesterId"));
                semester = semesterDAO.getObject(semester);
                Batch batch = new Batch();
                batch.setId(rs.getInt("BatchId"));
                batch.setName(rs.getString("BatchName"));
                batch.setCourse(course);
                batch.setSemester(semester);
                batch.setStartDate(Utility.sql2date(rs.getDate("StartDate")));
                batchs.add(batch);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return batchs;
    }

    @Override
    public Batch getObject(Batch t) throws Exception {
        Batch batch = new Batch();
        Connection conn = null;
        String sql = "{call Sel_BatchById (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                batch.setId(rs.getInt("BatchId"));
                batch.setName(rs.getString("BatchName"));
                CourseDAO courseDAO = new CourseDAO();
                Course course = new Course();
                course = courseDAO.getObject(course);
                batch.setCourse(course);
                SemesterDAO semesterDAO = new SemesterDAO();
                Semester semester = new Semester();
                semester = semesterDAO.getObject(semester);
                batch.setSemester(semester);
                batch.setStartDate(rs.getDate("StartDate"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return batch;
    }

    public ArrayList<Batch> listBatchOfSemester(int id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public ArrayList<Batch> listBatchNoStaff(Staff staff) throws Exception{
        Connection conn = null;
        ArrayList<Batch> batchs = new ArrayList<Batch>();
        String sql = "{call Sel_BatchNoStaff (?)}";
         CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, staff.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                CourseDAO courseDAO = new CourseDAO();
                Course course = new Course();
                course.setId(rs.getInt("CourseId"));
                course =       courseDAO.getObject(course);
                SemesterDAO semesterDAO = new SemesterDAO();
                Semester semester = new Semester();
                semester.setId(rs.getInt("SemesterId"));
                semester = semesterDAO.getObject(semester);
                Batch batch = new Batch();
                batch.setId(rs.getInt("BatchId"));
                batch.setName(rs.getString("BatchName"));
                batch.setCourse(course);
                batch.setSemester(semester);
                batch.setStartDate(Utility.sql2date(rs.getDate("StartDate")));
                batchs.add(batch);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return batchs;
    }
}
