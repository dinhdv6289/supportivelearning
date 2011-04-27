/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.AccountDAO;
import el.model.Account;
import el.model.Role;
import el.model.Staff;
import el.model.Student;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.MessagesService;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class AccountManagerBean extends UtilCheckLoginBean implements Serializable {

    private Account account;
    private Account[] selectedAccounts;
    private Staff staff;
    private Student student;
    private Role role;
    private ArrayList<Account> listAccountRoleStudent;
    private ArrayList<Account> listAccountRoleStaff;
    private ArrayList<Account> listAccountRoleStudentLocked;
    private ArrayList<Account> listAccountRoleStaffLocked;
    private static boolean panelListAccountRoleStudent;
    private static boolean panelListAccountRoleStudentLocked;
    private static boolean panelListAccountRoleStaff;
    private static boolean panelListAccountRoleStaffLocked;
    private AccountDAO accountDAO = new AccountDAO();
    private static final String REDIRECT = "faces-redirect=true";

    /** Creates a new instance of AccountManagerBean */
    public AccountManagerBean() {
        super();
        panelListAccountRoleStaff = false;
        panelListAccountRoleStudent = true;
        panelListAccountRoleStudentLocked = false;
        panelListAccountRoleStaffLocked = false;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Account[] getSelectedAccounts() {
        return selectedAccounts;
    }

    public void setSelectedAccounts(Account[] selectedAccounts) {
        this.selectedAccounts = selectedAccounts;
    }

    public String lockAccount(boolean status) {
        if (selectedAccounts.length>0) {
            try {
                boolean lockAccount = false;
                for (int i = 0; i < selectedAccounts.length; i++) {
                   lockAccount= accountDAO.lockAccount(selectedAccounts[i].getId(), status);
                }
                String message = "";
                if (status) {
                    message = "Allow Login ";
                } else {
                    message = "Lock ";
                }
                if (lockAccount) {
                    MessagesService.showMessage(message + " success.");
                } else {
                    MessagesService.showMessage(message + " failure.");
                }

            } catch (SQLException ex) {
                Logger.getLogger(AccountManagerBean.class.getName()).log(Level.SEVERE, null, ex);
                MessagesService.showMessage("Failure.");
            }
        } else {
            MessagesService.showMessage("Failure.");
        }
        return null;
    }

    public boolean isPanelListAccountRoleStaff() {
        return panelListAccountRoleStaff;
    }

    public void setPanelListAccountRoleStaff(boolean panelListAccountRoleStaff) {
        AccountManagerBean.panelListAccountRoleStaff = panelListAccountRoleStaff;
    }

    public boolean isPanelListAccountRoleStudent() {
        return panelListAccountRoleStudent;
    }

    public void setPanelListAccountRoleStudent(boolean panelListAccountRoleStudent) {
        AccountManagerBean.panelListAccountRoleStudent = panelListAccountRoleStudent;
    }

    public boolean isPanelListAccountRoleStaffLocked() {
        return panelListAccountRoleStaffLocked;
    }

    public void setPanelListAccountRoleStaffLocked(boolean panelListAccountRoleStaffLocked) {
        AccountManagerBean.panelListAccountRoleStaffLocked = panelListAccountRoleStaffLocked;
    }

    public boolean isPanelListAccountRoleStudentLocked() {
        return panelListAccountRoleStudentLocked;
    }

    public void setPanelListAccountRoleStudentLocked(boolean panelListAccountRoleStudentLocked) {
        AccountManagerBean.panelListAccountRoleStudentLocked = panelListAccountRoleStudentLocked;
    }

    public ArrayList<Account> getListAccountRoleStaff() {
        try {
            listAccountRoleStaff = accountDAO.listAccountRoleStaff();
        } catch (Exception ex) {
            Logger.getLogger(AccountManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAccountRoleStaff;
    }

    public void setListAccountRoleStaff(ArrayList<Account> listAccountRoleStaff) {
        this.listAccountRoleStaff = listAccountRoleStaff;
    }

    public ArrayList<Account> getListAccountRoleStudent() {
        try {
            listAccountRoleStudent = accountDAO.listAccountRoleStudent();
        } catch (Exception ex) {
            Logger.getLogger(AccountManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAccountRoleStudent;
    }

    public void setListAccountRoleStudent(ArrayList<Account> listAccountRoleStudent) {
        this.listAccountRoleStudent = listAccountRoleStudent;
    }

    public ArrayList<Account> getListAccountRoleStaffLocked() {
        try {
            listAccountRoleStaffLocked = accountDAO.listAccountRoleStaffLocked();
        } catch (Exception ex) {
            Logger.getLogger(AccountManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAccountRoleStaffLocked;
    }

    public void setListAccountRoleStaffLocked(ArrayList<Account> listAccountRoleStaffLocked) {
        this.listAccountRoleStaffLocked = listAccountRoleStaffLocked;
    }

    public ArrayList<Account> getListAccountRoleStudentLocked() {
        try {
            listAccountRoleStudentLocked = accountDAO.listAccountRoleStudentLocked();
        } catch (Exception ex) {
            Logger.getLogger(AccountManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAccountRoleStudentLocked;
    }

    public void setListAccountRoleStudentLocked(ArrayList<Account> listAccountRoleStudentLocked) {
        this.listAccountRoleStudentLocked = listAccountRoleStudentLocked;
    }

    public String onRequestAllStudent(boolean flag) {
        this.setPanelListAccountRoleStaff(false);
        this.setPanelListAccountRoleStudent(flag);
        this.setPanelListAccountRoleStaffLocked(false);
        this.setPanelListAccountRoleStudentLocked(false);
        return "accountManager.jsf" + REDIRECT;
    }

    public String onRequestAllStaff(boolean flag) {
        this.setPanelListAccountRoleStaff(flag);
        this.setPanelListAccountRoleStudent(false);
        this.setPanelListAccountRoleStaffLocked(false);
        this.setPanelListAccountRoleStudentLocked(false);
        return "accountManager.jsf" + REDIRECT;
    }

    public String onRequestAllStudentLocked(boolean flag) {
        this.setPanelListAccountRoleStaff(false);
        this.setPanelListAccountRoleStudent(false);
        this.setPanelListAccountRoleStaffLocked(false);
        this.setPanelListAccountRoleStudentLocked(flag);
        return "accountManager.jsf" + REDIRECT;
    }

    public String onRequestAllStaffLocked(boolean flag) {
        this.setPanelListAccountRoleStaff(false);
        this.setPanelListAccountRoleStudent(false);
        this.setPanelListAccountRoleStaffLocked(flag);
        this.setPanelListAccountRoleStudentLocked(false);
        return "accountManager.jsf" + REDIRECT;
    }
}
