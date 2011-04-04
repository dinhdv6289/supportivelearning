/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.FeedBack;
import el.model.FeedBackAnswer;
import el.model.Staff;
import el.utility.Utility;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class FeedBackAnswerDAO extends AbstractDAO<FeedBackAnswer> {

    @Override
    public int insert(FeedBackAnswer t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_FeedBackAnswer (?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try{
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getFeedBack().getId());
            cstmt.setInt(2, t.getStaff().getId());
            cstmt.setString(3, t.getFeedBackAnswer());
            cstmt.setDate(4, Utility.date2sql(t.getDateCreate()));
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
    public boolean update(FeedBackAnswer t) throws Exception {
        Connection conn = null;
        int a = 0;
        //tham so thu 5 la feedbackanswerid
        String sql = "{call Upd_FeedBackAnswer (?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try{
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getFeedBack().getId());
            cstmt.setInt(2, t.getStaff().getId());
            cstmt.setString(3, t.getFeedBackAnswer());
            cstmt.setDate(4, Utility.date2sql( t.getDateCreate()));
            cstmt.setInt(5, t.getId());

            a = cstmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(conn!=null){
                conn.close();
            }
        }
        return a == 1? true: false;
    }

    @Override
    public boolean delete(FeedBackAnswer t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Del_FeedBackAnswer (?)}";
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
        return a == 1? true: false;
    }

    @Override
    public ArrayList<FeedBackAnswer> list() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FeedBackAnswer getObject(FeedBackAnswer t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<FeedBackAnswer> getFeedbackAnswerByFeedbackId(int feedbackId)  throws Exception {
        ArrayList<FeedBackAnswer> feedBackAnswers = new ArrayList<FeedBackAnswer>();
        Connection conn = null;
        String sql = "{call Sel_FeedbackAnswerByFeedbackId (?)}";
        CallableStatement cstmt = null;
        try{
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, feedbackId);
            ResultSet rs = cstmt.executeQuery();
            while(rs.next()){
                FeedBackAnswer feedBackAnswer = new FeedBackAnswer();
                feedBackAnswer.setDateCreate(Utility.sql2date(rs.getDate("DateCreation")));
                FeedBack feedBack = new FeedBack();
                feedBack.setId(rs.getInt("FeedBackId"));
                FeedBackDAO feedBackDAO = new FeedBackDAO();
                feedBack = feedBackDAO.getObject(feedBack);
                feedBackAnswer.setFeedBack(feedBack);
                feedBackAnswer.setFeedBackAnswer(rs.getString("FeedBackAnswer"));
                feedBackAnswer.setId(rs.getInt("FeedBackAnswerId"));
                feedBackAnswers.add(feedBackAnswer);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(conn!=null){
                conn.close();
            }
        }
        return feedBackAnswers;
    }
}
