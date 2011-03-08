/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.beans;

import el.dao.StudentDAO;
import el.model.Student;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@RequestScoped
public class StudentBean {

    private StudentDAO studentDAO = new StudentDAO();

    private ArrayList<Student> students = studentDAO.list();
    private static Student selectedStudent;

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public StudentDAO getStudentDAO() {
        return studentDAO;
    }

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
}
