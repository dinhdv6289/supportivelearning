/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.AccountDAO;
import el.dao.StudentDAO;
import el.model.Account;
import el.model.Batch;
import el.model.ChangeLearning;
import el.model.Course;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import sl.utils.beans.MessagesService;

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
    private Student[] selectedStudents;
    private Batch selectedBatch;
    private final String REDIRECT = "?faces-redirect=true";
    private ArrayList<Student> listStudentsIsNotHaveBatch = new ArrayList<Student>();
    private ArrayList<Student> listStudentsHaveBatch = new ArrayList<Student>();

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

    public Batch getSelectedBatch() {
        return selectedBatch;
    }

    public void setSelectedBatch(Batch selectedBatch) {
        this.selectedBatch = selectedBatch;
    }

    public Student[] getSelectedStudents() {
        return selectedStudents;
    }

    public void setSelectedStudents(Student[] selectedStudents) {
        this.selectedStudents = selectedStudents;
    }

    public ArrayList<Student> getListStudentsIsNotHaveBatch() {
        try {
            listStudentsIsNotHaveBatch = studentDAO.getStudentsIsNotHaveBatch();
            return listStudentsIsNotHaveBatch;
        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setListStudentsIsNotHaveBatch(ArrayList<Student> listStudentsIsNotHaveBatch) {
        this.listStudentsIsNotHaveBatch = listStudentsIsNotHaveBatch;
    }

    public ArrayList<Student> getListStudentsHaveBatch() {
        try {
            listStudentsHaveBatch = studentDAO.getStudentsHaveBatch();
            return listStudentsHaveBatch;
        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setListStudentsHaveBatch(ArrayList<Student> listStudentsHaveBatch) {
        this.listStudentsHaveBatch = listStudentsHaveBatch;
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

    public String addStudentsToBatch() {
        if (selectedStudents.length > 0) {
            for (int i = 0; i < selectedStudents.length; i++) {
                try {
                    boolean result = studentDAO.updateBatchToStudent(selectedStudents[i].getId(), selectedBatch.getId());
                    if (result) {
                        MessagesService.showMessage("Success!");
                    } else {
                        MessagesService.showMessage("failure!");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
                    return "studentManager.jsf" + REDIRECT;
                }
            }
            return "addStudentToBatch.jsf" + REDIRECT;
        }
        return "studentManager.jsf" + REDIRECT;
    }

    public String changeLearning() {
        int result = 0;
        if (selectedStudents.length > 0) {
            ChangeLearning changeLearning = new ChangeLearning();
            changeLearning.setBatch(selectedBatch);
            changeLearning.setReason("busy");
            for (int i = 0; i < selectedStudents.length; i++) {
                try {
                    boolean resultUpdate = studentDAO.updateBatchToStudent(selectedStudents[i].getId(), selectedBatch.getId());
                    if (resultUpdate) {
                        changeLearning.setStudent(selectedStudents[i]);
                        result = studentDAO.changeLearning(changeLearning);
                    } else {
                        MessagesService.showMessage("Move to Batch failure!");
                        return "studentManager.jsf" + REDIRECT;
                    }

                } catch (Exception ex) {
                    Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
                    return "studentManager.jsf" + REDIRECT;
                }
            }
            if (result > 0) {
                MessagesService.showMessage("changeLearning Success!");
                return "addStudentToBatch.jsf" + REDIRECT;
            } else {
                MessagesService.showMessage("changeLearning  failure!");
                return "studentManager.jsf" + REDIRECT;
            }

        }
        return "studentManager.jsf" + REDIRECT;

    }
}
