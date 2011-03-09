/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.beans;

import el.dao.StudentDAO;
import el.model.Course;
import el.model.Student;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentBean {

    private StudentDAO studentDAO = new StudentDAO();
    private ArrayList<Student> students;

    public StudentBean() {
        loadStudent();
    }
    private String name;
    private String userName;
    private String password;
    private String repassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    private Student studentSelected;

    public Student getStudentSelected() {
        return studentSelected;
    }

    public void setStudentSelected(Student studentSelected) {
        this.studentSelected = studentSelected;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    private void loadStudent() {
        if (students == null) {
            students = studentDAO.list();
        }
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

    public void editStudent(ActionEvent event) {
        FacesMessage message;
        if (studentDAO.update(studentSelected)) {
            message = new FacesMessage("update success!!!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            message = new FacesMessage("update failure!!!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void deleteStudent(ActionEvent event) {
        FacesMessage message;
        if (studentDAO.delete(studentSelected)) {
            message = new FacesMessage("Delete Student success!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            message = new FacesMessage("Delete Student failure!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public String insert(){
        Student student = new Student();
        
       // student.setCourse()
     //   studentDAO.insert(this);
        return "new";
    }

    public void onRequestStudent(Student student){
        this.studentSelected = student;
    }
    
}
