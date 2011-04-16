/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.SemesterDAO;
import el.model.Semester;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINH
 */
@ManagedBean
@SessionScoped
public class SemesterManagerBean  implements Serializable {

    private Semester semester;
    private Semester selectedSemester;
    private ArrayList<Semester> listSemesters = new ArrayList<Semester>();
    private SemesterDAO semesterDAO = new SemesterDAO();

    /** Creates a new instance of SemesterManagerBean */
    public SemesterManagerBean() {
//        super();
    }

    @PostConstruct
    public void init() {
        getListSemesters();
    }

    public ArrayList<Semester> getListSemesters() {
        try {
            if (listSemesters.isEmpty()) {
                listSemesters = semesterDAO.list();
            }
            return listSemesters;
        } catch (Exception ex) {
            Logger.getLogger(SemesterManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setListSemesters(ArrayList<Semester> listSemesters) {
        this.listSemesters = listSemesters;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Semester getSelectedSemester() {
        return selectedSemester;
    }

    public void setSelectedSemester(Semester selectedSemester) {
        this.selectedSemester = selectedSemester;
    }
}
