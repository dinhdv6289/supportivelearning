/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.AssignmentDAO;
import el.model.Assignment;
import el.model.Batch;
import el.model.Staff;
import el.model.Student;
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
    private ArrayList<Assignment> listAssignmentsOfBatch = new ArrayList<Assignment>();
    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    private Batch batchDetails = new Batch();

    /** Creates a new instance of AssignmentBean */
    public AssignmentBean() {
        //loadListAssignmentsOfBatch();
    }

    public Batch getBatchDetails() {
        return batchDetails;
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

//    private void loadListAssignmentsOfBatch(int batchId) {
//        try {
//            this.listAssignmentsOfBatch = assignmentDAO.getListAssignmentsByBatchId(batchDetails.getId());
//        } catch (Exception ex) {
//            Logger.getLogger(AssignmentBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void onRequestBatch(Batch batch) {
        this.batchDetails = batch;
    }

    private void loadListAssignmentsOfBatch() {
        try {
            Student student = (Student) EachSession.getObjectFromSession("accountId");
            if (student != null) {
                this.listAssignmentsOfBatch = assignmentDAO.getListAssignmentsByBatchId(student.getBatch().getId());
            } else {
                this.listAssignmentsOfBatch = null;
            }
        } catch (Exception ex) {
            Logger.getLogger(AssignmentBean.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public String details() {
        loadListAssignmentsOfBatch();
        return "batchDetails.jsf?faces-redirect=true";
    }
}
