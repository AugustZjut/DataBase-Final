package com.example.dbdemo.dao;
import com.example.dbdemo.bean.Jiaoxueban;
import com.example.dbdemo.util.DBUtil;
import java.sql.*;
import java.util.*;

public class JiaoxuebanDAO {
    public List<Jiaoxueban> findAll() {
        List<Jiaoxueban> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT zyc_jxbbh, zyc_jxbmc, zyc_sksj, zyc_skdd, zyc_kcbh, zyc_jsbh, zyc_xdrs FROM zhouyc_jiaoxueban";
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Jiaoxueban j = new Jiaoxueban();
                j.setZyc_jxbbh(rs.getInt("zyc_jxbbh"));
                j.setZyc_jxbmc(rs.getString("zyc_jxbmc"));
                j.setZyc_sksj(rs.getString("zyc_sksj"));
                j.setZyc_skdd(rs.getString("zyc_skdd"));
                j.setZyc_kcbh(rs.getInt("zyc_kcbh"));
                j.setZyc_jsbh(rs.getString("zyc_jsbh"));
                j.setZyc_xdrs(rs.getInt("zyc_xdrs"));
                list.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public List<Jiaoxueban> findByCondition(String jxbbh, String kcmc, String xq) {
        List<Jiaoxueban> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder("SELECT j.*, k.zyc_kcmc, k.zyc_kkxq FROM zhouyc_jiaoxueban j JOIN zhouyc_kecheng k ON j.zyc_kcbh = k.zyc_kcbh WHERE 1=1");
        if (jxbbh != null && !jxbbh.isEmpty()) {
            sql.append(" AND j.zyc_jxbbh = ?");
        }
        if (kcmc != null && !kcmc.isEmpty()) {
            sql.append(" AND k.zyc_kcmc LIKE ?");
        }
        if (xq != null && !xq.isEmpty()) {
            sql.append(" AND k.zyc_kkxq LIKE ?");
        }
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (jxbbh != null && !jxbbh.isEmpty()) {
                ps.setInt(idx++, Integer.parseInt(jxbbh));
            }
            if (kcmc != null && !kcmc.isEmpty()) {
                ps.setString(idx++, "%" + kcmc + "%");
            }
            if (xq != null && !xq.isEmpty()) {
                ps.setString(idx++, "%" + xq + "%");
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Jiaoxueban j = new Jiaoxueban();
                j.setZyc_jxbbh(rs.getInt("zyc_jxbbh"));
                j.setZyc_jxbmc(rs.getString("zyc_jxbmc"));
                j.setZyc_sksj(rs.getString("zyc_sksj"));
                j.setZyc_skdd(rs.getString("zyc_skdd"));
                j.setZyc_kcbh(rs.getInt("zyc_kcbh"));
                j.setZyc_jsbh(rs.getString("zyc_jsbh"));
                j.setZyc_xdrs(rs.getInt("zyc_xdrs"));
                // 课程名和学期可通过Service层补全
                list.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public int insertJiaoxueban(Jiaoxueban j) {
        String sql = "INSERT INTO zhouyc_jiaoxueban (zyc_jxbmc, zyc_sksj, zyc_skdd, zyc_kcbh, zyc_jsbh, zyc_xdrs) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getZyc_jxbmc());
            ps.setString(2, j.getZyc_sksj());
            ps.setString(3, j.getZyc_skdd());
            ps.setInt(4, j.getZyc_kcbh());
            ps.setString(5, j.getZyc_jsbh());
            ps.setInt(6, j.getZyc_xdrs());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateJiaoxueban(Jiaoxueban j) {
        String sql = "UPDATE zhouyc_jiaoxueban SET zyc_jxbmc=?, zyc_sksj=?, zyc_skdd=?, zyc_kcbh=?, zyc_jsbh=?, zyc_xdrs=? WHERE zyc_jxbbh=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getZyc_jxbmc());
            ps.setString(2, j.getZyc_sksj());
            ps.setString(3, j.getZyc_skdd());
            ps.setInt(4, j.getZyc_kcbh());
            ps.setString(5, j.getZyc_jsbh());
            ps.setInt(6, j.getZyc_xdrs());
            ps.setInt(7, j.getZyc_jxbbh());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteJiaoxueban(int jxbbh) {
        String sql = "DELETE FROM zhouyc_jiaoxueban WHERE zyc_jxbbh=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jxbbh);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Jiaoxueban findById(int jxbbh) {
        String sql = "SELECT zyc_jxbbh, zyc_jxbmc, zyc_sksj, zyc_skdd, zyc_kcbh, zyc_jsbh, zyc_xdrs FROM zhouyc_jiaoxueban WHERE zyc_jxbbh=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jxbbh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Jiaoxueban j = new Jiaoxueban();
                    j.setZyc_jxbbh(rs.getInt("zyc_jxbbh"));
                    j.setZyc_jxbmc(rs.getString("zyc_jxbmc"));
                    j.setZyc_sksj(rs.getString("zyc_sksj"));
                    j.setZyc_skdd(rs.getString("zyc_skdd"));
                    j.setZyc_kcbh(rs.getInt("zyc_kcbh"));
                    j.setZyc_jsbh(rs.getString("zyc_jsbh"));
                    j.setZyc_xdrs(rs.getInt("zyc_xdrs"));
                    return j;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 教师端：按教师ID、学期、课程名称模糊查询教学班
     */
    public List<Jiaoxueban> findByTeacherAndCondition(String jsbh, String xq, String kcmc) {
        List<Jiaoxueban> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT j.*, k.zyc_kcmc, k.zyc_kkxq FROM zhouyc_jiaoxueban j JOIN zhouyc_kecheng k ON j.zyc_kcbh = k.zyc_kcbh WHERE j.zyc_jsbh = ?";
        if (xq != null && !xq.isEmpty()) {
            sql += " AND k.zyc_kkxq LIKE ?";
        }
        if (kcmc != null && !kcmc.isEmpty()) {
            sql += " AND k.zyc_kcmc LIKE ?";
        }
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            int idx = 1;
            ps.setString(idx++, jsbh);
            if (xq != null && !xq.isEmpty()) {
                ps.setString(idx++, "%" + xq + "%");
            }
            if (kcmc != null && !kcmc.isEmpty()) {
                ps.setString(idx++, "%" + kcmc + "%");
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Jiaoxueban j = new Jiaoxueban();
                j.setZyc_jxbbh(rs.getInt("zyc_jxbbh"));
                j.setZyc_jxbmc(rs.getString("zyc_jxbmc"));
                j.setZyc_sksj(rs.getString("zyc_sksj"));
                j.setZyc_skdd(rs.getString("zyc_skdd"));
                j.setZyc_kcbh(rs.getInt("zyc_kcbh"));
                j.setZyc_jsbh(rs.getString("zyc_jsbh"));
                j.setZyc_xdrs(rs.getInt("zyc_xdrs"));
                // 可扩展：j.setKcmc(rs.getString("zyc_kcmc"));
                list.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }
    // 可继续添加其他方法
}
