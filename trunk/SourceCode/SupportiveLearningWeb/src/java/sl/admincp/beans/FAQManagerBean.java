/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.FAQDAO;
import el.model.FAQ;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class FAQManagerBean implements Serializable {

    private ArrayList<FAQ> listFAQ;
    private FAQ faq = new FAQ();
    private FAQDAO faqDAO = new FAQDAO();
    private FAQ selectedFAQ = new FAQ();
    private static final String REDIRECT = "?faces-redirect=true";
    private static final String THISPAGE = "faqManger.jsf";
    private static boolean panelGroupListFAQ;
    private static boolean panelGroupCreateFAQ;
    private static boolean panelGroupUpdateFAQ;

    /** Creates a new instance of FAQManagerBean */
    public FAQManagerBean() {
        panelGroupListFAQ = true;
        panelGroupCreateFAQ = false;
        panelGroupUpdateFAQ = false;
    }

    public FAQ getFaq() {
        return faq;
    }

    public void setFaq(FAQ faq) {
        this.faq = faq;
    }

    public String insert() throws Exception {
        try {
            int insert = faqDAO.insert(faq);
            if (insert > 0) {
                panelGroupListFAQ = true;
                panelGroupCreateFAQ = false;
                return THISPAGE + REDIRECT;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(FAQManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String update() {
        try {
            boolean update = faqDAO.update(selectedFAQ);
            if (update) {
                panelGroupListFAQ = true;
                panelGroupUpdateFAQ = false;
                return THISPAGE + REDIRECT;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(FAQManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void delete() {
        try {
            faqDAO.delete(selectedFAQ);
        } catch (Exception ex) {
            Logger.getLogger(FAQManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the listFAQ
     */
    public ArrayList<FAQ> getListFAQ() {
        try {
            return listFAQ = faqDAO.list();
        } catch (Exception ex) {
            Logger.getLogger(FAQManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * @param listFAQ the listFAQ to set
     */
    public void setListFAQ(ArrayList<FAQ> listFAQ) {
        this.listFAQ = listFAQ;
    }

    /**
     * @return the selectedFAQ
     */
    public FAQ getSelectedFAQ() {
        return selectedFAQ;
    }

    /**
     * @param selectedFAQ the selectedFAQ to set
     */
    public void setSelectedFAQ(FAQ selectedFAQ) {
        this.selectedFAQ = selectedFAQ;
    }

    public String detailsFAQ(SelectEvent event) {
        return "detailFAQToUpdate" + REDIRECT;
    }

    public boolean isPanelGroupCreateFAQ() {
        return panelGroupCreateFAQ;
    }

    public void setPanelGroupCreateFAQ(boolean panelGroupCreateFAQ) {
        FAQManagerBean.panelGroupCreateFAQ = panelGroupCreateFAQ;
    }

    public boolean isPanelGroupListFAQ() {
        return panelGroupListFAQ;
    }

    public void setPanelGroupListFAQ(boolean panelGroupListFAQ) {
        FAQManagerBean.panelGroupListFAQ = panelGroupListFAQ;
    }

    public boolean isPanelGroupUpdateFAQ() {
        return panelGroupUpdateFAQ;
    }

    public void setPanelGroupUpdateFAQ(boolean apanelGroupUpdateFAQ) {
        panelGroupUpdateFAQ = apanelGroupUpdateFAQ;
    }

    public String onRequestCreateFAQs(boolean flag) {
        this.setPanelGroupCreateFAQ(flag);
        this.setPanelGroupListFAQ(false);
        this.setPanelGroupUpdateFAQ(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestUpdateFAQs(boolean flag) {
        this.setPanelGroupCreateFAQ(false);
        this.setPanelGroupListFAQ(false);
        this.setPanelGroupUpdateFAQ(flag);
        return THISPAGE + REDIRECT;
    }
}
