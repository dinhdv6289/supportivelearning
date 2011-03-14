/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DINHDV
 */
public class Batch {

    private int id;
    private String name;
    private Course course;
    private Semester semester;
    private Date startDate;
    private ArrayList<Student> students;

    public Batch(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Batch(int id, String name, Course course, Semester semester, Date startDate, ArrayList<Student> students) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.semester = semester;
        this.startDate = startDate;
        this.students = students;
    }

    public Batch(int id, String name, Course course, Semester semester, Date startDate ) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.semester = semester;
        this.startDate = startDate;
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

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public Batch() {
    }

    public Batch(String name, ArrayList<Student> students) {
        this.name = name;
        this.students = students;
    }

    public Batch(int id, String name, ArrayList<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }
    
    
}
