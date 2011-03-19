/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.NewsDAO;
import el.model.News;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class NewsBean implements Serializable {

    private News news = new News();
    private ArrayList<News> listNews = new ArrayList<News>();
    private NewsDAO newsDAO = new NewsDAO();

    /** Creates a new instance of NewsBean */
    public NewsBean() {
        loadListNews();
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public ArrayList<News> getListNews() {
        return listNews;
    }

    public void setListNews(ArrayList<News> listNews) {
        this.listNews = listNews;
    }

    private void loadListNews() {
        try {
            this.listNews = newsDAO.list();
        } catch (Exception ex) {
            Logger.getLogger(NewsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
