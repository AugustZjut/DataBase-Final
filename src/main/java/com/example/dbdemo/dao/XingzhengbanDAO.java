package com.example.dbdemo.dao;

import com.example.dbdemo.bean.Xingzhengban;
import java.sql.*;
import java.util.*;

public class XingzhengbanDAO {
    public List<Xingzhengban> findAll(Connection conn) throws SQLException {
        List<Xingzhengban> list = new ArrayList<>();
        String sql = "SELECT zyc_xzbbh, zyc_xzbmc, zyc_zybh FROM zhouyc_xingzhengban";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Xingzhengban x = new Xingzhengban();
                x.setXzbbh(rs.getInt("zyc_xzbbh"));
                x.setXzbmc(rs.getString("zyc_xzbmc"));
                x.setZybh(rs.getInt("zyc_zybh"));
                list.add(x);
            }
        }
        return list;
    }

    public String getNameById(Connection conn, int xzbbh) throws SQLException {
        String sql = "SELECT zyc_xzbmc FROM zhouyc_xingzhengban WHERE zyc_xzbbh = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, xzbbh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("zyc_xzbmc");
                }
            }
        }
        return null;
    }
}
