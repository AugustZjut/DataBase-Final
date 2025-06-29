package com.example.dbdemo.servlet;

import com.example.dbdemo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/changePassword")
public class ChangePasswordServlet extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user/change_password.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String zh = null;
        Object userObj = session.getAttribute("user");
        if (userObj != null && userObj instanceof com.example.dbdemo.bean.Yonghu) {
            zh = ((com.example.dbdemo.bean.Yonghu) userObj).getZyc_zh();
        }
        String oldPwd = req.getParameter("oldPwd");
        String newPwd = req.getParameter("newPwd");
        String confirmPwd = req.getParameter("confirmPwd");
        String msg = null;
        if (zh == null) {
            msg = "未登录，请重新登录！";
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login.jsp?msg=" + java.net.URLEncoder.encode(msg, "UTF-8"));
            return;
        } else if (newPwd == null || !newPwd.equals(confirmPwd)) {
            msg = "两次输入的新密码不一致！";
        } else {
            com.example.dbdemo.bean.Yonghu user = userService.login(zh, oldPwd);
            if (user == null) {
                msg = "原密码错误！";
            } else {
                boolean ok = userService.updatePassword(zh, newPwd);
                if (ok) {
                    session.invalidate();
                    resp.sendRedirect(req.getContextPath() + "/login.jsp?msg=" + java.net.URLEncoder.encode("密码修改成功，请重新登录！", "UTF-8"));
                    return;
                } else {
                    msg = "密码修改失败！";
                }
            }
        }
        req.setAttribute("msg", msg);
        req.getRequestDispatcher("/user/change_password.jsp").forward(req, resp);
    }
}
