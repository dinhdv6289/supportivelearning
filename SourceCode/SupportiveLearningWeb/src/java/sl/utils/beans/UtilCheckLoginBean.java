/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.utils.beans;

import el.dao.AccountDAO;
import el.model.Account;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DINH
 */
@ManagedBean
@SessionScoped
public class UtilCheckLoginBean implements Serializable {

    private AccountDAO accountDAO = new AccountDAO();

    /** Creates a new instance of UtilCheckLoginBean */
    public UtilCheckLoginBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        Account accountLogin = null;
        if (SessionManager.getSession("accountId") == null) {
            redirectLogin();
        } else {
            try {
                int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
                accountLogin = accountDAO.getAccountById(accountId);
                if (accountLogin != null) {
                    if (accountLogin.getRole().getName().equals("Admin")) {
                        response.sendRedirect("../ui.admin/index.jsf");
                    } else if (accountLogin.getRole().getName().equals("Staff")) {
                        response.sendRedirect("../ui.staff/index.jsf");
                    } else {
                        response.sendRedirect("../ui.client/index.jsf");
                    }
                } else {
                    response.sendRedirect("../ui.client/index.jsf");
                }
            } catch (Exception ex) {
                Logger.getLogger(UtilCheckLoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String redirectLogin() {
        return "login.jsf?faces-redirect=true";
    }
}
