/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.FeedBackDAO;
import el.model.FeedBack;
import el.model.Staff;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.EachSession;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class FeedBackBean implements Serializable {

    private FeedBack feedBack;
    private ArrayList<FeedBack> listFeedBacks;
    private FeedBackDAO feedBackDAO = new FeedBackDAO();
    private static final String REDIRECT = "?faces-redirect=true";
    private Staff staff;

    /** Creates a new instance of FeedBackBean */
    public FeedBackBean() {
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public FeedBack getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
    }

    public ArrayList<FeedBack> getListFeedBacks() {
        return listFeedBacks;
    }

    public void setListFeedBacks(ArrayList<FeedBack> listFeedBacks) {
        this.listFeedBacks = listFeedBacks;
    }

    
    public String insertFeedback() {
        try {
            Student student = (Student) EachSession.getObjectFromSession("accountId");
            if (student != null) {
                feedBack.setFeedBackTitle("tgess");
                feedBack.setStudent(student);
                feedBack.setStaff(staff);
                feedBackDAO.insert(feedBack);
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(FeedBackBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String onRequestStaffToSendFeedBack(Staff staff) {
        this.staff = staff;
        return "feedBack" + REDIRECT;
    }
}
