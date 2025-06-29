package com.example.dbdemo.dao;
import com.example.dbdemo.bean.Kecheng;
import com.example.dbdemo.util.DBUtil;
import java.sql.*;
import java.util.*;

public class KechengDAO {
    public List<Kecheng> findAll() {
        List<Kecheng> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT zyc_kcbh, zyc_kcmc, zyc_kkxq, zyc_xs, zyc_ksfs, zyc_xf FROM zhouyc_kecheng";
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Kecheng k = new Kecheng();
                k.setZyc_kcbh(rs.getInt("zyc_kcbh"));
                k.setZyc_kcmc(rs.getString("zyc_kcmc"));
                k.setZyc_kkxq(rs.getString("zyc_kkxq"));
                k.setZyc_xs(rs.getInt("zyc_xs"));
                k.setZyc_ksfs(rs.getString("zyc_ksfs"));
                k.setZyc_xf(rs.getBigDecimal("zyc_xf"));
                list.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }
    // 可继续添加insert/update/delete等方法

    public String getNameById(Connection conn, int kcbh) throws SQLException {
        String sql = "SELECT zyc_kcmc FROM zhouyc_kecheng WHERE zyc_kcbh = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kcbh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("zyc_kcmc");
                }
            }
        }
        return null;
    }

    public String getXueqiById(Connection conn, int kcbh) throws SQLException {
        String sql = "SELECT zyc_kkxq FROM zhouyc_kecheng WHERE zyc_kcbh = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kcbh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("zyc_kkxq");
                }
            }
        }
        return null;
    }

    public List<Kecheng> findByCondition(String kcbh, String kcmc, String kkxq) {
        List<Kecheng> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder("SELECT zyc_kcbh, zyc_kcmc, zyc_kkxq, zyc_xs, zyc_ksfs, zyc_xf FROM zhouyc_kecheng WHERE 1=1");
        if (kcbh != null && !kcbh.isEmpty()) {
            sql.append(" AND zyc_kcbh = ?");
        }
        if (kcmc != null && !kcmc.isEmpty()) {
            sql.append(" AND zyc_kcmc LIKE ?");
        }
        if (kkxq != null && !kkxq.isEmpty()) {
            sql.append(" AND zyc_kkxq LIKE ?");
        }
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (kcbh != null && !kcbh.isEmpty()) {
                ps.setInt(idx++, Integer.parseInt(kcbh));
            }
            if (kcmc != null && !kcmc.isEmpty()) {
                ps.setString(idx++, "%" + kcmc + "%");
            }
            if (kkxq != null && !kkxq.isEmpty()) {
                ps.setString(idx++, "%" + kkxq + "%");
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Kecheng k = new Kecheng();
                k.setZyc_kcbh(rs.getInt("zyc_kcbh"));
                k.setZyc_kcmc(rs.getString("zyc_kcmc"));
                k.setZyc_kkxq(rs.getString("zyc_kkxq"));
                k.setZyc_xs(rs.getInt("zyc_xs"));
                k.setZyc_ksfs(rs.getString("zyc_ksfs"));
                k.setZyc_xf(rs.getBigDecimal("zyc_xf"));
                list.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public int insertKecheng(Kecheng k) {
        String sql = "INSERT INTO zhouyc_kecheng (zyc_kcmc, zyc_kkxq, zyc_xs, zyc_ksfs, zyc_xf) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, k.getZyc_kcmc());
            ps.setString(2, k.getZyc_kkxq());
            ps.setInt(3, k.getZyc_xs());
            ps.setString(4, k.getZyc_ksfs());
            ps.setBigDecimal(5, k.getZyc_xf());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateKecheng(Kecheng k) {
        String sql = "UPDATE zhouyc_kecheng SET zyc_kcmc=?, zyc_kkxq=?, zyc_xs=?, zyc_ksfs=?, zyc_xf=? WHERE zyc_kcbh=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, k.getZyc_kcmc());
            ps.setString(2, k.getZyc_kkxq());
            ps.setInt(3, k.getZyc_xs());
            ps.setString(4, k.getZyc_ksfs());
            ps.setBigDecimal(5, k.getZyc_xf());
            ps.setInt(6, k.getZyc_kcbh());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteKecheng(int kcbh) {
        String sql = "DELETE FROM zhouyc_kecheng WHERE zyc_kcbh=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kcbh);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Kecheng findById(int kcbh) {
        String sql = "SELECT zyc_kcbh, zyc_kcmc, zyc_kkxq, zyc_xs, zyc_ksfs, zyc_xf FROM zhouyc_kecheng WHERE zyc_kcbh=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kcbh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Kecheng k = new Kecheng();
                    k.setZyc_kcbh(rs.getInt("zyc_kcbh"));
                    k.setZyc_kcmc(rs.getString("zyc_kcmc"));
                    k.setZyc_kkxq(rs.getString("zyc_kkxq"));
                    k.setZyc_xs(rs.getInt("zyc_xs"));
                    k.setZyc_ksfs(rs.getString("zyc_ksfs"));
                    k.setZyc_xf(rs.getBigDecimal("zyc_xf"));
                    return k;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
