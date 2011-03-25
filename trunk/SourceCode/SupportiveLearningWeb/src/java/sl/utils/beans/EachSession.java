/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.utils.beans;

import el.dao.AccountDAO;
import el.dao.StaffDAO;
import el.dao.StudentDAO;
import el.model.Account;

/**
 *
 * @author DINHDV
 */
public class EachSession {

    private static StudentDAO studentDAO = new StudentDAO();
    private static StaffDAO staffDAO = new StaffDAO();
    private static AccountDAO accountDAO = new AccountDAO();
    //private static AdminDAO adminDAO = new AdminDAO();
    //private static Admi

    public static Object getObjectFromSession(String key) throws Exception {
        Object value = null;
        if (key.length() != 0 || key != null) {
            int accountId = Integer.valueOf(SessionManager.getSession(key).toString());
            if (accountId != 0) {
                Account account = accountDAO.getAccountById(accountId);
                if (account != null) {
                    String role = account.getRole().getName();
                    if (role.equals("Admin")) {
                        // value = adminDAO.getAdminByAccountId(account);
                    } else if (role.equals("Staff")) {
                        value = staffDAO.getStaffByAccountId(account.getId());
                    } else {
                        value = studentDAO.getStudentByAccountId(account.getId());
                    }
                }

            }
        }
        return value;

    }
}
