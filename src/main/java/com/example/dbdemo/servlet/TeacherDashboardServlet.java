package com.example.dbdemo.servlet;

import com.example.dbdemo.bean.Jiaoshi;
import com.example.dbdemo.bean.Yonghu;
import com.example.dbdemo.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "TeacherDashboardServlet", urlPatterns = "/teacher/dashboard")
public class TeacherDashboardServlet extends HttpServlet {
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Yonghu yonghu = (session != null) ? (Yonghu) session.getAttribute("user") : null;

        if (yonghu != null && "教师".equals(yonghu.getZyc_qx())) {
            // Get teacher's workload info
            Jiaoshi teacherInfo = teacherService.getTeacherInfo(yonghu.getZyc_zh());
            request.setAttribute("teacherInfo", teacherInfo);

            // Get semester from request parameter for filtering
            String semester = request.getParameter("semester");

            // Get teacher's course list
            List<Map<String, Object>> courseList = teacherService.getTaughtCourses(yonghu.getZyc_zh(), semester);
            request.setAttribute("courseList", courseList);
            request.setAttribute("selectedSemester", semester);

            request.getRequestDispatcher("/teacher/dashboard.jsp").forward(request, response);
        } else {
            // If user is not a teacher or not logged in, redirect to login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
