/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;


import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DINHDV
 */
public class Course implements Serializable {

    private int id;
    private String name;
    private Date dateStart;
    private Date dateEnd;


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
}
