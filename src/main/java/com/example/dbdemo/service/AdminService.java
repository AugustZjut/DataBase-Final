package com.example.dbdemo.service;

import com.example.dbdemo.dao.JiaoshiDAO;
import com.example.dbdemo.dao.KechengDAO;
import com.example.dbdemo.dao.XueshengDAO;
import com.example.dbdemo.dao.YonghuDAO;
import com.example.dbdemo.bean.Yonghu;
import com.example.dbdemo.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminService {
    private final XueshengDAO xueshengDAO = new XueshengDAO();
    private final JiaoshiDAO jiaoshiDAO = new JiaoshiDAO();
    private final KechengDAO kechengDAO = new KechengDAO();
    private final YonghuDAO yonghuDAO = new YonghuDAO();

    public Map<String, Integer> getSystemStats() {
        Map<String, Integer> stats = new HashMap<>();
        try (Connection conn = DBUtil.getConnection()) {
            stats.put("studentCount", xueshengDAO.findAll().size());
            stats.put("teacherCount", jiaoshiDAO.findAll(conn).size());
            stats.put("courseCount", kechengDAO.findAll().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    public List<Yonghu> getUsers(String zyc_zh, String zyc_qx) {
        // 需实现YonghuDAO的相关查询方法，这里仅返回全部用户
        return Collections.emptyList();
    }

    public Map<String, Integer> getStudentCountByDiqu() {
        Map<String, Integer> map = new java.util.LinkedHashMap<>();
        try (java.sql.Connection conn = com.example.dbdemo.util.DBUtil.getConnection()) {
            String sql = "SELECT d.zyc_dqmc, COUNT(x.zyc_xh) AS cnt FROM zhouyc_diqu d LEFT JOIN zhouyc_xuesheng x ON d.zyc_dqbh = x.zyc_syd GROUP BY d.zyc_dqmc ORDER BY cnt DESC, d.zyc_dqmc ASC";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("zyc_dqmc"), rs.getInt("cnt"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
