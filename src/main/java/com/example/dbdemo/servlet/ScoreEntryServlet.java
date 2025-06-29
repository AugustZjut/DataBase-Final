package com.example.dbdemo.servlet;

import com.example.dbdemo.service.ChengjiService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/teacher/scoreEntry")
public class ScoreEntryServlet extends HttpServlet {
    private ChengjiService chengjiService = new ChengjiService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String xh = req.getParameter("xh");
        String xq = req.getParameter("xq");
        String jxbbhStr = req.getParameter("jxbbh");
        String cjStr = req.getParameter("cj");
        String message;
        try {
            int jxbbh = Integer.parseInt(jxbbhStr);
            BigDecimal cj = new BigDecimal(cjStr);
            boolean success = chengjiService.updateScore(xh, xq, jxbbh, cj);
            message = success ? "成绩录入成功" : "成绩录入失败";
        } catch (Exception e) {
            message = "参数错误或成绩格式不正确";
        }
        req.setAttribute("message", message);
        req.getRequestDispatcher("/teacher/score_entry.jsp").forward(req, resp);
    }
}
