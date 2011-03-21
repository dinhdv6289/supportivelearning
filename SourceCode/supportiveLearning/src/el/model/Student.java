/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;

/**
 *
 * @author DINHDV
 */
public class Student extends Account {

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

    public Student setBatch(Batch batch) {
        this.batch = batch;
        return this;
    }

    public Course getCourse() {
        return course;
    }

    public Student setCourse(Course course) {
        this.course = course;
        return this;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public Student setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
        return this;
    }
}
