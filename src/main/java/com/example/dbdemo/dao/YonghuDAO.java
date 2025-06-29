package com.example.dbdemo.dao;
import com.example.dbdemo.bean.Yonghu;
import com.example.dbdemo.util.DBUtil;
import java.sql.*;
import java.util.*;

public class YonghuDAO {
    public Yonghu findByZyc_zh(String zyc_zh) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Yonghu y = null;
        String sql = "SELECT zyc_yhbh, zyc_zh, zyc_mm, zyc_qx, zyc_cjsj, zyc_zt FROM zhouyc_users WHERE zyc_zh = ?";
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, zyc_zh);
            rs = ps.executeQuery();
            if (rs.next()) {
                y = new Yonghu();
                y.setZyc_yhbh(rs.getInt("zyc_yhbh"));
                y.setZyc_zh(rs.getString("zyc_zh"));
                y.setZyc_mm(rs.getString("zyc_mm"));
                y.setZyc_qx(rs.getString("zyc_qx"));
                y.setZyc_cjsj(rs.getTimestamp("zyc_cjsj"));
                y.setZyc_zt(rs.getString("zyc_zt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return y;
    }

    public List<Yonghu> findAll(Connection conn) throws SQLException {
        List<Yonghu> list = new ArrayList<>();
        String sql = "SELECT zyc_yhbh, zyc_zh, zyc_mm, zyc_qx, zyc_cjsj, zyc_zt FROM zhouyc_users";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Yonghu y = new Yonghu();
                y.setZyc_yhbh(rs.getInt("zyc_yhbh"));
                y.setZyc_zh(rs.getString("zyc_zh"));
                y.setZyc_mm(rs.getString("zyc_mm"));
                y.setZyc_qx(rs.getString("zyc_qx"));
                y.setZyc_cjsj(rs.getTimestamp("zyc_cjsj"));
                y.setZyc_zt(rs.getString("zyc_zt"));
                list.add(y);
            }
        }
        return list;
    }

    public List<Yonghu> findByCondition(Connection conn, String zh, String zt) throws SQLException {
        List<Yonghu> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT zyc_yhbh, zyc_zh, zyc_mm, zyc_qx, zyc_cjsj, zyc_zt FROM zhouyc_users WHERE 1=1");
        if (zh != null && !zh.isEmpty()) {
            sql.append(" AND zyc_zh = ?");
        }
        if (zt != null && !zt.isEmpty()) {
            sql.append(" AND zyc_zt = ?");
        }
        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (zh != null && !zh.isEmpty()) {
                ps.setString(idx++, zh);
            }
            if (zt != null && !zt.isEmpty()) {
                ps.setString(idx++, zt);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Yonghu y = new Yonghu();
                    y.setZyc_yhbh(rs.getInt("zyc_yhbh"));
                    y.setZyc_zh(rs.getString("zyc_zh"));
                    y.setZyc_mm(rs.getString("zyc_mm"));
                    y.setZyc_qx(rs.getString("zyc_qx"));
                    y.setZyc_cjsj(rs.getTimestamp("zyc_cjsj"));
                    y.setZyc_zt(rs.getString("zyc_zt"));
                    list.add(y);
                }
            }
        }
        return list;
    }

    public int updateStatusByZh(Connection conn, String zh, String zt) throws SQLException {
        String sql = "UPDATE zhouyc_users SET zyc_zt=? WHERE zyc_zh=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, zt);
            ps.setString(2, zh);
            return ps.executeUpdate();
        }
    }
    // 可继续添加insert/update/delete等方法
}
