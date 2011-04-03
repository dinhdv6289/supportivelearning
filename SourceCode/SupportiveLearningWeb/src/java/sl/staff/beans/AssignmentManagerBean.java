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
import el.model.Subject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import sl.utils.beans.MessagesService;
import sl.utils.beans.SessionManager;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class AssignmentManagerBean implements Serializable {

    private Assignment assignment = new Assignment();
    private static Assignment selectedAssignment;
    private Staff staff;
    private Batch batch;
    private Subject subject;
    private Batch batchDetailsToStaff;
    private static Assignment assignmentDetails;
    private ArrayList<Assignment> listAssignmentsUploadByStaff = new ArrayList<Assignment>();
    private ArrayList<Assignment> listAssignmentsStaff = new ArrayList<Assignment>();
    private ArrayList<Batch> listMyBatchs;
    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    private StaffDAO staffDAO = new StaffDAO();
    private static final String REDIRECT = "?faces-redirect=true";
    private static boolean haveAssignment = false;
    private static boolean notHaveAssignment = false;
    private static final String THISPAGE = "managerAssignment.jsf";
    private static final int BUFFER_SIZE = 1024;
    private static String fileUpload;
    private static Staff thisStaff;

    /** Creates a new instance of AssignmentManagerBean */
    public AssignmentManagerBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (SessionManager.getSession("accountId") == null) {
            // LoginService.loginService(request.getRequestURI());
        } else {
            try {
                int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
                thisStaff = staff = staffDAO.getStaffByAccountId(accountId);
            } catch (Exception ex) {
                Logger.getLogger(StaffBatchManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Staff getThisStaff() {
        return thisStaff;
    }

    public void setThisStaff(Staff thisStaff) {
        AssignmentManagerBean.thisStaff = thisStaff;
    }

    public ArrayList<Batch> getListMyBatchs() {
        try {
            listMyBatchs = staffDAO.getListBatchsByStaffId(thisStaff.getId());
        } catch (Exception ex) {
            Logger.getLogger(AssignmentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listMyBatchs;
    }

    public void setListMyBatchs(ArrayList<Batch> listMyBatchs) {
        this.listMyBatchs = listMyBatchs;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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

    public Assignment getSelectedAssignment() {
        return selectedAssignment;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(String fileUpload) {
        AssignmentManagerBean.fileUpload = fileUpload;
    }

    public void setSelectedAssignment(Assignment selectedAssignment) {
        this.selectedAssignment = selectedAssignment;
    }

    public String onRequestAssignment(Assignment assignment) {
        AssignmentManagerBean.assignmentDetails = assignment;
        return "assignmentDetails.jsf" + REDIRECT;
    }

    public String onRequestAssignmentToEdit(Assignment assignment) {
        this.selectedAssignment = assignment;
        return "editAssignment.jsf" + REDIRECT;
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

    public ArrayList<Assignment> listAssignmentsOfBatch(Batch batch) {
        try {
            return assignmentDAO.getListAssignmentsByBatchId(batch.getId());
        } catch (Exception ex) {
            Logger.getLogger(AssignmentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /** Creates a new instance of FileUploadController */
    public void handleFileUpload(FileUploadEvent event) {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        File result = new File(extContext.getRealPath("/documents/assignmentFiles") + "//" + event.getFile().getFileName());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(result);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bulk;
            InputStream inputStream = event.getFile().getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
            this.setFileUpload("documents/assignmentFiles/" + event.getFile().getFileName());

        } catch (IOException e) {
            MessagesService.showMessage("The files were not uploaded!");
            e.printStackTrace();

        }
    }

    public String insertAssignment() {
        int result = 0;
        try {
            if (SessionManager.getSession("accountId") != null) {
                int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
                Staff staffSearch = staffDAO.getStaffByAccountId(accountId);
                if (staffSearch != null) {
                    assignment.setFileUpload(this.getFileUpload());
                    assignment.setSubject(subject);
                    assignment.setBatch(batch);
                    assignment.setStaff(staffSearch);
                    result = assignmentDAO.insert(assignment);
                    if (result > 0) {
                        MessagesService.showMessage("Succesful " + this.getFileUpload() + "is uploaded.");
                        return "managerAssignment.jsf" + REDIRECT;
                    } else {
                        MessagesService.showMessage("Upload " + this.getFileUpload() + "is failure.");
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(AssignmentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public String updateAssignment() {
        boolean result = false;
        try {
            if (SessionManager.getSession("accountId") != null) {
                int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
                Staff staffSearch = staffDAO.getStaffByAccountId(accountId);
                if (staffSearch != null) {
                    selectedAssignment.setFileUpload(this.getFileUpload());
                    selectedAssignment.setSubject(subject);
                    selectedAssignment.setBatch(batch);
                    selectedAssignment.setStaff(staffSearch);
                    result = assignmentDAO.update(selectedAssignment);
                    if (result) {
                        MessagesService.showMessage("Succesful " + this.getFileUpload() + "is uploaded.");
                    } else {
                        MessagesService.showMessage("Upload " + this.getFileUpload() + "is failure.");
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AssignmentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "managerAssignment.jsf" + REDIRECT;
    }

    public String deleteAssignment(Assignment assignment) {
        try {
            if(assignmentDAO.delete(assignment)){
                MessagesService.showMessage("Ok.");
            }else{
                MessagesService.showMessage("Ok.");
            }
        } catch (Exception ex) {
            Logger.getLogger(AssignmentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
