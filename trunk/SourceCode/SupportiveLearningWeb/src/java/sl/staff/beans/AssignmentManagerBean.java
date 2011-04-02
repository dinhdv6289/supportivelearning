/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.staff.beans;

import el.dao.AssignmentDAO;
import el.dao.StaffDAO;
import el.model.Assignment;
import el.model.Batch;
import el.model.Staff;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sl.utils.beans.EachSession;
import sl.utils.beans.SessionManager;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class AssignmentManagerBean implements Serializable {

    private Assignment assignment = new Assignment();
    private ArrayList<Assignment> listAssignmentsUploadByStaff = new ArrayList<Assignment>();
    private ArrayList<Assignment> listAssignmentsStaff = new ArrayList<Assignment>();
    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    private StaffDAO staffDAO = new StaffDAO();
    private Batch batchDetailsToStaff = new Batch();
    private static final String REDIRECT = "?faces-redirect=true";
    private static boolean haveAssignment = false;
    private static boolean notHaveAssignment = false;
    private static Assignment assignmentDetails = new Assignment();
    private Staff staff;

    /** Creates a new instance of AssignmentManagerBean */
    public AssignmentManagerBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (SessionManager.getSession("accountId") == null) {
            // LoginService.loginService(request.getRequestURI());
        } else {
            try {
                int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
                staff = staffDAO.getStaffByAccountId(accountId);
            } catch (Exception ex) {
                Logger.getLogger(StaffBatchManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Assignment getAssignmentDetails() {
        return assignmentDetails;
    }

    public void setAssignmentDetails(Assignment assignmentDetails) {
        AssignmentManagerBean.assignmentDetails = assignmentDetails;
    }

    public boolean isHaveAssignment() {
        return haveAssignment;
    }

    public void setHaveAssignment(boolean haveAssignment) {
        AssignmentManagerBean.haveAssignment = haveAssignment;
    }

    public boolean isNotHaveAssignment() {
        return notHaveAssignment;
    }

    public void setNotHaveAssignment(boolean notHaveAssignment) {
        AssignmentManagerBean.notHaveAssignment = notHaveAssignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Batch getBatchDetailsToStaff() {
        return batchDetailsToStaff;
    }

    public void setBatchDetailsToStaff(Batch baatchDetailsToStaff) {
        this.batchDetailsToStaff = baatchDetailsToStaff;
    }

    public ArrayList<Assignment> getListAssignmentsUploadByStaff() {
        return listAssignmentsUploadByStaff;
    }

    public void setListAssignmentsUploadByStaff(ArrayList<Assignment> listAssignmentsUploadByStaff) {
        this.listAssignmentsUploadByStaff = listAssignmentsUploadByStaff;
    }

    public String onRequestAssignment(Assignment assignment) {
        AssignmentManagerBean.assignmentDetails = assignment;
        return "assignmentDetails.jsf" + REDIRECT;
    }

    public ArrayList<Assignment> getListAssignmentsStaff() {
        try {
            listAssignmentsStaff = assignmentDAO.getListAssignmentsByStaff(staff);
            return listAssignmentsStaff;
        } catch (Exception ex) {
            Logger.getLogger(AssignmentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setListAssignmentsStaff(ArrayList<Assignment> listAssignmentsStaff) {
        this.listAssignmentsStaff = listAssignmentsStaff;
    }

    public String onRequestBatchToStaff(Batch batch) {
        try {
            listAssignmentsUploadByStaff = assignmentDAO.getListAssignmentsByBatchId(batch.getId());
            if (listAssignmentsUploadByStaff.size() > 0) {
                haveAssignment = true;
                notHaveAssignment = false;
            } else {
                haveAssignment = false;
                notHaveAssignment = true;
            }
            return "batchDetails.jsf" + REDIRECT;
        } catch (Exception ex) {
            Logger.getLogger(AssignmentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
