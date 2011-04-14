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
import sl.utils.beans.LoginService;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINH
 */
@ManagedBean
@SessionScoped
public class AssignmentManagementBean extends UtilCheckLoginBean implements Serializable {

    private Assignment assignment;
    private Assignment selectedAssignment;
    private ArrayList<Assignment> listAssignments;
    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    private static boolean panelGroupListAssignment;

    /** Creates a new instance of AssignmentManagementBean */
    public AssignmentManagementBean() {
        super();
        panelGroupListAssignment = true;
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
            LoginService.loginService("");

        }
        return listAssignments;
    }

    public void setListAssignments(ArrayList<Assignment> listAssignments) {
        this.listAssignments = listAssignments;
    }

    public Assignment getSelectedAssignment() {
        return selectedAssignment;
    }

    public void setSelectedAssignment(Assignment selectedAssignment) {
        this.selectedAssignment = selectedAssignment;
    }

    public boolean isPanelGroupListAssignment() {
        return panelGroupListAssignment;
    }

    public void setPanelGroupListAssignment(boolean panelGroupListAssignment) {
        AssignmentManagementBean.panelGroupListAssignment = panelGroupListAssignment;
    }

}
