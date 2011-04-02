/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.FeedBack;
import el.model.Student;
import el.utility.Utility;
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
public class FeedBackDAO extends AbstractDAO<FeedBack>{

    @Override
    public int insert(FeedBack t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_FeedBack (?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try{
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getStudent().getId());
            cstmt.setInt(2, t.getStaff().getId());
            cstmt.setString(3, t.getFeedBackTitle());
            cstmt.setString(4, t.getFeedBackContent());
             a = cstmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(conn!=null){
                conn.close();
            }
        }
        return a;
    }

    @Override
    public boolean update(FeedBack t) throws Exception {
        Connection conn = null;
        int a = 0;
        //tham so thu 5 la feedbackId
        String sql = "{call Upd_FeedBack (?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try{
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getStudent().getId());
            cstmt.setString(2, t.getFeedBackTitle());
            cstmt.setString(3, t.getFeedBackContent());
            cstmt.setDate(4, (Date) t.getDateCreation());
            cstmt.setInt(5, t.getId());

             a = cstmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(conn!=null){
                conn.close();
            }
        }
        return a == 1? true:false;
    }

    @Override
    public boolean delete(FeedBack t) throws Exception {
         Connection conn = null;
        int a = 0;
        String sql = "{call Del_FeedBack (?)}";
        CallableStatement cstmt = null;
        try{
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());

             a = cstmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(conn!=null){
                conn.close();
            }
        }
        return a == 1? true:false;
    }

    @Override
    public ArrayList<FeedBack> list() throws Exception {
        Connection conn = null;
        ArrayList<FeedBack> feedBacks = new ArrayList<FeedBack>();
        String sql = "Sel_Feedbacks";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                FeedBack feedBack = new FeedBack();
                feedBack.setDateCreation(rs.getDate("DateCreation"));
                feedBack.setFeedBackContent(rs.getString("FeedBackContent"));
                feedBack.setFeedBackTitle(rs.getString("FeedBackTitle"));
                feedBack.setId(rs.getInt("FeedBackId"));
                Student student = new Student();
                StudentDAO studentDAO = new StudentDAO();
                student.setId(rs.getInt("StudentId"));
                student = studentDAO.getObject(student);
                feedBack.setStudent(student);
                feedBacks.add(feedBack);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return feedBacks;
    }

    @Override
    public FeedBack getObject(FeedBack t) throws Exception {
        Connection conn = null;
        FeedBack feedBack = new FeedBack();
        String sql = "{call Sel_FeedBackById (?)}";
        try {
             CallableStatement cstmt = null;
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                feedBack.setDateCreation(rs.getDate("DateCreation"));
                feedBack.setFeedBackContent(rs.getString("FeedBackContent"));
                feedBack.setFeedBackTitle(rs.getString("FeedBackTitle"));
                feedBack.setId(rs.getInt("FeedBackId"));
                Student student = new Student();
                StudentDAO studentDAO = new StudentDAO();
                student.setId(rs.getInt("StudentId"));
                student = studentDAO.getObject(student);
                feedBack.setStudent(student);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return feedBack;
    }

}
