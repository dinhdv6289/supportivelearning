/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.StudentDAO;
import el.model.Course;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.EachSession;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentBean   implements Serializable {

    private Student student = new Student();
    private ArrayList<Course> listCourses = new ArrayList<Course>();
    private StudentDAO studentDAO = new StudentDAO();
    private ArrayList<Student> listStudents = new ArrayList<Student>();

    /** Creates a new instance of StudentBean */
    public StudentBean() {

    }

    public ArrayList<Course> getListCourses() {
        return listCourses;
    }

    public void setListCourses(ArrayList<Course> listCourses) {
        this.listCourses = listCourses;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ArrayList<Student> getListStudents() {
        return listStudents;
    }

    public void setListStudents(ArrayList<Student> listStudents) {
        this.listStudents = listStudents;
    }

    private void loadStudent() {
        try {
            Object object = EachSession.getObjectFromSession("accountId");
            if (object != null) {
                this.student = studentDAO.getObject((Student) object);
            }

        } catch (Exception ex) {
            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
