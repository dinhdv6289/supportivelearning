/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.AssignmentDAO;
import el.model.Assignment;
import el.model.Batch;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    private ArrayList<Assignment> listAssignmentsOfBatch = new ArrayList<Assignment>();
    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    private Batch batchDetails = new Batch();
    
    private Assignment assignmentDetails = new Assignment();
    private static boolean haveAssignment = false;
    private static boolean notHaveAssignment = false;
    private String redirect = "?faces-redirect=true";

    private static boolean dueDate = false;

    /** Creates a new instance of AssignmentBean */
    public AssignmentBean() {
    }

    public Batch getBatchDetails() {
        return batchDetails;
    }

    public boolean isHaveAssignment() {
        return haveAssignment;
    }

    public void setHaveAssignment(boolean haveAssignment) {
        AssignmentBean.haveAssignment = haveAssignment;
    }

    public boolean isNotHaveAssignment() {
        return notHaveAssignment;
    }

    public void setNotHaveAssignment(boolean notHaveAssignment) {
        AssignmentBean.notHaveAssignment = notHaveAssignment;
    }

    public Assignment getAssignmentDetails() {
        return assignmentDetails;
    }

    public void setAssignmentDetails(Assignment assignmentDetails) {
        this.assignmentDetails = assignmentDetails;
    }

    public boolean isDueDate() {
        return dueDate;
    }

    public void setDueDate(boolean dueDate) {
        AssignmentBean.dueDate = dueDate;
    }

    public void setBatchDetails(Batch batchDetails) {
        this.batchDetails = batchDetails;
    }

    public ArrayList<Assignment> getListAssignmentsOfStaff() {
        return listAssignmentsOfStaff;
    }

    public void setListAssignmentsOfStaff(ArrayList<Assignment> listAssignmentsOfStaff) {
        this.listAssignmentsOfStaff = listAssignmentsOfStaff;
    }

    public ArrayList<Assignment> getListAssignmentsOfBatch() {
        return listAssignmentsOfBatch;
    }

    public void setListAssignmentsOfBatch(ArrayList<Assignment> listAssignmentsOfBatch) {
        this.listAssignmentsOfBatch = listAssignmentsOfBatch;
    }

    public void onRequestBatch(Batch batch) {
        this.batchDetails = batch;
    }

    public String onRequestAssignment(Assignment assignment) {
        this.assignmentDetails = assignment;
        checkDueDate(assignment);
        return "assignmentDetails" + redirect;
    }

    private void loadListAssignmentsOfBatch() {
        try {
            Student student = (Student) EachSession.getObjectFromSession("accountId");
            if (student != null) {
                this.listAssignmentsOfBatch = assignmentDAO.getListAssignmentsByBatchId(student.getBatch().getId());
                if (listAssignmentsOfBatch.size() > 0) {
                    haveAssignment = true;
                    notHaveAssignment = false;
                } else {
                    haveAssignment = false;
                    notHaveAssignment = true;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AssignmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String details() {
        loadListAssignmentsOfBatch();
        return "batchDetails.jsf" + redirect;

    }


    private void checkDueDate(Assignment assignment) {
        long endDate = assignment.getEndDate().getTime();
        long date = new Date().getTime();
        long day = endDate - date;
        if (day > 0 || day <= 5) {
            dueDate = true;
        } else {
            dueDate = false;
        }
    }
}
