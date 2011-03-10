/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.beans;

import el.dao.CLazzDAO;
import el.dao.StudentDAO;
import el.model.Clazz;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
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

    private Student selectedStudent;
    private StudentDAO studentDAO = new StudentDAO();
    private CLazzDAO clazzDAO = new CLazzDAO();
    private ArrayList<Student> listStudents = new ArrayList<Student>();
    private ArrayList<Clazz> listClazzs = new ArrayList<Clazz>();
    /** Creates a new instance of StudentBean */
    public StudentBean() {
        
    }


    {
        loadStudents();
        selectedStudent = new Student();
        loadListClazzs();
    }
    private int id;
    private String name;
    private int clazzId;

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

    public int getClazzId() {
        return clazzId;
    }

    public void setClazzId(int clazzId) {
        this.clazzId = clazzId;
    }


    

    public ArrayList<Student> getListStudents() {
        return listStudents;
    }

    public void setListStudents(ArrayList<Student> listStudents) {
        this.listStudents = listStudents;
    }

    public void loadStudents() {
        this.listStudents = studentDAO.list();
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        System.out.println("selected student:" + selectedStudent);
        this.selectedStudent = selectedStudent;
    }

    public void onRequestStudent(Student student) {
        this.setSelectedStudent(student);
    }

    public ArrayList<Clazz> getListClazzs() {
        return listClazzs;
    }

    public void setListClazzs(ArrayList<Clazz> listClazzs) {
        this.listClazzs = listClazzs;
    }


    public void loadListClazzs(){
        this.listClazzs = clazzDAO.list();
    }

    public void editStudent(ActionEvent event) {
        FacesMessage message;
        if (studentDAO.update(selectedStudent)) {
            //    loadStudents();
            message = new FacesMessage("update success!!!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            message = new FacesMessage("update failure!!!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void deleteStudent(ActionEvent event) {
        FacesMessage message;
        if (studentDAO.delete(selectedStudent)) {
            //  loadStudents();
            message = new FacesMessage("Delete Student success!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            message = new FacesMessage("Delete Student failure!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}
