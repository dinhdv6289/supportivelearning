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
public class StudentWork {

    private int id;
    private Student student;
    private Assignment assignment;
    private String fileUpload;
    private float mark;
    private Date dateUpload;

    public StudentWork() {
    }

    public StudentWork(int id) {
        this.id = id;
    }

    public StudentWork(Student student, Assignment assignment, String fileUpload, float mark, Date dateUpload) {
        this.student = student;
        this.assignment = assignment;
        this.fileUpload = fileUpload;
        this.mark = mark;
        this.dateUpload = dateUpload;
    }

    public StudentWork(int id, Student student, Assignment assignment, String fileUpload, float mark, Date dateUpload) {
        this.id = id;
        this.student = student;
        this.assignment = assignment;
        this.fileUpload = fileUpload;
        this.mark = mark;
        this.dateUpload = dateUpload;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    
}
