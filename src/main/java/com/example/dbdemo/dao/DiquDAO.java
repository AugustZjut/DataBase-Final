package com.example.dbdemo.dao;

import com.example.dbdemo.bean.Diqu;
import java.sql.*;
import java.util.*;

public class DiquDAO {
    public List<Diqu> findAll(Connection conn) throws SQLException {
        List<Diqu> list = new ArrayList<>();
        String sql = "SELECT zyc_dqbh, zyc_dqmc FROM zhouyc_diqu";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Diqu d = new Diqu();
                d.setDqbh(rs.getInt("zyc_dqbh"));
                d.setDqmc(rs.getString("zyc_dqmc"));
                list.add(d);
            }
        }
        return list;
    }

    public String getNameById(Connection conn, int dqbh) throws SQLException {
        String sql = "SELECT zyc_dqmc FROM zhouyc_diqu WHERE zyc_dqbh = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dqbh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("zyc_dqmc");
                }
            }
        }
        return null;
    }

    public Integer getIdByName(Connection conn, String dqmc) throws SQLException {
        String sql = "SELECT zyc_dqbh FROM zhouyc_diqu WHERE zyc_dqmc = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dqmc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("zyc_dqbh");
                }
            }
        }
        return null;
    }
}
