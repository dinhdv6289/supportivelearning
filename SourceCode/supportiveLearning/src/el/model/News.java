/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.model;

import java.util.Date;

/**
 *
 * @author DINHDV
 */
public class News {

    private int id;
    private String title;
    private String picture;
    private String subContent;
    private String newsContent;
    private Date dateCreate;
    public News() {
    }

    public News(int id) {
        this.id = id;
    }

    public News(int id, String title) {
        this(id);
        this.title = title;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
