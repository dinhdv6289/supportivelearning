/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.staff.beans;

import el.dao.StaffDAO;
import el.dao.StudentWorkDAO;
import el.model.Assignment;
import el.model.Staff;
import el.model.Student;
import el.model.StudentWork;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sl.utils.beans.LoginService;
import sl.utils.beans.MessagesService;
import sl.utils.beans.SessionManager;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentWorkManagerBean extends UtilCheckLoginBean  implements Serializable {

    private StudentWork studentWork = new StudentWork();
    private StudentWork selectedStudentWork;
    private static Assignment assignment;
    private Staff staff;
    private Student studentSearch;
    private ArrayList<StudentWork> listStudentWorks = new ArrayList<StudentWork>();
    private static ArrayList<StudentWork> listAllStudentWorks = new ArrayList<StudentWork>();
    private StudentWorkDAO studentWorkDAO = new StudentWorkDAO();
    private StaffDAO staffDAO = new StaffDAO();
    private static boolean haveStudentWork = false;
    private static boolean notHaveStudentWork = false;
    private static boolean panelHaveStudentWork;
    private static boolean panelEditMark;
    private static boolean panelAssignmentDetails = true;
    private static final String REDIRECT = "?faces-redirect=true";
    private static final String THISPAGE = "assignmentDetails.jsf";

    /** Creates a new instance of StudentWorkManagerBean */
    public StudentWorkManagerBean() {
//        super();
        panelHaveStudentWork = true;
        panelEditMark = false;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        if (SessionManager.getSession("accountId") == null) {
            // LoginService.loginService(request.getRequestURI());
        } else {
            try {
                int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
                staff = staffDAO.getStaffByAccountId(accountId);
                if (staff.getId() > 0) {
                    loadAllStudentWorks(staff);
                } else {
                    response.sendRedirect("ui.client/login.jsf");
                }
            } catch (Exception ex) {
                Logger.getLogger(StudentWorkManagerBean.class.getName()).log(Level.SEVERE, null, ex);
                //LoginService.loginService("");
            }
        }
    }

    @PostConstruct
    public void init() {
    }

    private void loadAllStudentWorks(Staff staff) {
        try {
            ArrayList<StudentWork> listStudentsWorkByStaffId = studentWorkDAO.listStudentsWorkByStaffId(staff.getId());
            if (!listStudentsWorkByStaffId.isEmpty()) {
                listAllStudentWorks = listStudentsWorkByStaffId;
            }
        } catch (Exception ex) {
            Logger.getLogger(StudentWorkManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Student getStudentSearch() {
        return studentSearch;
    }

    public void setStudentSearch(Student studentSearch) {
        this.studentSearch = studentSearch;
    }

    public ArrayList<StudentWork> getListAllStudentWorks() {
        return listAllStudentWorks;
    }

    public void setListAllStudentWorks(ArrayList<StudentWork> listAllStudentWorks) {
        this.listAllStudentWorks = listAllStudentWorks;
    }

    public boolean isPanelAssignmentDetails() {
        return panelAssignmentDetails;
    }

    public void setPanelAssignmentDetails(boolean panelAssignmentDetails) {
        StudentWorkManagerBean.panelAssignmentDetails = panelAssignmentDetails;
    }

    public boolean isPanelEditMark() {
        return panelEditMark;
    }

    public void setPanelEditMark(boolean panelEditMark) {
        StudentWorkManagerBean.panelEditMark = panelEditMark;
    }

    public boolean isPanelHaveStudentWork() {
        return panelHaveStudentWork;
    }

    public void setPanelHaveStudentWork(boolean panelHaveStudentWork) {
        StudentWorkManagerBean.panelHaveStudentWork = panelHaveStudentWork;
    }

    public boolean isHaveStudentWork() {
        return haveStudentWork;
    }

    public void setHaveStudentWork(boolean haveStudentWork) {
        StudentWorkManagerBean.haveStudentWork = haveStudentWork;
    }

    public boolean isNotHaveStudentWork() {
        return notHaveStudentWork;
    }

    public void setNotHaveStudentWork(boolean notHaveStudentWork) {
        StudentWorkManagerBean.notHaveStudentWork = notHaveStudentWork;
    }

    public ArrayList<StudentWork> getListStudentWorks() {
        try {
            this.listStudentWorks = studentWorkDAO.getAllStudentByAssignmentId(assignment.getId());
        } catch (Exception ex) {
            Logger.getLogger(StudentWorkManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return this.listStudentWorks;
    }

    public void setListStudentWorks(ArrayList<StudentWork> listStudentWorks) {
        this.listStudentWorks = listStudentWorks;
    }

    public StudentWork getStudentWork() {
        return studentWork;
    }

    public void setStudentWork(StudentWork studentWork) {
        this.studentWork = studentWork;
    }

    public StudentWork getSelectedStudentWork() {
        return selectedStudentWork;
    }

    public void setSelectedStudentWork(StudentWork selectedStudentWork) {
        this.selectedStudentWork = selectedStudentWork;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        StudentWorkManagerBean.assignment = assignment;
    }

    public String onRequestUpdateMark(boolean flag) {
        this.setPanelHaveStudentWork(false);
        this.setPanelAssignmentDetails(false);
        this.setPanelEditMark(flag);
        return THISPAGE + REDIRECT;
    }

    public String updateMark() {
        try {
            if (studentWorkDAO.updateMark(selectedStudentWork)) {
                // String subject = "Mark of Assignment: " + selectedStudentWork.getAssignment().getName();
                // String content = "Your mark: " + selectedStudentWork.getMark() + "<br/>";
                // content += "For details your mark, you can click here :<br/> http://localhost:8080/SupportiveLearningWeb/ui.client/markView.jsf";
                //String from = "sleaning99@gmail.com";
                // SendMailService.postMail(new String[]{selectedStudentWork.getStudent().getEmail()}, subject, content, from);
                this.setPanelEditMark(false);
                this.setPanelHaveStudentWork(true);
                this.setPanelAssignmentDetails(true);
                MessagesService.showMessage("Update mark success!");
            } else {
                MessagesService.showMessage("Update mark failure!");
            }
        } catch (Exception ex) {
            Logger.getLogger(StudentWorkManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
