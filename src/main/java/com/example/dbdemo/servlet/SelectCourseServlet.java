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
        if (jxbbhStr == null) {
            System.out.println("[选课] 表单未提交jxbbh参数");
        }
//         System.out.println("[选课] xq参数=" + xq + "，jxbbh=" + jxbbhStr + "，action=" + action);
        if (xh != null && jxbbhStr != null) {
            int jxbbh = Integer.parseInt(jxbbhStr);
            boolean ok;
            if ("withdraw".equals(action)) {
                ok = xuanKeService.withdrawCourse(xh, xq, jxbbh);
                msg = ok ? "退选成功！" : "退选失败！";
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
