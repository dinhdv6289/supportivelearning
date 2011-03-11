/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sl.beans;

import el.dao.StudentDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentBean implements Serializable {

    private StudentDAO studentDAO = new StudentDAO();


    /** Creates a new instance of StudentBean */
    public StudentBean() {
    }

}
