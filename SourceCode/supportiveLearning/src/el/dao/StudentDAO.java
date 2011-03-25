/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Batch;
import el.model.Role;
import el.model.Student;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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

    }

    @Override
    public int insert(Student student) throws Exception {
        return 0;
    }

    public int insertStudent(Student student, int accountId) throws Exception {
        String sql = "{call Ins_Student (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, student.getBatch().getId());
            stmt.setInt(2, student.getCourse().getId());
            stmt.setString(3, student.getName());
            stmt.setString(4, student.getAddress());
            stmt.setString(5, student.getEmail());
            stmt.setString(6, student.getPhone());
            stmt.setString(7, student.getUserName());
            stmt.setString(8, student.getPassword());
            stmt.setDate(9, (Date) student.getBirthDay());
            stmt.setBoolean(10, student.getGender());
            stmt.setDate(11, (Date) student.getDateCreate());
            stmt.setString(12, student.getRollNumber());

            // Phai them trong store proc 1 truong la AccountId
            stmt.setInt(13, accountId);
            a = stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return a;
    }

    @Override
    public boolean update(Student student) throws Exception {
        String sql = "{call Udp_StudentById (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, student.getId());
            stmt.setInt(2, student.getBatch().getId());
            stmt.setInt(3, student.getCourse().getId());
            stmt.setString(4, student.getName());
            stmt.setString(5, student.getAddress());
            stmt.setString(6, student.getEmail());
            stmt.setString(7, student.getPhone());
            stmt.setString(8, student.getUserName());
            stmt.setString(9, student.getPassword());
            stmt.setDate(10, (Date) student.getBirthDay());
            stmt.setBoolean(11, student.getGender());
            stmt.setDate(12, (Date) student.getDateCreate());

            stmt.setString(13, student.getRollNumber());
            a = stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return a == 1 ? true : false;
    }

    @Override
    public boolean delete(Student student) throws Exception {
        String sql = "{call Del_StudentById (?)}";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, student.getId());
            a = stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return a == 1 ? true : false;
    }

    @Override
    public ArrayList<Student> list() throws Exception {
        ArrayList<Student> students = new ArrayList<Student>();
        Connection conn = null;
        String sql = "Sel_Students";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rsstudents = stmt.executeQuery(sql);
            while (rsstudents.next()) {
                RoleDAO roleDAO = new RoleDAO();
                Role roleSearch = new Role();
                roleSearch.setId(rsstudents.getInt("RoleId"));
                Role role = roleDAO.getObject(roleSearch);
                BatchDAO batchDAO = new BatchDAO();
                Batch batchSearch = new Batch();
                batchSearch.setId(rsstudents.getInt("BatchId"));
                Batch batch = batchDAO.getObject(batchSearch);
                Student student = new Student();
                student.setId(rsstudents.getInt("StudentId"));
                student.setName(rsstudents.getString("FullName"));
                student.setUserName(rsstudents.getString("UserName"));
                student.setPassword(rsstudents.getString("Password"));
                student.setDateCreate(rsstudents.getDate("DateCreation"));
                student.setRole(role);
                student.setBatch(batch);
                student.setBirthDay(rsstudents.getDate("BirthDay"));
                student.setGender(rsstudents.getBoolean("Gender"));
                student.setPhone(rsstudents.getString("Phone"));
                student.setEmail(rsstudents.getString("Email"));
                student.setAddress(rsstudents.getString("Address"));
                student.setRollNumber(rsstudents.getString("RollNumber"));
                students.add(student);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return students;
    }

    @Override
    public Student getObject(Student s) throws Exception {
        Student student = new Student();
        Connection conn = null;
        String sql = "{call Sel_StudentById (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, s.getId());
            ResultSet rsstudents = cstmt.executeQuery();
            while (rsstudents.next()) {
                RoleDAO roleDAO = new RoleDAO();
                Role roleSearch = new Role();
                roleSearch.setId(rsstudents.getInt("RoleId"));
                Role role = roleDAO.getObject(roleSearch);
                BatchDAO batchDAO = new BatchDAO();
                Batch batchSearch = new Batch();
                batchSearch.setId(rsstudents.getInt("BatchId"));
                Batch batch = batchDAO.getObject(batchSearch);
                student.setId(rsstudents.getInt("StudentId"));
                student.setName(rsstudents.getString("FullName"));
                student.setUserName(rsstudents.getString("UserName"));
                student.setPassword(rsstudents.getString("Password"));
                student.setDateCreate(rsstudents.getDate("DateCreation"));
                student.setRole(role);
                student.setBatch(batch);
                student.setBirthDay(rsstudents.getDate("BirthDay"));
                student.setGender(rsstudents.getBoolean("Gender"));
                student.setPhone(rsstudents.getString("Phone"));
                student.setEmail(rsstudents.getString("Email"));
                student.setAddress(rsstudents.getString("Address"));
                student.setRollNumber(rsstudents.getString("RollNumber"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return student;
    }

    public ArrayList<Student> getStudentsByBatch(Batch b) throws Exception {
        ArrayList<Student> students = new ArrayList<Student>();
        Connection conn = null;
        String sql = "{call Sel_StudentsByBatchId (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, b.getId());
            ResultSet rsstudents = cstmt.executeQuery();
            while (rsstudents.next()) {
                RoleDAO roleDAO = new RoleDAO();
                Role roleSearch = new Role();
                roleSearch.setId(rsstudents.getInt("RoleId"));
                Role role = roleDAO.getObject(roleSearch);
                BatchDAO batchDAO = new BatchDAO();
                Batch batchSearch = new Batch();
                batchSearch.setId(rsstudents.getInt("BatchId"));
                Batch batch = batchDAO.getObject(batchSearch);
                Student student = new Student();
                student.setId(rsstudents.getInt("StudentId"));
                student.setName(rsstudents.getString("FullName"));
                student.setUserName(rsstudents.getString("UserName"));
                student.setPassword(rsstudents.getString("Password"));
                student.setDateCreate(rsstudents.getDate("DateCreation"));
                student.setRole(role);
                student.setBatch(batch);
                student.setBirthDay(rsstudents.getDate("BirthDay"));
                student.setGender(rsstudents.getBoolean("Gender"));
                student.setPhone(rsstudents.getString("Phone"));
                student.setEmail(rsstudents.getString("Email"));
                student.setAddress(rsstudents.getString("Address"));
                student.setRollNumber(rsstudents.getString("RollNumber"));
                students.add(student);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return students;
    }

    public Student getStudentByAccountId(int accountId) throws Exception {
        Student student = new Student();
        Connection conn = null;
        String sql = "{call Sel_StudentByAccountId (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, accountId);
            ResultSet rsstudents = cstmt.executeQuery();
            if (rsstudents.next()) {
                RoleDAO roleDAO = new RoleDAO();
                Role roleSearch = new Role();
                roleSearch.setId(rsstudents.getInt("RoleId"));
                Role role = roleDAO.getObject(roleSearch);
                BatchDAO batchDAO = new BatchDAO();
                Batch batchSearch = new Batch();
                batchSearch.setId(rsstudents.getInt("BatchId"));
                Batch batch = batchDAO.getObject(batchSearch);
                student.setId(rsstudents.getInt("StudentId"));
                student.setName(rsstudents.getString("FullName"));
                student.setUserName(rsstudents.getString("UserName"));
                student.setPassword(rsstudents.getString("Password"));
                student.setDateCreate(rsstudents.getDate("DateCreation"));
                student.setRole(role);
                student.setBatch(batch);
                student.setBirthDay(rsstudents.getDate("BirthDay"));
                student.setGender(rsstudents.getBoolean("Gender"));
                student.setPhone(rsstudents.getString("Phone"));
                student.setEmail(rsstudents.getString("Email"));
                student.setAddress(rsstudents.getString("Address"));
                student.setRollNumber(rsstudents.getString("RollNumber"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return student;
    }
}
