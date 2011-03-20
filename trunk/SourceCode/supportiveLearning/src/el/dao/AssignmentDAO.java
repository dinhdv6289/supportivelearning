/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Assignment;
import el.model.Batch;
import el.model.Staff;
import el.model.Subject;
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
        return null;
    }

    @Override
    public Assignment getObject(Assignment t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
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
                subject = subjectDAO.getObject(subject);
                assignment.setSubject(subject);
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
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

    public ArrayList<Assignment> getListAssignmentsByBatchId(int batchId) throws Exception {
         ArrayList<Assignment> assignments = new ArrayList<Assignment>();

        Connection conn = null;
        String sql = "{call Sel_AssignmentsByBacthId (?)}";
        try {
            conn = getConnection();
            CallableStatement cstmt = null;
            
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1,batchId);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("AssignmentId"));
                assignment.setName(rs.getString("AssignmentName"));
                assignment.setFileUpload(rs.getString("AssignmentFile"));
                assignment.setStartDate(rs.getDate("StartData"));
                assignment.setEndDate(rs.getDate("EndDate"));
                StaffDAO staffDAO = new StaffDAO();
                Staff s = new Staff();
                s.setId(rs.getInt("StaffId"));
                s = staffDAO.getObject(s);
                assignment.setStaff(s);
                SubjectDAO subjectDAO = new SubjectDAO();
                Subject subject = new Subject();
                subject = subjectDAO.getObject(subject);
                assignment.setSubject(subject);
                BatchDAO batchDAO = new BatchDAO();
                Batch batch = new Batch();
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
