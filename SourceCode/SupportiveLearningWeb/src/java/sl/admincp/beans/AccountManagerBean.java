/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.model.Account;
import el.model.Role;
import el.model.Staff;
import el.model.Student;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class AccountManagerBean extends UtilCheckLoginBean implements Serializable {

    private Account account;
    private Staff staff;
    private Student student;
    private Role role;
    
    /** Creates a new instance of AccountManagerBean */
    public AccountManagerBean() {
        super();
        account = new Account();
        staff = new Staff();
        student = new Student();
        role = new Role();
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

    public String insert() {
        
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
