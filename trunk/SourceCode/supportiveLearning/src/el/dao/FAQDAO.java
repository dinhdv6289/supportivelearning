/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.FAQ;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 *
 * @author MrPham
 */
public class FAQDAO extends AbstractDAO<FAQ> {

    @Override
    public int insert(FAQ t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_FAQ (?, ?, ?)}";
        CallableStatement cstmt = null;
        try{
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, t.getQuestion());
            cstmt.setString(2, t.getAnswer());
            cstmt.setDate(3, (Date) t.getDate());

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
    public boolean update(FAQ t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Udp_FAQById  (?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall (sql);
            cstmt.setInt(1, t.getId());
            cstmt.setString(2, t.getQuestion());
            cstmt.setString(3, t.getAnswer());
            cstmt.setDate(4, (Date) t.getDate());

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
    public boolean delete(FAQ t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<FAQ> list() throws Exception {
        Connection conn = null;
        ArrayList<FAQ> faq = new ArrayList<FAQ>();
        String sql = "Sel_AllFAQ";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                faq.add(new FAQ(rs.getInt("FAQId"), rs.getString("Question"), rs.getString("Answer"), rs.getDate("DateCreation")));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return faq;
    }

    public FAQ getFAQById(int FAQId) throws Exception {
        Connection conn = null;
        FAQ faq = null;

        String sql = "{call Sel_FAQById (?)}";
        CallableStatement cstmt = null;

        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, FAQId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                faq = new FAQ(rs.getInt("FAQId"), rs.getString("Question"), rs.getString("Answer"), rs.getDate("DateCreation"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return faq;
    }

    @Override
    public FAQ getObject(FAQ t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
