/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.StaffDAO;
import el.model.Staff;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author HIEUTT
 */
@ManagedBean
@SessionScoped
public class StaffBean implements Serializable {

    private Staff staff;
    private StaffDAO staffDAO = new StaffDAO();
    private static final String REDIRECT = "?faces-redirect=true";

    /** Creates a new instance of StaffBean */
    public StaffBean() {
    //    super();
    }

    /**
     * @return the staff
     */
    public Staff getStaff() {
        return staff;
    }

    /**
     * @param staff the staff to set
     */
    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String onRequestStaffProfile(Staff staff) {
        try {
            this.staff = staffDAO.getObject(staff);
            return "profile" + REDIRECT;
        } catch (Exception ex) {
            Logger.getLogger(StaffBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
