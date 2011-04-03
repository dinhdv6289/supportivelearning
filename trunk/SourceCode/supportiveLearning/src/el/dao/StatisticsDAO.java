/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.Statistics;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TuyenPV
 */
public class StatisticsDAO  extends AbstractDAO<Statistics>{

    @Override
    public int insert(Statistics t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Statistics t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Statistics t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Statistics> list() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Statistics getObject(Statistics t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Statistics getStatistics() throws Exception{
        Statistics statistics = new Statistics();
        Connection conn = null;
        String sql = "Sel_Statistics";
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                statistics.setAccountOnline(rs.getInt("AccountOnline"));
                statistics.setStaffHaveBatch(rs.getInt("StaffHaveBatch"));
                statistics.setStaffHaveNotBatch(rs.getInt("StaffHaveNotBatch"));
                statistics.setTotalBatch(rs.getInt("TotalBatch"));
                statistics.setTotalStaff(rs.getInt("TotalStaff"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return statistics;
    }
}
