/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MrPham
 */
public class FAQ implements Serializable {
    private int id;
    private String question;
    private String answer;
    private Date date;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public FAQ() {
    }

    public FAQ(int id, String question, String answer, Date date) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.date = date;
    }

}
