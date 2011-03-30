/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.StaffDAO;
import el.dao.StudentDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author TuyenPV
 */
@ManagedBean
@RequestScoped
public class StatisticsBean {

    /** Creates a new instance of StatisticsBean */
    public StatisticsBean() {
    }
    private StudentDAO studentDAO = new StudentDAO();
    private StaffDAO staffDAO = new StaffDAO();
    
    private int totalStudent;
    private int studentNotBatch;
    private int studentInBatch;
    private int studentisDrop;
    private int staffNotBatch;
    private int staffInBatch;
    private int totalStaff;

    public int getTotalStaff() {
        try {
            return staffDAO.list().size();
        } catch (Exception ex) {
            return 0;
        }
    }

    public void setTotalStaff(int totalStaff) {
        this.totalStaff = totalStaff;
    }
    

    public int getStaffInBatch() {
        return 0;
    }

    public void setStaffInBatch(int staffInBatch) {
        this.staffInBatch = staffInBatch;
    }

    public int getStaffNotBatch() {
        return staffNotBatch;
    }

    public void setStaffNotBatch(int staffNotBatch) {
        this.staffNotBatch = staffNotBatch;
    }
    

    public int getStudentisDrop() {
        try {
            return studentDAO.getStudentsIsLock().size();
        } catch (Exception ex) {
            return 0;
        }
    }

    public void setStudentisDrop(int studentisDrop) {
        this.studentisDrop = studentisDrop;
    }

    public int getStudentInBatch() {
        try {
            return studentDAO.getStudentsHaveBatch().size();
        } catch (Exception ex) {
            return 0;
        }
    }

    public void setStudentInBatch(int studentInBatch) {
        this.studentInBatch = studentInBatch;
    }

    public int getStudentNotBatch() {
        try {
            return studentDAO.getStudentsIsNotHaveBatch().size();
        } catch (Exception ex) {
            return 0;
        }
    }

    public void setStudentNotBatch(int studentNotBatch) {
        this.studentNotBatch = studentNotBatch;
    }

    public int getTotalStudent() {
        try {
            return studentDAO.list().size();
        } catch (Exception ex) {
            return 0;
        }
    }

    public void setTotalStudent(int totalStudent) {
        this.totalStudent = totalStudent;
    }
}
