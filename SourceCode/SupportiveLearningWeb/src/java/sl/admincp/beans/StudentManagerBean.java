/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.AccountDAO;
import el.dao.StudentDAO;
import el.model.Account;
import el.model.Batch;
import el.model.Course;
import el.model.Student;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentManagerBean implements Serializable {

    private Student student = new Student();
    private Student selectedStudent = new Student();
    private Account account = new Account();
    private Batch batch = new Batch();
    private Course course = new Course();
    
    private StudentDAO studentDAO = new StudentDAO();
    private AccountDAO accountDAO = new AccountDAO();

    /** Creates a new instance of StudentManagerBean */
    public StudentManagerBean() {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }



    public String insertStudent() {
        try {
            if (account != null) {
                int accountId = accountDAO.insert(account);
                if (accountId != 0) {
                    //student.setBatch(batch);
                    //student.setCourse(course);
                    // cho them sinh vien nay.
                    // course va batch se dc cap nhat vao sau.
                    // thong qua viec cho sv vao lop'.
                    if (studentDAO.insertStudent(student, accountId) > 0) {
                        return "studentList";
                    }
                }
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String updateStudent() {
        try {
            if (studentDAO.update(selectedStudent)) {
                return "studentList";
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteStudent(ActionEvent event) {
        try {
            studentDAO.delete(student);
        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
