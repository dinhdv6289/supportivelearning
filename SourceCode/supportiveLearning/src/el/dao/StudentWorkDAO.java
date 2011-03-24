/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Assignment;
import el.model.Student;
import el.model.StudentWork;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class StudentWorkDAO extends AbstractDAO<StudentWork> {

    @Override
    public int insert(StudentWork studentWork) throws Exception {
        int a = 0;
        Connection conn = null;
        String sql = "{call Ins_StudentWork (?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, studentWork.getStudent().getId());
            cstmt.setInt(2, studentWork.getAssignment().getId());
            cstmt.setString(3, studentWork.getFileUpload());
            cstmt.setFloat(4, studentWork.getMark());
            cstmt.setDate(5, date2sql(studentWork.getDateUpload()));

            a = cstmt.executeUpdate();

             }
        finally {
            if (conn != null) {
                conn.close();
            }
        }
        return a;
    }

    @Override
    public boolean update(StudentWork studentWork) throws Exception {
        int a = 0;
        Connection conn = null;
        String sql = "{call Upd_StudentWork (?, ?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, studentWork.getStudent().getId());
            cstmt.setInt(2, studentWork.getAssignment().getId());
            cstmt.setString(3, studentWork.getFileUpload());
            cstmt.setFloat(4, studentWork.getMark());
            cstmt.setDate(5, date2sql(studentWork.getDateUpload()));
            cstmt.setInt(6, studentWork.getId());
            
            a = cstmt.executeUpdate();

             }
        finally {
            if (conn != null) {
                conn.close();
            }
        }
        return a == 1 ? true : false;
    }

    @Override
    public boolean delete(StudentWork studentWork) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<StudentWork> list() throws Exception {
        Connection conn = null;
        ArrayList<StudentWork> studentWorks = new ArrayList<StudentWork>();
        String sql = "{call Sel_StudentWorks}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                StudentWork studentWork = new StudentWork();
                studentWork.setId(rs.getInt("StudentWorkId"));
                AssignmentDAO assignmentDAO = new AssignmentDAO();
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment = assignmentDAO.getObject(assignment);
                studentWork.setAssignment(assignment);
                studentWork.setDateUpload(sql2date(rs.getDate("DateUpload")));
                studentWork.setFileUpload(rs.getString("FileUpload"));
                studentWork.setMark(rs.getFloat("Mark"));
                StudentDAO studentDAO = new StudentDAO();
                Student student = new Student();
                student.setId(rs.getInt("StudentId"));
                student = studentDAO.getObject(student);
                studentWork.setStudent(student);
                studentWorks.add(studentWork);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return studentWorks;
    }

    public ArrayList<StudentWork> listStudentWorkByStudentId(int studentId) throws Exception {
        Connection conn = null;
        ArrayList<StudentWork> studentWorks = new ArrayList<StudentWork>();
        String sql = "{call Sel_StudentWorkByByStudentId (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, studentId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                StudentWork studentWork = new StudentWork();
                studentWork.setId(rs.getInt("StudentWorkId"));
                AssignmentDAO assignmentDAO = new AssignmentDAO();
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment = assignmentDAO.getObject(assignment);
                studentWork.setAssignment(assignment);
                studentWork.setDateUpload(sql2date(rs.getDate("DateUpload")));
                studentWork.setFileUpload(rs.getString("FileUpload"));
                studentWork.setMark(rs.getFloat("Mark"));
                StudentDAO studentDAO = new StudentDAO();
                Student student = new Student();
                student.setId(rs.getInt("StudentId"));
                student = studentDAO.getObject(student);
                studentWork.setStudent(student);
                studentWorks.add(studentWork);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return studentWorks;

    }

    @Override
    public StudentWork getObject(StudentWork studentWork) throws Exception {
        Connection conn = null;
        String sql = "{call Sel_StudentWorkByById (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, studentWork.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {

                studentWork.setId(rs.getInt("StudentWorkId"));
                AssignmentDAO assignmentDAO = new AssignmentDAO();
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment = assignmentDAO.getObject(assignment);
                studentWork.setAssignment(assignment);
                studentWork.setDateUpload(sql2date(rs.getDate("DateUpload")));
                studentWork.setFileUpload(rs.getString("FileUpload"));
                studentWork.setMark(rs.getFloat("Mark"));
                StudentDAO studentDAO = new StudentDAO();
                Student student = new Student();
                student.setId(rs.getInt("StudentId"));
                student = studentDAO.getObject(student);
                studentWork.setStudent(student);

            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return studentWork;
    }

    public ArrayList<StudentWork> listStudentWorkByRollNumber(String rollNumber) throws Exception{
        Connection conn = null;
        ArrayList<StudentWork> studentWorks = new ArrayList<StudentWork>();
        String sql = "{call Sel_StudentWorkByRollNumber (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, rollNumber);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                StudentWork studentWork = new StudentWork();
                studentWork.setId(rs.getInt("StudentWorkId"));
                AssignmentDAO assignmentDAO = new AssignmentDAO();
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment = assignmentDAO.getObject(assignment);
                studentWork.setAssignment(assignment);
                studentWork.setDateUpload(sql2date(rs.getDate("DateUpload")));
                studentWork.setFileUpload(rs.getString("FileUpload"));
                studentWork.setMark(rs.getFloat("Mark"));
                StudentDAO studentDAO = new StudentDAO();
                Student student = new Student();
                student.setId(rs.getInt("StudentId"));
                student = studentDAO.getObject(student);
                studentWork.setStudent(student);
                studentWorks.add(studentWork);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return studentWorks;
    }
}
