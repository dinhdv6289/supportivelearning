/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;

import java.io.Serializable;

/**
 *
 * @author TuyenPV
 */
public class Statistics implements Serializable {

    private int totalStaff,
            staffHaveBatch,
            staffHaveNotBatch,
            accountOnline,
            totalBatch;

    public int getAccountOnline() {
        return accountOnline;
    }

    public void setAccountOnline(int accountOnline) {
        this.accountOnline = accountOnline;
    }

    public int getStaffHaveBatch() {
        return staffHaveBatch;
    }

    public void setStaffHaveBatch(int staffHaveBatch) {
        this.staffHaveBatch = staffHaveBatch;
    }

    public int getStaffHaveNotBatch() {
        return staffHaveNotBatch;
    }

    public void setStaffHaveNotBatch(int staffHaveNotBatch) {
        this.staffHaveNotBatch = staffHaveNotBatch;
    }

    public int getTotalBatch() {
        return totalBatch;
    }

    public void setTotalBatch(int totalBatch) {
        this.totalBatch = totalBatch;
    }

    public int getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(int totalStaff) {
        this.totalStaff = totalStaff;
    }
}
