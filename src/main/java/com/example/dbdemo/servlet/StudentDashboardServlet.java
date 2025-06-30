package com.example.dbdemo.servlet;

import com.example.dbdemo.bean.Yonghu;
import com.example.dbdemo.bean.Xuesheng;
import com.example.dbdemo.bean.Chengji;
import com.example.dbdemo.service.StudentService;
import com.example.dbdemo.service.CourseQueryService;
import com.example.dbdemo.bean.Jiaoxueban;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "StudentDashboardServlet", urlPatterns = "/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final CourseQueryService courseQueryService = new CourseQueryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Yonghu yonghu = (session != null) ? (Yonghu) session.getAttribute("user") : null;

        if (yonghu != null && "学生".equals(yonghu.getZyc_qx())) {
            Xuesheng studentInfo = studentService.getStudentInfo(yonghu.getZyc_zh());
            request.setAttribute("studentInfo", studentInfo);

            // 查询平均绩点
            Double avgGPA = studentService.getStudentAvgGPA(yonghu.getZyc_zh());
            request.setAttribute("avgGPA", avgGPA);

            String semester = request.getParameter("semester");
            List<Map<String, Object>> gradeList = studentService.getGradeWithCourseName(yonghu.getZyc_zh(), semester);
            request.setAttribute("gradeList", gradeList);
            request.setAttribute("selectedSemester", semester);

            // 查询下学期所有教学班及选课状态
            List<Map<String, Object>> availableCourseList = courseQueryService.getNextSemesterAllCoursesWithSelectStatus(yonghu.getZyc_zh());
            request.setAttribute("availableCourseList", availableCourseList);
            // 选课结果提示
            String msg = request.getParameter("msg");
            if (msg != null) {
                request.setAttribute("msg", msg);
            }

            request.getRequestDispatcher("/student/dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
