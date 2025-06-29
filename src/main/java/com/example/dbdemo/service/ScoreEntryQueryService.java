package com.example.dbdemo.service;

import com.example.dbdemo.util.DBUtil;
import java.sql.*;
import java.util.*;

/**
 * 用于教师端成绩录入页面，查询某教学班所有学生及成绩
 */
public class ScoreEntryQueryService {
    public List<Map<String, Object>> getStudentScoreList(int jxbbh, String xq) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT s.zyc_xh AS xh, s.zyc_xsxm AS xm, k.zyc_kcmc AS kcm, c.zyc_jxbbh AS jxbbh, c.zyc_xq AS xq, c.zyc_cj AS cj " +
                "FROM zhouyc_sc c " +
                "JOIN zhouyc_xuesheng s ON c.zyc_xh = s.zyc_xh " +
                "JOIN zhouyc_jiaoxueban j ON c.zyc_jxbbh = j.zyc_jxbbh " +
                "JOIN zhouyc_kecheng k ON j.zyc_kcbh = k.zyc_kcbh " +
                "WHERE c.zyc_jxbbh = ? AND c.zyc_xq = ? ";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jxbbh);
            ps.setString(2, xq);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("xh", rs.getString("xh"));
                    map.put("xm", rs.getString("xm"));
                    map.put("kcm", rs.getString("kcm"));
                    map.put("jxbbh", rs.getInt("jxbbh"));
                    map.put("xq", rs.getString("xq"));
                    map.put("cj", rs.getBigDecimal("cj"));
                    list.add(map);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
