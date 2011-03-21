/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.utils.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class CheckLoginToProcessBean implements Serializable {

    private String link;

    /** Creates a new instance of CheckLoginToProcessBean */
    public CheckLoginToProcessBean() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String redirectToPage() {
        if (link.length() > 0) {
            if (SessionManager.getSession("accountId") == null) {
                return "login.jsf?faces-redirect=truepage=" + link;
            } else {
                return link;
            }
        }
        return null;
    }
}
