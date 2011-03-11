/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.beans;

import el.dao.ClazzDAO;
import el.dao.StudentDAO;
import el.model.Clazz;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentBean implements Serializable {

    private StudentDAO studentDAO = new StudentDAO();
    private Student selectedStudent;
    private ClazzDAO clazzDAO = new ClazzDAO();
    private ArrayList<Student> listStudents = new ArrayList<Student>();
    private ArrayList<Clazz> listClazzs = new ArrayList<Clazz>();

    /** Creates a new instance of StudentBean */
    public StudentBean() {
        selectedStudent = new Student();
    }
    private int id;
    private String name;
    private String userName;
    private String email;
    private String password;
    private Date birthDay;
    private boolean gender;
    private String phone;
    private String address;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public ArrayList<Clazz> getListClazzs() {
        return listClazzs;
    }

    public void setListClazzs(ArrayList<Clazz> listClazzs) {
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

    public void editStudent(ActionEvent event) {
        try {
            FacesMessage message;
            if (studentDAO.update(selectedStudent)) {
                message = new FacesMessage("update success!!!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                message = new FacesMessage("update failure!!!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception ex) {
            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteStudent(ActionEvent event) {
        try {
            FacesMessage message;
            if (studentDAO.delete(selectedStudent)) {
                message = new FacesMessage("Delete Student success!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                message = new FacesMessage("Delete Student failure!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception ex) {
            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String login() {

        return null;
    }
}
