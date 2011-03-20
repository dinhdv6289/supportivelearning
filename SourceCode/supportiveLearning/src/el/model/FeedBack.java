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
public class FeedBack {

    private int id;
    private Student student;
    private String feedBackTitle;
    private String feedBackContent;
    private Date dateCreation;

    public FeedBack() {
    }

    public FeedBack(int id, Student student, String feedBackTitle, String feedBackContent, Date dateCreation) {
        this.id = id;
        this.student = student;
        this.feedBackTitle = feedBackTitle;
        this.feedBackContent = feedBackContent;
        this.dateCreation = dateCreation;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getFeedBackContent() {
        return feedBackContent;
    }

    public void setFeedBackContent(String feedBackContent) {
        this.feedBackContent = feedBackContent;
    }

    public String getFeedBackTitle() {
        return feedBackTitle;
    }

    public void setFeedBackTitle(String feedBackTitle) {
        this.feedBackTitle = feedBackTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
