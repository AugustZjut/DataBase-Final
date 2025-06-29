package com.example.dbdemo.dao;

import com.example.dbdemo.bean.Jiaoshi;
import java.sql.*;
import java.util.*;

public class JiaoshiDAO {
    public List<Jiaoshi> findAll(Connection conn) throws SQLException {
        List<Jiaoshi> list = new ArrayList<>();
        String sql = "SELECT zyc_jsbh, zyc_jsxm, zyc_jsxb, zyc_jscsrq, zyc_jszc, zyc_jslxdh FROM zhouyc_jiaoshi";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Jiaoshi j = new Jiaoshi();
                j.setJsbh(rs.getString("zyc_jsbh"));
                j.setJsxmc(rs.getString("zyc_jsxm"));
                j.setJsxb(rs.getString("zyc_jsxb"));
                j.setJscsrq(rs.getDate("zyc_jscsrq"));
                j.setJszc(rs.getString("zyc_jszc"));
                j.setJslxdh(rs.getString("zyc_jslxdh"));
                list.add(j);
            }
        }
        return list;
    }

    public List<Jiaoshi> findByCondition(Connection conn, String jsbh, String jsxmc, String jszc) throws SQLException {
        List<Jiaoshi> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT zyc_jsbh, zyc_jsxm, zyc_jsxb, zyc_jscsrq, zyc_jszc, zyc_jslxdh FROM zhouyc_jiaoshi WHERE 1=1");
        if (jsbh != null && !jsbh.isEmpty()) {
            sql.append(" AND zyc_jsbh = ?");
        }
        if (jsxmc != null && !jsxmc.isEmpty()) {
            sql.append(" AND zyc_jsxm LIKE ?");
        }
        if (jszc != null && !jszc.isEmpty()) {
            sql.append(" AND zyc_jszc LIKE ?");
        }
        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (jsbh != null && !jsbh.isEmpty()) {
                ps.setString(idx++, jsbh);
            }
            if (jsxmc != null && !jsxmc.isEmpty()) {
                ps.setString(idx++, "%" + jsxmc + "%");
            }
            if (jszc != null && !jszc.isEmpty()) {
                ps.setString(idx++, "%" + jszc + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Jiaoshi j = new Jiaoshi();
                    j.setJsbh(rs.getString("zyc_jsbh"));
                    j.setJsxmc(rs.getString("zyc_jsxm"));
                    j.setJsxb(rs.getString("zyc_jsxb"));
                    j.setJscsrq(rs.getDate("zyc_jscsrq"));
                    j.setJszc(rs.getString("zyc_jszc"));
                    j.setJslxdh(rs.getString("zyc_jslxdh"));
                    list.add(j);
                }
            }
        }
        return list;
    }

    public Jiaoshi findByJsbh(Connection conn, String jsbh) throws SQLException {
        String sql = "SELECT zyc_jsbh, zyc_jsxm, zyc_jsxb, zyc_jscsrq, zyc_jszc, zyc_jslxdh FROM zhouyc_jiaoshi WHERE zyc_jsbh = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jsbh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Jiaoshi j = new Jiaoshi();
                    j.setJsbh(rs.getString("zyc_jsbh"));
                    j.setJsxmc(rs.getString("zyc_jsxm"));
                    j.setJsxb(rs.getString("zyc_jsxb"));
                    j.setJscsrq(rs.getDate("zyc_jscsrq"));
                    j.setJszc(rs.getString("zyc_jszc"));
                    j.setJslxdh(rs.getString("zyc_jslxdh"));
                    return j;
                }
            }
        }
        return null;
    }

    public int update(Connection conn, Jiaoshi j) throws SQLException {
        String sql = "UPDATE zhouyc_jiaoshi SET zyc_jsxm=?, zyc_jsxb=?, zyc_jszc=?, zyc_jslxdh=? WHERE zyc_jsbh=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getJsxmc());
            ps.setString(2, j.getJsxb());
            ps.setString(3, j.getJszc());
            ps.setString(4, j.getJslxdh());
            ps.setString(5, j.getJsbh());
            return ps.executeUpdate();
        }
    }

    public int deleteByJsbh(Connection conn, String jsbh) throws SQLException {
        String sql = "DELETE FROM zhouyc_jiaoshi WHERE zyc_jsbh = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jsbh);
            return ps.executeUpdate();
        }
    }

    public int insert(Connection conn, Jiaoshi j) throws SQLException {
        String sql = "INSERT INTO zhouyc_jiaoshi (zyc_jsbh, zyc_jsxm, zyc_jsxb, zyc_jscsrq, zyc_jszc, zyc_jslxdh) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getJsbh());
            ps.setString(2, j.getJsxmc());
            ps.setString(3, j.getJsxb());
            ps.setDate(4, j.getJscsrq());
            ps.setString(5, j.getJszc());
            ps.setString(6, j.getJslxdh());
            return ps.executeUpdate();
        }
    }
}
