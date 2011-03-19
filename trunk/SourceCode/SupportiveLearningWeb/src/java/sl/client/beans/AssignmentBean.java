/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.AssignmentDAO;
import el.model.Assignment;
import el.model.Staff;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.EachSession;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class AssignmentBean implements Serializable {

    private ArrayList<Assignment> listAssignmentsOfStaff = new ArrayList<Assignment>();
    private AssignmentDAO assignmentDAO = new AssignmentDAO();

    /** Creates a new instance of AssignmentBean */
    public AssignmentBean() {
        loadAssignmentsOfStaff();
    }

    public ArrayList<Assignment> getListAssignmentsOfStaff() {
        return listAssignmentsOfStaff;
    }

    public void setListAssignmentsOfStaff(ArrayList<Assignment> listAssignmentsOfStaff) {
        this.listAssignmentsOfStaff = listAssignmentsOfStaff;
    }

    private void loadAssignmentsOfStaff() {
        try {
            Staff staff = (Staff) EachSession.getObjectFromSession("accountId");
            if (staff != null) {
                listAssignmentsOfStaff = this.loadListAssignmentsByStaff(staff);
            }
        } catch (Exception ex) {
            Logger.getLogger(AssignmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Assignment> loadListAssignmentsByStaff(Staff staff) {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        try {
            if (staff != null) {
                //assignments = assignmentDAO.getListAssignmentsByStaff(staff);
            }

        } catch (Exception ex) {
            Logger.getLogger(AssignmentBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return assignments;
    }
}
