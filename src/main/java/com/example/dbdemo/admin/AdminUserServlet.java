package com.example.dbdemo.admin;

import com.example.dbdemo.service.AdminUserService;
import com.example.dbdemo.bean.Yonghu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/user")
public class AdminUserServlet extends HttpServlet {
    private AdminUserService userService = new AdminUserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || "list".equals(action)) {
            String zh = req.getParameter("zh");
            String zt = req.getParameter("zt");
            List<Yonghu> userList;
            if ((zh != null && !zh.isEmpty()) || (zt != null && !zt.isEmpty())) {
                userList = userService.listUsers(zh, zt);
            } else {
                userList = userService.listUsers();
            }
            req.setAttribute("userList", userList);
            req.setAttribute("zh", zh);
            req.setAttribute("zt", zt);
            req.getRequestDispatcher("/admin/user/list.jsp").forward(req, resp);
        } else if ("enable".equals(action)) {
            String zh = req.getParameter("zh");
            if (zh != null && !zh.isEmpty()) {
                userService.enableUser(zh);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/user");
        } else if ("disable".equals(action)) {
            String zh = req.getParameter("zh");
            if (zh != null && !zh.isEmpty()) {
                userService.disableUser(zh);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/user");
        } else if ("delete".equals(action)) {
            // ...existing code...
        } else if ("reset".equals(action)) {
            String zh = req.getParameter("zh");
            if (zh != null && !zh.isEmpty()) {
                String defaultPwd = zh.length() > 6 ? zh.substring(zh.length() - 6) : zh;
                userService.resetPassword(zh, defaultPwd);
                req.setAttribute("msg", "密码已重置为账号后六位: " + defaultPwd);
            }
            req.getRequestDispatcher("/admin/user/list.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理新增、编辑、删除等表单提交
    }
}
