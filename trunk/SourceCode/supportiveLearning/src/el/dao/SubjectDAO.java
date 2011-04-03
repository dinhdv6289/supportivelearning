/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.Subject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TuyenPV
 */
public class SubjectDAO extends AbstractDAO<Subject>{

    @Override
    public int insert(Subject t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_Subject (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, t.getName());
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
    public boolean update(Subject t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Subject t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Subject> list() throws Exception {
        Connection conn = null;
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        String sql = "Sel_AllSubject";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("SubjectId"));
                subject.setName(rs.getString("SubjectName"));
                subjects.add(subject);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return subjects;
    }

    @Override
    public Subject getObject(Subject t) throws Exception {
        Subject s = new Subject();
        Connection conn = null;
            String sql = "{call Sel_SubjectById (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());           
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {             
                s.setId(rs.getInt("SubjectId"));
                s.setName(rs.getString("SubjectName"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return s;
    }

}
