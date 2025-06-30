package com.example.dbdemo.dao;
import com.example.dbdemo.bean.Xuesheng;
import com.example.dbdemo.util.DBUtil;
import java.sql.*;
import java.util.*;

public class XueshengDAO {
    public List<Xuesheng> findAll() {
        List<Xuesheng> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT zyc_xh, zyc_xsxm, zyc_xsxb, zyc_xscsrq, zyc_syd, zyc_yxxf, zyc_bjbh FROM zhouyc_xuesheng";
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Xuesheng x = new Xuesheng();
                x.setZyc_xh(rs.getString("zyc_xh"));
                x.setZyc_xsxm(rs.getString("zyc_xsxm"));
                x.setZyc_xsxb(rs.getString("zyc_xsxb"));
                x.setZyc_xscsrq(rs.getDate("zyc_xscsrq"));
                x.setZyc_syd(rs.getInt("zyc_syd"));
                x.setZyc_yxxf(rs.getBigDecimal("zyc_yxxf"));
                x.setZyc_bjbh(rs.getInt("zyc_bjbh"));
                list.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public List<Xuesheng> findByCondition(String xh, String xsxm, String bjmc) {
        List<Xuesheng> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder("SELECT x.*, b.zyc_xzbmc AS zyc_bjmc FROM zhouyc_xuesheng x LEFT JOIN zhouyc_xingzhengban b ON x.zyc_bjbh = b.zyc_xzbbh WHERE 1=1");
        if (xh != null && !xh.isEmpty()) {
            sql.append(" AND x.zyc_xh = ?");
        }
        if (xsxm != null && !xsxm.isEmpty()) {
            sql.append(" AND x.zyc_xsxm LIKE ?");
        }
        if (bjmc != null && !bjmc.isEmpty()) {
            sql.append(" AND b.zyc_xzbmc LIKE ?");
        }
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (xh != null && !xh.isEmpty()) {
                ps.setString(idx++, xh);
            }
            if (xsxm != null && !xsxm.isEmpty()) {
                ps.setString(idx++, "%" + xsxm + "%");
            }
            if (bjmc != null && !bjmc.isEmpty()) {
                ps.setString(idx++, "%" + bjmc + "%");
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Xuesheng x = new Xuesheng();
                x.setZyc_xh(rs.getString("zyc_xh"));
                x.setZyc_xsxm(rs.getString("zyc_xsxm"));
                x.setZyc_xsxb(rs.getString("zyc_xsxb"));
                x.setZyc_xscsrq(rs.getDate("zyc_xscsrq"));
                x.setZyc_syd(rs.getInt("zyc_syd"));
                x.setZyc_yxxf(rs.getBigDecimal("zyc_yxxf"));
                x.setZyc_bjbh(rs.getInt("zyc_bjbh"));
                x.setZyc_bjmc(rs.getString("zyc_bjmc"));
                list.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public int insert(Xuesheng x) {
        String sql = "INSERT INTO zhouyc_xuesheng (zyc_xh, zyc_xsxm, zyc_xsxb, zyc_xscsrq, zyc_syd, zyc_bjbh) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, x.getZyc_xh());
            ps.setString(2, x.getZyc_xsxm());
            ps.setString(3, x.getZyc_xsxb());
            ps.setDate(4, x.getZyc_xscsrq());
            ps.setInt(5, x.getZyc_syd());
            ps.setInt(6, x.getZyc_bjbh());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteByXh(String xh) {
        String sql = "DELETE FROM zhouyc_xuesheng WHERE zyc_xh = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, xh);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Xuesheng findByXh(String xh) {
        String sql = "SELECT * FROM zhouyc_xuesheng WHERE zyc_xh = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, xh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Xuesheng x = new Xuesheng();
                    x.setZyc_xh(rs.getString("zyc_xh"));
                    x.setZyc_xsxm(rs.getString("zyc_xsxm"));
                    x.setZyc_xsxb(rs.getString("zyc_xsxb"));
                    x.setZyc_xscsrq(rs.getDate("zyc_xscsrq"));
                    x.setZyc_syd(rs.getInt("zyc_syd"));
                    x.setZyc_yxxf(rs.getBigDecimal("zyc_yxxf"));
                    x.setZyc_bjbh(rs.getInt("zyc_bjbh"));
                    return x;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int update(Xuesheng x) {
        String sql = "UPDATE zhouyc_xuesheng SET zyc_xsxm=?, zyc_xsxb=?, zyc_xscsrq=?, zyc_syd=?, zyc_bjbh=? WHERE zyc_xh=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, x.getZyc_xsxm());
            ps.setString(2, x.getZyc_xsxb());
            ps.setDate(3, x.getZyc_xscsrq());
            ps.setInt(4, x.getZyc_syd());
            ps.setInt(5, x.getZyc_bjbh());
            ps.setString(6, x.getZyc_xh());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取学生平均绩点（用教学班编号zyc_jxbbh关联课程表）
     */
    public Double getAvgGPA(String xh) throws SQLException {
        String sql = "SELECT SUM(" +
                "CASE " +
                "WHEN zyc_cj >= 90 THEN 4.0 " +
                "WHEN zyc_cj >= 80 THEN 3.0 " +
                "WHEN zyc_cj >= 70 THEN 2.0 " +
                "WHEN zyc_cj >= 60 THEN 1.0 " +
                "ELSE 0 END * COALESCE(kc.zyc_xf,0)) / NULLIF(SUM(COALESCE(kc.zyc_xf,0)),0) AS avg_gpa " +
                "FROM zhouyc_sc sc " +
                "JOIN zhouyc_jiaoxueban jxb ON sc.zyc_jxbbh = jxb.zyc_jxbbh " +
                "JOIN zhouyc_kecheng kc ON jxb.zyc_kcbh = kc.zyc_kcbh " +
                "WHERE sc.zyc_xh = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, xh);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Object obj = rs.getObject("avg_gpa");
                return obj == null ? null : rs.getDouble("avg_gpa");
            }
        }
        return null;
    }
    // 可继续添加update/delete等方法
}
