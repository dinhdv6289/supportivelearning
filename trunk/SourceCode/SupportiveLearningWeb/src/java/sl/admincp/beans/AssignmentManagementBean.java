/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.AssignmentDAO;
import el.model.Assignment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINH
 */
@ManagedBean
@SessionScoped
public class AssignmentManagementBean implements Serializable {

    private Assignment assignment;
    private ArrayList<Assignment> listAssignments;
    private AssignmentDAO assignmentDAO = new AssignmentDAO();

    /** Creates a new instance of AssignmentManagementBean */
    public AssignmentManagementBean() {
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public ArrayList<Assignment> getListAssignments() {
        try {
            listAssignments = assignmentDAO.list();
        } catch (Exception ex) {
            Logger.getLogger(AssignmentManagementBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAssignments;
    }

    public void setListAssignments(ArrayList<Assignment> listAssignments) {
        this.listAssignments = listAssignments;
    }
}
