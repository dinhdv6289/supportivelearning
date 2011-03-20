/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.AccountDAO;
import el.model.Account;
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
public class AccountBean implements Serializable {

    private Account account;
    private ArrayList<Account> listAccounts;
    private ArrayList<Account> listAccountsOnline;
    private AccountDAO accountDAO = new AccountDAO();

    /** Creates a new instance of AccountBean */
    public AccountBean() {
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

    private void loadListAccountsOnline(){
        //this.listAccountsOnline = acc
    }
}
