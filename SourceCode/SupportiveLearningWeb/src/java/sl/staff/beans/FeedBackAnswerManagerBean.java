/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.staff.beans;

import el.dao.FeedBackAnswerDAO;
import el.dao.FeedBackDAO;
import el.dao.StaffDAO;
import el.model.FeedBack;
import el.model.FeedBackAnswer;
import el.model.Staff;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sl.utils.beans.LoginService;
import sl.utils.beans.MessagesService;
import sl.utils.beans.SessionManager;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class FeedBackAnswerManagerBean extends UtilCheckLoginBean implements Serializable {

    private static final String REDIRECT = "?faces-redirect=true";
    private static final String THISPAGE = "messages.jsf";
    private FeedBack selectedFeedBack = new FeedBack();
    private FeedBackAnswer feedBackAnswer = new FeedBackAnswer();
    private FeedBackAnswerDAO feedBackAnswerDAO = new FeedBackAnswerDAO();
    private ArrayList<FeedBack> listFeedBacks = new ArrayList<FeedBack>();
    private ArrayList<FeedBack> listTopFeedBacks = new ArrayList<FeedBack>();
    private ArrayList<FeedBack> listFeedbackForStudent;
    private FeedBackDAO feedBackDAO = new FeedBackDAO();
    private Staff currentStaff = new Staff();
    private StaffDAO staffDAO = new StaffDAO();
    private static boolean panelGroupMessageDetails;
    private static boolean panelGroupMessage;
    private static boolean panelAnswer;
    private static boolean panelViewDetails;
    private TreeNode selectedTreNode;
    private ArrayList<FeedBackAnswer> listFeedBackAnswers;

    /** Creates a new instance of FeedBackAnswerManagerBean */
    public FeedBackAnswerManagerBean() {
//        super();
        panelGroupMessage = true;
        panelGroupMessageDetails = false;
        panelAnswer = false;
        panelViewDetails = false;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        if (SessionManager.getSession("accountId") == null) {
            // LoginService.loginService(request.getRequestURI());
        } else {
            try {
                int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
                currentStaff = staffDAO.getStaffByAccountId(accountId);
                if (currentStaff.getId() > 0) {
                    //loadAllStudentWorks(staff);
                } else {
                    response.sendRedirect("ui.client/login.jsf");
                }
            } catch (Exception ex) {
                Logger.getLogger(FeedBackAnswerManagerBean.class.getName()).log(Level.SEVERE, null, ex);
                //   LoginService.loginService("");
            }
        }
    }

    @PostConstruct
    public void init() {
        loadListFeedBacks();
    }

    public TreeNode getSelectedTreNode() {
        return selectedTreNode;
    }

    public void setSelectedTreNode(TreeNode selectedTreNode) {
        this.selectedTreNode = selectedTreNode;
    }

    public boolean isPanelAnswer() {
        return panelAnswer;
    }

    public void setPanelAnswer(boolean panelAnswer) {
        FeedBackAnswerManagerBean.panelAnswer = panelAnswer;
    }
    private TreeNode feedbackRoot = new DefaultTreeNode("feedbackRoot", null);

    ;

    public void setFeedbackRoot(TreeNode feedbackRoot) {
        this.feedbackRoot = feedbackRoot;
    }

    public TreeNode getFeedbackRoot() {
        try {

            int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
            Staff staffByAccountId = staffDAO.getStaffByAccountId(accountId);
            if (staffByAccountId.getId() > 0) {
                ArrayList<FeedBack> listFB = feedBackDAO.listFeedbackForStudent(selectedFeedBack.getStudent(), staffByAccountId);
                for (FeedBack fb : listFB) {
                    TreeNode tn = new DefaultTreeNode(fb, feedbackRoot);
                    ArrayList<FeedBackAnswer> feedBackAnswers = feedBackAnswerDAO.getFeedbackAnswerByFeedbackId(fb.getId());
                    for (FeedBackAnswer fba : feedBackAnswers) {
                        FeedBack feedBack1 = new FeedBack();
                        feedBack1.setDateCreation(fba.getDateCreate());
                        feedBack1.setFeedBackContent(fba.getFeedBackAnswer());
                        feedBack1.setId(0);
                        TreeNode tn1 = new DefaultTreeNode(feedBack1, tn);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FeedBackAnswerManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return feedbackRoot;
    }

    public ArrayList<FeedBack> getListFeedbackForStudent() {
        if (currentStaff.getId() > 0) {
            try {
                listFeedbackForStudent = feedBackDAO.listFeedbackForStudent(selectedFeedBack.getStudent(), currentStaff);
            } catch (Exception ex) {
                Logger.getLogger(FeedBackAnswerManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listFeedbackForStudent;
    }

    public void setListFeedbackForStudent(ArrayList<FeedBack> listFeedbackForStudent) {
        this.listFeedbackForStudent = listFeedbackForStudent;
    }

    public ArrayList<FeedBackAnswer> getListFeedBackAnswers() {
        if (selectedFeedBack.getId() > 0) {
            try {
                listFeedBackAnswers = feedBackAnswerDAO.getFeedbackAnswerByFeedbackId(selectedFeedBack.getId());
            } catch (Exception ex) {
                Logger.getLogger(FeedBackAnswerManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listFeedBackAnswers;
    }

    public void setListFeedBackAnswers(ArrayList<FeedBackAnswer> listFeedBackAnswers) {
        this.listFeedBackAnswers = listFeedBackAnswers;
    }

    public boolean checkFeedBack(FeedBack fb) {
        if (fb.getId() > 0) {
            return true;
        }
        return false;
    }

    public boolean isPanelGroupMessage() {
        return panelGroupMessage;
    }

    public void setPanelGroupMessage(boolean panelGroupMessage) {
        FeedBackAnswerManagerBean.panelGroupMessage = panelGroupMessage;
    }

    public boolean isPanelGroupMessageDetails() {
        return panelGroupMessageDetails;
    }

    public void setPanelGroupMessageDetails(boolean panelGroupMessageDetails) {
        FeedBackAnswerManagerBean.panelGroupMessageDetails = panelGroupMessageDetails;
    }

    public boolean isPanelViewDetails() {
        return panelViewDetails;
    }

    public void setPanelViewDetails(boolean panelViewDetails) {
        FeedBackAnswerManagerBean.panelViewDetails = panelViewDetails;
    }

    public String onRequestpanelGroupMessageDetails(boolean flag) {
        this.panelGroupMessage = true;
        this.panelAnswer = false;
        this.setPanelGroupMessageDetails(flag);
        this.setPanelViewDetails(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestpanelAnswer(boolean flag) {
        this.panelGroupMessage = true;
        this.setPanelGroupMessageDetails(true);
        this.setPanelAnswer(flag);
        setPanelViewDetails(true);
        return THISPAGE + REDIRECT;
    }

    public String onRequestPanelViewDetails(boolean flag) {
        this.panelGroupMessage = true;
        this.setPanelGroupMessageDetails(true);
        this.setPanelAnswer(false);
        setPanelViewDetails(flag);
        return THISPAGE + REDIRECT;
    }

    public String onRequestpanelGroupMessage(boolean flag) {
        this.setPanelGroupMessage(flag);
        return THISPAGE + REDIRECT;
    }

    public ArrayList<FeedBack> getListTopFeedBacks() {
        try {
            if (currentStaff.getId() > 0) {
                this.listTopFeedBacks = feedBackDAO.listTopFeedbackForStaff(currentStaff);
            }
        } catch (Exception ex) {
            Logger.getLogger(FeedBackAnswerManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTopFeedBacks;
    }

    public FeedBackAnswer getFeedBackAnswer() {
        return feedBackAnswer;
    }

    public void setFeedBackAnswer(FeedBackAnswer feedBackAnswer) {
        this.feedBackAnswer = feedBackAnswer;
    }

    public FeedBack getSelectedFeedBack() {
        return selectedFeedBack;
    }

    public void setSelectedFeedBack(FeedBack selectedFeedBack) {
        this.selectedFeedBack = selectedFeedBack;
    }

    public ArrayList<FeedBack> getListFeedBacks() {
        try {
            if ((currentStaff.getId() > 0)) {
                this.listFeedBacks = feedBackDAO.listFeedbackForStaff(currentStaff);
            }
        } catch (Exception ex) {
            Logger.getLogger(FeedBackAnswerManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listFeedBacks;
    }

    public void setListFeedBacks(ArrayList<FeedBack> listFeedBacks) {
        this.listFeedBacks = listFeedBacks;
    }

    public Staff getCurrentStaff() {
        return currentStaff;
    }

    public void setCurrentStaff(Staff currentStaff) {
        this.currentStaff = currentStaff;
    }

    public void onRequestFeedBack(FeedBack feedBack) {
        this.selectedFeedBack = feedBack;
    }

    private void loadListFeedBacks() {
        try {
            if (listFeedBacks.isEmpty()) {
                this.listFeedBacks = feedBackDAO.list();
            }
        } catch (Exception ex) {
            Logger.getLogger(FeedBackAnswerManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String insertFeedBackAnswer() {
        try {
            if (selectedFeedBack.getId() > 0) {
                feedBackAnswer.setFeedBack(selectedFeedBack);
                int insert = feedBackAnswerDAO.insert(feedBackAnswer);
                if (insert > 0) {
                    MessagesService.showMessage("Send answer success.");
                } else {
                    MessagesService.showMessage("Send answer failure.");
                }

            } else {
                MessagesService.showMessage("Send answer failure.");
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(FeedBackAnswerManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            MessagesService.showMessage("Send answer failure.");
            return null;
        }

    }

//    private Staff getStaff() {
//        try {
//            int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
//            Staff staffByAccountId = staffDAO.getStaffByAccountId(accountId);
//            if (staffByAccountId != null) {
//                return currentStaff = staffByAccountId;
//            } else {
//                return null;
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(FeedBackAnswerManagerBean.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
    public String onRowSelectNavigate(SelectEvent event) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedFeedBack", event.getObject());
        return "messageDetails.jsf?faces-redirect=true";
    }
}
