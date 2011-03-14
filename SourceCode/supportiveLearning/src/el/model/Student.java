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
}
