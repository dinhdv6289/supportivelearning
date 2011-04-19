/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.FeedBackAnswerDAO;
import el.dao.FeedBackDAO;
import el.dao.StaffDAO;
import el.model.Assignment;
import el.model.FeedBack;
import el.model.FeedBackAnswer;
import el.model.Staff;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sl.utils.beans.EachSession;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class FeedBackBean  implements Serializable {

    private FeedBack feedBack = new FeedBack();
    private ArrayList<FeedBack> listFeedBacks;
    private FeedBackDAO feedBackDAO = new FeedBackDAO();
    private static final String REDIRECT = "?faces-redirect=true";
    private Assignment assignment;
    private Staff staff;
    private TreeNode feedbackRoot;

    public TreeNode getFeedbackRoot() {
        try {
            FeedBackAnswerDAO feedBackAnswerDAO = new FeedBackAnswerDAO();
            feedbackRoot = new DefaultTreeNode("feedbackRoot", null);
            Student s = (Student) EachSession.getObjectFromSession("accountId");
            ArrayList<FeedBack> listFB = feedBackDAO.listFeedbackForStudent(s, staff);
            for (FeedBack fb : listFB) {
                TreeNode tn = new DefaultTreeNode(fb, feedbackRoot);
                ArrayList<FeedBackAnswer> feedBackAnswers = feedBackAnswerDAO.getFeedbackAnswerByFeedbackId(fb.getId());
                for (FeedBackAnswer feedBackAnswer : feedBackAnswers) {
                    FeedBack feedBack1 = new FeedBack();
                    feedBack1.setDateCreation(feedBackAnswer.getDateCreate());
                    feedBack1.setFeedBackContent(feedBackAnswer.getFeedBackAnswer());
                     TreeNode tn1 = new DefaultTreeNode(feedBack1, tn);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FeedBackBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return feedbackRoot;
    }
    

    public ArrayList<Staff> loadListContact(Student s) {
        StaffDAO staffDAO = new StaffDAO();
        try {
            return staffDAO.listContactForStudent(s);
        } catch (Exception ex) {
            Logger.getLogger(FeedBackBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }



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

    public Assignment getAssignment() {
        return assignment;
    }
    
    public String insertFeedback() {
        try {
            Student student = (Student) EachSession.getObjectFromSession("accountId");
            if (student != null) {
                
                feedBack.setStudent(student);
                feedBack.setStaff(staff);
                feedBackDAO.insert(feedBack);
                feedBack = new FeedBack();
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(FeedBackBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public String onRequestFeedback(Assignment assignment) {
        this.assignment = assignment;
        return "messageDetails.jsf" + REDIRECT;
    }
    public String onRequestStaffToSendFeedBack(Staff staff) {
        this.staff = staff;
        return "feedBack" + REDIRECT;
    }
}
