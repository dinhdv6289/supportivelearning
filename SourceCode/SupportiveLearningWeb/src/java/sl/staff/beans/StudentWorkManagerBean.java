/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.staff.beans;

import el.dao.StudentWorkDAO;
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

    private StudentWork studentWork;
    private StudentWork selectedStudentWork;
    private ArrayList<StudentWork> listStudentWorks;
    private StudentWorkDAO studentWorkDAO = new StudentWorkDAO();

    /** Creates a new instance of StudentWorkManagerBean */
    public StudentWorkManagerBean() {
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

    private void loadListStudentWorks() {
        //this.listStudentWorks = studentWorkDAO.
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
