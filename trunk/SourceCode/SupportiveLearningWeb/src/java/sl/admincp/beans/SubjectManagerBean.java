/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.SubjectDAO;
import el.model.Subject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINH
 */
@ManagedBean
@SessionScoped
public class SubjectManagerBean  implements Serializable {

    private Subject subject;
    private ArrayList<Subject> listSubjects;
    private SubjectDAO subjectDAO = new SubjectDAO();

    /** Creates a new instance of SubjectManagerBean */
    public SubjectManagerBean() {
      //  super();
    }

    public ArrayList<Subject> getListSubjects() {
        try {
            listSubjects = subjectDAO.list();
        } catch (Exception ex) {
            Logger.getLogger(SubjectManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSubjects;
    }

    public void setListSubjects(ArrayList<Subject> listSubjects) {
        this.listSubjects = listSubjects;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
