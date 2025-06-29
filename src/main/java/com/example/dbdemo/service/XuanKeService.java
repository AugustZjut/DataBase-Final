package com.example.dbdemo.service;

import com.example.dbdemo.dao.ChengjiDAO;
import com.example.dbdemo.bean.Chengji;
import java.util.List;

public class XuanKeService {
    private ChengjiDAO chengjiDAO = new ChengjiDAO();

    // 学生选课
    public boolean selectCourse(String xh, String xq, int jxbbh) {
        List<Chengji> selected = chengjiDAO.findAll();
        for (Chengji cj : selected) {
            if (cj.getZyc_xh().equals(xh) && cj.getZyc_xq().equals(xq) && cj.getZyc_jxbbh() == jxbbh) {
                return false; // 已选过
            }
        }
        return chengjiDAO.insertEmptyScore(xh, xq, jxbbh);
    }

    // 退选
    public boolean withdrawCourse(String xh, String xq, int jxbbh) {
        return chengjiDAO.deleteScore(xh, xq, jxbbh);
    }
}
