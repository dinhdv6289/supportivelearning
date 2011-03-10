/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Student;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class StudentDAO extends AbstractDAO<Student> {

    //private SchoolData schoolData = new SchoolData();
    public StudentDAO() {

        SchoolData.getListStudents();
    }

    @Override
    public boolean insert(Student student) throws Exception {
        String sql = "";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, student.getClazz().getId());
            ps.setInt(2, student.getCourse().getId());
            ps.setString(3, student.getName());
            ps.setString(4, student.getAddress());
            ps.setString(5, student.getEmail());
            ps.setString(6, student.getPhone());
            ps.setString(7, student.getUserName());
            ps.setString(8, student.getPassword());
            ps.setDate(9, (Date) student.getBirthDay());
            ps.setBoolean(10, student.getGender());
            ps.setDate(11, (Date) student.getDateCreate());
            a = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return true ? a == 1 : false;
    }

    @Override
    public boolean update(Student t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Student t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Student> list() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
//        StudentDAO studentDAO = new StudentDAO();
//        ArrayList<Student> list = studentDAO.list();
//        if (list != null) {
//            for (int i = 0; i < list.size(); i++) {
//                System.out.println("course:" + list.get(i).getCourse().getName());
//            }
//
//        }
    }
}
