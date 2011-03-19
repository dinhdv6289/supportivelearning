/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.SemesterDAO;
import el.model.Semester;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class SemesterBean implements Serializable {

    private Semester semester;
    private ArrayList<Semester> listSemesters = new ArrayList<Semester>();
    private SemesterDAO semesterDAO = new SemesterDAO();

    /** Creates a new instance of SemesterBean */
    public SemesterBean() {
        loadSemesters();
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public ArrayList<Semester> getListSemesters() {
        return listSemesters;
    }

    public void setListSemesters(ArrayList<Semester> listSemesters) {
        this.listSemesters = listSemesters;
    }

    private void loadSemesters() {
        try {
            this.listSemesters = semesterDAO.list();
        } catch (Exception ex) {
            Logger.getLogger(SemesterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String viewDetailsSemester(Semester semester){

        return "";
    }
}
