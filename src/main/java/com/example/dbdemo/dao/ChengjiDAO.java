package com.example.dbdemo.dao;
import com.example.dbdemo.bean.Chengji;
import com.example.dbdemo.util.DBUtil;
import java.sql.*;
import java.util.*;

public class ChengjiDAO {
    public List<Chengji> findAll() {
        List<Chengji> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT zyc_xh, zyc_xq, zyc_jxbbh, zyc_cj FROM zhouyc_sc";
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Chengji c = new Chengji();
                c.setZyc_xh(rs.getString("zyc_xh"));
                c.setZyc_xq(rs.getString("zyc_xq"));
                c.setZyc_jxbbh(rs.getInt("zyc_jxbbh"));
                c.setZyc_cj(rs.getBigDecimal("zyc_cj"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }
    // 学生选课：插入空成绩记录
    public boolean insertEmptyScore(String xh, String xq, int jxbbh) {
        String sql = "INSERT INTO zhouyc_sc (zyc_xh, zyc_xq, zyc_jxbbh, zyc_cj) VALUES (?, ?, ?, NULL)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, xh);
            ps.setString(2, xq);
            ps.setInt(3, jxbbh);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 退选：删除成绩记录
    public boolean deleteScore(String xh, String xq, int jxbbh) {
        String sql = "DELETE FROM zhouyc_sc WHERE zyc_xh = ? AND zyc_xq = ? AND zyc_jxbbh = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, xh);
            ps.setString(2, xq);
            ps.setInt(3, jxbbh);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 教师录入/修改单条成绩
    public boolean updateScore(String xh, String xq, int jxbbh, java.math.BigDecimal cj) {
        String sql = "UPDATE zhouyc_sc SET zyc_cj = ? WHERE zyc_xh = ? AND zyc_xq = ? AND zyc_jxbbh = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, cj);
            ps.setString(2, xh);
            ps.setString(3, xq);
            ps.setInt(4, jxbbh);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 可继续添加insert/update/delete等方法
}
