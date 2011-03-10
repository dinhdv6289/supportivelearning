/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Clazz;
import el.model.Role;
import el.model.Student;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
        String sql = "proc insert chua viet";
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
    public boolean update(Student student) throws Exception {
         String sql = "proc update chua viet";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, student.getId());
            ps.setInt(2, student.getClazz().getId());
            ps.setInt(3, student.getCourse().getId());
            ps.setString(4, student.getName());
            ps.setString(5, student.getAddress());
            ps.setString(6, student.getEmail());
            ps.setString(7, student.getPhone());
            ps.setString(8, student.getUserName());
            ps.setString(9, student.getPassword());
            ps.setDate(10, (Date) student.getBirthDay());
            ps.setBoolean(11, student.getGender());
            ps.setDate(12, (Date) student.getDateCreate());
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
    public boolean delete(Student student) throws Exception {
        String sql = "proc delete chua viet";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, student.getId());
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
    public ArrayList<Student> list() throws Exception {
        ArrayList<Student> students = new ArrayList<Student>();
        Connection conn = null;
        String sql = "Sel_Students";
        try {
            conn = getConnection(); // from BaseDAO
            Statement stmt = conn.createStatement();
            ResultSet rsstudents = stmt.executeQuery(sql);
            while (rsstudents.next()) {   
                RoleDAO roleDAO = new RoleDAO();
                Role role = roleDAO.getRoleById(rsstudents.getInt("RoleId"));
                ClazzDAO clazzDAO = new ClazzDAO();
                Clazz clazz = clazzDAO.getClazzById(rsstudents.getInt("ClazzId"));
                students.add(new Student(rsstudents.getInt("StudentId")
                        , rsstudents.getString("FullName")
                        , rsstudents.getString("UserName")
                        , rsstudents.getString("Password")
                        , rsstudents.getDate("DateCreation")
                        , role
                        , clazz
                        , rsstudents.getDate("BirthDay")
                        , rsstudents.getBoolean("Gender")
                        , rsstudents.getString("Phone")
                        , rsstudents.getString("Email")
                        , rsstudents.getString("Address")));

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return students;
    }

    public Student getStudentById() throws Exception {
        Student student = new Student();
        Connection conn = null;
        String sql = "Sel_StudentById ?";
        try {
            conn = getConnection(); // from BaseDAO
            Statement stmt = conn.createStatement();
            ResultSet rsstudents = stmt.executeQuery(sql);
            while (rsstudents.next()) {
                RoleDAO roleDAO = new RoleDAO();
                Role role = roleDAO.getRoleById(rsstudents.getInt("RoleId"));
                ClazzDAO clazzDAO = new ClazzDAO();
                Clazz clazz = clazzDAO.getClazzById(rsstudents.getInt("ClazzId"));
                student  = new Student(rsstudents.getInt("StudentId")
                        , rsstudents.getString("FullName")
                        , rsstudents.getString("UserName")
                        , rsstudents.getString("Password")
                        , rsstudents.getDate("DateCreation")
                        , role
                        , clazz
                        , rsstudents.getDate("BirthDay")
                        , rsstudents.getBoolean("Gender")
                        , rsstudents.getString("Phone")
                        , rsstudents.getString("Email")
                        , rsstudents.getString("Address"));

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return student;
    }
}
