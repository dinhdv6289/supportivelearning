/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.StudentWork;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class StudentWorkDAO extends AbstractDAO<StudentWork> {

    @Override
    public int insert(StudentWork studentWork) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(StudentWork studentWork) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(StudentWork studentWork) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<StudentWork> list() throws Exception {
        return null;
    }

    public ArrayList<StudentWork> listStudentWorkByStudentId(int studentId) throws Exception {
        return null;
    }

    @Override
    public StudentWork getObject(StudentWork studentWork) throws Exception {
        return null;
    }

    public ArrayList<StudentWork> listStudentWorkByRollNumber(String rollNumber) throws Exception{
        return null;
    }
}
