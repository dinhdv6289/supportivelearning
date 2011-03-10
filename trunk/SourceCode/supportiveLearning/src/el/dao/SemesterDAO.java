/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.Semester;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TuyenPV
 */
public class SemesterDAO extends AbstractDAO<Semester> {

    @Override
    public boolean insert(Semester t) throws Exception {
        String sql = "Ins_Semester ?, ?";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, t.getSemesterName());
            ps.setInt(2, t.getSemesterTime());
           
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
    public boolean update(Semester t) throws Exception {
        
        String sql = "Udp_SemesterById ?, ?, ?";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, t.getId());
            ps.setString(2, t.getSemesterName());
            ps.setInt(3, t.getSemesterTime());

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
                            rs.getInt("SemesterId")
                        , rs.getString("SemesterName")
                        , rs.getInt("SemesterTime")));

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
        String sql = "Sel_SemesterById ?";
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, semesterId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                semester = new Semester(rs.getInt("SemesterId")
                        , rs.getString("SemesterName")
                        ,rs.getInt("SemesterTime"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return semester;
    }

}
