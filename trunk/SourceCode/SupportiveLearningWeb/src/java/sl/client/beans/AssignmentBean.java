/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.AssignmentDAO;
import el.dao.StudentWorkDAO;
import el.model.Assignment;
import el.model.Batch;
import el.model.Student;
import el.model.StudentWork;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.EachSession;
import sl.utils.beans.LoginService;

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
    private StudentWorkDAO studentWorkDAO = new StudentWorkDAO();
    private Batch batchDetails = new Batch();
    private Assignment assignmentDetails = new Assignment();
    private static boolean haveAssignment = false;
    private static boolean notHaveAssignment = false;
    private static final String REDIRECT = "?faces-redirect=true";
    private static boolean dueDate = false;
//    private static boolean latestAssignments = false;
//    private static boolean oldAssignments = false;
    private ArrayList<Assignment> listAssignmentsOfBatchInDueDate;

    /** Creates a new instance of AssignmentBean */
    public AssignmentBean() {
//        latestAssignments = true;
//        oldAssignments = false;
    }

//    public boolean isLatestAssignments() {
//        return latestAssignments;
//    }
//
//    public void setLatestAssignments(boolean latestAssignments) {
//        AssignmentBean.latestAssignments = latestAssignments;
//    }
//
//    public void onRequestActiveTabView() {
//        if (latestAssignments) {
//            latestAssignments = false;
//            oldAssignments = true;
//        } else {
//            latestAssignments = true;
//            oldAssignments = false;
//        }
//    }
//
//    public boolean isOldAssignments() {
//        return oldAssignments;
//    }
//
//    public void setOldAssignments(boolean oldAssignments) {
//        AssignmentBean.oldAssignments = oldAssignments;
//    }

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
        this.checkDueDate(assignment);

        return "assignmentDetails" + REDIRECT;
    }

    public ArrayList<Assignment> getListAssignmentsOfBatchInDueDate() {
        return listAssignmentsOfBatchInDueDate;
    }

    public void setListAssignmentsOfBatchInDueDate(ArrayList<Assignment> listAssignmentsOfBatchInDueDate) {
        this.listAssignmentsOfBatchInDueDate = listAssignmentsOfBatchInDueDate;
    }

    private void loadListAssignmentsOfBatch() {
        try {
            Student student = (Student) EachSession.getObjectFromSession("accountId");
            if (student != null) {
                this.listAssignmentsOfBatch = assignmentDAO.getListAssignmentsByBatchId(student.getBatch().getId());
                this.listAssignmentsOfBatchInDueDate = assignmentDAO.getListAssignmentsByBatchIdDueDate(student.getBatch().getId());
                if (listAssignmentsOfBatch.size() > 0 || listAssignmentsOfBatchInDueDate.size() > 0) {
                    haveAssignment = true;
                    notHaveAssignment = false;
                } else {
                    haveAssignment = false;
                    notHaveAssignment = true;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AssignmentBean.class.getName()).log(Level.SEVERE, null, ex);
            LoginService.loginService("");
        }
    }

    public String details() {
        loadListAssignmentsOfBatch();
        return "batchDetails.jsf" + REDIRECT;

    }

    private void checkDueDate(Assignment assignment) {
        Date date = new Date();
        if (date.before(assignment.getEndDate()) || checkMarkToUpload(assignment)) {
            dueDate = true;
        } else {
            dueDate = false;
        }
    }

    private boolean checkMarkToUpload(Assignment assignment) {
        boolean checkMarkToUpload = false;
        try {
            StudentWork studentWork = new StudentWork();
            studentWork.setAssignment(assignment);
            Student student = (Student) EachSession.getObjectFromSession("accountId");
            if (student != null) {
                studentWork.setStudent(student);
                checkMarkToUpload = studentWorkDAO.checkMarkToUpload(studentWork);
            } else {
                checkMarkToUpload = false;
            }
        } catch (Exception ex) {
            Logger.getLogger(AssignmentBean.class.getName()).log(Level.SEVERE, null, ex);
            checkMarkToUpload = false;
        }
        return checkMarkToUpload;
    }

    public String sendFeedBack() {
        return "feedBack.jsf" + REDIRECT;
    }
}
