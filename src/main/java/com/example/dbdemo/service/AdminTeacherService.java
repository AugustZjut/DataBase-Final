package com.example.dbdemo.service;

import com.example.dbdemo.dao.JiaoshiDAO;
import com.example.dbdemo.bean.Jiaoshi;
import com.example.dbdemo.util.DBUtil;
import com.example.dbdemo.dao.KechengDAO;
import com.example.dbdemo.bean.Kecheng;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AdminTeacherService {
    private JiaoshiDAO teacherDAO = new JiaoshiDAO();
    public List<Jiaoshi> listTeachers() {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return teacherDAO.findAll(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            DBUtil.close(conn);
        }
    }
    public List<Jiaoshi> listTeachers(String jsbh, String jsxmc, String jszc) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return teacherDAO.findByCondition(conn, jsbh, jsxmc, jszc);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            DBUtil.close(conn);
        }
    }
    public Jiaoshi getTeacherByJsbh(String jsbh) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return teacherDAO.findByJsbh(conn, jsbh);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.close(conn);
        }
    }
    public boolean updateTeacher(Jiaoshi j) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return teacherDAO.update(conn, j) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn);
        }
    }
    public boolean deleteTeacher(String jsbh) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return teacherDAO.deleteByJsbh(conn, jsbh) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn);
        }
    }
    public boolean addTeacher(Jiaoshi j) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return teacherDAO.insert(conn, j) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn);
        }
    }
    // 其他增删改查方法
}
