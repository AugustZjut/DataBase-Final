package com.example.dbdemo.service;

import com.example.dbdemo.dao.XueshengDAO;
import com.example.dbdemo.dao.DiquDAO;
import com.example.dbdemo.util.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class AdminStatsService {
    public Map<String, Integer> getStudentCountByDiqu() {
        Map<String, Integer> map = new LinkedHashMap<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT d.zyc_dqmc, COUNT(x.zyc_xh) AS cnt FROM zhouyc_diqu d LEFT JOIN zhouyc_xuesheng x ON d.zyc_dqbh = x.zyc_syd GROUP BY d.zyc_dqmc ORDER BY cnt DESC, d.zyc_dqmc ASC";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("zyc_dqmc"), rs.getInt("cnt"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn);
        }
        return map;
    }
}
