/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Assignment;
import el.model.Batch;
import el.model.Staff;
import el.model.Subject;
import el.utility.Utility;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class AssignmentDAO extends AbstractDAO<Assignment> {

    @Override
    public int insert(Assignment t) throws Exception {

        Connection conn = null;
        int a = 0;
        String sql = "{call Ins_Assignment (?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getSubject().getId());
            cstmt.setInt(2, t.getStaff().getId());
            cstmt.setInt(3, t.getBatch().getId());
            cstmt.setString(4, t.getName());
            cstmt.setString(5, t.getFileUpload());
            cstmt.setString(6, t.getContent());
            cstmt.setDate(7, Utility.date2sql(t.getStartDate()));
            cstmt.setDate(8, Utility.date2sql(t.getEndDate()));

            a = cstmt.executeUpdate();
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
    public boolean update(Assignment t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Upd_Assignment (?, ?, ?, ?, ?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, t.getName());
            cstmt.setString(2, t.getFileUpload());
            cstmt.setString(3, t.getContent());
            cstmt.setDate(4, Utility.date2sql(t.getStartDate()));
            cstmt.setDate(5, Utility.date2sql(t.getEndDate()));
            cstmt.setInt(6, t.getId());
            a = cstmt.executeUpdate();
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
    public boolean delete(Assignment t) throws Exception {
        Connection conn = null;
        int a = 0;
        String sql = "{call Del_Assignment  (?, ?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());
            cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
            cstmt.execute();
            a = cstmt.getInt(2);
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
    public ArrayList<Assignment> list() throws Exception {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();

        Connection conn = null;
        String sql = "{call Sel_AllAssignments}";
        try {
            conn = getConnection();
            CallableStatement cstmt = null;
            cstmt = conn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment.setName(rs.getString("AssignmentName"));
                assignment.setContent(rs.getString("AssignmentContent"));
                assignment.setFileUpload(rs.getString("AssignmentFile"));
                assignment.setStartDate(rs.getDate("StartDate"));
                assignment.setEndDate(rs.getDate("EndDate"));
                StaffDAO staffDAO = new StaffDAO();
                Staff s = new Staff();
                s.setId(rs.getInt("StaffId"));
                s = staffDAO.getObject(s);
                assignment.setStaff(s);
                SubjectDAO subjectDAO = new SubjectDAO();
                Subject subject = new Subject();
                subject.setId(rs.getInt("SubjectId"));
                subject = subjectDAO.getObject(subject);
                assignment.setSubject(subject);
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
                batch.setId(rs.getInt("BatchId"));
                batch = batchDAO.getObject(batch);
                assignment.setBatch(batch);
                assignments.add(assignment);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return assignments;
    }

    @Override
    public Assignment getObject(Assignment t) throws Exception {
        Connection conn = null;
        String sql = "{call Sel_AssignmentsById (?)}";
        Assignment assignment = new Assignment();
        try {
            conn = getConnection();
            CallableStatement cstmt = null;
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, t.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {

                assignment.setId(rs.getInt("AssignmentId"));
                assignment.setName(rs.getString("AssignmentName"));
                assignment.setContent(rs.getString("AssignmentContent"));
                assignment.setFileUpload(rs.getString("AssignmentFile"));
                assignment.setStartDate(Utility.sql2date(rs.getDate("StartDate")));
                assignment.setEndDate(rs.getDate("EndDate"));
                StaffDAO staffDAO = new StaffDAO();
                Staff s = new Staff();
                s.setId(rs.getInt("StaffId"));
                s = staffDAO.getObject(s);
                assignment.setStaff(s);
                SubjectDAO subjectDAO = new SubjectDAO();
                Subject subject = new Subject();
                subject.setId(rs.getInt("SubjectId"));
                subject = subjectDAO.getObject(subject);
                assignment.setSubject(subject);
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
                batch = batchDAO.getObject(batch);
                assignment.setBatch(batch);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return assignment;
    }

    public ArrayList<Assignment> getListAssignmentsByStaff(Staff staff) throws Exception {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        Connection conn = null;
        String sql = "{call Sel_AssignmentsByStaffId (?)}";
        try {
            conn = getConnection();
            CallableStatement cstmt = null;
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, staff.getId());
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment.setName(rs.getString("AssignmentName"));
                assignment.setContent(rs.getString("AssignmentContent"));
                assignment.setFileUpload(rs.getString("AssignmentFile"));
                assignment.setStartDate(rs.getDate("StartDate"));
                assignment.setEndDate(rs.getDate("EndDate"));
                StaffDAO staffDAO = new StaffDAO();
                Staff s = new Staff();
                s.setId(rs.getInt("StaffId"));
                s = staffDAO.getObject(s);
                assignment.setStaff(s);
                SubjectDAO subjectDAO = new SubjectDAO();
                Subject subject = new Subject();
                subject.setId(rs.getInt("SubjectId"));
                subject = subjectDAO.getObject(subject);
                assignment.setSubject(subject);
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
                batch.setId(rs.getInt("BatchId"));
                batch = batchDAO.getObject(batch);
                assignment.setBatch(batch);
                assignments.add(assignment);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return assignments;
    }

//    public ArrayList<Assignment> getListAssignmentsByBatchId(int batchId) throws Exception {
//        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
//
//        Connection conn = null;
//        String sql = "{call Sel_AssignmentsByBacthId (?)}";
//        try {
//            conn = getConnection();
//            CallableStatement cstmt = null;
//
//            cstmt = conn.prepareCall(sql);
//            cstmt.setInt(1, batchId);
//            ResultSet rs = cstmt.executeQuery();
//            while (rs.next()) {
//                Assignment assignment = new Assignment();
//                assignment.setId(rs.getInt("AssignmentId"));
//                assignment.setName(rs.getString("AssignmentName"));
//                assignment.setContent(rs.getString("AssignmentContent"));
//                assignment.setFileUpload(rs.getString("AssignmentFile"));
//                assignment.setStartDate(rs.getDate("StartDate"));
//                assignment.setEndDate(rs.getDate("EndDate"));
//                StaffDAO staffDAO = new StaffDAO();
//                Staff s = new Staff();
//                s.setId(rs.getInt("StaffId"));
//                s = staffDAO.getObject(s);
//                assignment.setStaff(s);
//                SubjectDAO subjectDAO = new SubjectDAO();
//                Subject subject = new Subject();
//                subject.setId(rs.getInt("SubjectId"));
//                subject = subjectDAO.getObject(subject);
//                assignment.setSubject(subject);
//                BatchDAO batchDAO = new BatchDAO();
//                Batch batch = new Batch();
//                batch.setId(rs.getInt("BatchId"));
//                batch = batchDAO.getObject(batch);
//                assignment.setBatch(batch);
//                assignments.add(assignment);
//            }
//        } finally {
//            if (conn != null) {
//                conn.close();
//            }
//        }
//
//        return assignments;
//    }
    public ArrayList<Assignment> getListAssignmentsByBatchId(int batchId) throws Exception {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();

        Connection conn = null;
        String sql = "{call Sel_AssignmentsByBacthIdOld (?)}";
        try {
            conn = getConnection();
            CallableStatement cstmt = null;

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, batchId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment.setName(rs.getString("AssignmentName"));
                assignment.setContent(rs.getString("AssignmentContent"));
                assignment.setFileUpload(rs.getString("AssignmentFile"));
                assignment.setStartDate(rs.getDate("StartDate"));
                assignment.setEndDate(rs.getDate("EndDate"));
                StaffDAO staffDAO = new StaffDAO();
                Staff s = new Staff();
                s.setId(rs.getInt("StaffId"));
                s = staffDAO.getObject(s);
                assignment.setStaff(s);
                SubjectDAO subjectDAO = new SubjectDAO();
                Subject subject = new Subject();
                subject.setId(rs.getInt("SubjectId"));
                subject = subjectDAO.getObject(subject);
                assignment.setSubject(subject);
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
                batch.setId(rs.getInt("BatchId"));
                batch = batchDAO.getObject(batch);
                assignment.setBatch(batch);
                assignments.add(assignment);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return assignments;
    }

    public ArrayList<Assignment> getListAssignmentsByBatchIdDueDate(int batchId) throws Exception {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();

        Connection conn = null;
        String sql = "{call Sel_AssignmentsByBacthIdDueDate (?)}";
        try {
            conn = getConnection();
            CallableStatement cstmt = null;

            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, batchId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment.setName(rs.getString("AssignmentName"));
                assignment.setContent(rs.getString("AssignmentContent"));
                assignment.setFileUpload(rs.getString("AssignmentFile"));
                assignment.setStartDate(rs.getDate("StartDate"));
                assignment.setEndDate(rs.getDate("EndDate"));
                StaffDAO staffDAO = new StaffDAO();
                Staff s = new Staff();
                s.setId(rs.getInt("StaffId"));
                s = staffDAO.getObject(s);
                assignment.setStaff(s);
                SubjectDAO subjectDAO = new SubjectDAO();
                Subject subject = new Subject();
                subject.setId(rs.getInt("SubjectId"));
                subject = subjectDAO.getObject(subject);
                assignment.setSubject(subject);
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
                batch.setId(rs.getInt("BatchId"));
                batch = batchDAO.getObject(batch);
                assignment.setBatch(batch);
                assignments.add(assignment);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return assignments;
    }

    public ArrayList<Assignment> getListAssignmentsDueDate(int staffId, int batchId) throws Exception {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        Connection conn = null;
        String sql = "{call Sel_AssignmentsDueDate(?,?)}";
        try {
            conn = getConnection();
            CallableStatement cstmt = null;
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, staffId);
            cstmt.setInt(2, batchId);

            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment.setName(rs.getString("AssignmentName"));
                assignment.setContent(rs.getString("AssignmentContent"));
                assignment.setFileUpload(rs.getString("AssignmentFile"));
                assignment.setStartDate(rs.getDate("StartDate"));
                assignment.setEndDate(rs.getDate("EndDate"));
                StaffDAO staffDAO = new StaffDAO();
                Staff s = new Staff();
                s.setId(rs.getInt("StaffId"));
                s = staffDAO.getObject(s);
                assignment.setStaff(s);
                SubjectDAO subjectDAO = new SubjectDAO();
                Subject subject = new Subject();
                subject.setId(rs.getInt("SubjectId"));
                subject = subjectDAO.getObject(subject);
                assignment.setSubject(subject);
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
                batch.setId(rs.getInt("BatchId"));
                batch = batchDAO.getObject(batch);
                assignment.setBatch(batch);
                assignments.add(assignment);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return assignments;
    }

    public ArrayList<Assignment> getListAssignmentsOld(int staffId, int batchId) throws Exception {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        Connection conn = null;
        String sql = "{call Sel_AssignmentsOld(?,?)}";
        try {
            conn = getConnection();
            CallableStatement cstmt = null;
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, staffId);
            cstmt.setInt(2, batchId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment.setName(rs.getString("AssignmentName"));
                assignment.setContent(rs.getString("AssignmentContent"));
                assignment.setFileUpload(rs.getString("AssignmentFile"));
                assignment.setStartDate(rs.getDate("StartDate"));
                assignment.setEndDate(rs.getDate("EndDate"));
                StaffDAO staffDAO = new StaffDAO();
                Staff s = new Staff();
                s.setId(rs.getInt("StaffId"));
                s = staffDAO.getObject(s);
                assignment.setStaff(s);
                SubjectDAO subjectDAO = new SubjectDAO();
                Subject subject = new Subject();
                subject.setId(rs.getInt("SubjectId"));
                subject = subjectDAO.getObject(subject);
                assignment.setSubject(subject);
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
                batch.setId(rs.getInt("BatchId"));
                batch = batchDAO.getObject(batch);
                assignment.setBatch(batch);
                assignments.add(assignment);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return assignments;
    }
}
