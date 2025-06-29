package com.example.dbdemo.servlet;

import com.example.dbdemo.service.ScoreEntryQueryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/teacher/scoreEntryPage")
public class ScoreEntryPageServlet extends HttpServlet {
    private ScoreEntryQueryService queryService = new ScoreEntryQueryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jxbbhStr = req.getParameter("jxbbh");
        String xq = req.getParameter("xq");
        List<Map<String, Object>> studentList = null;
        try {
            int jxbbh = Integer.parseInt(jxbbhStr);
            studentList = queryService.getStudentScoreList(jxbbh, xq);
        } catch (Exception e) {
            studentList = java.util.Collections.emptyList();
        }
        req.setAttribute("studentList", studentList);
        req.getRequestDispatcher("/teacher/score_entry.jsp").forward(req, resp);
    }
}
