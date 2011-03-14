/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.beans;

import el.dao.BatchDAO;
import el.dao.StudentDAO;
import el.model.Batch;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import sl.utils.SessionManager;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentBean extends AbstractBean implements Serializable {

    private StudentDAO studentDAO = new StudentDAO();
    private Student selectedStudent;
    private Student student;
    private BatchDAO clazzDAO = new BatchDAO();
    private ArrayList<Student> listStudents = new ArrayList<Student>();
    private ArrayList<Batch> listClazzs = new ArrayList<Batch>();

    /** Creates a new instance of StudentBean */
    public StudentBean() {
        selectedStudent = new Student();
        student = new Student();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public ArrayList<Batch> getListClazzs() {
        return listClazzs;
    }

    public void setListClazzs(ArrayList<Batch> listClazzs) {
        this.listClazzs = listClazzs;
    }

    public ArrayList<Student> getListStudents() {
        return listStudents;
    }

    public void setListStudents(ArrayList<Student> listStudents) {
        this.listStudents = listStudents;
    }

    public void loadListClazzs() {
        try {
            this.listClazzs = clazzDAO.list();
        } catch (Exception ex) {
            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRequestStudent(Student student) {
        this.setSelectedStudent(student);
    }

    @Override
    public String insert() {

        return null;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

}
