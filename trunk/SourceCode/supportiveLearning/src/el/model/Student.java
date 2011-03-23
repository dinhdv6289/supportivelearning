/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;

import java.io.Serializable;

/**
 *
 * @author DINHDV
 */
public class Student extends Account implements Serializable {

    private Batch batch;
    private Course course;
    private String rollNumber;

    public Student() {
    }

    public Student(Batch batch, Course course) {
        this.batch = batch;
        this.course = course;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
}
