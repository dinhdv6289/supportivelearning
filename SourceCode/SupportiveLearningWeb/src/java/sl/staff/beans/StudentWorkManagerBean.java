/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.staff.beans;

import el.dao.StudentWorkDAO;
import el.model.Assignment;
import el.model.StudentWork;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.MessagesService;
import sl.utils.beans.SendMailService;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentWorkManagerBean implements Serializable {

    private StudentWork studentWork = new StudentWork();
    private StudentWork selectedStudentWork;
    private ArrayList<StudentWork> listStudentWorks = new ArrayList<StudentWork>();
    private StudentWorkDAO studentWorkDAO = new StudentWorkDAO();
    private static Assignment assignment;
    private static boolean haveStudentWork = false;
    private static boolean notHaveStudentWork = false;
    private static boolean panelHaveStudentWork;
    private static boolean panelEditMark;
    private static final String REDIRECT = "?faces-redirect=true";
    private static final String THISPAGE = "assignmentDetails.jsf";

    /** Creates a new instance of StudentWorkManagerBean */
    public StudentWorkManagerBean() {
        panelHaveStudentWork = true;
        panelEditMark = false;
        //loadListStudentWorks();
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
                MessagesService.showMessage("Update mark success!");
            }
            else{
                MessagesService.showMessage("Update mark failure!");
            }
        } catch (Exception ex) {
            Logger.getLogger(StudentWorkManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
