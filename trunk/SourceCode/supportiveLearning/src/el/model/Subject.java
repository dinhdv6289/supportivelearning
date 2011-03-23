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
public class Subject implements Serializable  {

    private int id;
    private String name;

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


    
}
