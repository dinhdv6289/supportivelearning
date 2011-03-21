/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.News;
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
public class NewsDAO extends AbstractDAO<News> {

    @Override
    public int insert(News t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_News (?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, t.getTitle());
            cstmt.setString(2, t.getPicture());
            cstmt.setString(3, t.getSubContent());
            cstmt.setString(4, t.getNewsContent());
            cstmt.setDate(5, (Date) t.getDateCreate());

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
    public boolean update(News t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Upd_News (?, ?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, t.getTitle());
            cstmt.setString(2, t.getPicture());
            cstmt.setString(3, t.getSubContent());
            cstmt.setString(4, t.getNewsContent());
            cstmt.setDate(5, (Date) t.getDateCreate());
            cstmt.setInt(6, t.getId());

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
    public boolean delete(News t) throws Exception {
       Connection conn = null;
        int a = 0;
        String sql = "{call Del_News (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());

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
    public ArrayList<News> list() throws Exception {
        ArrayList<News> newses = new ArrayList<News>();
        Connection conn = null;
        String sql = "Sel_AllNews";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                News news = new News();
                news.setId(rs.getInt("NewsId"));
                news.setTitle(rs.getString("Tittle"));
                news.setPicture(rs.getString("Picture"));
                news.setSubContent(rs.getString("SubContent"));
                news.setNewsContent(rs.getString("NewsContent"));
                news.setDateCreate(rs.getDate("DateCreation"));

                newses.add(news);

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return newses;
    }

    @Override
    public News getObject(News t) throws Exception {
        News news = new News();
        Connection conn = null;
        String sql = "{call Sel_NewsById (?)}";
       CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {               
                news.setId(rs.getInt("NewsId"));
                news.setTitle(rs.getString("Tittle"));
                news.setPicture(rs.getString("Picture"));
                news.setSubContent(rs.getString("SubContent"));
                news.setNewsContent(rs.getString("NewsContent"));
                news.setDateCreate(rs.getDate("DateCreation"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return news;
    }

    public ArrayList<News> listTop3New() throws Exception {
         ArrayList<News> newses = new ArrayList<News>();
        Connection conn = null;
        String sql = "Sel_TopNews";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                News news = new News();
                news.setId(rs.getInt("NewsId"));
                news.setTitle(rs.getString("Tittle"));
                news.setPicture(rs.getString("Picture"));
                news.setSubContent(rs.getString("SubContent"));
                news.setNewsContent(rs.getString("NewsContent"));
                news.setDateCreate(rs.getDate("DateCreation"));

                newses.add(news);

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return newses;
    }
}
