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
public class StaffBatch {

    private int id;
    private String name;
    private ArrayList<Subject> subjects;
    private ArrayList<Batch> batchs;

    public ArrayList<Batch> getBatchs() {
        return batchs;
    }

    public void setBatchs(ArrayList<Batch> batchs) {
        this.batchs = batchs;
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

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }
}
