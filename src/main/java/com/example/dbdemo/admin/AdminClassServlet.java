package com.example.dbdemo.admin;

import com.example.dbdemo.service.AdminClassService;
import com.example.dbdemo.bean.Jiaoxueban;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/class")
public class AdminClassServlet extends HttpServlet {
    private AdminClassService classService = new AdminClassService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || "list".equals(action)) {
            String jxbbh = req.getParameter("jxbbh");
            String kcmc = req.getParameter("kcmc");
            String xq = req.getParameter("xq");
            List<Map<String, Object>> classList;
            if ((jxbbh != null && !jxbbh.isEmpty()) || (kcmc != null && !kcmc.isEmpty()) || (xq != null && !xq.isEmpty())) {
                classList = classService.listClasses(jxbbh, kcmc, xq);
            } else {
                classList = classService.listClasses();
            }
            req.setAttribute("classList", classList);
            req.setAttribute("jxbbh", jxbbh);
            req.setAttribute("kcmc", kcmc);
            req.setAttribute("xq", xq);
            req.getRequestDispatcher("/admin/class/list.jsp").forward(req, resp);
        } else if ("add".equals(action)) {
            req.setAttribute("teacherList", classService.getAllTeachers());
            req.getRequestDispatcher("/admin/class/add.jsp").forward(req, resp);
        } else if ("edit".equals(action)) {
            String jxbbh = req.getParameter("jxbbh");
            if (jxbbh != null && !jxbbh.isEmpty()) {
                Jiaoxueban clazz = classService.getClassById(Integer.parseInt(jxbbh));
                req.setAttribute("clazz", clazz);
                req.setAttribute("teacherList", classService.getAllTeachers());
                req.getRequestDispatcher("/admin/class/edit.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/class");
            }
        } else if ("delete".equals(action)) {
            String jxbbh = req.getParameter("jxbbh");
            if (jxbbh != null && !jxbbh.isEmpty()) {
                classService.deleteClass(Integer.parseInt(jxbbh));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/class");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            Jiaoxueban j = new Jiaoxueban();
            j.setZyc_jxbmc(req.getParameter("jxbmc"));
            j.setZyc_kcbh(parseIntOrDefault(req.getParameter("kcbh"), 0));
            j.setZyc_jsbh(req.getParameter("jsbh"));
            j.setZyc_sksj(req.getParameter("sksj"));
            j.setZyc_skdd(req.getParameter("skdd"));
            j.setZyc_xdrs(parseIntOrDefault(req.getParameter("xdrs"), 0));
            classService.addClass(j);
            resp.sendRedirect(req.getContextPath() + "/admin/class");
        } else if ("edit".equals(action)) {
            Jiaoxueban j = new Jiaoxueban();
            j.setZyc_jxbbh(parseIntOrDefault(req.getParameter("jxbbh"), 0));
            j.setZyc_jxbmc(req.getParameter("jxbmc"));
            j.setZyc_kcbh(parseIntOrDefault(req.getParameter("kcbh"), 0));
            j.setZyc_jsbh(req.getParameter("jsbh"));
            j.setZyc_sksj(req.getParameter("sksj"));
            j.setZyc_skdd(req.getParameter("skdd"));
            j.setZyc_xdrs(parseIntOrDefault(req.getParameter("xdrs"), 0));
            classService.updateClass(j);
            resp.sendRedirect(req.getContextPath() + "/admin/class");
        }
    }

    private int parseIntOrDefault(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}
