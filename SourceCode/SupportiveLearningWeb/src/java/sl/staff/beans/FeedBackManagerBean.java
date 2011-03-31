/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.staff.beans;

import el.dao.FeedBackDAO;
import el.model.FeedBack;
import el.model.FeedBackAnswer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class FeedBackManagerBean implements Serializable {

    private FeedBack feedBack;
    private ArrayList<FeedBack> listFeedBacks;
    private FeedBackDAO feedBackDAO = new FeedBackDAO();
    private FeedBack selectedFeedBack;
    private FeedBackAnswer feedBackAnswer;

    /** Creates a new instance of FeedBackManagerBean */
    public FeedBackManagerBean() {
    }

    @PostConstruct
    public void init() {
        loadListFeedBacks();
    }

    public FeedBackAnswer getFeedBackAnswer() {
        return feedBackAnswer;
    }

    public void setFeedBackAnswer(FeedBackAnswer feedBackAnswer) {
        this.feedBackAnswer = feedBackAnswer;
    }

    public FeedBack getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
    }

    public FeedBack getSelectedFeedBack() {
        return selectedFeedBack;
    }

    public void setSelectedFeedBack(FeedBack selectedFeedBack) {
        this.selectedFeedBack = selectedFeedBack;
    }

    public ArrayList<FeedBack> getListFeedBacks() {
        return listFeedBacks;
    }

    public void setListFeedBacks(ArrayList<FeedBack> listFeedBacks) {
        this.listFeedBacks = listFeedBacks;
    }

    private void loadListFeedBacks() {
        try {
            if (listFeedBacks.isEmpty()) {
                this.listFeedBacks = feedBackDAO.list();
            }
        } catch (Exception ex) {
            Logger.getLogger(FeedBackManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String insertFeedBackAnswer() {
        try {
            feedBackDAO.insert(selectedFeedBack);

        } catch (Exception ex) {
            Logger.getLogger(FeedBackManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
