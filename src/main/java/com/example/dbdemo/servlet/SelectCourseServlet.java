package com.example.dbdemo.servlet;

import com.example.dbdemo.service.XuanKeService;
import com.example.dbdemo.util.ConfigUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/selectCourse")
public class SelectCourseServlet extends HttpServlet {
    private XuanKeService xuanKeService = new XuanKeService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        com.example.dbdemo.bean.Yonghu yonghu = (com.example.dbdemo.bean.Yonghu) req.getSession().getAttribute("user");
        String xh = (yonghu != null) ? yonghu.getZyc_zh() : null;
        String xq = req.getParameter("xq");
        if (xq == null || xq.isEmpty()) {
            xq = com.example.dbdemo.util.ConfigUtil.getCurrentSemester();
        }
        String jxbbhStr = req.getParameter("jxbbh");
        String action = req.getParameter("action");
        String msg;
        if (xh == null) {
            System.out.println("[选课] session中user为空或未登录，xh为null");
        }
        if (jxbbhStr == null || jxbbhStr.isEmpty()) {
            System.out.println("[选课] 表单未提交jxbbh参数或jxbbh为空字符串");
        }
        // System.out.println("[选课] xq参数=" + xq + "，jxbbh=" + jxbbhStr + "，action=" + action);
        if (xh != null && jxbbhStr != null && !jxbbhStr.isEmpty()) {
            int jxbbh = Integer.parseInt(jxbbhStr);
            boolean ok;
            if ("withdraw".equals(action)) {
                ok = xuanKeService.withdrawCourse(xh, xq, jxbbh);
                if (ok) {
                    msg = "退选成功！";
                } else {
                    // 判断是否因为已评分
                    // 再查一次成绩，给出更明确提示
                    com.example.dbdemo.dao.ChengjiDAO chengjiDAO = new com.example.dbdemo.dao.ChengjiDAO();
                    java.util.List<com.example.dbdemo.bean.Chengji> list = chengjiDAO.findAll();
                    boolean hasScore = false;
                    for (com.example.dbdemo.bean.Chengji cj : list) {
                        if (cj.getZyc_xh().equals(xh) && cj.getZyc_xq().equals(xq) && cj.getZyc_jxbbh() == jxbbh && cj.getZyc_cj() != null) {
                            hasScore = true;
                            break;
                        }
                    }
                    if (hasScore) {
                        msg = "已评分课程不允许退选！";
                    } else {
                        msg = "退选失败！";
                    }
                }
            } else {
                ok = xuanKeService.selectCourse(xh, xq, jxbbh);
                msg = ok ? "选课成功！" : "选课失败或已选过该课！";
            }
        } else {
            msg = "参数错误";
        }
        resp.sendRedirect(req.getContextPath() + "/student/dashboard?msg=" + java.net.URLEncoder.encode(msg, "UTF-8"));
    }
}
