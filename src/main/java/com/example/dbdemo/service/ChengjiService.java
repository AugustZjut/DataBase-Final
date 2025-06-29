package com.example.dbdemo.service;

import com.example.dbdemo.dao.ChengjiDAO;

public class ChengjiService {
    private ChengjiDAO chengjiDAO = new ChengjiDAO();

    /**
     * 教师录入/修改单条成绩
     * @param xh 学号
     * @param xq 学期
     * @param jxbbh 教学班编号
     * @param cj 成绩
     * @return 是否成功
     */
    public boolean updateScore(String xh, String xq, int jxbbh, java.math.BigDecimal cj) {
        return chengjiDAO.updateScore(xh, xq, jxbbh, cj);
    }
}
