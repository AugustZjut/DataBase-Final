package com.example.dbdemo.admin;

import com.example.dbdemo.service.AdminStudentService;
import com.example.dbdemo.bean.Xuesheng;
import com.example.dbdemo.dao.DiquDAO;
import com.example.dbdemo.dao.XingzhengbanDAO;
import com.example.dbdemo.bean.Diqu;
import com.example.dbdemo.bean.Xingzhengban;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/student")
public class AdminStudentServlet extends HttpServlet {
    private AdminStudentService studentService = new AdminStudentService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || "list".equals(action)) {
            String xh = req.getParameter("xh");
            String xsxm = req.getParameter("xsxm");
            String bjmc = req.getParameter("bjmc");
            List<Xuesheng> studentList;
            if ((xh != null && !xh.isEmpty()) || (xsxm != null && !xsxm.isEmpty()) || (bjmc != null && !bjmc.isEmpty())) {
                studentList = studentService.listStudents(xh, xsxm, bjmc);
            } else {
                studentList = studentService.listStudents();
            }
            req.setAttribute("studentList", studentList);
            req.getRequestDispatcher("/admin/student/list.jsp").forward(req, resp);
        } else if ("add".equals(action)) {
            try {
                DiquDAO diquDAO = new DiquDAO();
                XingzhengbanDAO banDAO = new XingzhengbanDAO();
                java.sql.Connection conn = com.example.dbdemo.util.DBUtil.getConnection();
                List<Diqu> diquList = diquDAO.findAll(conn);
                List<Xingzhengban> banList = banDAO.findAll(conn);
                com.example.dbdemo.util.DBUtil.close(conn);
                req.setAttribute("diquList", diquList);
                req.setAttribute("banList", banList);
            } catch (Exception e) {
                req.setAttribute("diquList", java.util.Collections.emptyList());
                req.setAttribute("banList", java.util.Collections.emptyList());
            }
            req.getRequestDispatcher("/admin/student/add.jsp").forward(req, resp);
        } else if ("delete".equals(action)) {
            String xh = req.getParameter("xh");
            if (xh != null && !xh.isEmpty()) {
                studentService.deleteStudent(xh);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/student");
            return;
        } else if ("edit".equals(action)) {
            String xh = req.getParameter("xh");
            if (xh != null && !xh.isEmpty()) {
                Xuesheng student = studentService.getStudentByXh(xh);
                try {
                    DiquDAO diquDAO = new DiquDAO();
                    XingzhengbanDAO banDAO = new XingzhengbanDAO();
                    java.sql.Connection conn = com.example.dbdemo.util.DBUtil.getConnection();
                    List<Diqu> diquList = diquDAO.findAll(conn);
                    List<Xingzhengban> banList = banDAO.findAll(conn);
                    com.example.dbdemo.util.DBUtil.close(conn);
                    req.setAttribute("diquList", diquList);
                    req.setAttribute("banList", banList);
                } catch (Exception e) {
                    req.setAttribute("diquList", java.util.Collections.emptyList());
                    req.setAttribute("banList", java.util.Collections.emptyList());
                }
                req.setAttribute("student", student);
                req.getRequestDispatcher("/admin/student/edit.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/admin/student");
            return;
        }
        // 其他操作：add、edit、delete
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            Xuesheng x = new Xuesheng();
            x.setZyc_xh(req.getParameter("xh"));
            x.setZyc_xsxm(req.getParameter("xsxm"));
            x.setZyc_xsxb(req.getParameter("xsxb"));
            try {
                String xscsrq = req.getParameter("xscsrq");
                if (xscsrq != null && !xscsrq.isEmpty()) {
                    x.setZyc_xscsrq(java.sql.Date.valueOf(xscsrq));
                }
            } catch (Exception e) { x.setZyc_xscsrq(null); }
            // 下拉框直接取ID
            try {
                x.setZyc_syd(Integer.parseInt(req.getParameter("syd")));
            } catch (Exception e) { x.setZyc_syd(0); }
            try {
                x.setZyc_bjbh(Integer.parseInt(req.getParameter("bjbh")));
            } catch (Exception e) { x.setZyc_bjbh(0); }
            studentService.addStudent(x);
            resp.sendRedirect(req.getContextPath() + "/admin/student");
        } else if ("edit".equals(action)) {
            Xuesheng x = new Xuesheng();
            x.setZyc_xh(req.getParameter("xh"));
            x.setZyc_xsxm(req.getParameter("xsxm"));
            x.setZyc_xsxb(req.getParameter("xsxb"));
            try {
                String xscsrq = req.getParameter("xscsrq");
                if (xscsrq != null && !xscsrq.isEmpty()) {
                    x.setZyc_xscsrq(java.sql.Date.valueOf(xscsrq));
                }
            } catch (Exception e) { x.setZyc_xscsrq(null); }
            try {
                x.setZyc_syd(Integer.parseInt(req.getParameter("syd")));
            } catch (Exception e) { x.setZyc_syd(0); }
            try {
                x.setZyc_bjbh(Integer.parseInt(req.getParameter("bjbh")));
            } catch (Exception e) { x.setZyc_bjbh(0); }
            studentService.updateStudent(x);
            resp.sendRedirect(req.getContextPath() + "/admin/student");
            return;
        }
        // ...existing code...
    }
}
