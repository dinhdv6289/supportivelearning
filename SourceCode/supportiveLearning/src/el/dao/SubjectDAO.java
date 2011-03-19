/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.Subject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author TuyenPV
 */
public class SubjectDAO extends AbstractDAO<Subject>{

    @Override
    public int insert(Subject t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Subject t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Subject t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Subject> list() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Subject getObject(Subject t) throws Exception {
        Subject s = new Subject();
        Connection conn = null;
        String sql = "{call Sel_SubjectById (?)}";
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            cstmt.setInt(1, t.getId());
            cstmt = conn.prepareCall(sql);
            ResultSet rs = cstmt.executeQuery(sql);
            while (rs.next()) {             
                s.setId(rs.getInt("StaffId"));
                s.setName(rs.getString("StaffName"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return s;
    }

}
