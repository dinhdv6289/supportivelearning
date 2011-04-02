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
import el.ultility.Ultility;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import sl.utils.beans.MessagesService;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentManagerBean implements Serializable {

    private Student student = new Student();
    private Student selectedStudent;
    private Account account = new Account();
    private Batch batch;
    private Course course;
    private StudentDAO studentDAO = new StudentDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private Student[] selectedStudents;
    private Batch selectedBatch;
    private static final String REDIRECT = "?faces-redirect=true";
    private static final String THISPAGE = "studentManager.jsf";
    private ArrayList<Student> listStudentsIsNotHaveBatch = new ArrayList<Student>();
    private ArrayList<Student> listStudentsHaveBatch = new ArrayList<Student>();
    private ArrayList<Student> listStudent = new ArrayList<Student>();
    private ArrayList<Student> listStudentsInBatch = new ArrayList<Student>();
    private ChangeLearning changeLearning = new ChangeLearning();
    private static boolean panelGroupHaveNotBatch;
    private static boolean panelGroupHaveBatch;
    private static boolean panelGroupChangeLearning;
    private static boolean panelGroupNewStudent;

    public boolean isPanelGroupNewStudent() {
        return panelGroupNewStudent;
    }

    public void setPanelGroupNewStudent(boolean panelGroupNewStudent) {
        StudentManagerBean.panelGroupNewStudent = panelGroupNewStudent;
    }
    private int batchId;

    /** Creates a new instance of StudentManagerBean */
    public StudentManagerBean() {
        panelGroupHaveBatch = false;
        panelGroupChangeLearning = false;
        panelGroupHaveNotBatch = true;
        panelGroupNewStudent = false;
    }

    @PostConstruct
    public void init() {
        getListStudentsHaveBatch();
        getListStudentsIsNotHaveBatch();
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
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

    public ChangeLearning getChangeLearning() {
        return changeLearning;
    }

    public void setChangeLearning(ChangeLearning changeLearning) {
        this.changeLearning = changeLearning;
    }

    public boolean isPanelGroupHaveBatch() {
        return panelGroupHaveBatch;
    }

    public void setPanelGroupHaveBatch(boolean panelGroupHaveBatch) {
        StudentManagerBean.panelGroupHaveBatch = panelGroupHaveBatch;
    }

    public boolean isPanelGroupHaveNotBatch() {
        return panelGroupHaveNotBatch;
    }

    public void setPanelGroupHaveNotBatch(boolean panelGroupHaveNotBatch) {
        StudentManagerBean.panelGroupHaveNotBatch = panelGroupHaveNotBatch;
    }

    public String onRequestPanelGroupHaveNotBatch(boolean panelGroupHaveNotBatch) {
        StudentManagerBean.panelGroupHaveNotBatch = panelGroupHaveNotBatch;
        StudentManagerBean.panelGroupHaveBatch = false;
        this.setPanelGroupChangeLearning(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestPanelGroupHaveBatch(boolean panelGroupHaveBatch) {
        StudentManagerBean.panelGroupHaveBatch = panelGroupHaveBatch;
        StudentManagerBean.panelGroupHaveNotBatch = false;
        this.setPanelGroupChangeLearning(false);
        return THISPAGE + REDIRECT;
    }

    public boolean isPanelGroupChangeLearning() {
        return panelGroupChangeLearning;
    }

    public void setPanelGroupChangeLearning(boolean panelGroupChangeLearning) {
        StudentManagerBean.panelGroupChangeLearning = panelGroupChangeLearning;
    }

    public String onRequestPanelGroupChangeLearning(boolean flag) {
        this.setPanelGroupChangeLearning(flag);
        this.setPanelGroupHaveBatch(false);
        this.setPanelGroupHaveNotBatch(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestPanelGroupNewStudent(boolean newstudent){
        this.setPanelGroupChangeLearning(false);
        this.setPanelGroupHaveBatch(false);
        this.setPanelGroupHaveNotBatch(false);
        this.setPanelGroupNewStudent(newstudent);
        return THISPAGE + REDIRECT;
    }
    public ArrayList<Student> getListStudentsIsNotHaveBatch() {
        try {
            if (listStudentsIsNotHaveBatch.isEmpty()) {
                listStudentsIsNotHaveBatch = studentDAO.getStudentsIsNotHaveBatch();
            }
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
            if (listStudentsHaveBatch.isEmpty()) {
                listStudentsHaveBatch = studentDAO.getStudentsHaveBatch();
            }
            return listStudentsHaveBatch;
        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setListStudentsHaveBatch(ArrayList<Student> listStudentsHaveBatch) {
        this.listStudentsHaveBatch = listStudentsHaveBatch;
    }

    public ArrayList<Student> getListStudentsInBatch() {
        try {
            return listStudentsInBatch = studentDAO.getStudentsByBatch(selectedBatch);
        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setListStudentsInBatch(ArrayList<Student> listStudentsInBatch) {
        this.listStudentsInBatch = listStudentsInBatch;
    }

    public String insertStudent() {
        try {
            String username = student.getName();
            username = Ultility.GenerateUserName(username);         
            student.setUserName(username);
            int newid = studentDAO.insertStudent(student);
            selectedStudent.setId(newid);
            selectedStudent = studentDAO.getObject(selectedStudent);
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

//    public String onRowSelectNavigate(SelectEvent event) {
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedStudent", event.getObject());
//        return "changeLearning.jsf?faces-redirect=true";
//    }
    public String addStudentsToBatch() {
        if (selectedStudents.length > 0) {
            for (int i = 0; i < selectedStudents.length; i++) {
                try {
                    boolean result = studentDAO.updateBatchToStudent(selectedStudents[i].getId(), selectedBatch.getId());
                    if (result) {
                        changeListStudentsHaveBatch();
                        changeListStudentsIsNotHaveBatch();
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

//    public String changeLearning() {
//        int result = 0;
//        if (selectedStudents.length > 0) {
//            ChangeLearning changeLearning = new ChangeLearning();
//            changeLearning.setBatch(selectedBatch);
//            changeLearning.setReason("busy");
//            for (int i = 0; i < selectedStudents.length; i++) {
//                try {
//                    boolean resultUpdate = studentDAO.updateBatchToStudent(selectedStudents[i].getId(), selectedBatch.getId());
//                    if (resultUpdate) {
//                        changeLearning.setStudent(selectedStudents[i]);
//                        result = studentDAO.changeLearning(changeLearning);
//                    } else {
//                        MessagesService.showMessage("Move to Batch failure!");
//                        return "studentManager.jsf" + REDIRECT;
//                    }
//
//                } catch (Exception ex) {
//                    Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
//                    return "studentManager.jsf" + REDIRECT;
//                }
//            }
//            if (result > 0) {
//                MessagesService.showMessage("changeLearning Success!");
//                return "addStudentToBatch.jsf" + REDIRECT;
//            } else {
//                MessagesService.showMessage("changeLearning  failure!");
//                return "studentManager.jsf" + REDIRECT;
//            }
//
//        }
//        return "studentManager.jsf" + REDIRECT;
//
//    }
    public String changeLearningForStudent() {
        int result = 0;
//        Batch bat = new Batch();
//        bat.setId(batchId);
        changeLearning.setBatch(selectedBatch);
        try {
            boolean resultUpdate = studentDAO.updateBatchToStudent(selectedStudent.getId(), selectedBatch.getId());
            if (resultUpdate) {
                changeLearning.setStudent(selectedStudent);
                result = studentDAO.changeLearning(changeLearning);
                if (result > 0) {
                    changeListStudentsHaveBatch();
                    changeListStudentsIsNotHaveBatch();
                    MessagesService.showMessage("changeLearning Success!");
                    this.setPanelGroupChangeLearning(false);
                    this.setPanelGroupHaveBatch(true);
                    return "studentManager.jsf" + REDIRECT;
                } else {
                    MessagesService.showMessage("changeLearning  failure!");
                    return "changeLearning.jsf" + REDIRECT;
                }
            } else {
                MessagesService.showMessage("Move to Batch failure!");
                return "changeLearning.jsf" + REDIRECT;
            }

        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return "changeLearning.jsf" + REDIRECT;
        }

    }

    /**
     * @return the listStudent
     */
    public ArrayList<Student> getListStudent() {
        try {
            listStudent = studentDAO.list();
            return listStudent;
        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * @param listStudent the listStudent to set
     */
    public void setListStudent(ArrayList<Student> listStudent) {
        this.listStudent = listStudent;
    }

    private void changeListStudentsHaveBatch() {
        try {
            listStudentsHaveBatch = studentDAO.getStudentsHaveBatch();
        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void changeListStudentsIsNotHaveBatch() {
        try {
            listStudentsIsNotHaveBatch = studentDAO.getStudentsIsNotHaveBatch();
        } catch (Exception ex) {
            Logger.getLogger(StudentManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
