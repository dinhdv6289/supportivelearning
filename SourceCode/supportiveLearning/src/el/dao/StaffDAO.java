/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Account;
import el.model.Staff;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class StaffDAO extends AbstractDAO<Staff> {

    @Override
    public boolean insert(Staff staff) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Staff staff) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Staff staff) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Staff> list() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Staff getObject(Staff staff) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Staff getStaffByAccount(Account account) throws Exception{
        throw new UnsupportedOperationException("Not yet implemented");
    }

    

}
