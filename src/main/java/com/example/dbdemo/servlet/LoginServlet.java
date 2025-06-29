package com.example.dbdemo.servlet;

import com.example.dbdemo.bean.Yonghu;
import com.example.dbdemo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to the login page for GET requests
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String zyc_zh = request.getParameter("username");
        String zyc_mm = request.getParameter("password");

        Yonghu yonghu = userService.login(zyc_zh, zyc_mm);

        if (yonghu != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", yonghu);

            // Redirect based on user role
            switch (yonghu.getZyc_qx()) {
                case "学生":
                    response.sendRedirect(request.getContextPath() + "/student/dashboard");
                    break;
                case "教师":
                    response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
                    break;
                case "管理员":
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    break;
                default:
                    // Fallback to login page if role is unknown
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    break;
            }
        } else {
            // If login fails, set an error message and forward back to the login page
            request.setAttribute("error", "账号或密码错误，或账户已被禁用。");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
