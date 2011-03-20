/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.FeedBackDAO;
import el.model.FeedBack;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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

    /** Creates a new instance of FeedBackBean */
    public FeedBackBean() {
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


}
