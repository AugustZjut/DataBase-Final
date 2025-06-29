package com.example.dbdemo.admin;

import com.example.dbdemo.service.AdminTeacherService;
import com.example.dbdemo.bean.Jiaoshi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/teacher")
public class AdminTeacherServlet extends HttpServlet {
    private AdminTeacherService teacherService = new AdminTeacherService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || "list".equals(action)) {
            String jsbh = req.getParameter("jsbh");
            String jsxmc = req.getParameter("jsxmc");
            String jszc = req.getParameter("jszc");
            List<Jiaoshi> teacherList;
            if ((jsbh != null && !jsbh.isEmpty()) || (jsxmc != null && !jsxmc.isEmpty()) || (jszc != null && !jszc.isEmpty())) {
                teacherList = teacherService.listTeachers(jsbh, jsxmc, jszc);
            } else {
                teacherList = teacherService.listTeachers();
            }
            req.setAttribute("teacherList", teacherList);
            req.getRequestDispatcher("/admin/teacher/list.jsp").forward(req, resp);
        } else if ("add".equals(action)) {
            req.getRequestDispatcher("/admin/teacher/add.jsp").forward(req, resp);
        } else if ("edit".equals(action)) {
            String jsbh = req.getParameter("jsbh");
            if (jsbh != null && !jsbh.isEmpty()) {
                Jiaoshi teacher = teacherService.getTeacherByJsbh(jsbh);
                req.setAttribute("teacher", teacher);
                req.getRequestDispatcher("/admin/teacher/edit.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/admin/teacher");
            return;
        } else if ("delete".equals(action)) {
            String jsbh = req.getParameter("jsbh");
            if (jsbh != null && !jsbh.isEmpty()) {
                teacherService.deleteTeacher(jsbh);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/teacher");
            return;
        }
        // 其他操作：add、edit、delete
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            Jiaoshi j = new Jiaoshi();
            j.setJsbh(req.getParameter("jsbh"));
            j.setJsxmc(req.getParameter("jsxmc"));
            j.setJsxb(req.getParameter("jsxb"));
            j.setJszc(req.getParameter("jszc"));
            j.setJslxdh(req.getParameter("jslxdh"));
            try {
                String jscsrq = req.getParameter("jscsrq");
                if (jscsrq != null && !jscsrq.isEmpty()) {
                    j.setJscsrq(java.sql.Date.valueOf(jscsrq));
                }
            } catch (Exception e) { j.setJscsrq(null); }
            teacherService.addTeacher(j); // 需在Service实现
            resp.sendRedirect(req.getContextPath() + "/admin/teacher");
        } else if ("edit".equals(action)) {
            Jiaoshi j = new Jiaoshi();
            j.setJsbh(req.getParameter("jsbh"));
            j.setJsxmc(req.getParameter("jsxmc"));
            j.setJsxb(req.getParameter("jsxb"));
            j.setJszc(req.getParameter("jszc"));
            j.setJslxdh(req.getParameter("jslxdh"));
            teacherService.updateTeacher(j);
            resp.sendRedirect(req.getContextPath() + "/admin/teacher");
        }
        // 其他表单提交处理
    }
}
