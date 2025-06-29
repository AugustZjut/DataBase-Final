package com.example.dbdemo.servlet;

import com.example.dbdemo.bean.Yonghu;
import com.example.dbdemo.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = "/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private final AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Yonghu yonghu = (session != null) ? (Yonghu) session.getAttribute("user") : null;

        if (yonghu != null && "管理员".equals(yonghu.getZyc_qx())) {
            // Get system stats
            Map<String, Integer> stats = adminService.getSystemStats();
            request.setAttribute("stats", stats);

            // Get search filters
            String searchUsername = request.getParameter("searchUsername");
            String searchRole = request.getParameter("searchRole");

            // Get user list
            List<Yonghu> userList = adminService.getUsers(searchUsername, searchRole);
            request.setAttribute("userList", userList);
            request.setAttribute("searchUsername", searchUsername);
            request.setAttribute("searchRole", searchRole);

            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        } else {
            // If user is not an admin or not logged in, redirect to login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
