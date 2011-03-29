/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;

import java.util.Date;

/**
 *
 * @author DINH
 */
public class ChangeLearning {

    private int id;
    private Student student;
    private Batch batch;
    private String reason;
    private Date dateChangeLearning;

    public ChangeLearning() {
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Date getDateChangeLearning() {
        return dateChangeLearning;
    }

    public void setDateChangeLearning(Date dateChangeLearning) {
        this.dateChangeLearning = dateChangeLearning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
