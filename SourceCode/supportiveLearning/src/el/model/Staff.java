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
public class Staff extends Account {

    private ArrayList<Assignment> assignments;

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }
}
