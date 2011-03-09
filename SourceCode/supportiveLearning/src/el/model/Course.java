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
public class Course {

    private int id;
    private String name;
    private ArrayList<Clazz> clazzs;

    
    public ArrayList<Clazz> getClazzs() {
        return clazzs;
    }

    public void setClazzs(ArrayList<Clazz> clazzs) {
        this.clazzs = clazzs;
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

    public Course() {
    }

    public Course(String name, ArrayList<Clazz> clazzs) {
        this.name = name;
        this.clazzs = clazzs;
    }

    public Course(int id, String name, ArrayList<Clazz> clazzs) {
        this.id = id;
        this.name = name;
        this.clazzs = clazzs;
    }

    
    
}
