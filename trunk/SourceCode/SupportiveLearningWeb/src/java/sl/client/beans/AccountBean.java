/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.AccountDAO;
import el.model.Account;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.EachSession;
import sl.utils.beans.MessagesService;
import sl.utils.beans.SessionManager;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class AccountBean implements Serializable {

    private Account account = new Account();
    private ArrayList<Account> listAccounts;
    private ArrayList<Account> listAccountsOnline;
    private AccountDAO accountDAO = new AccountDAO();
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    /** Creates a new instance of AccountBean */
    public AccountBean() {
        loadListAccountsOnline();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<Account> getListAccounts() {
        return listAccounts;
    }

    public void setListAccounts(ArrayList<Account> listAccounts) {
        this.listAccounts = listAccounts;
    }

    public ArrayList<Account> getListAccountsOnline() {
        return listAccountsOnline;
    }

    public void setListAccountsOnline(ArrayList<Account> listAccountsOnline) {
        this.listAccountsOnline = listAccountsOnline;
    }

    private void loadListAccountsOnline() {
        try {
            this.listAccountsOnline = accountDAO.getObjectIsOnline();
        } catch (Exception ex) {
            Logger.getLogger(AccountBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String forGotPassword() {

        return null;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String changePassword() {
        if (checkPassword()) {
            try {
                int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
                Account accountById = accountDAO.getAccountById(accountId);
                if (accountById.getPassword().equals(oldPassword)) {
                    boolean updatePasswordStand = accountDAO.updatePassword(accountById.getId(), newPassword);
                    if (updatePasswordStand) {
                        MessagesService.showMessage("Update password success.");
                    } else {
                        MessagesService.showMessage("Update password failure.");
                    }
                } else {
                    MessagesService.showMessage("Old password incorrect.");
                }

            } catch (Exception ex) {
                Logger.getLogger(AccountBean.class.getName()).log(Level.SEVERE, null, ex);
                MessagesService.showMessage("Update password failure.");
            }
        }
        return null;
    }

    private boolean checkPassword() {
        boolean flag = false;
        if (confirmPassword == null || confirmPassword.length() == 0) {
            flag = false;
        } else {
            if (confirmPassword.equals(newPassword)) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }
}
