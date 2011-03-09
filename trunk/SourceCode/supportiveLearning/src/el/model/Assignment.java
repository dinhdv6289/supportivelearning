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
public class Assignment {

    private int id;
    private Staff staff;
    private Student student;
    private Float mark;
    private Subject subject;
    private String fileUpload;
    private Date dateUpload;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getMark() {
        return mark;
    }

    public void setMark(Float mark) {
        this.mark = mark;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(String fileUpload) {
        this.fileUpload = fileUpload;
    }

    public Assignment() {
    }

    public Assignment(Staff staff, Student student, Float mark, Subject subject, String fileUpload, Date dateUpload) {
        this.staff = staff;
        this.student = student;
        this.mark = mark;
        this.subject = subject;
        this.fileUpload = fileUpload;
        this.dateUpload = dateUpload;
    }

    public Assignment(int id, Staff staff, Student student, Float mark, Subject subject, String fileUpload, Date dateUpload) {
        this.id = id;
        this.staff = staff;
        this.student = student;
        this.mark = mark;
        this.subject = subject;
        this.fileUpload = fileUpload;
        this.dateUpload = dateUpload;
    }

    

}
