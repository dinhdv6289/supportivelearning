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
public class Assignment implements Serializable {

    private int id;
    private Staff staff;
    private Subject subject;
    private Batch batch;
    private String name;
    private String content;
    private String fileUpload;
    private Date startDate;
    private Date endDate;

    public Batch getBatch() {
        return batch;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(String fileUpload) {
        this.fileUpload = fileUpload;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    
}
