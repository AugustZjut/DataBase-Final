package com.example.dbdemo.service;

import com.example.dbdemo.dao.XueshengDAO;
import com.example.dbdemo.dao.DiquDAO;
import com.example.dbdemo.dao.XingzhengbanDAO;
import com.example.dbdemo.bean.Xuesheng;
import com.example.dbdemo.util.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AdminStudentService {
    private XueshengDAO studentDAO = new XueshengDAO();
    private DiquDAO diquDAO = new DiquDAO();
    private XingzhengbanDAO xingzhengbanDAO = new XingzhengbanDAO();
    public List<Xuesheng> listStudents() {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            List<Xuesheng> list = studentDAO.findAll();
            for (Xuesheng x : list) {
                x.setZyc_syd_name(diquDAO.getNameById(conn, x.getZyc_syd()));
                x.setZyc_bjmc(xingzhengbanDAO.getNameById(conn, x.getZyc_bjbh()));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            DBUtil.close(conn);
        }
    }
    public List<Xuesheng> listStudents(String xh, String xsxm, String bjmc) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            List<Xuesheng> list = studentDAO.findByCondition(xh, xsxm, bjmc);
            for (Xuesheng x : list) {
                x.setZyc_syd_name(diquDAO.getNameById(conn, x.getZyc_syd()));
                x.setZyc_bjmc(xingzhengbanDAO.getNameById(conn, x.getZyc_bjbh()));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            DBUtil.close(conn);
        }
    }
    public boolean addStudent(Xuesheng x) {
        return studentDAO.insert(x) > 0;
    }
    public boolean deleteStudent(String xh) {
        return studentDAO.deleteByXh(xh) > 0;
    }
    public Xuesheng getStudentByXh(String xh) {
        return studentDAO.findByXh(xh);
    }
    public boolean updateStudent(Xuesheng x) {
        return studentDAO.update(x) > 0;
    }
    // 其他增删改查方法
}
