/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Account;
import el.model.Batch;
import el.model.ChangeLearning;
import el.model.Role;
import el.model.Student;
import el.utility.Utility;
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

    public int[] insertStudent(Student student) throws Exception {
        int[] result = new int[2];
        String sql = "{call Ins_Student (?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.registerOutParameter(8, java.sql.Types.INTEGER);
            stmt.registerOutParameter(9, java.sql.Types.INTEGER);
            stmt.setString(1, student.getUserName());
            stmt.setString(2, student.getName());
            stmt.setDate(3, Utility.date2sql(student.getBirthDay()));
            stmt.setBoolean(4, student.getGender());
            stmt.setString(5, student.getPhone());
            stmt.setString(6, student.getEmail());
            stmt.setString(7, student.getAddress());

            stmt.execute();
            result[0] = stmt.getInt(8);
            result[1] = stmt.getInt(9);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    @Override
    public boolean update(Student student) throws Exception {
        String sql = "{call Udp_StudentById (?, ?, ?, ?, ?, ?, ?)}";
        Connection conn = null;
        int a = 0;
        try {
            conn = getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, student.getId());
            stmt.setString(2, student.getName());
            stmt.setDate(3, Utility.date2sql(student.getBirthDay()));
            stmt.setBoolean(4, student.getGender());
            stmt.setString(5, student.getPhone());
            stmt.setString(6, student.getEmail());
            stmt.setString(7, student.getAddress());
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

    public ArrayList<Student> getStudentsIsLock() throws Exception {
        ArrayList<Student> students = new ArrayList<Student>();
        Connection conn = null;
        String sql = "Sel_StudentsIsLock";
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

    public ArrayList<Student> getStudentsIsNotHaveBatch() throws Exception {
        ArrayList<Student> students = new ArrayList<Student>();
        Connection conn = null;
        String sql = "{call Sel_StudentForAddToBatch}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            ResultSet rsstudents = cstmt.executeQuery();
            while (rsstudents.next()) {
                Student student = new Student();
                student.setId(rsstudents.getInt("StudentId"));
                student.setName(rsstudents.getString("FullName"));
                student.setDateCreate(rsstudents.getDate("DateCreation"));
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

    public boolean updateBatchToStudent(int studentId, int batchId) throws Exception {
        String sql = "{call Upd_BatchToStudent (?, ?)}";
        Connection conn = null;
        int status = 0;
        try {
            conn = getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, studentId);
            stmt.setInt(2, batchId);
            status = stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return status == 1 ? true : false;
    }

    public ArrayList<Student> getStudentsHaveBatch() throws Exception {
        ArrayList<Student> students = new ArrayList<Student>();
        Connection conn = null;
        String sql = "{call Sel_AllStudentHaveBatch}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            ResultSet rsstudents = cstmt.executeQuery();
            while (rsstudents.next()) {
                Student student = new Student();
                student.setId(rsstudents.getInt("StudentId"));
                student.setName(rsstudents.getString("FullName"));
                student.setDateCreate(rsstudents.getDate("DateCreation"));
                student.setBirthDay(rsstudents.getDate("BirthDay"));
                student.setGender(rsstudents.getBoolean("Gender"));
                student.setPhone(rsstudents.getString("Phone"));
                student.setEmail(rsstudents.getString("Email"));
                student.setAddress(rsstudents.getString("Address"));
                student.setRollNumber(rsstudents.getString("RollNumber"));
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
                batch.setId(rsstudents.getInt("BatchId"));
                batch = batchDAO.getObject(batch);
                student.setBatch(batch);
                students.add(student);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return students;
    }

    public int changeLearning(ChangeLearning changeLearning) throws Exception {
        String sql = "{call Ins_ChangeLearning (?, ?, ?)}";
        Connection conn = null;
        int result = 0;
        try {
            conn = getConnection();
            CallableStatement ps = conn.prepareCall(sql);
            ps.setInt(1, changeLearning.getStudent().getId());
            ps.setInt(2, changeLearning.getBatch().getId());
            ps.setString(3, changeLearning.getReason());
            result = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public ArrayList<Student> getAllStudents() throws Exception {
        ArrayList<Student> students = new ArrayList<Student>();
        Connection conn = null;
        String sql = "{call Sel_AllStudent}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            ResultSet rsstudents = cstmt.executeQuery();
            while (rsstudents.next()) {
                Student student = new Student();
                student.setId(rsstudents.getInt("StudentId"));
                student.setName(rsstudents.getString("FullName"));
                student.setDateCreate(rsstudents.getDate("DateCreation"));
                student.setBirthDay(rsstudents.getDate("BirthDay"));
                student.setGender(rsstudents.getBoolean("Gender"));
                student.setPhone(rsstudents.getString("Phone"));
                student.setEmail(rsstudents.getString("Email"));
                student.setAddress(rsstudents.getString("Address"));
                student.setRollNumber(rsstudents.getString("RollNumber"));
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
                batch.setId(rsstudents.getInt("BatchId"));
                batch = batchDAO.getObject(batch);
                student.setBatch(batch);
                students.add(student);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return students;
    }
}
