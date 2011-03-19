/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

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
public class StudentWorkBean implements Serializable {

    private ArrayList<StudentWork> listStudentWorksOfStudent = new ArrayList<StudentWork>();
    private StudentWorkDAO studentWorkDAO = new StudentWorkDAO();

    /** Creates a new instance of StudentWorkBean */
    public StudentWorkBean() {
        loadStudentWorks();
    }

    public ArrayList<StudentWork> getListStudentWorksOfStudent() {
        return listStudentWorksOfStudent;
    }

    public void setListStudentWorksOfStudent(ArrayList<StudentWork> listStudentWorksOfStudent) {
        this.listStudentWorksOfStudent = listStudentWorksOfStudent;
    }

    private void loadStudentWorks() {
        try {
            listStudentWorksOfStudent = studentWorkDAO.list();
        } catch (Exception ex) {
            Logger.getLogger(StudentWorkBean.class.getName()).log(Level.SEVERE, null, ex);
            listStudentWorksOfStudent = null;
        }
    }
}
