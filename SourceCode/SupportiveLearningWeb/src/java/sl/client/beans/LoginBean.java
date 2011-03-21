/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.AccountDAO;
import el.dao.StudentDAO;
import el.model.Account;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sl.utils.beans.SessionManager;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private Account account = new Account();
    private static boolean panelLogin = true;
    private static boolean panelHi = false;
    private static boolean panelStudent = true;
    private static boolean panelStaff = false;
    private AccountDAO accountDAO = new AccountDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private static String pageRequest = "";
    //private

    /** Creates a new instance of LoginBean */
    public LoginBean() {
    }

    private void getRequestPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (request.getAttribute("page") != null) {
            this.setPageRequest(request.getParameter("page"));
        }
    }

    public String getPageRequest() {
        return pageRequest;
    }

    public final void setPageRequest(String pageRequest) {
        LoginBean.pageRequest = pageRequest;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isPanelHi() {
        return panelHi;
    }

    public void setPanelHi(boolean panelHi) {
        LoginBean.panelHi = panelHi;
    }

    public boolean isPanelLogin() {
        return panelLogin;
    }

    public void setPanelLogin(boolean panelLogin) {
        LoginBean.panelLogin = panelLogin;
    }

    public boolean isPanelStaff() {
        return panelStaff;
    }

    public void setPanelStaff(boolean panelStaff) {
        LoginBean.panelStaff = panelStaff;
    }

    public boolean isPanelStudent() {
        return panelStudent;
    }

    public void setPanelStudent(boolean panelStudent) {
        LoginBean.panelStudent = panelStudent;
    }

    public String login() {
        getRequestPage();
        String result = "";
        if (account != null) {
            try {
                Account accountLogin = accountDAO.getObject(account);
                if (accountLogin.getId() > 0) {
                    account = accountLogin;
                    setPanelHi(true);
                    setPanelLogin(false);
                    // co nen check kieu nay??? lay ra ten hay id.
                    // xem xet'
                    //
                    // cach khac. ko can check role. Cu' the de trang mac dinh.
                    SessionManager.setSession("accountId", accountLogin.getId());
                    if (accountLogin.getRole().getName().equals("Admin")) {
                        // chuyen ra trang tuong
                    } else if (accountLogin.getRole().getName().equals("Staff")) {
                        setPanelStaff(true);
                        setPanelStudent(false);
                    } else {
                        setPanelStaff(false);
                        setPanelStudent(true);
                    }
                    result = this.getPageRequest();
                }
            } catch (Exception ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                result = "";
            }
        }
        return result + "?faces-redirect=true";
    }



    

    public String logout() {
        setPanelHi(false);
        setPanelLogin(true);
        SessionManager.invalidate("accountId");
        return null;
    }
}
