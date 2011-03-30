/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.staff.beans;

import el.dao.AssignmentDAO;
import el.dao.StaffDAO;
import el.model.Assignment;
import el.model.Batch;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class AssignmentManagerBean implements Serializable {

    private Assignment assignment = new Assignment();
    private ArrayList<Assignment> listAssignmentsUploadByStaff = new ArrayList<Assignment>();
    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    private StaffDAO staffDAO = new StaffDAO();
    private Batch batchDetailsToStaff = new Batch();
    private String redirect = "?faces-redirect=true";
    private static boolean haveAssignment = false;
    private static boolean notHaveAssignment = false;
    private static Assignment assignmentDetails = new Assignment();

    /** Creates a new instance of AssignmentManagerBean */
    public AssignmentManagerBean() {
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
        return "assignmentDetails" + redirect;
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
            return "batchDetails.jsf" + redirect;
        } catch (Exception ex) {
            Logger.getLogger(AssignmentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
