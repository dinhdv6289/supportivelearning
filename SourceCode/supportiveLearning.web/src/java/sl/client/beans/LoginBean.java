/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.AccountDAO;
import el.model.Account;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.SessionManager;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private AccountDAO accountDAO = new AccountDAO();
    private Account account;

    /** Creates a new instance of LoginBean */
    public LoginBean() {
        account = new Account();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String login() {
        if (account != null) {
            try {
                Account accountLogin = accountDAO.getObject(account);
                if (accountLogin != null) {
                    // co nen check kieu nay??? lay ra ten hay id.
                    // xem xet'
                    //
                    // cach khac. ko can check role. Cu' the de trang mac dinh.
                    SessionManager.setSession("accountLogon", accountLogin);
                    if (accountLogin.getRole().getName().equals("Admin")) {
                        // chuyen ra trang tuong
                    } else if (accountLogin.getRole().getName().equals("Staff")) {
                    } else {
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            }
        }
        return "";
    }
}
