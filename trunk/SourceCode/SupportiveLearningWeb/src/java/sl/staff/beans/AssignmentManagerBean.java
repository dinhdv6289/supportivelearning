/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.staff.beans;

import el.dao.AssignmentDAO;
import el.dao.StaffDAO;
import el.model.Account;
import el.model.Assignment;
import el.model.Role;
import el.model.Staff;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.SessionManager;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class AssignmentManagerBean implements Serializable {

    private Assignment assignment;
    private ArrayList<Assignment> listAssignmentsUploadByStaff;
    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    private StaffDAO staffDAO = new StaffDAO();

    /** Creates a new instance of AssignmentManagerBean */
    public AssignmentManagerBean() {
        assignment = new Assignment();
        loadListAssignmentsUpLoadByStaff();

    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public ArrayList<Assignment> getListAssignmentsUploadByStaff() {
        return listAssignmentsUploadByStaff;
    }

    public void setListAssignmentsUploadByStaff(ArrayList<Assignment> listAssignmentsUploadByStaff) {
        this.listAssignmentsUploadByStaff = listAssignmentsUploadByStaff;
    }

    private void loadListAssignmentsUpLoadByStaff() {
        try {
            if (SessionManager.getSession("accountLogon") != null) {
                Account account = (Account) SessionManager.getSession("accountLogon");
                Role role = account.getRole();
                if (role.getName().equals("Staff")) {
                    Staff staff = new Staff();
                    staff = staffDAO.getStaffByAccount(account);
                    this.listAssignmentsUploadByStaff = assignmentDAO.getListAssignmentsByStaff(staff);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AssignmentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
