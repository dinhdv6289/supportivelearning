/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.model.FAQ;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class FAQManagerBean implements Serializable {

    private FAQ fag;

    /** Creates a new instance of FAQManagerBean */
    public FAQManagerBean() {
        fag = new FAQ();
    }

    public FAQ getFag() {
        return fag;
    }

    public void setFag(FAQ fag) {
        this.fag = fag;
    }

    public String insert() {
        //trycatch
        return null;
    }

    public String update() {
        //trycatch
        return null;
    }

    public String delete() {

        return null;
    }
}
