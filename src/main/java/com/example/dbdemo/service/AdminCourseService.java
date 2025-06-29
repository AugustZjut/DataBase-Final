package com.example.dbdemo.service;

import com.example.dbdemo.dao.KechengDAO;
import com.example.dbdemo.bean.Kecheng;
import java.util.List;

public class AdminCourseService {
    private KechengDAO courseDAO = new KechengDAO();
    public List<Kecheng> listCourses() {
        return courseDAO.findAll();
    }
    public List<Kecheng> listCourses(String kcbh, String kcmc, String kkxq) {
        return courseDAO.findByCondition(kcbh, kcmc, kkxq);
    }

    public int addCourse(Kecheng k) {
        return courseDAO.insertKecheng(k);
    }
    public int updateCourse(Kecheng k) {
        return courseDAO.updateKecheng(k);
    }
    public int deleteCourse(int kcbh) {
        return courseDAO.deleteKecheng(kcbh);
    }
    public Kecheng getCourseById(int kcbh) {
        return courseDAO.findById(kcbh);
    }
}
