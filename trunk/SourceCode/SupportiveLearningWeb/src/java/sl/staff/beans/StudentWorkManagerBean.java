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

        } catch (Exception ex) {
            Logger.getLogger(StudentWorkManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
