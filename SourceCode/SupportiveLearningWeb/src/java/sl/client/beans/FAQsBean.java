/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.FAQDAO;
import el.model.FAQ;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class FAQsBean  implements Serializable {

    private FAQ faq = new FAQ();
    private ArrayList<FAQ> listFAQs = new ArrayList<FAQ>();
    private FAQDAO faqDAO = new FAQDAO();

    /** Creates a new instance of FAQsBean */
    public FAQsBean() {
        super();
    }

    @PostConstruct
    public void init() {
        loadListFAQs();
    }

    public FAQ getFaq() {
        return faq;
    }

    public void setFaq(FAQ faq) {
        this.faq = faq;
    }

    public ArrayList<FAQ> getListFAQs() {
        return listFAQs;
    }

    public void setListFAQs(ArrayList<FAQ> listFAQs) {
        this.listFAQs = listFAQs;
    }

    private void loadListFAQs() {
        try {
            this.listFAQs = faqDAO.list();
        } catch (Exception ex) {
            Logger.getLogger(FAQsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
