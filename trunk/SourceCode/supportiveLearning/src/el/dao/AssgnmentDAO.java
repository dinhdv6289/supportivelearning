/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Assignment;
import el.model.Staff;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class AssgnmentDAO extends AbstractDAO<Assignment> {

    @Override
    public boolean insert(Assignment t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Assignment t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Assignment t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Assignment> list() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Assignment getObject(Assignment t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Assignment> getListAssignmentsByStaff(Staff staff) throws Exception{
        
        return null;
    }
    
}
