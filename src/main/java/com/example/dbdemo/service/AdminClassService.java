package com.example.dbdemo.service;

import com.example.dbdemo.dao.JiaoxuebanDAO;
import com.example.dbdemo.dao.KechengDAO;
import com.example.dbdemo.bean.Jiaoxueban;
import com.example.dbdemo.util.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminClassService {
    private JiaoxuebanDAO classDAO = new JiaoxuebanDAO();
    private KechengDAO kechengDAO = new KechengDAO();
    public List<Map<String, Object>> listClasses() {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            List<Jiaoxueban> list = classDAO.findAll();
            List<Map<String, Object>> result = new ArrayList<>();
            for (Jiaoxueban jxb : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("jxb", jxb);
                map.put("kcmc", kechengDAO.getNameById(conn, jxb.getZyc_kcbh()));
                map.put("xueqi", kechengDAO.getXueqiById(conn, jxb.getZyc_kcbh()));
                result.add(map);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            DBUtil.close(conn);
        }
    }
    public List<Map<String, Object>> listClasses(String jxbbh, String kcmc, String xq) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            List<Jiaoxueban> list = classDAO.findByCondition(jxbbh, kcmc, xq);
            List<Map<String, Object>> result = new ArrayList<>();
            for (Jiaoxueban jxb : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("jxb", jxb);
                map.put("kcmc", kechengDAO.getNameById(conn, jxb.getZyc_kcbh()));
                map.put("xueqi", kechengDAO.getXueqiById(conn, jxb.getZyc_kcbh()));
                result.add(map);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            DBUtil.close(conn);
        }
    }
    public int addClass(Jiaoxueban j) {
        return classDAO.insertJiaoxueban(j);
    }
    public int updateClass(Jiaoxueban j) {
        return classDAO.updateJiaoxueban(j);
    }
    public int deleteClass(int jxbbh) {
        return classDAO.deleteJiaoxueban(jxbbh);
    }
    public Jiaoxueban getClassById(int jxbbh) {
        return classDAO.findById(jxbbh);
    }
    // 其他增删改查方法
    public List<com.example.dbdemo.bean.Jiaoshi> getAllTeachers() {
        java.sql.Connection conn = null;
        try {
            conn = com.example.dbdemo.util.DBUtil.getConnection();
            com.example.dbdemo.dao.JiaoshiDAO dao = new com.example.dbdemo.dao.JiaoshiDAO();
            return dao.findAll(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        } finally {
            com.example.dbdemo.util.DBUtil.close(conn);
        }
    }
}
