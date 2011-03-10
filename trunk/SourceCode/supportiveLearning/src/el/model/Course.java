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
public class Course {

    private int id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private String batch;

    public Course(int id, String name, Date dateStart, Date dateEnd, String batch) {
        this.id = id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.batch = batch;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    
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
