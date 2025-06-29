package com.example.dbdemo.service;

import com.example.dbdemo.bean.Yonghu;
import com.example.dbdemo.dao.YonghuDAO;

public class UserService {
    private YonghuDAO yonghuDAO = new YonghuDAO();

    public Yonghu login(String zyc_zh, String zyc_mm) {
        Yonghu yonghu = yonghuDAO.findByZyc_zh(zyc_zh);
        if (yonghu != null && yonghu.getZyc_mm().equals(zyc_mm)) {
            if ("正常".equals(yonghu.getZyc_zt())) {
                return yonghu;
            }
        }
        return null;
    }

    public boolean updatePassword(String zh, String newPwd) {
        java.sql.Connection conn = null;
        try {
            conn = com.example.dbdemo.util.DBUtil.getConnection();
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
            com.example.dbdemo.util.DBUtil.close(conn);
        }
    }
}
