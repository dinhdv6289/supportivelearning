/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.Account;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class AccountDAO extends AbstractDAO<Account>{

    @Override
    public boolean insert(Account account) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Account account) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Account account) throws Exception {
        return false;
    }

    @Override
    public ArrayList<Account> list() throws Exception {
        return null;
    }

    @Override
    public Account getObject(Account account) throws Exception {
        return null;
    }

}
