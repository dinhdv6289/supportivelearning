/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.model.Account;
import el.model.Staff;
import el.model.Student;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class AccountManagerBean extends AbstractManagerBean<Account> implements Serializable {

    private Account account;
    private Staff staff;
    private Student student;

    /** Creates a new instance of AccountManagerBean */
    public AccountManagerBean() {
        account = new Account();
        staff = new Staff();
        student = new Student();
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

    @Override
    public String insert() {
        //trycatch
        return null;
    }

    @Override
    public String update() {
        //trycatch
        return null;
    }

    @Override
    public String delete() {

        return null;
    }
}
