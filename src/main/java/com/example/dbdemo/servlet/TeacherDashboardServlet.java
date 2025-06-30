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
            Jiaoshi teacherInfo = teacherService.getTeacherInfo(yonghu.getZyc_zh());
            request.setAttribute("teacherInfo", teacherInfo);

            String semester = request.getParameter("semester");
            String courseName = request.getParameter("courseName");

            List<Map<String, Object>> courseList = teacherService.getMyTaughtCourses(yonghu.getZyc_zh(), semester, courseName);
            request.setAttribute("courseList", courseList);
            request.setAttribute("selectedSemester", semester);
            request.setAttribute("selectedCourseName", courseName);

            request.getRequestDispatcher("/teacher/dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
