package com.example.dbdemo.dao;

import com.example.dbdemo.bean.Zhuanye;
import java.sql.*;
import java.util.*;

public class ZhuanyeDAO {
    public List<Zhuanye> findAll(Connection conn) throws SQLException {
        List<Zhuanye> list = new ArrayList<>();
        String sql = "SELECT zyc_zybh, zyc_zymc FROM zhouyc_zhuanye";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Zhuanye z = new Zhuanye();
                z.setZybh(rs.getInt("zyc_zybh"));
                z.setZymc(rs.getString("zyc_zymc"));
                list.add(z);
            }
        }
        return list;
    }
}
