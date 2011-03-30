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

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class FAQManagerBean implements Serializable {

    private ArrayList<FAQ> listFAQ;
    private FAQ faq;
    private FAQDAO faqDAO = new FAQDAO();   
    private FAQ selectedFAQ = new FAQ();

    /** Creates a new instance of FAQManagerBean */
    public FAQManagerBean() {
        faq = new FAQ();
    }

    public FAQ getFaq() {
        return faq;
    }

    public void setFaq(FAQ faq) {
        this.faq = faq;
    }

    public String insert() throws Exception {
        try{
            faqDAO.insert(faq);
                return "listFAQs";
        }catch(Exception ex){
            Logger.getLogger(FAQManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String update() {
        try {
            if (faqDAO.update(selectedFAQ)) {
                return "listStudent";
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(FAQManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void delete(ActionEvent event) {
        try {
            faqDAO.delete(faq);
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
}
