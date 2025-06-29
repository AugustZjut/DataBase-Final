package com.example.dbdemo.service;

import com.example.dbdemo.dao.YonghuDAO;
import com.example.dbdemo.bean.Yonghu;
import com.example.dbdemo.util.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AdminUserService {
    private YonghuDAO userDAO = new YonghuDAO();
    public List<Yonghu> listUsers() {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return userDAO.findAll(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            DBUtil.close(conn);
        }
    }
    public List<Yonghu> listUsers(String zh, String zt) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return userDAO.findByCondition(conn, zh, zt);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            DBUtil.close(conn);
        }
    }
    public boolean enableUser(String zh) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return userDAO.updateStatusByZh(conn, zh, "正常") > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn);
        }
    }
    public boolean disableUser(String zh) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return userDAO.updateStatusByZh(conn, zh, "禁用") > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn);
        }
    }
    public boolean resetPassword(String zh, String newPwd) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE zhouyc_users SET zyc_mm=? WHERE zyc_zh=?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPwd);
            ps.setString(2, zh);
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn);
        }
    }
    // 其他增删改查方法
}
