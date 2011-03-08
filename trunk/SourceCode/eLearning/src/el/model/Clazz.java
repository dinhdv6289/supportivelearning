/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;

import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class Clazz {

    private int id;
    private String name;
    private ArrayList<Student> students;

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

    public Clazz() {
    }

    public Clazz(String name, ArrayList<Student> students) {
        this.name = name;
        this.students = students;
    }

    public Clazz(int id, String name, ArrayList<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }
    
    
}
