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
import sl.utils.beans.SendMailService;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentWorkManagerBean implements Serializable {

    private StudentWork studentWork = new StudentWork();
    private StudentWork selectedStudentWork = new StudentWork();
    private ArrayList<StudentWork> listStudentWorks = new  ArrayList<StudentWork>();
    private StudentWorkDAO studentWorkDAO = new StudentWorkDAO();
    private  Assignment assignment = new Assignment();
    private static boolean haveStudentWork = false;
    private static boolean notHaveStudentWork = false;

    /** Creates a new instance of StudentWorkManagerBean */
    public StudentWorkManagerBean() {
        loadListStudentWorks();
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
        return listStudentWorks;
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
        this.assignment = assignment;
    }

    private void loadListStudentWorks() {
        if (assignment.getId() > 0) {
            try {
                this.listStudentWorks = studentWorkDAO.getAllStudentByAssignmentId(assignment.getId());
                if (listStudentWorks.size() > 0) {
                    haveStudentWork = true;
                    notHaveStudentWork = false;
                } else {
                    haveStudentWork = false;
                    notHaveStudentWork = true;
                }

            } catch (Exception ex) {
                Logger.getLogger(StudentWorkManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String updateMark() {
        try {
            studentWorkDAO.update(selectedStudentWork);
            String subject = "You have mark!";
            String content = "You have mark!";
            String from = "2upportiveleaning@gmail.com";
            SendMailService.postMail(new String[]{selectedStudentWork.getStudent().getEmail()}, subject, content, from);
        } catch (Exception ex) {
            Logger.getLogger(StudentWorkManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
