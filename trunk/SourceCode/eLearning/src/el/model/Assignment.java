/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;

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

    public Assignment() {
    }

    public Assignment(Staff staff, Student student, Float mark, Subject subject) {
        this.staff = staff;
        this.student = student;
        this.mark = mark;
        this.subject = subject;
    }

    public Assignment(int id, Staff staff, Student student, Float mark, Subject subject) {
        this.id = id;
        this.staff = staff;
        this.student = student;
        this.mark = mark;
        this.subject = subject;
    }
}
