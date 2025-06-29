package com.example.dbdemo.admin;

import com.example.dbdemo.service.AdminCourseService;
import com.example.dbdemo.bean.Kecheng;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/course")
public class AdminCourseServlet extends HttpServlet {
    private AdminCourseService courseService = new AdminCourseService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || "list".equals(action)) {
            String kcbh = req.getParameter("kcbh");
            String kcmc = req.getParameter("kcmc");
            String kkxq = req.getParameter("kkxq");
            List<Kecheng> courseList;
            if ((kcbh != null && !kcbh.isEmpty()) || (kcmc != null && !kcmc.isEmpty()) || (kkxq != null && !kkxq.isEmpty())) {
                courseList = courseService.listCourses(kcbh, kcmc, kkxq);
            } else {
                courseList = courseService.listCourses();
            }
            req.setAttribute("courseList", courseList);
            req.getRequestDispatcher("/admin/course/list.jsp").forward(req, resp);
        } else if ("add".equals(action)) {
            req.getRequestDispatcher("/admin/course/add.jsp").forward(req, resp);
        } else if ("edit".equals(action)) {
            String kcbh = req.getParameter("kcbh");
            if (kcbh != null && !kcbh.isEmpty()) {
                Kecheng course = courseService.getCourseById(Integer.parseInt(kcbh));
                req.setAttribute("course", course);
                req.getRequestDispatcher("/admin/course/edit.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/course");
            }
        } else if ("delete".equals(action)) {
            String kcbh = req.getParameter("kcbh");
            if (kcbh != null && !kcbh.isEmpty()) {
                courseService.deleteCourse(Integer.parseInt(kcbh));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/course");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            Kecheng k = new Kecheng();
            k.setZyc_kcmc(req.getParameter("kcmc"));
            k.setZyc_kkxq(req.getParameter("kkxq"));
            k.setZyc_xs(parseIntOrDefault(req.getParameter("xs"), 0));
            k.setZyc_ksfs(req.getParameter("ksfs"));
            k.setZyc_xf(parseBigDecimalOrDefault(req.getParameter("xf"), null));
            courseService.addCourse(k);
            resp.sendRedirect(req.getContextPath() + "/admin/course");
        } else if ("edit".equals(action)) {
            Kecheng k = new Kecheng();
            k.setZyc_kcbh(parseIntOrDefault(req.getParameter("kcbh"), 0));
            k.setZyc_kcmc(req.getParameter("kcmc"));
            k.setZyc_kkxq(req.getParameter("kkxq"));
            k.setZyc_xs(parseIntOrDefault(req.getParameter("xs"), 0));
            k.setZyc_ksfs(req.getParameter("ksfs"));
            k.setZyc_xf(parseBigDecimalOrDefault(req.getParameter("xf"), null));
            courseService.updateCourse(k);
            resp.sendRedirect(req.getContextPath() + "/admin/course");
        }
    }

    private int parseIntOrDefault(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
    private java.math.BigDecimal parseBigDecimalOrDefault(String s, java.math.BigDecimal def) {
        try { return new java.math.BigDecimal(s); } catch (Exception e) { return def; }
    }
}
