/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DINHDV
 */
public class FeedBackAnswer implements Serializable {

    private int id;
    private FeedBack feedBack;
    private Staff staff;
    private String feedBackAnswer;
    private Date dateCreate;

    public FeedBackAnswer() {
    }

    public FeedBackAnswer(int id, FeedBack feedBack, Staff staff, String feedBackAnswer, Date dateCreate) {
        this.id = id;
        this.feedBack = feedBack;
        this.staff = staff;
        this.feedBackAnswer = feedBackAnswer;
        this.dateCreate = dateCreate;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public FeedBack getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
    }

    public String getFeedBackAnswer() {
        return feedBackAnswer;
    }

    public void setFeedBackAnswer(String feedBackAnswer) {
        this.feedBackAnswer = feedBackAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
