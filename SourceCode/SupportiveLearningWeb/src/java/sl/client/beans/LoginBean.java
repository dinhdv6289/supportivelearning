/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.AccountDAO;
import el.dao.StaffDAO;
import el.dao.StudentDAO;
import el.model.Account;
import el.model.Batch;
import el.model.Staff;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import sl.utils.beans.SessionManager;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private Account account = new Account();
    private Student student = new Student();
    private static boolean panelLogin = true;
    private static boolean panelHi = false;
    private static boolean panelStudent = true;
    private static boolean panelStaff = false;
    private AccountDAO accountDAO = new AccountDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private StaffDAO staffDAO = new StaffDAO();
    private ArrayList<Batch> listBatchs = new ArrayList<Batch>();
    private static String pageRequest = "index.jsf";
    private String redirect = "?faces-redirect=true";

    /** Creates a new instance of LoginBean */
    public LoginBean() {
    }

    public String getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(String pageRequest) {
        LoginBean.pageRequest = pageRequest;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public ArrayList<Batch> getListBatchs() {
        return listBatchs;
    }

    public void setListBatchs(ArrayList<Batch> listBatchs) {
        this.listBatchs = listBatchs;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        String result = "";
        if (account != null) {
            try {
                Account accountLogin = accountDAO.getObject(account);
                if (accountLogin.getId() > 0) {
                    account = accountLogin;
                    setPanelHi(true);
                    setPanelLogin(false);
                    SessionManager.setSession("accountId", accountLogin.getId());
                    updateStatusOnline(true, accountLogin.getId());
                    if (accountLogin.getRole().getName().equals("Admin")) {
                        response.sendRedirect("../ui.admin/index.jsf");
                    } else if (accountLogin.getRole().getName().equals("Staff")) {
                        loadListBatchs(accountLogin.getId());
                        setPanelStaff(true);
                        setPanelStudent(false);
                        response.sendRedirect("../ui.staff/index.jsf");
                    } else {
                        setPanelStaff(false);
                        setPanelStudent(true);
                        getStudentByAccount(accountLogin.getId());
                    }
                    result = this.getPageRequest();
                }
            } catch (Exception ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                result = "";
            }
        }
        return result + redirect;
    }

    public String logout() {
        setPanelHi(false);
        setPanelLogin(true);
        setPanelStaff(false);
        setPanelStudent(true);
        this.setPageRequest("");
        int accountId = Integer.valueOf(SessionManager.getSession("accountId").toString());
        updateStatusOnline(false, accountId);
        SessionManager.invalidate("accountId");
        return "/ui.client/index.jsf" + redirect;
    }

    private void getStudentByAccount(int accountId) {
        try {
            this.student = studentDAO.getStudentByAccountId(accountId);
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadListBatchs(int accountId) {
        try {
            Staff staff = staffDAO.getStaffByAccountId(accountId);
            if (staff.getId() != 0) {
                this.listBatchs = staffDAO.getListBatchsByStaffId(staff.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateStatusOnline(boolean status, int accountId) {
        try {
            accountDAO.setAccountOnline(accountId, status);
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
